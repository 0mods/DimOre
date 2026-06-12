import gg.meza.stonecraft.mod

val publishType = if (mod.hasProp("build.release_type")) mod.prop("build.release_type") else null
val kotlinVersion: String by rootProject
val allSupportedMC = mutableListOf<String>().apply {
    if (mod.hasProp("minecraft_version.additional"))
        this.addAll(mod.prop("minecraft_version.additional").split(',').map { it.trim() })
}

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
        val flkVersion = mod.prop("mod.depend.flk_version").toString()

        implementMod("net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
    } else {
        val klfVersion = mod.prop("mod.depend.klf_version")
        val klfLoaderVersion = mod.prop("mod.depend.klf_loader_version")
        implementation("dev.nyon:KotlinLangForge:$klfVersion-k$kotlinVersion-$klfLoaderVersion+neoforge")
    }
}

modSettings {
    val replaces = mutableMapOf(
        "fabric_loader" to mod.prop("loader_version"), "java_version" to java.toolchain.languageVersion.get().toString(),
        "fabric_api" to mod.prop("fabric_version"),
        "flk_version" to "${mod.prop("mod.depend.flk_version")}+kotlin.$kotlinVersion",
        "neoforge_version" to mod.prop("loader_version"), "klf_version" to mod.prop("mod.depend.klf_version")
    )
    variableReplacements.putAll(replaces)
}

publishMods {
    val dependType = when {
        mod.isForge -> "forge"
        mod.isNeoforge -> "neoforge"
        else -> "fabric"
    }

    val likeProject = when {
        mod.isForgeLike -> "forgelike"
        else -> "fabriclike"
    }

    dryRun = false

    if (
        mod.hasProp("build.no_publish")
        && (mod.prop("build.no_publish") == "true"
                || (mod.prop("build.no_publish") == dependType || mod.prop("build.no_publish") == likeProject))
    ) {
        println("Publishing disabled. Skipping...")
        return@publishMods
    }

    changelog = rootProject.file("CHANGELOG.md").readText()

    displayName = "[${mod.loader}-${mod.minecraftVersion}] ${mod.name} (v.${mod.version})"
    version = "${mod.version}+mc${mod.minecraftVersion}"
    modLoaders.add(mod.loader)

    val modrinthProject: String? = if (mod.hasProp("publish.modrinth.project_id")) mod.prop("publish.modrinth.project_id") else null
    val modrinthToken = System.getenv("MODRINTH_TOKEN")

    val curseProject: String? = if (mod.hasProp("publish.curseforge.project_id")) mod.prop("publish.curseforge.project_id") else null
    val curseToken = System.getenv("CURSEFORGE_TOKEN")

    val uniqueVersions = (listOf(mod.minecraftVersion) + allSupportedMC).distinct()

    if (modrinthToken != null && modrinthProject != null) modrinth {
        projectId = modrinthProject
        accessToken = modrinthToken

        if (mod.hasProp("publish.modrinth.$dependType.depends")) {
            val depends = mod.prop("publish.modrinth.$dependType.depends").split(',')
            requires(*depends.toTypedArray())
        }

        minecraftVersions.set(uniqueVersions)
    }

    if (curseToken != null && curseProject != null) curseforge {
        projectId = curseProject
        accessToken = curseToken

        if (mod.hasProp("publish.curseforge.$dependType.depends")) {
            val depends = mod.prop("publish.curseforge.$dependType.depends").split(',')
            requires(*depends.toTypedArray())
        }

        minecraftVersions.set(uniqueVersions)
    }
}

fun DependencyHandlerScope.implementMod(dependencyNotation: Any) {
    if (stonecutter.eval(mod.minecraftVersion, ">=26.1.0"))
        implementation(dependencyNotation)
    else "modImplementation"(dependencyNotation)
}
