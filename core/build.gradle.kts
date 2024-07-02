plugins {
    id("someone.code.library")
    id("someone.code.compose")
    alias(libs.plugins.maven)
}

android {
    namespace = "nbe.someone.code.core"
}

dependencies {
    implementation(libs.compose.foundation)
}