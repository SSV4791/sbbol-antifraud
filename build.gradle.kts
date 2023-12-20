import org.springframework.boot.gradle.tasks.bundling.BootJar
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;

plugins {
    `java-library`
    idea
    jacoco
    id("org.springframework.boot") version "2.5.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.sonarqube") version "3.2.0"
    id("ru.sbt.meta.meta-gradle-plugin")
    id("ru.sbrf.build.gradle.qa.reporter") version "3.3.4"
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

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
                    url = uri(project.properties["libRepositoryUrl"]!!)
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
    from("${rootDir}/gradle") {
        include("dependency-locks/*")
        into("locks")
    }
    from("${rootDir}/gradle/wrapper") {
        include("gradle-wrapper.properties")
        into("locks")
    }
    from("${rootDir}/vectors/") {
        into("vectors")
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
        "**/*Config*",
        "**/ru/sberbank/pprb/sbbol/antifraud/service/validator/**"
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
            **/ru/sberbank/pprb/sbbol/antifraud/config/**,
            "**/ru/sberbank/pprb/sbbol/antifraud/service/validator/**"
        """.trimIndent()
        )
        property(
                "sonar.cpd.exclusions", """
            antifraud-api/src/main/java/ru/sberbank/pprb/sbbol/antifraud/api/**, 
            antifraud-service/src/main/java/ru/sberbank/pprb/sbbol/antifraud/service/entity/**,
            "**/ru/sberbank/pprb/sbbol/antifraud/service/validator/**"
        """.trimIndent()
        )
    }
}

project.tasks["sonarqube"].dependsOn("qaReporterUpload")

qaReporter {
    projectKey.set("sbbol-antifraud")
}

tasks.register("newpatch") {
    description = "Создание sql патча"
    var currentVersion = ""

    doFirst {
        if (!project.hasProperty("patchname")) {
            throw IllegalArgumentException("Property patchname not provided")
        }
        val patchname = project.property("patchname")
        currentVersion = if (project.hasProperty("releaseversion")) {
            project.property("releaseversion").toString()
        } else {
            try {
                //От текущей версии вида major.manor.hotfix_build откидываем последнюю группу с build
                //например от 01.000.00_123-SNAPSHOT получаем версию вида 01.000.00
                //для минора например от 01.000.00_123-SNAPSHOT получаем версию вида 01.000.00 и т.д.
                project.property("version").toString().split("\\_")[0]
            } catch (e: Exception) {
                throw GradleException("Unable to parse version parameter value $version from gradle.properties", e)
            }
        }
        val path = "${projectDir}/antifraud-application/src/main/resources/db/changelog"
        val patchDirectory = "sql/${currentVersion}"
        val patchPath = "${path}/${patchDirectory}"

        val folder = File(patchPath)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val patchTs = System.currentTimeMillis()

        val updateFileName = "${patchTs}_${patchname}.sql"
        val updateFilePath = "${patchPath}/${updateFileName}"

        val patchText = "-- liquibase formatted sql"
        File(updateFilePath).writeText(patchText, Charsets.UTF_8)
        println("File ${updateFileName} was created")

        val changelog = "changelog.yaml"
        val changelogFilePath = "${patchPath}/${changelog}"
        val mainChangeLogFilePath = "${path}/${changelog}"
        val file = File(changelogFilePath)
        var changelogText =
                """  - include:
      file: ${updateFileName}
      relativeToChangelogFile: true
"""
        if (file.length().toInt() == 0) {
            var mainChangelogText =
                    """  - include:
      file: ${patchDirectory}/${changelog}
      relativeToChangelogFile: true
"""
            val mainChangeLogFile = File(mainChangeLogFilePath)
            if (mainChangeLogFile.length().toInt() == 0) {
                mainChangeLogFile.appendText("databaseChangeLog:\n${mainChangelogText}", Charsets.UTF_8)
            } else {
                if (!mainChangeLogFile.readText(Charsets.UTF_8).endsWith("\n")) {
                    mainChangelogText = "\n" + mainChangelogText
                }
                mainChangeLogFile.appendText(mainChangelogText, Charsets.UTF_8)
            }
            file.appendText("databaseChangeLog:\n${changelogText}", Charsets.UTF_8)
        } else {
            if (!file.readText(Charsets.UTF_8).endsWith("\n")) {
                changelogText = "\n" + changelogText
            }
            file.appendText(changelogText, Charsets.UTF_8)
        }
    }
}
