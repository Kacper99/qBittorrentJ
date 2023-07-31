plugins {
    `java-library`
    `maven-publish`
    signing
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "qBittorrentJ"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("qBittorrentJ")
                description.set("A Java library to talk to the qBittorrentJ web UI API")
                url.set("https://github.com/Kacper99/qBittorrentJ")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/license/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("kacper99")
                        name.set("Kacper Martela")
                        email.set("kacper99@hotmail.co.uk")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Kacper99/qBittorrentJ.git")
                    developerConnection.set("scm:git:ssh://github.com/Kacper99/qBittorrentJ.git")
                    url.set("https://github.com/Kacper99/qBittorrentJ")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = project.property("ossrhUsername").toString()
                password = project.property("ossrhPassword").toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}