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
    dependencies = mapOf()
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

kotlin.compilerOptions.freeCompilerArgs.add("-Xjvm-default=all")
