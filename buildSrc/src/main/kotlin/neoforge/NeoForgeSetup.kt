package neoforge

import common.DependencySetup
import install
import org.gradle.kotlin.dsl.DependencyHandlerScope

object NeoForgeSetup: DependencySetup {
    override fun DependencyHandlerScope.setup(minecraftVersion: String) {
        when (minecraftVersion) {
            "1.21" -> "neoForge"("net.neoforged:neoforge:21.0.167")
            "1.21.1" -> "neoForge"("net.neoforged:neoforge:21.1.197")
            else -> throw IllegalStateException("Unsupported NeoForge version $minecraftVersion!")
        }

        install("io.github.llamalad7:mixinextras-neoforge:0.4.1")
    }

    fun forgeVersion(minecraftVersion: String) = when(minecraftVersion) {
        "1.21" -> "21.0.167"
        "1.21.1" -> "21.1.197"
        else -> error("Unsupported forge version for $minecraftVersion")
    }
}