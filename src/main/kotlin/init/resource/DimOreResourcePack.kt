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
    private val targetPath = File("config/$ModId/client/")

    init {
        if (!targetPath.exists()) {
            targetPath.mkdirs()
            LOGGER.info("Created client directories")
        }
    }

    override fun getRootResource(vararg path: String): IoSupplier<InputStream>? = null

    override fun getResource(
        type: PackType,
        location: ResLoc
    ): IoSupplier<InputStream>? {
        if (location.namespace != ModId) return null

        val fileName = mapResourcePathToFile(location.path) ?: return null
        val target = targetPath.resolve(fileName)

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

        if (targetPath.exists() && targetPath.isDirectory) {
            targetPath.listFiles()
                .filter { it.isFile && !it.name.startsWith("_") }
                .forEach { file ->
                    val resPath = mapFileToResourcePath(file.name)
                    if (resPath != null && resPath.startsWith(directory)) {
                        val resLoc = ResLoc.parse("$ModId:$resPath")
                        output.accept(resLoc) { file.inputStream() }
                    }
                }
        }
    }

    override fun getNamespaces(type: PackType): Set<String> = setOf(ModId)

    override fun close() {}

    private fun mapResourcePathToFile(path: String): String? {
        return when {
            path.startsWith("blockstates/custom.")
                    && path.endsWith(".json") -> "blockstate." + path.removePrefix("blockstates/custom.")
            path.startsWith("models/block/custom.")
                    && path.endsWith(".json") -> "block.model." + path.removePrefix("models/block/custom.")
            path.startsWith("items/custom.")
                    && path.endsWith(".json") -> "items." + path.removePrefix("items/custom.")
            path.startsWith("models/item/custom.")
                    && path.endsWith(".json") -> "item.model." + path.removePrefix("models/item/custom.")
            path.startsWith("textures/") && path.endsWith(".png") -> "texture." + path.removePrefix("textures/")
            path.endsWith(".png") && !path.contains("/") -> "texture.$path"
            else -> null
        }
    }

    private fun mapFileToResourcePath(fileName: String): String? {
        return when {
            fileName.startsWith("blockstate.")
                    && fileName.endsWith(".json") -> "blockstates/custom." + fileName.removePrefix("blockstate.")
            fileName.startsWith("block.model.")
                    && fileName.endsWith(".json") -> "models/block/custom." + fileName.removePrefix("block.model.")
            fileName.startsWith("items.") && fileName.endsWith(".json") -> "items/custom." + fileName.removePrefix("items.")
            fileName.startsWith("item.model.")
                    && fileName.endsWith(".json") -> "models/item/custom." + fileName.removePrefix("item.model.")
            fileName.startsWith("texture.")
                    && fileName.endsWith(".png") -> "textures/" + fileName.removePrefix("texture.")
            else -> null
        }
    }
}