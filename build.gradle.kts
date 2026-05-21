import gg.meza.stonecraft.mod

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
        modCompileClasspath("net.fabricmc:fabric-language-kotlin:$flkVersion+kotlin.$kotlinVersion")
    } else {
        val klfVersion = project.properties["klf_version"].toString()
        val klfLoaderVersion = project.properties["klf_loader_version"].toString()
        implementation("dev.nyon:KotlinLangForge:$klfVersion-k$kotlinVersion-$klfLoaderVersion+neoforge")
    }
}

tasks.processResources {
    if (mod.isFabric) expand(
        "fabric_loader" to mod.prop("loader_version"),
        "java_version" to java.toolchain.languageVersion.toString(),
        "fabric_api" to mod.prop("fabric_version"), "flk_version" to "${mod.prop("flk_version")}+kotlin.$kotlinVersion"
    )
}


