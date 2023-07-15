plugins { `library-conventions` }

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.bundles.logging)

    integrationTestImplementation(libs.assertj.core)
    integrationTestImplementation(libs.testcontainers)
    integrationTestImplementation(libs.testcontainers.junit)
}
