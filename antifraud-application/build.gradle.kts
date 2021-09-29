apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":antifraud-api"))
    implementation(project(":antifraud-service"))

    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    implementation("com.sun.xml.ws:jaxws-rt:2.3.3")
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

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    // заглушка для тестирования репликации между БД
    testImplementation("sbp.integration.orm:orm-tests-common:4.1.14")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

description = "antifraud-application"
