import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
    kotlin("plugin.jpa") version "1.3.50"
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate:hibernate-core:5.4.6.Final")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-config-client:2.2.0.RELEASE")

    implementation(project(":core:database-core"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
