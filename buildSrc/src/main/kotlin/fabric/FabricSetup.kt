package fabric

import ModProject
import common.DependencySetup
import install
import modImplementation
import org.gradle.kotlin.dsl.DependencyHandlerScope

object FabricSetup: DependencySetup {
    override fun DependencyHandlerScope.setup(minecraftVersion: String, modProject: ModProject) {
        modImplementation("net.fabricmc:fabric-loader:${fabricLoader(minecraftVersion, modProject)}")
        modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApi(minecraftVersion, modProject)}")
        install("io.github.llamalad7:mixinextras-fabric:0.4.1")
    }

    fun fabricLoader(minecraftVersion: String, modProject: ModProject): String {
        val modLoaderVersions = modProject.modLoaderVersions[ModProject.ModPlatform.FABRIC] ?: return "0.17.0"
        val version = modLoaderVersions["${minecraftVersion}-loader"] ?: modLoaderVersions["loader"] ?: return "0.17.0"
        return version
    }

    fun fabricApi(minecraftVersion: String, modProject: ModProject) =
        modProject.modLoaderVersions[ModProject.ModPlatform.FABRIC]?.get("$minecraftVersion-api")
            ?: error("Unsupported forge version for $minecraftVersion")
}
