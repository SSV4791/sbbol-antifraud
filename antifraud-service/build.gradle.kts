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
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

description = "antifraud-service"
