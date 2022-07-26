/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Used for diffing sets of versioned Remote Config parameters
    implementation("io.github.java-diff-utils:java-diff-utils:4.11")

    // Provides Cloud Function apis
    implementation("com.google.cloud.functions:functions-framework-api:1.0.1")

    // Enable us to send notifications to our desired Slack channel
    implementation("com.slack.api:slack-api-client:1.21.1")

    // Allow us to work directly with Firebase to query/update Remote Config values
    implementation("com.google.firebase:firebase-admin:8.1.0")
}