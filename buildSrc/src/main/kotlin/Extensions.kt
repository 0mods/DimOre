import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named

var isForgelike = false

fun DependencyHandlerScope.install(path: String, includeInJar: Boolean = true, isMod: Boolean = false) {
    val dependency = if (isMod) modImplementation(path) else "implementation"(path) {
        exclude("org.jetbrains.kotlin")
        exclude("org.lwjgl")
        exclude("org.ow2.asm")
        exclude("net.sourceforge.jaad.aac")
        exclude("org.slf4j")
        exclude("commons-logging")
    }

    dependency.takeIf { isForgelike && !isMod }?.let { "forgeRuntimeLibrary"(it) }
    if (includeInJar) dependency?.let { "include"(it) }
}

fun DependencyHandlerScope.minecraft(version: String) = "minecraft"("com.mojang:minecraft:$version")

@Suppress("UnstableApiUsage")
fun LoomGradleExtensionAPI.setupMappings(version: String, modProject: ModProject): Dependency = layered {
    officialMojangMappings()
    val mappingsVer = modProject.mappingsVersion[version] ?: ""
    if (mappingsVer.isNotEmpty())
        parchment("org.parchmentmc.data:parchment-$version:$mappingsVer")
}

val SourceSetContainer.main get() = named<SourceSet>("main")

val StonecutterBuildExtension.modPlatform get() = current.project.substringAfterLast('-')
val StonecutterBuildExtension.minecraftVersion get() = current.project.substringBeforeLast('-')