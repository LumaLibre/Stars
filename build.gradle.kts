plugins {
    id("java")
    id("com.gradleup.shadow") version "9.2.2"
    id("io.freefair.lombok") version "9.2.0"
}

group = "dev.lumas.utilities"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.okaeri.cloud/releases")
    maven("https://repo.jsinco.dev/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:6.1.0-beta.1")
    compileOnly("dev.lumas.lumacore:LumaCore:fe80018")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("eu.okaeri", "dev.lumas.utilities.okaeri")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }
}
