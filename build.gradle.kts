import org.springframework.boot.gradle.tasks.bundling.BootJar
import nu.studer.gradle.credentials.domain.CredentialsContainer
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    `java-library`
    idea
    jacoco
    id("nu.studer.credentials") version "2.1"
    id("org.springframework.boot") version "2.4.4"
    id("org.sonarqube") version "3.2.0"
    id("ru.sbt.meta.meta-gradle-plugin")
    id("ru.sbrf.build.gradle.qa.reporter") version "3.0.4"
    id("io.qameta.allure") version "2.8.1"
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

idea {
    module {
        outputDir = file("${projectDir}/build/classes/java/main")
        testOutputDir = file("${projectDir}/build/classes/java/test")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
    targetCompatibility = JavaVersion.VERSION_15
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    dependencies {
        implementation(platform(SpringBootPlugin.BOM_COORDINATES))

        testImplementation("ru.dcbqa.allureee.annotations:dcb-allure-annotations:1.+")
        testImplementation("io.qameta.allure:allure-junit5:2.13.5")

        testImplementation(group = "au.com.dius.pact.consumer", name = "junit5", version = "${property("pactVersion")}")
        testImplementation(group = "au.com.dius.pact.provider", name = "junit5", version = "${property("pactVersion")}")
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

sonarqube {
    properties {
        property("sonar.coverage.jacoco.xmlReportPaths", "${rootProject.buildDir}/coverage/jacoco/jacocoTestReport.xml")
        // исключаем из анализа на duplication все DTO и Entity
        property("sonar.cpd.exclusions", coverageExclusions)
        property("sonar.coverage.exclusions", coverageExclusions)
    }
}

meta {
    nexusUrl = null
    nexusUser = nexusLoginValue
    nexusPassword = nexusPasswordValue
    componentId = "8255f180-74c2-11eb-6742-005056b72594"
    ext {
        set("url", "https://meta.sigma.sbrf.ru")
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