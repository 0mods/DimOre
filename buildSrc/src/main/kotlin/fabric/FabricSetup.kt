package fabric

import common.DependencySetup
import install
import modImplementation
import modImplementation
import org.gradle.kotlin.dsl.DependencyHandlerScope

object FabricSetup: DependencySetup {
    override fun DependencyHandlerScope.setup(minecraftVersion: String) {
        modImplementation("net.fabricmc:fabric-loader:0.17.0")
        when (minecraftVersion) {
            "1.21.1" -> {
                modImplementation("net.fabricmc.fabric-api:fabric-api:0.116.4+$minecraftVersion")
            }

            "1.21" -> {
                modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+$minecraftVersion")
            }

            "1.20.1" -> {
                modImplementation("net.fabricmc.fabric-api:fabric-api:0.92.2+$minecraftVersion")
            }

            "1.19.2" -> {
                modImplementation("net.fabricmc.fabric-api:fabric-api:0.77.0+$minecraftVersion")
            }

            else -> throw IllegalStateException("Unsupported Fabric version $minecraftVersion!")
        }
        install("io.github.llamalad7:mixinextras-fabric:0.4.1")
    }

    fun fabricLoader(minecraftVersion: String) = when(minecraftVersion) {
        "1.21.1" -> "0.17.0"
        else -> "0.15.11"
    }
    fun fabricApi(minecraftVersion: String) = when(minecraftVersion) {
        "1.21.1" -> "0.116.4+$minecraftVersion"
        "1.21" -> "0.102.0+$minecraftVersion"
        "1.20.1" -> "0.92.2+$minecraftVersion"
        "1.19.2" -> "0.77.0+$minecraftVersion"
        else -> error("Unsupported fabric api version for $minecraftVersion")
    }
}