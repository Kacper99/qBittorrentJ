plugins { `library-conventions` }

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.bundles.logging)

    testImplementation(libs.wiremock)

    integrationTestImplementation(libs.assertj.core)
    integrationTestImplementation(libs.testcontainers)
    integrationTestImplementation(libs.testcontainers.junit)
}
