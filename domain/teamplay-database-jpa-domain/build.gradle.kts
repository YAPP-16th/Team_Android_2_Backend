import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.2.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.2.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.2.0.RELEASE")

    runtimeOnly("mysql:mysql-connector-java:8.0.18")

    implementation(project(":core:database-core"))
    implementation(project(":domain:teamplay-database-domain"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}