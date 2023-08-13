plugins {
    alias(deps.plugins.android.application)
    alias(deps.plugins.kotlin.android)
}

android {
    namespace = "com.edwardstock.bip3x_app"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.edwardstock.bip3x_app"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
        }
    }

}

dependencies {
    implementation(deps.base.android.appcompat)
}
