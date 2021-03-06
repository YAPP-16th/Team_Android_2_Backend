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
    implementation(project(":core:function-core"))
    implementation(project(":domain:teamplay-database-domain"))

    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("net.logstash.logback:logstash-logback-encoder:4.8")
    implementation("ch.qos.logback:logback-classic:1.1.6")
    implementation("ch.qos.logback:logback-core:1.1.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
