val pactVersion: String by rootProject

dependencies {
    implementation(project(":antifraud-api"))
    implementation(project(":antifraud-rpc-api"))

    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-jcache:5.4.29.Final")
    implementation("sbp.integration.orm:sbp-hibernate-standin:4.1.14")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    implementation("io.micrometer:micrometer-core:1.6.5")
    implementation("io.micrometer:micrometer-registry-jmx:1.6.5")
    implementation("io.micrometer:micrometer-registry-prometheus:1.6.5")
    implementation("org.aspectj:aspectjrt:1.9.6")

    runtimeOnly("org.ehcache:ehcache:3.9.2")
    runtimeOnly("org.aspectj:aspectjweaver:1.9.5")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("uk.co.jemos.podam:podam:7.2.6.RELEASE")
    // Обход бага с локами в gradle
    testImplementation("org.hibernate:hibernate-core")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

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

description = "antifraud-service"
