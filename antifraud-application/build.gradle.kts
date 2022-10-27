apply(plugin = "org.springframework.boot")

val pactVersion: String by rootProject
val allureVersion: String by rootProject
val junitVersion: String by rootProject
val junitPlatformVersion: String by rootProject

dependencies {
    implementation(project(":antifraud-api"))
    implementation(project(":antifraud-service"))

    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    // решает ошибку при старте приложения: java.lang.ClassNotFoundException: javax.jws.WebParam
    implementation("com.sun.xml.ws:jaxws-rt:2.3.3") {
        // решает ошибку: Could not find ha-api-3.1.12.hk2-jar (org.glassfish.ha:ha-api:3.1.12)
        exclude("org.glassfish.ha", "ha-api")
    }
    implementation("org.glassfish.ha:ha-api:3.1.12@jar")
    implementation("net.logstash.logback:logstash-logback-encoder:6.3")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-orm:5.3.5")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("sbp.integration.orm:sbp-hibernate-standin:4.1.14")
    implementation("ru.sbrf.journal:standin-client-cloud:4.0.27")
    implementation("org.liquibase:liquibase-core")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.postgresql:postgresql:42.2.19")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit:junit")
        exclude(group= "com.vaadin.external.google", module="android-json")
        exclude("com.h2database", "h2")
    }

    testImplementation("org.testcontainers:postgresql:1.17.3")

    // заглушка для тестирования репликации между БД
    testImplementation("sbp.integration.orm:orm-tests-common:4.1.14") {
        exclude("com.vaadin.external.google", "android-json")
        exclude("com.h2database", "h2")
    }

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

description = "antifraud-application"
