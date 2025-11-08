plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "org.team_leg3nd"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.hibernate.validator:hibernate-validator:6.2.0.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    //logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    to {
        image = "your-registry-host/your-project-name/your-image-name"
        tags = setOf("latest")
        auth {
            username = System.getenv("REGISTRY_ID") ?: ""
            password = System.getenv("REGISTRY_PASSWORD") ?: ""
        }
    }
}