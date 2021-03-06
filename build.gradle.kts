import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  idea
  id("com.github.ben-manes.versions") version "0.33.0"
  kotlin("jvm") version "1.4.20-M1" // Keep in sync with README
}

idea.module {
  excludeDirs.add(file("latex"))
  isDownloadJavadoc = true
  isDownloadSources = true
}

allprojects {
  repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/mipt-npm/dev")
    maven("https://dl.bintray.com/hotkeytlt/maven")
    maven("https://dl.bintray.com/egor-bogomolov/astminer")
    maven("https://maven.jzy3d.org/releases")
    maven("https://jetbrains.bintray.com/lets-plot-maven")
//    maven("https://oss.sonatype.org/content/repositories/snapshots")
    //Kotlin EAP
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("http://logicrunch.research.it.uu.se/maven/")
  }

  group = "com.github.breandan"
  version = "0.3.5"

  apply(plugin = "org.jetbrains.kotlin.jvm")

  dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  }

  tasks {
    val jvmTarget = VERSION_1_8.toString()
    compileKotlin {
      kotlinOptions.jvmTarget = jvmTarget
//      kotlinOptions.useIR = true
    }
    compileTestKotlin {
      kotlinOptions.jvmTarget = jvmTarget
    }
    test {
      minHeapSize = "1024m"
      maxHeapSize = "4096m"
      useJUnitPlatform()
      testLogging {
        events = setOf(FAILED, PASSED, SKIPPED, STANDARD_OUT)
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
      }
    }
  }
}