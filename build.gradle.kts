val modId = "mod_id".fromProperties
val modName = "archivesName".fromProperties
val modVersion = "mod_version".fromProperties
val kotlinVersion: String by rootProject
val license = "mod_license".fromProperties

plugins {
    java
    idea
    `maven-publish`
    id("architectury-plugin")
    id("dev.architectury.loom")
    id("me.fallenbreath.yamlang")
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("me.shedaniel.unified-publishing") version "0.1.+"
}

val String.fromProperties
    get() = project.properties[this].toString()

val container = ModProject(
    modId = modId,
    modName = modName,
    modVersion = modVersion,
    license = license,

    entryPoints = mapOf(
        "main" to listOf("com.algorithmlx.dimore.Mod::onInitialize")
    ),
    dependencies = mapOf(
        "neoforge" to mapOf("kotlinforforge" to "5.3.0"),
        "fabric" to mapOf("fabric-language-kotlin" to "1.13.4+kotlin.2.2.0")
    )
)

setupEnviroment(container, kotlinVersion, includeKotlin = true)

repositories {
    mavenCentral()
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    fabricModImplementation(stonecutter, "net.fabricmc:fabric-language-kotlin:1.13.4+kotlin.2.2.0")
    neoforgeModImplementation(stonecutter, "thedarkcolour:kotlinforforge-neoforge:5.3.0")
    neoforgeImplementation(stonecutter, "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    neoforgeImplementation(stonecutter, "org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
    neoforgeImplementation(stonecutter, "org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

stonecutter {
    filters {
        if (stonecutter.modPlatform == "fabric") {
            exclude("**/neoforge/**")
            exclude("**/forge/**")
        }
    }
}

kotlin.compilerOptions.freeCompilerArgs.add("-Xjvm-default=all")

unifiedPublishing {
    project {
        gameVersions = listOf(stonecutter.minecraftVersion)
        gameLoaders = listOf(stonecutter.modPlatform)
        releaseType = "release"

        mainPublication(tasks.remapJar.get())

        val curseToken = System.getenv("CURSE_TOKEN")
        val curseProject = System.getenv("CURSE_PROJECT")
        val modrinthToken = System.getenv("MODRINTH_TOKEN")
        val modrinthProject = System.getenv("MODRINTH_PROJECT")

        if (curseToken != null && curseProject != null) curseforge {
            token = curseToken
            id = curseProject
            displayName = "[${stonecutter.modPlatform}] ${container.modName} (v.${container.modVersion})"
            relations {
                if (stonecutter.modPlatform == "neoforge") depends("kotlin-for-forge")
                else {
                    depends("fabric-api")
                    depends("fabric-language-kotlin")
                }
            }
        }


        if (modrinthToken != null && modrinthProject != null) modrinth {
            token = modrinthToken
            id = modrinthProject
            displayName = "[${stonecutter.modPlatform}] ${container.modName} (v.${container.modVersion})"
            relations {
                if (stonecutter.modPlatform == "neoforge") depends("kotlin-for-forge")
                else {
                    depends("fabric-api")
                    depends("fabric-language-kotlin")
                }
            }
        }
    }
}
