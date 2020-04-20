import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":domain:teamplay-database-domain"))
    implementation(project(":core:token-core"))
    implementation(project(":core:function-core"))
    implementation(project(":core:database-core"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}