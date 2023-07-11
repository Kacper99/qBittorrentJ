plugins {
    id("com.diffplug.spotless")
}


spotless {
    java {
        removeUnusedImports()
        importOrder()

        palantirJavaFormat()
    }

    kotlin {
        targetExclude("**/build/generated/**", "**/build/tmp/**")
        ktfmt("0.43").dropboxStyle()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktfmt("0.43").dropboxStyle()
    }
}