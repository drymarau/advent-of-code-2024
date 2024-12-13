plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spotless)
}

group = "com.dzmitryrymarau.adventofcode2024"
version = "0.1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

spotless {
    kotlin {
        ktlint()
        licenseHeaderFile("LICENSE_HEADER")
    }
    kotlinGradle {
        ktlint()
    }
    format("misc") {
        target(".gitignore", "*.md")
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.okio)
}

tasks.test {
    useJUnitPlatform()
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
    testLogging {
        events("passed", "skipped", "failed")
    }
}
