import gg.meza.stonecraft.mod
import me.modmuss50.mpp.ReleaseType

val publishType = if (mod.hasProp("build.release_type")) mod.prop("build.release_type") else null
val isBeta = publishType != null && publishType == "beta"
val isAlpha = publishType != null && publishType == "alpha"
val kotlinVersion: String by rootProject

plugins {
    id("gg.meza.stonecraft")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
    maven("https://repo.nyon.dev/releases")
}

dependencies {
    if (mod.isFabric) {
        val flkVersion = project.properties["flk_version"].toString()

        implementMod("net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
    } else {
        val klfVersion = project.properties["klf_version"].toString()
        val klfLoaderVersion = project.properties["klf_loader_version"].toString()
        implementation("dev.nyon:KotlinLangForge:$klfVersion-k$kotlinVersion-$klfLoaderVersion+neoforge")
    }
}

modSettings {
    val replaces = mutableMapOf(
        "fabric_loader" to mod.prop("loader_version"), "java_version" to java.toolchain.languageVersion.get().toString(),
        "fabric_api" to mod.prop("fabric_version"),
        "flk_version" to "${mod.prop("flk_version")}+kotlin.$kotlinVersion",
        "neoforge_version" to mod.prop("loader_version"), "klf_version" to mod.prop("klf_version")
    )
    variableReplacements.putAll(replaces)
}

publishMods {
    val dependType = when {
        mod.isFabricLike -> "fabriclike"
        mod.isForge -> "forge"
        mod.isForgeLike -> "forgelike"
        mod.isNeoforge -> "neoforge"
        else -> "fabric"
    }

    dryRun = false

    if (
        mod.hasProp("build.no_publish")
        && (
            mod.prop("build.no_publish") == "true"
                    || ((mod.prop("build.no_publish") == "neoforge") && mod.isNeoforge)
                    || ((mod.prop("build.no_publish") == "fabric") && mod.isFabric)
                    || ((mod.prop("build.no_publish") == "forge") && mod.isForge)
                    || ((mod.prop("build.no_publish") == "fabriclike") && mod.isFabricLike)
                    || ((mod.prop("build.no_publish") == "forgelike") && mod.isForgeLike)
        )
    ) {
        println("Publishing disabled. Skipping...")
        return@publishMods
    }

    val additionalMinecraftVersions = if (mod.hasProp("minecraft_version.additional"))
        mod.prop("minecraft_version.additional").split(',').map { it.trim() }
    else listOf()

    changelog = rootProject.file("CHANGELOG.md").readText()

    type = when {
        isBeta -> ReleaseType.BETA
        isAlpha -> ReleaseType.ALPHA
        else -> ReleaseType.STABLE
    }

    displayName = "[${mod.loader}-${mod.minecraftVersion}] ${mod.name} (v.${mod.version})"
    modLoaders.add(mod.loader)

    val modrinthProject: String? = if (mod.hasProp("publish.modrinth.project_id")) mod.prop("publish.modrinth.project_id") else null
    val modrinthToken = System.getenv("MODRINTH_TOKEN")

    val curseProject: String? = if (mod.hasProp("publish.curseforge.project_id")) mod.prop("publish.modrinth.project_id") else null
    val curseToken = System.getenv("CURSE_TOKEN")

    if (modrinthToken != null && modrinthProject != null) modrinth {
        projectId = modrinthProject
        accessToken = modrinthToken

        if (mod.hasProp("publish.modrinth.$dependType.depends")) {
            val depends = mod.prop("publish.modrinth.$dependType.depends").split(',')
            requires(*depends.toTypedArray())
        }

        minecraftVersions.add(mod.minecraftVersion)
        minecraftVersions.addAll(additionalMinecraftVersions)
    }

    if (curseToken != null && curseProject != null) curseforge {
        projectId = curseProject
        accessToken = curseToken

        if (mod.hasProp("publish.curseforge.$dependType.depends")) {
            val depends = mod.prop("publish.curseforge.$dependType.depends").split(',')
            requires(*depends.toTypedArray())
        }

        if (mod.minecraftVersion.contains("snapshot")) {
            val modifiedVersion = buildString {
                val oldVersion = mod.minecraftVersion
                val splitted = oldVersion.split("-")
                append(splitted[0] + "-snapshot")
            }
            minecraftVersions.add(modifiedVersion)
        } else minecraftVersions.add(mod.minecraftVersion)
        minecraftVersions.addAll(additionalMinecraftVersions)
    }
}

fun DependencyHandlerScope.implementMod(dependencyNotation: Any) {
    if (stonecutter.eval(mod.minecraftVersion, ">=26.1.0"))
        implementation(dependencyNotation)
    else "modImplementation"(dependencyNotation)
}
