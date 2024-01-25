plugins {
  kotlin("multiplatform")
  id("com.android.library")
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }

  js()
  android()

  sourceSets {
    val commonMain by getting {
      dependencies {
        // Common dependencies for all platforms
      }
    }
    val jvmMain by getting {
      dependencies {
        // JVM specific dependencies
      }
    }
    val androidMain by getting {
      dependencies {
        // Android specific dependencies
      }
    }
    val jsMain by getting {
      dependencies {
        // JS specific dependencies
      }
    }
  }
}

android {
  compileSdk = 32
  namespace = "io.exoquery.pprint"
}