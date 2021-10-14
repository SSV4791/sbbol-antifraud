import org.springframework.boot.gradle.tasks.bundling.BootJar
import nu.studer.gradle.credentials.domain.CredentialsContainer

plugins {
    `java-library`
    idea
    jacoco
    id("nu.studer.credentials") version "2.1"
    id("org.springframework.boot") version "2.4.4"
    id("org.sonarqube") version "3.2.0"
    id("ru.sbt.meta.meta-gradle-plugin")
}

val credentials: CredentialsContainer by project.extra
val nexusLoginValue = (project.properties["nexusLogin"] ?: credentials.getProperty("nexusLogin")) as String?
val nexusPasswordValue = (project.properties["nexusPassword"] ?: credentials.getProperty("nexusPassword")) as String?

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

    dependencies {
        dependencyLocking {
            lockAllConfigurations()
            lockFile.set(file("${rootDir}/gradle/dependency-locks/gradle-${project.name}.lockfile"))
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
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    }
}

tasks {

    val allTestsCoverageFile = "${rootProject.buildDir}/coverage/jacoco/rootTestsCoverage.exec"
    val allTestsCoverageReportXml = "${rootProject.buildDir}/coverage/jacoco/report/report.xml"
    val allTestsCoverageReportHtml = "${rootProject.buildDir}/coverage/jacoco/report/html"

    // Таска берет .exec файлы по проекту и мержит в один
    val jacocoMergeTest by registering(JacocoMerge::class) {
        dependsOn(subprojects.map { it.tasks.test })
        destinationFile = file(allTestsCoverageFile)
        executionData = project.fileTree(rootDir) {
            include("**/build/jacoco/test.exec")
        }
    }

    // таска на основе общего exec файла генерирует отчет
    val jacocoRootReport by registering(JacocoReport::class) {
        group = "verification"
        dependsOn(jacocoMergeTest)
        reports {
            xml.isEnabled = true
            xml.destination = file(allTestsCoverageReportXml)
            html.isEnabled = true
            html.destination = file(allTestsCoverageReportHtml)
        }
        additionalSourceDirs.setFrom(files(subprojects.map { it.sourceSets["main"].allSource.srcDirs }.flatten()))
        sourceDirectories.setFrom(files(subprojects.map { it.sourceSets["main"].allSource.srcDirs }.flatten()))
        classDirectories.setFrom(files(subprojects.map { it.sourceSets["main"].output }.flatten()))
        executionData.setFrom(files(allTestsCoverageFile))

        //Фильтры для путей, которые не попадут в общий Jacoco отчет.
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                        "ru.sberbank.pprb.sbbol.antifraud.service.entity",
                        "ru.sberbank.pprb.sbbol.antifraud.api"
                )
            }
        }))
    }
}

jacoco {
    toolVersion = "0.8.7"
}

sonarqube {
    properties {
        property("sonar.coverage.jacoco.xmlReportPaths", "${rootProject.projectDir}/build/coverage/jacoco/report/report.xml")
        // исключаем из анализа на duplication все DTO и Entity
        property("sonar.cpd.exclusions", "antifraud-api/src/main/java/ru/sberbank/pprb/sbbol/antifraud/api/**," +
                                         "antifraud-service/src/main/java/ru/sberbank/pprb/sbbol/antifraud/service/entity/**")
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

project.tasks["sonarqube"].dependsOn("jacocoRootReport")

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
