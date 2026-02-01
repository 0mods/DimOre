import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import me.modmuss50.mpp.ReleaseType

val kotlinVersion: String by rootProject
val modId = project.properties["mod_id"].toString()
val modName = project.properties["mod_name"].toString()
val modVersion = project.properties["mod_version"].toString()
val license = project.properties["mod_license"].toString()

val publishType = project.properties["publish.type"]?.toString()
val isBeta = publishType != null && publishType == "beta"
val isAlpha = publishType != null && publishType == "alpha"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")

    id("dev.isxander.modstitch.base")
    id("me.modmuss50.mod-publish-plugin")
}

val StonecutterBuildExtension.modPlatform get() = current.project.substringAfterLast('-')
val StonecutterBuildExtension.minecraftVersion get() = project.properties["minecraft_version"]?.toString() ?: current.project.substringBeforeLast('-')

val modLoaderVersions = mutableMapOf(
    "fabric" to mutableMapOf("loader" to "0.17.0", // or can use {mc-ver}-loader
        "1.19.2-api" to "0.77.0+1.19.2", "1.20.1-api" to "0.92.2+1.20.1", "1.21-api" to "0.102.0+1.21",
        "1.21.1-api" to "0.116.4+1.21.1", "1.21.2-api" to "0.106.1+1.21.2", "1.21.3-api" to "0.114.1+1.21.3",
        "1.21.4-api" to "0.119.4+1.21.4", "1.21.5-api" to "0.128.2+1.21.5", "1.21.6-api" to "0.128.2+1.21.6",
        "1.21.7-api" to "0.129.0+1.21.7", "1.21.8-api" to "0.136.1+1.21.8", "1.21.9-api" to "0.134.1+1.21.9",
        "1.21.10-api" to "0.138.4+1.21.10", "1.21.11-api" to "0.141.1+1.21.11", "26.1-snapshot-5-api" to "0.143.0+26.1",
        "26.1-snapshot-5-loader" to "0.18.4"
    ),
    // forge is dead lol. no more versions need
    "forge" to mutableMapOf("1.19.2" to "1.19.2-43.4.2", "1.20.1" to "1.20.1-47.4.3", "1.21" to "1.21-51.0.8",),
    "neoforge" to mutableMapOf("1.21" to "21.0.167", "1.21.1" to "21.1.197", "1.21.2" to "21.2.1-beta",
        "1.21.3" to "21.3.95", "1.21.4" to "21.4.156", "1.21.5" to "21.5.96", "1.21.6" to "21.6.20-beta",
        "1.21.7" to "21.7.25-beta", "1.21.8" to "21.8.52", "1.21.9" to "21.9.16-beta", "1.21.10" to "21.10.64",
        "1.21.11" to "21.11.36-beta"
    )
)

val parchmentVersion = mutableMapOf(
    "1.19.2" to "2022.11.27", "1.20.1" to "2023.09.03", "1.21" to "2024.07.28", "1.21.1" to "2024.11.17",
    "1.21.3" to "2024.12.07", "1.21.4" to "2025.03.23", "1.21.5" to "2025.06.15", "1.21.6" to "2025.06.29",
    "1.21.7" to "2025.07.18", "1.21.8" to "2025.09.14", "1.21.9" to "2025.10.05", "1.21.10" to "2025.10.12"
)

java.toolchain.languageVersion = JavaLanguageVersion.of(when {
    stonecutter.eval(stonecutter.minecraftVersion, ">1.21.11") -> 25
    stonecutter.eval(stonecutter.minecraftVersion, "<=1.21.11") && stonecutter.eval(stonecutter.minecraftVersion, ">1.19.4") -> 21
    else -> 17
})

modstitch {
    minecraftVersion = stonecutter.minecraftVersion

    loom {
        val verArr = modLoaderVersions["fabric"]!!
        fabricLoaderVersion = verArr["${stonecutter.minecraftVersion}-loader"]
            ?: verArr["loader"]
                    ?: error("Failed to find loader version for ${stonecutter.minecraftVersion} on fabric")
    }

    moddevgradle {
        val verArr = modLoaderVersions["neoforge"]!!
        neoForgeVersion = verArr[stonecutter.minecraftVersion] ?: error("Failed to find loader version for ${stonecutter.minecraftVersion} on neoforge")
    }

    parchmentVersion[stonecutter.minecraftVersion]?.let { ver ->
        parchment {
            version = ver
        }
    }

    metadata {
        modId = project.properties["mod_id"].toString()
        modVersion = "${stonecutter.modPlatform}-${stonecutter.minecraftVersion}-${project.properties["mod_version"].toString()}${when {
            isBeta -> "-B"
            isAlpha -> "-A"
            else -> ""
        }}"
        modName = project.properties["mod_name"].toString()
        modGroup = "com.algorithmlx"
        modLicense = project.properties["mod_license"].toString()
        modDescription = project.properties["mod_description"].toString()
        modCredits = project.properties["mod_credits"].toString()
        modAuthor = project.properties["mod_authors"].toString()

        replacementProperties.put("mc_version", stonecutter.minecraftVersion)
        if (modstitch.isLoom) {
            val fabricLoader = modLoaderVersions["fabric"]!!["${stonecutter.minecraftVersion}-loader"]
                ?: modLoaderVersions["fabric"]!!["loader"]
                ?: error("Failed to find loader version for ${stonecutter.minecraftVersion} on fabric")
            val flkVersion = project.properties["flk_version"].toString()

            replacementProperties.put("fabric_loader", fabricLoader)
            replacementProperties.put("java_version", project.java.toolchain.languageVersion.toString())
            replacementProperties.put("fabric_api", modLoaderVersions["fabric"]!!["${stonecutter.minecraftVersion}-api"]!!)
            replacementProperties.put("flk_version", "$flkVersion+kotlin.$kotlinVersion")
        } else {
            val klfVersion = project.properties["klf_version"].toString()
            replacementProperties.put("neoforge_version", modLoaderVersions["neoforge"]!![stonecutter.minecraftVersion]!!)
            replacementProperties.put("klf_version", klfVersion)
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.nyon.dev/releases")
}

dependencies {
    if (modstitch.isLoom) {
        val flkVersion = project.properties["flk_version"].toString()
        if (stonecutter.eval(stonecutter.minecraftVersion, "<=1.21.11")) {
            "modImplementation"("net.fabricmc.fabric-api:fabric-api:${modLoaderVersions["fabric"]!!["${stonecutter.minecraftVersion}-api"]}")
            "modImplementation"("net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
        } else {
            implementation("net.fabricmc.fabric-api:fabric-api:${modLoaderVersions["fabric"]!!["${stonecutter.minecraftVersion}-api"]}")
            implementation("net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
        }
    } else {
        val klfVersion = project.properties["klf_version"].toString()
        val klfLoaderVersion = project.properties["klf_loader_version"].toString()
        implementation("dev.nyon:KotlinLangForge:$klfVersion-k$kotlinVersion-$klfLoaderVersion+neoforge")
    }
}

stonecutter {
    constants {
        put("fabric", modstitch.isLoom)
        put("neoforge", modstitch.isModDevGradleRegular)
        put("forge", modstitch.isModDevGradleLegacy)
        put("forgelike", modstitch.isModDevGradle)
    }
}

val buildAndCollect = project.tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(project.tasks.named<Jar>("remapJar").map { it.archiveFile.get().asFile })
    into(project.rootProject.layout.buildDirectory.file("../merged"))

    dependsOn("build")
}

if (stonecutter.current.isActive) {
    project.rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(buildAndCollect)
    }

    project.rootProject.tasks.register("runActive") {
        group = "project"
        dependsOn(project.tasks.named("runClient"))
    }
}

publishMods {
    dryRun = false

    if (project.file("nopub").exists()) {
        println("No publish file exists (${project.name}/nopub). Skipping")
        return@publishMods
    }

    displayName = "[${stonecutter.modPlatform}-${stonecutter.minecraftVersion}] ${modstitch.metadata.modName.get()} (v.${project.properties["mod_version"].toString()})"
    file = modstitch.finalJarTask.flatMap { it.archiveFile }

    changelog = rootProject.file("CHANGELOG.md").readText()

    type = when {
        isBeta -> ReleaseType.BETA
        isAlpha -> ReleaseType.ALPHA
        else -> ReleaseType.STABLE
    }

    modLoaders.add(stonecutter.modPlatform)

    val modrinthProject: String? = rootProject.properties["modrinth_project"]?.toString()
    val modrinthToken = System.getenv("MODRINTH_TOKEN")

    val curseProject: String? = rootProject.properties["curseforge_project"]?.toString()
    val curseToken = System.getenv("CURSE_TOKEN")

    if (modrinthToken != null && modrinthProject != null) modrinth {
        projectId = modrinthProject
        accessToken = modrinthToken

        if (modstitch.isLoom)
            requires("fabric-api", "fabric-language-kotlin")
        else requires("kotlin-lang-forge")

        minecraftVersions.add(stonecutter.minecraftVersion)
    }

    if (curseToken != null && curseProject != null) curseforge {
        projectId = curseProject
        accessToken = curseToken

        if (modstitch.isLoom)
            requires("fabric-api", "fabric-language-kotlin")
        else requires("kotlinlangforge")

        minecraftVersions.add(stonecutter.minecraftVersion)
    }
}
