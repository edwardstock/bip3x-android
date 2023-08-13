@file:Suppress("UnstableApiUsage")
rootProject.name = "bip3x"
include(
        ":app",
        ":bip3x",
)

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        mavenLocal()

        maven(url = uri("https://repo1.maven.org/maven2/"))
        maven(url = uri("https://oss.sonatype.org/content/repositories/releases/"))
        maven(url = uri("https://clojars.org/repo/"))
        maven(url = uri("https://oss.sonatype.org/content/repositories/snapshots/"))
        maven(url = uri("https://jitpack.io"))
        maven(url = uri("https://oss.jfrog.org/libs-snapshot/"))
        maven(url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/"))
    }

    versionCatalogs {
        create("deps") {
            from(files("gradle/deps.versions.toml"))
        }
    }
}

