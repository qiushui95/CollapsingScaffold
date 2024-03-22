plugins {
    id("someone.code.application")
    id("someone.code.application.compose")
}

android {
    namespace = "me.ysy.collapsing.scaffold"


    defaultConfig {
        applicationId = "me.ysy.collapsing.scaffold"

        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.compose)

    implementation(libs.compose.foundation)
    implementation(libs.compose.preview)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material)
}