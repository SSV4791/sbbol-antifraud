import groovy.lang.Tuple
import org.springframework.boot.gradle.tasks.bundling.BootJar
import nu.studer.gradle.credentials.domain.CredentialsContainer
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `java-library`
    idea
    jacoco
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("nu.studer.credentials") version "2.1"
    id("org.sonarqube") version "3.2.0"
    id("ru.sbt.meta.meta-gradle-plugin")
    id("ru.sbrf.build.gradle.qa.reporter") version "3.0.4"
    id("io.qameta.allure") version "2.8.1"
    `maven-publish`
}

val credentials: CredentialsContainer by project.extra
val nexusLoginValue = (project.properties["nexusLogin"] ?: credentials.getProperty("nexusLogin")) as String?
val nexusPasswordValue = (project.properties["nexusPassword"] ?: credentials.getProperty("nexusPassword")) as String?

val coverageExclusions = listOf(
    "antifraud-api/src/main/java/ru/sberbank/pprb/sbbol/antifraud/api/**",
    "antifraud-service/src/main/java/ru/sberbank/pprb/sbbol/antifraud/service/entity/**"
)

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "jacoco")
    apply(plugin = "nu.studer.credentials")

    repositories {
        repositories {

            maven {
                val publicRepositoryUrl: String by project
                url = uri(publicRepositoryUrl)
                isAllowInsecureProtocol = true
            }

            maven {
                url = uri("https://nexus.sigma.sbrf.ru/nexus/content/groups/internal")
                credentials {
                    username = nexusLoginValue
                    password = nexusPasswordValue
                }
                isAllowInsecureProtocol = true
            }
        }
    }

    val pactVersion: String by rootProject

    dependencies {
        dependencyLocking {
            lockAllConfigurations()
            lockFile.set(file("${rootDir}/gradle/dependency-locks/gradle-${parent?.let { "${it.name}-" } ?: ""}${project.name}.lockfile"))
        }
    }

    tasks.register("resolveAndLockAll") {
        doFirst {
            require(gradle.startParameter.isWriteDependencyLocks)
        }
        doLast {
            configurations.filter {
                it.isCanBeResolved
            }.forEach { it.resolve() }
        }
    }
}

allure {
    autoconfigure = true
    version = "2.14.0"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }
    systemProperty("file.encoding", "UTF-8")
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
    targetCompatibility = JavaVersion.VERSION_15
}

idea {
    module {
        outputDir = file("${projectDir}/build/classes/java/main")
        testOutputDir = file("${projectDir}/build/classes/java/test")
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

        testImplementation("ru.dcbqa.allureee.annotations:dcb-allure-annotations:1.+")
        testImplementation("io.qameta.allure:allure-junit5:2.13.5")

        testImplementation(group = "au.com.dius.pact.consumer", name = "junit5", version = "${property("pactVersion")}")
        testImplementation(group = "au.com.dius.pact.provider", name = "junit5", version = "${property("pactVersion")}")
    }
}

tasks {
    val coverageFile = "${rootProject.buildDir}/jacoco/test.exec"
    val coverageReportXml = "${rootProject.buildDir}/jacoco/report/report.xml"
    val coverageReportHtml = "${rootProject.buildDir}/jacoco/report/html"

    // таска на основе общего exec файла генерирует отчет
    val jacocoRootReport by registering(JacocoReport::class) {
        group = "verification"
        reports {
            xml.required.set(true)
            xml.outputLocation.set(file(coverageReportXml))
            html.required.set(true)
            html.outputLocation.set(file(coverageReportHtml))
        }
        sourceDirectories.setFrom(sourceSets["main"].allSource.srcDirs)
        classDirectories.setFrom(sourceSets["main"].output)
        executionData.setFrom(files(coverageFile))
    }

    val sonarqube by getting {
        dependsOn(jacocoRootReport)
    }
}

// отключаем генерацию *-plain.jar
val jar: Task by tasks.getting {
    enabled = false
}

tasks.register<Zip>("fullDistrib") {
    destinationDirectory.set(file("${rootProject.buildDir}"))
    archiveFileName.set("${rootProject.name}.zip")
    from("${rootDir}/openshift/") {
        into("openshift")
        exclude("Deployment*")
    }
    from("${rootDir}/openshift/") {
        into("openshift")
        include("Deployment*")
        filter { line: String -> line.replace("{app_docker_image}", project.properties["dockerImage"] as String) }
    }
    from("${rootDir}/docs/build/docs") {
        into("docs")
    }
    from("${rootDir}/src/main/resources/db/changelog/") {
        into("liquibase")
    }
}

tasks.register("snapshot") {
    group = "publishing"
    description = "Publish snapshot distributions to nexus CI snapshot repository"
    dependsOn(tasks.withType<PublishToMavenRepository>().matching {
        it.repository == publishing.repositories["snapshot"] && it.publication == publishing.publications["snapshot"]
    })
}

tasks.register("dev") {
    group = "publishing"
    description = "Publish develop distributions to nexus CI release repository"
    dependsOn(tasks.withType<PublishToMavenRepository>().matching {
        it.repository == publishing.repositories["dev"] && it.publication == publishing.publications["dev"]
    })
}
tasks.register("release") {
    group = "publishing"
    description = "Publish release distributions to nexus CDP release repository"
    dependsOn(tasks.withType<PublishToMavenRepository>().matching {
        it.repository == publishing.repositories["release"] && it.publication == publishing.publications["release"]
    })
}

publishing {
    publications {
        listOf<Tuple<String>>(
            Tuple("api", "antifraud-api", "antifraud-api/build/libs/antifraud-api-${version}.jar"),
            Tuple("rpc-api", "antifraud-rpc-api", "antifraud-rpc-api/build/libs/antifraud-rpc-api-${version}.jar")
        ).forEach {
            create<MavenPublication>(it.first()) {
                groupId = "ru.sberbank.pprb.sbbol.antifraud"
                artifactId = it[1]
                artifact(layout.projectDirectory.file(it.last()).asFile)
            }
        }
        listOf<Tuple<String>>(
            Tuple("snapshot", "ru.sberbank.pprb.sbbol.antifraud"),
            Tuple("dev", "ru.sberbank.pprb.sbbol.antifraud"),
            Tuple("release", "Nexus_PROD.CI03045533_sbbol-antifraud")
        ).forEach {
            create<MavenPublication>(it.first()) {
                groupId = it.last()
                artifactId = "pprb4-digital-fraud"
                artifact(tasks["fullDistrib"]) {
                    classifier = "distrib.configs"
                }
            }
        }
    }

    repositories {
        listOf<Tuple<String>>(
            Tuple("snapshot", "https://nexus.sigma.sbrf.ru/nexus/content/repositories/SBT_CI_distr_repo-snapshot"),
            Tuple("dev", "https://nexus.sigma.sbrf.ru/nexus/content/repositories/SBT_CI_distr_repo"),
            Tuple("release", "https://sbrf-nexus.sigma.sbrf.ru/nexus/content/repositories/Nexus_PROD")
        ).forEach {
            maven {
                name = it.first()
                url = uri(it.last())
                isAllowInsecureProtocol = true
                credentials {
                    username = nexusLoginValue
                    password = nexusPasswordValue
                }
            }
        }
    }
}

tasks {

    portalUpload {
        /**
         * Добавляем фильтры для классов или целых пакетов, которые не должны учитываться в покрытии Jacoco
         */
        jacocoExcludes.addAll(coverageExclusions)
    }
}

jacoco {
    toolVersion = "0.8.7"
}

meta {
    nexusUrl = null
    nexusUser = nexusLoginValue
    nexusPassword = nexusPasswordValue
    componentId = "8255f180-74c2-11eb-6742-005056b72594"
    ext {
        set("url", "https://meta.sigma.sbrf.ru")
        set("analyzeJava", false)
        set("failBuildOnError", true)
    }
}

sonarqube {
    properties {
        property("sonar.coverage.jacoco.xmlReportPaths", "${rootProject.buildDir}/coverage/jacoco/jacocoTestReport.xml")
        // исключаем из анализа на duplication все DTO и Entity
        property("sonar.cpd.exclusions", coverageExclusions)
        property("sonar.coverage.exclusions", coverageExclusions)
    }
}

project.tasks["sonarqube"].dependsOn("portalUpload")

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

qaPortal {
    projectKey.set("sbbol-antifraud")
}