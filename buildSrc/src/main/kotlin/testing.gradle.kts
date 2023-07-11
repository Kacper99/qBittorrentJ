plugins {
    java
    `jvm-test-suite`
    jacoco
}

testing {
    suites {
        configureEach {
            if (this is JvmTestSuite) {
                useJUnitJupiter()
                targets {
                    all {
                        testTask.configure{
                            finalizedBy(tasks.jacocoTestReport)
                        }
                    }
                }
            }
        }

        register<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project())
                implementation(libs.kotest)
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(testing.suites.named("test"))
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.1".toBigDecimal() // TODO: Change this 
            }
        }
    }
}