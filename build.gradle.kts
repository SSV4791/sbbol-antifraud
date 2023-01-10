import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;

plugins {
    `java-library`
    idea
    jacoco
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.sonarqube") version "3.2.0"
    id("ru.sbt.meta.meta-gradle-plugin")
    id("ru.sbrf.build.gradle.qa.reporter") version "3.2.+"
    id("io.qameta.allure") version "2.8.1"
    `maven-publish`
}

//нужны для meta
val nexusLogin = project.properties["nexusLogin"] as String?
val nexusPassword = project.properties["nexusPassword"] as String?

val tokenName = project.properties["tokenName"] as String?
val tokenPassword = project.properties["tokenPassword"] as String?

val allureVersion: String by rootProject

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "jacoco")

    repositories {
        repositories {

            maven {
                url = uri(project.properties["publicRepositoryUrl"]!!)
                credentials {
                    username = tokenName
                    password = tokenPassword
                }
                isAllowInsecureProtocol = true
            }

            maven {
                url = uri(project.properties["proxyRepositoryUrl"]!!)
                credentials {
                    username = tokenName
                    password = tokenPassword
                }
                isAllowInsecureProtocol = true
            }
        }
    }

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
    version = allureVersion
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

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    if (project.properties["publishApi"]!! == "true" && setOf("antifraud-api", "antifraud-rpc-api").contains(project.name)) {
        apply(plugin = "maven-publish")
        publishing {
            publications {
                if (setOf("antifraud-api", "antifraud-rpc-api").contains(project.name)) {
                    create<MavenPublication>("api") {
                        from(components["java"])
                        groupId = "ru.sberbank.pprb.antifraud"
                        artifactId = project.name
                    }
                }
            }

            repositories {
                maven {
                    name = "api"
                    url = uri(project.properties["libReleaseRepositoryUrl"]!!)
                    isAllowInsecureProtocol = true
                    credentials {
                        username = tokenName
                        password = tokenPassword
                    }
                }
            }
        }
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
    from("${rootDir}/antifraud-application/src/main/resources/db/changelog/") {
        into("liquibase")
    }
}

publishing {
    publications {
        create<MavenPublication>("publish") {
            groupId = "${project.properties["groupId"]}"
            artifactId = "${project.properties["artifactId"]}"
            artifact(tasks["fullDistrib"]) {
                classifier = "distrib"
            }
        }
    }

    repositories {
        maven {
            name = "publish"
            url = uri(project.properties["repo"]!!)
            isAllowInsecureProtocol = true
            credentials {
                username = tokenName
                password = tokenPassword
            }
        }
    }
}

val coverageExclusions = listOf(
        // Классы с конфигурациями
        "antifraud-application/src/main/java/ru/sberbank/pprb/sbbol/antifraud/AntiFraudRunner.java",
        "**/ru/sberbank/pprb/sbbol/antifraud/config/**",
        "**/ru/sberbank/pprb/sbbol/antifraud/logging/**",
        //POJO
        "**/ru/sberbank/pprb/sbbol/antifraud/api/**",
        //Классы с контроллерами и вызовами сервисов без логики, в которых происходит только вызов соответствующего сервиса
        "**/ru/sberbank/pprb/sbbol/antifraud/rpc/**",
        "**/ru/sberbank/pprb/sbbol/antifraud/service/rpc/**",
        //Классы с exception
        "**/exception/**",
        //Инфраструктура
        "**/*Aspect*",
        "**/*Config*"
)

tasks {
    register("sonarCoverage", DefaultTask::class) {
        group = "verification"
        dependsOn(jacocoTestReport)
        finalizedBy(sonarqube)
    }

    qaReporterUpload {
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
    nexusUser = nexusLogin
    nexusPassword = this@Build_gradle.nexusPassword
    componentId = "8255f180-74c2-11eb-6742-005056b72594"
    ext {
        set("url", "https://meta.sigma.sbrf.ru")
        set("analyzeJava", false)
        set("failBuildOnError", true)
    }
}

sonarqube {
    properties {
        property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${rootProject.projectDir}/build/coverage/jacoco/jacocoTestReport.xml"
        )
        property(
                "sonar.coverage.exclusions", """
            **/ru/sberbank/pprb/sbbol/antifraud/service/entity/**,
            **/ru/sberbank/pprb/sbbol/antifraud/api/**,
            **/ru/sberbank/pprb/sbbol/antifraud/service/aspect/logging/**,
            **/ru/sberbank/pprb/sbbol/antifraud/logging/**,
            **/ru/sberbank/pprb/sbbol/antifraud/config/**
        """.trimIndent()
        )
        property(
                "sonar.cpd.exclusions", """
            antifraud-api/src/main/java/ru/sberbank/pprb/sbbol/antifraud/api/**, 
            antifraud-service/src/main/java/ru/sberbank/pprb/sbbol/antifraud/service/entity/**
        """.trimIndent()
        )
    }
}

project.tasks["sonarqube"].dependsOn("qaReporterUpload")

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

qaReporter {
    projectKey.set("sbbol-antifraud")
}
