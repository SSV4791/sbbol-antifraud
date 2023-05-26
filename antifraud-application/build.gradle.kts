apply(plugin = "org.springframework.boot")

val pactVersion: String by rootProject
val allureVersion: String by rootProject
val junitVersion: String by rootProject
val junitPlatformVersion: String by rootProject
val standinPluginVersion: String by project

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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-orm:5.3.5")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("sbp.integration.orm:sbp-hibernate-standin:$standinPluginVersion")
    implementation("org.liquibase:liquibase-core")
    // библиотека для автоматизации health-check
    // https://confluence.sberbank.ru/display/RND/Healthcheck+in+microservices
    implementation("ru.sbrf.sbbol.starters:http-healthcheck-starter:4.1")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.postgresql:postgresql:42.2.19")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit:junit")
        exclude(group= "com.vaadin.external.google", module="android-json")
        exclude("com.h2database", "h2")
    }

    testImplementation("org.testcontainers:postgresql:1.17.3")

    // заглушка для тестирования репликации между БД
    testImplementation("sbp.integration.orm:orm-tests-common:$standinPluginVersion") {
        exclude("com.vaadin.external.google", "android-json")
        exclude("com.h2database", "h2")
    }

    // генерация случайно заполненных pojo
    testImplementation("org.jeasy:easy-random-core:4.3.0")
    testImplementation("uk.co.jemos.podam:podam:7.2.9.RELEASE")

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

tasks.register<Test>("generateVectorTest") {
    useJUnitPlatform()
    filter { includeTestsMatching("**changevector.generate.*") }
}
tasks.register<Test>("applyVectorTest") {
    useJUnitPlatform()
    filter { includeTestsMatching("**changevector.apply.*") }
}

tasks.test {
    useJUnitPlatform()
    exclude("**/changevector/**")
    systemProperty ("allure.results.directory", "${rootProject.buildDir}/allure-results")
    systemProperty("file.encoding", "UTF-8")
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// отключаем генерацию *-plain.jar
val jar: Task by tasks.getting {
    enabled = false
}

description = "antifraud-application"
