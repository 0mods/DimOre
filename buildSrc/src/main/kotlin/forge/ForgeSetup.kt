package forge

import ModProject
import common.DependencySetup
import install
import modImplementation
import org.gradle.kotlin.dsl.DependencyHandlerScope

object ForgeSetup: DependencySetup {
    override fun DependencyHandlerScope.setup(minecraftVersion: String, modProject: ModProject) {
        "forge"("net.minecraftforge:forge:${forgeVersion(minecraftVersion, modProject)}")
        install("io.github.llamalad7:mixinextras-forge:0.4.1")
    }

    fun forgeVersion(minecraftVersion: String, modProject: ModProject) =
        modProject.modLoaderVersions[ModProject.ModPlatform.FORGE]?.get(minecraftVersion)
            ?: error("Unsupported forge version for $minecraftVersion")
}
