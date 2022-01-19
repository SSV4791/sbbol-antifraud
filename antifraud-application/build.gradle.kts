apply(plugin = "org.springframework.boot")

val pactVersion: String by rootProject

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

    runtimeOnly("com.h2database:h2:1.4.200")
    runtimeOnly("org.postgresql:postgresql:42.2.19")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit:junit")
        exclude(group= "com.vaadin.external.google", module="android-json")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    // заглушка для тестирования репликации между БД
    testImplementation("sbp.integration.orm:orm-tests-common:4.1.14")

    testImplementation("ru.dcbqa.allureee.annotations:dcb-allure-annotations:1.2.+")
    testImplementation("io.qameta.allure:allure-junit5:2.16.1")

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
