package com.algorithmlx.dimore.init.resource

import com.algorithmlx.dimore.LOGGER
import com.algorithmlx.dimore.ModId
import com.algorithmlx.dimore.util.ResLoc
import net.minecraft.server.packs.AbstractPackResources
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.IoSupplier
import java.io.File
import java.io.InputStream

class DimOreResourcePack(location: PackLocationInfo) : AbstractPackResources(location) {
    private val targetPath = File("config/$ModId/custom/client/")

    init {
        if (!targetPath.exists()) {
            targetPath.parentFile.mkdirs()
            targetPath.mkdirs()
            LOGGER.info("Created client directories")
        }
    }

    override fun getRootResource(vararg path: String): IoSupplier<InputStream>? = null

    override fun getResource(
        type: PackType,
        location: ResLoc
    ): IoSupplier<InputStream>? {
        LOGGER.info("Loading custom resources")
        if (location.namespace != ModId) return null

        val path = location.path

        if (!path.contains("custom.")) return null

        var relative = when {
            path.startsWith("models/") -> path.substring("models/".length)
            path.startsWith("textures/") -> path.substring("textures/".length)
            path.startsWith("model/") -> path.substring("model/".length)
            else -> path
        }

        relative = relative.replace("custom.", "")

        val target = targetPath.resolve(relative)
        if (target.exists() && target.isFile) {
            return IoSupplier { target.inputStream() }
        }

        return null
    }

    override fun listResources(
        type: PackType,
        namespace: String,
        directory: String,
        output: PackResources.ResourceOutput
    ) {
        if (namespace != ModId) return

        val relative = when {
            directory.startsWith("models/") -> directory.substring("models/".length)
            directory.startsWith("textures/") -> directory.substring("textures/".length)
            directory.startsWith("model/") -> directory.substring("model/".length)
            else -> directory
        }

        val target = targetPath.resolve(relative)

        if (target.exists() && target.isDirectory) {
            target.listFiles()
                .filter { it.isFile }
                .filter { !it.name.startsWith("_") }
                .forEach { file ->
                    val path = "$directory/custom.${file.name}"
                    val resLoc = ResLoc.parse("$ModId:$path")
                    output.accept(resLoc) { file.inputStream() }
                }
        }
    }

    override fun getNamespaces(type: PackType): Set<String> = setOf(ModId)

    override fun close() {}
}