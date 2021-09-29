import nu.studer.gradle.credentials.domain.CredentialsContainer

plugins {
    `java-base`
    id("nu.studer.credentials") version "2.1"
}

val credentials: CredentialsContainer by project.extra
val nexusLoginValue = (project.properties["nexusLogin"] ?: credentials.getProperty("nexusLogin")) as String?
val nexusPasswordValue = (project.properties["nexusPassword"] ?: credentials.getProperty("nexusPassword")) as String?

repositories {
    maven {
        url = uri("http://sbtatlas.sigma.sbrf.ru/nexus/content/groups/public/")
        isAllowInsecureProtocol = true
    }

    maven {
        url = uri("http://sbtatlas.sigma.sbrf.ru/nexus/content/groups/internal/")
        isAllowInsecureProtocol = true
        credentials {
            username = nexusLoginValue
            password = nexusPasswordValue
        }
    }
}

dependencies {
    implementation("ru.sbt.meta:meta-gradle-plugin:1.4.0")
}