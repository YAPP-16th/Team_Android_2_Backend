import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	base
	kotlin("jvm") version "1.3.50" apply false
}

allprojects {
	group = "com.teamplay"
	version = "0.0.1-SNAPSHOT"
	repositories {
		mavenCentral()
	}
}

dependencies {
	subprojects.forEach{
		archives(it)
	}
}