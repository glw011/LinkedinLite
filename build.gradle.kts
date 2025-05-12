import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.dokka") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.components.resources)
    implementation("org.jetbrains.exposed:exposed-core:0.60.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.60.0")
    implementation("org.jetbrains.exposed:exposed-crypt:0.60.0")
    implementation(compose.materialIconsExtended)
    implementation(project(":backend"))
    implementation("junit:junit:4.12")
    testImplementation("junit:junit:4.13.2")
}

compose.desktop {
    application {
        mainClass = "MainKt" // Specify the full package path to MainKt

        jvmArgs += "-Dsun.java2d.opengl=true"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "LinkedInLiteUI"
            packageVersion = "1.0.0"
        }
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
}