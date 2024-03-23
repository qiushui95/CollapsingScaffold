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

    implementation(projects.core)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.compose)
}