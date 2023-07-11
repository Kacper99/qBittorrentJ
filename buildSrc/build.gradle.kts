plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.spotless)
    implementation(libs.spring.dependency.management.plugin)
    implementation(libs.spring.boot.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}