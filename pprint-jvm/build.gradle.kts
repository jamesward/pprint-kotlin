plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":pprint-core"))
  implementation(kotlin("reflect"))
}

kotlin {
  sourceSets {
    val main by getting {
      kotlin.srcDir("src/main/kotlin")
    }
  }
}
