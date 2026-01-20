package neoforge

import ModProject
import common.DependencySetup
import install
import org.gradle.kotlin.dsl.DependencyHandlerScope

object NeoForgeSetup: DependencySetup {
    override fun DependencyHandlerScope.setup(minecraftVersion: String, modProject: ModProject) {
        "neoForge"("net.neoforged:neoforge:${forgeVersion(minecraftVersion, modProject)}")
        install("io.github.llamalad7:mixinextras-neoforge:0.4.1")
    }

    fun forgeVersion(minecraftVersion: String, modProject: ModProject) =
        modProject.modLoaderVersions[ModProject.ModPlatform.NEOFORGE]?.get(minecraftVersion)
            ?: error("Unsupported forge version for $minecraftVersion")
}