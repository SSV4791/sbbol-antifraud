val pactVersion: String by rootProject
val allureVersion: String by rootProject
val junitVersion: String by rootProject
val junitPlatformVersion: String by rootProject
val standinPluginVersion: String by project

dependencies {
    implementation(project(":antifraud-api"))
    implementation(project(":antifraud-rpc-api"))

    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-jcache:5.4.29.Final")
    implementation("sbp.integration.orm:sbp-hibernate-standin:$standinPluginVersion")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    implementation("io.micrometer:micrometer-core:1.6.5")
    implementation("io.micrometer:micrometer-registry-jmx:1.6.5")
    implementation("io.micrometer:micrometer-registry-prometheus:1.6.5")
    implementation("org.aspectj:aspectjrt:1.9.6")

    runtimeOnly("org.ehcache:ehcache:3.9.2")
    runtimeOnly("org.aspectj:aspectjweaver:1.9.5")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    testImplementation("uk.co.jemos.podam:podam:7.2.6.RELEASE")
    // Обход бага с локами в gradle
    testImplementation("org.hibernate:hibernate-core")

    testImplementation(group = "ru.dcbqa.allureee.annotations", name = "dcb-allure-annotations", version = "1.3.+")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = junitVersion)

    testImplementation(group = "org.junit.platform", name = "junit-platform-commons", version = junitPlatformVersion)
    testImplementation(group = "org.junit.platform", name = "junit-platform-engine", version = junitPlatformVersion)
    testImplementation(group = "org.junit.platform", name = "junit-platform-launcher", version = junitPlatformVersion)

    testImplementation(group = "io.qameta.allure", name = "allure-junit5", version = allureVersion)
    testImplementation(group = "io.qameta.allure", name = "allure-spring-web", version = allureVersion)

    testImplementation(group = "au.com.dius.pact.consumer", name = "junit5", version = pactVersion)
    testImplementation(group = "au.com.dius.pact.consumer", name = "java8", version = pactVersion)

    testImplementation(group = "au.com.dius.pact.provider", name = "spring", version = pactVersion)
    testImplementation(group = "au.com.dius.pact.provider", name = "junit5", version = pactVersion)
    testImplementation(group = "au.com.dius.pact.provider", name = "junit5spring", version = pactVersion)
}

tasks.test {
    useJUnitPlatform()
    systemProperty ("allure.results.directory", "${rootProject.buildDir}/allure-results")
    testLogging {
        events("passed", "skipped", "failed")
    }
}

description = "antifraud-service"
