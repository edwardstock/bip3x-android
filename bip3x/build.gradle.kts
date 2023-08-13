@file:Suppress("UnstableApiUsage")

plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.kotlin.android)
    id("maven-publish")
    id("signing")
}

version = "1.0.0"
group = "com.edwardstock.android"

android {
    namespace = "com.edwardstock.android.bip3x"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        externalNativeBuild {
            cmake {
                arguments(
                    "-Dbip3x_BUILD_TESTS=Off",
                    "-Dbip3x_BUILD_C_BINDINGS=Off",
                    "-Dbip3x_BUILD_SHARED_LIBS=On",
                    "-Dbip3x_BUILD_JNI_BINDINGS=On",
                )
                cppFlags(
                    "-std=c++14"
                )
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = deps.versions.cmakeVersion.get()
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs {
                srcDirs(file("src/main/cpp/bip3x/src/jni"))
                srcDirs(file("libs"))
            }
            java {
                srcDirs(file("src/main/java"))
                srcDirs(file("src/main/cpp/bip3x/src/jni"))
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += listOf(
                "META-INF/*",
            )
        }
    }

    buildFeatures {
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

tasks.withType<Test> {
    val sourceOsName = System.getProperty("os.name").lowercase()
    val osName = when {
        sourceOsName.startsWith("mac os") -> "darwin"
        sourceOsName.startsWith("linux") -> "linux"
        sourceOsName.startsWith("windows") -> "windows"
        else -> throw IllegalStateException("Unsupported OS for unit tests: $sourceOsName")
    }
    val arch = System.getProperty("os.arch").lowercase()
    allJvmArgs = allJvmArgs + listOf(
        "-Djava.library.path=${rootProject.file("bip3x/src/test/resources/jniLibs/${osName}/${arch}").absolutePath}",
        "-Xdebug"
    )
}

dependencies {
    // annotations
    compileOnly("javax.annotation:jsr250-api:1.0")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2@jar")
    testImplementation("junit:junit:4.13.2")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            groupId = project.group as String
            artifactId = "bip3x"
            version = project.version as String

            pom {
                name.set(project.name)
                description.set("Bip39 mnemonic implementation for android")
                url.set("https://github.com/edwardstock/bip3x-android")
                scm {
                    connection.set("scm:git:${pom.url.get()}.git")
                    developerConnection.set(connection.get())
                    url.set(pom.url.get())
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("${pom.url.get()}/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("edwardstock")
                        name.set("Eduard Maximovich")
                        email.set("edward.vstock@gmail.com")
                        roles.add("owner")
                        timezone.set("Asia/Tbilisi")
                    }
                }
            }
        }
    }
    repositories {
        mavenLocal()
        maven(url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")) {
            credentials.username = findProperty("ossrhUsername") as String?
            credentials.password = findProperty("ossrhPassword") as String?
        }
    }
}

project.tasks.withType<PublishToMavenLocal> {
    dependsOn("publishAllPublicationsToMavenLocalRepository")
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}