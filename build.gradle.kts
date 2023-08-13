// Top-level build file where you can add configuration options common to all sub-projects/modules.
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
}
