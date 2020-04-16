import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.3.50"
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencies {
    implementation(project(":domain:teamplay-business-domain"))
    implementation(project(":domain:teamplay-database-domain"))
    implementation(project(":domain:teamplay-database-jpa-domain"))
    implementation(project(":core:token-core"))
    implementation(project(":core:function-core"))
    implementation(project(":core:database-core"))

    implementation("io.springfox:springfox-swagger2:2.6.1")
    implementation("io.springfox:springfox-swagger-ui:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}