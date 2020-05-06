import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation ("io.jsonwebtoken:jjwt-api:0.10.5")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-config-client:2.2.0.RELEASE")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.10.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.10.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}