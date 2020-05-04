import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
    kotlin("plugin.jpa") version "1.3.50"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate:hibernate-core:5.4.6.Final")

    implementation(project(":core:database-core"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}