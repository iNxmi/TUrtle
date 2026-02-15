plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("kapt") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("plugin.jpa") version "2.3.0"

    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.ben-manes.versions") version "0.53.0"
}

group = "de.csw"
version = "0.0.1-SNAPSHOT"
description = "TUrtleAPI"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jackson")
    implementation("io.github.perplexhub:rsql-jpa-spring-boot-starter:6.0.33")
    implementation("org.springframework.session:spring-session-jdbc")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
    implementation("com.hierynomus:sshj:0.40.0")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    //Fake Data Generator
    implementation("io.github.serpro69:kotlin-faker:1.16.0")

    //Rate Limiting
    implementation("com.bucket4j:bucket4j_jdk17-core:8.16.1")

    //Caching
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.3")

    //Captcha
    implementation("org.altcha:altcha:1.3.0")

    runtimeOnly("org.postgresql:postgresql:42.7.10")

    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.mockk:mockk:1.14.9")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

kapt {
    correctErrorTypes = true
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
