plugins { `library-conventions` }

dependencies {
    implementation(libs.okhttp)

    integrationTestImplementation(libs.assertj.core)
    integrationTestImplementation(libs.testcontainers)
    integrationTestImplementation(libs.testcontainers.junit)
}
