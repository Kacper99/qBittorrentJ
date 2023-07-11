plugins {
    kotlin("jvm")
    id("versioning")
    id("spotless")
    id("testing")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(libs.kotest)
}