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
//    testRuns["test"].executionTask.configure {
//      useJUnitPlatform()
//    }
//    tasks.withType<Test>().configureEach {
//      useJUnitPlatform()
//    }
  }

  js()
  //android()

  sourceSets {
    val commonMain by getting { dependencies { } }
    val commonTest by getting { dependencies {
      implementation("io.kotest:kotest-framework-engine:5.8.0")
      implementation("io.kotest:kotest-assertions-core:5.8.0")
      implementation(kotlin("test-common"))
      implementation(kotlin("test-annotations-common"))

    } }
    val jvmTest by getting { dependencies {
      implementation("io.kotest:kotest-runner-junit5:5.8.0")
    } }
//    val androidMain by getting { dependencies {
//    } }
    val jsMain by getting { dependencies {
    } }
  }
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