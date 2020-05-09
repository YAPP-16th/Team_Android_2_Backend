import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-config-client:2.2.0.RELEASE")
    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("net.logstash.logback:logstash-logback-encoder:4.8")
    implementation("ch.qos.logback:logback-classic:1.1.6")
    implementation("ch.qos.logback:logback-core:1.1.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
