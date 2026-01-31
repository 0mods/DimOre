import me.shedaniel.unifiedpublishing.PublicationRelations

val modId = "mod_id".fromProperties
val modName = "mod_name".fromProperties
val modVersion = "mod_version".fromProperties
val kotlinVersion: String by rootProject
val license = "mod_license".fromProperties
val flkVersion = "flk_version".fromProperties
val klfVersion = "klf_version".fromProperties
val klfLoaderVersion = "klf_loader_version".fromProperties

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
        "fabric" to mapOf("fabric-language-kotlin" to "$flkVersion+kotlin.$kotlinVersion")
    ),

    neoforgeLoaderName = "klf",
    neoforgeLoaderVersion = "[${klfVersion},)",

    modLoaderVersions = defaultModLoaderVersions.apply {
        this[ModProject.ModPlatform.FABRIC]?.put("1.21.11-loader", "0.17.3")
    }
)

setupEnviroment(container, kotlinVersion, includeKotlin = true)

repositories {
    mavenCentral()
    maven("https://repo.nyon.dev/releases")
}

dependencies {
    fabricModImplementation(stonecutter, "net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
    neoforgeModImplementation(stonecutter, "dev.nyon:KotlinLangForge:$klfVersion-k$kotlinVersion-$klfLoaderVersion+neoforge")
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

val curseProject: String? = rootProject.properties["curseforge_project"]?.toString()
val modrinthProject: String? = rootProject.properties["modrinth_project"]?.toString()

unifiedPublishing {
    project {
        if (project.file("nopub").exists()) {
            println("No publish file exists (${project.name}/nopub). Skipping")
            return@project
        }

        fun PublicationRelations.deps(isCurseForge: Boolean = false) = if (stonecutter.modPlatform.contains("forge")) {
            if (!isCurseForge) depends("kotlin-lang-forge")
            else depends("kotlinlangforge")
        } else {
            depends("fabric-api")
            depends("fabric-language-kotlin")
        }

        gameVersions = listOf(stonecutter.minecraftVersion)
        gameLoaders = listOf(stonecutter.modPlatform)
        releaseType = "release"
        displayName = "[${stonecutter.modPlatform}-${stonecutter.minecraftVersion}] ${container.modName} (v.${container.modVersion})"

        changelog = "Pushed via Auto Compile. View changelog here: https://github.com/AlgorithmLX/DimOre"

        mainPublication(tasks.remapJar.get())

        val curseToken = System.getenv("CURSE_TOKEN")
        val modrinthToken = System.getenv("MODRINTH_TOKEN")

        if (curseToken != null && curseProject != null) curseforge {
            token = curseToken
            id = curseProject
            relations { deps(true) }
        }

        if (modrinthToken != null && modrinthProject != null) modrinth {
            token = modrinthToken
            id = modrinthProject
            version = "${stonecutter.modPlatform}-${stonecutter.minecraftVersion}_v.${container.modVersion}"
            relations { deps() }
        }
    }
}
