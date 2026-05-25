package com.algorithmlx.dimore.init.resource

import com.algorithmlx.dimore.ModId
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.AbstractPackResources
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.IoSupplier
import java.io.File
import java.io.InputStream

class DimOreResourcePack(location: PackLocationInfo) : AbstractPackResources(location) {
    override fun getRootResource(vararg path: String): IoSupplier<InputStream>? = null

    override fun getResource(
        type: PackType,
        location: Identifier
    ): IoSupplier<InputStream>? {
        if (location.namespace != ModId) return null

        val path = location.path
        if (!path.contains("generated.")) return null

        val relative = when {
            path.startsWith("models/") -> path.substring("models/".length)
            path.startsWith("textures/") -> path.substring("textures/".length)
            path.startsWith("model/") -> path.substring("model/".length)
            else -> path
        }

        val target = File("config/$ModId/custom/client/$relative")
        if (target.exists()) {
            return IoSupplier { target.inputStream() }
        }

        return null
    }

    override fun listResources(
        type: PackType,
        namespace: String,
        directory: String,
        output: PackResources.ResourceOutput
    ) {}

    override fun getNamespaces(type: PackType): Set<String> = setOf(ModId)

    override fun close() {}
}