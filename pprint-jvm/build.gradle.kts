plugins {
  java
  id("java-library")
  kotlin("multiplatform")
  //id("com.android.library")
  id("io.kotest.multiplatform") version "5.8.0"
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "17"
    }
  }

  sourceSets {
    val jvmMain by getting { dependencies {
      implementation("io.kotest:kotest-runner-junit5:5.8.0")
      implementation(kotlin("reflect"))
    } }
  }
}

dependencies {
  //implementation(kotlin("reflect"))
  implementation(project(":pprint-core"))
}

//android {
//  compileSdk = 32
//  namespace = "io.exoquery.pprint"
//}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
  filter {
    isFailOnNoMatchingTests = false
  }
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events = setOf(
      org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
      org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
    )
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
  }
}