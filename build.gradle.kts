import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    `maven-publish`
    id("io.kotest.multiplatform") version "5.8.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.9.10"
    signing
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

group = "io.exoquery"
version = "1.2.2-SNAPSHOT"

repositories {
    mavenCentral()
}

// Needed for Kotest
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvm {
        jvmToolchain(8)
    }

    linuxX64()

    /*
    js {
        browser()
        nodejs()
    }
     */

    // todo: kotest doesn't have wasm targets yet
    /*
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()

    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi()
     */

    /*
    macosArm64()

    iosArm64()
     */

    /*
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyHierarchyTemplate {
        withSourceSetTree(KotlinSourceSetTree.main, KotlinSourceSetTree.test)

        common {
            withCompilations { true }

            group("serial") {
                group("jvmSerial") {
                    //withJvm()
                }
                group("linuxSerial") {
                    withLinuxX64()
                }
                group("jsSerial") {
                    withJs()
                }
            }
            group("reflect") {
                withJvm()
                //group("jvm")
            }
        }
    }
     */

    sourceSets {
        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
            }
        }

        commonTest {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.8.0")
                implementation("io.kotest:kotest-framework-engine:5.8.0")
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmMain {
            dependencies {
                api(kotlin("reflect"))
            }
        }

        jvmTest {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:5.8.0")
            }
        }
    }

}

// todo
//   Util uses toHexString which is new in 1.9
//   Issue in 1.6 with shadowing
/*
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_6.version
        apiVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_6.version
    }
}
 */

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}

tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        showStandardStreams = true
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
        events(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

val varintName = project.name

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("decomat")
            description.set("DecoMat - Deconstructive Pattern Matching for Kotlin")
            url.set("https://github.com/deusaquilus/pprint-kotlin")

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }

            developers {
                developer {
                    name.set("Alexander Ioffe")
                    email.set("deusaquilus@gmail.com")
                    organization.set("github")
                    organizationUrl.set("http://www.github.com")
                }
            }

            scm {
                url.set("https://github.com/exoquery/decomat/tree/main")
                connection.set("scm:git:git://github.com/ExoQuery/DecoMat.git")
                developerConnection.set("scm:git:ssh://github.com:ExoQuery/DecoMat.git")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

tasks.withType<Sign> {
    onlyIf { !project.hasProperty("nosign") }
}
