plugins {
    id("someone.code.library")
    id("someone.code.library.compose")
    alias(libs.plugins.maven)
}

android {
    namespace = "nbe.someone.code.core"
}

dependencies {
}