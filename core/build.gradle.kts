plugins {
    id("someone.code.library")
    id("someone.code.compose")
    alias(libs.plugins.maven)
}

android {
    namespace = "nbe.someone.code.collapsing.toolbar"
}

dependencies {
    implementation(libs.compose.foundation)
}