plugins {
  kotlin("multiplatform")
  //id("com.android.library")
  // for testing
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

kotlin {
  jvm()
  js()
//  android()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(project(":pprint-core"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
      }
    }
    val jvmMain by getting {
      dependencies {
        // JVM specific dependencies
      }
    }
//    val androidMain by getting {
//      dependencies {
//        // Android specific dependencies
//      }
//    }
    val jsMain by getting {
      dependencies {
        // JS specific dependencies
      }
    }
  }
}

//android {
//  compileSdk = 32
//  namespace = "io.exoquery.pprint"
//}