plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // This allows your code to run
    implementation(kotlin("stdlib"))
}

kotlin {
    // This forces the project to use Java 21
    jvmToolchain(21)
}