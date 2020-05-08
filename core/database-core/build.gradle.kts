import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("net.logstash.logback:logstash-logback-encoder:4.8")
    implementation("ch.qos.logback:logback-classic:1.1.6")
    implementation("ch.qos.logback:logback-core:1.1.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
