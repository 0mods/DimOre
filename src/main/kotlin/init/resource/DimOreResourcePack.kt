package com.algorithmlx.dimore.init.resource

import com.algorithmlx.dimore.ModId
import com.google.gson.JsonObject
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackSelectionConfig
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.PathPackResources
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.repository.PackSource
import net.minecraft.server.packs.resources.IoSupplier
import java.io.InputStream
import java.nio.file.Path
import java.util.Optional
import kotlin.io.path.Path
import kotlin.io.path.exists

class DimOreResourcePack(location: PackLocationInfo, root: Path) : PathPackResources(location, root) {
    private val packMetadata: String = JsonObject().apply {
        add("pack", JsonObject().apply {
            addProperty("description", "$ModId Directory Pack Resources")
            addProperty("pack_format", 48)
        })
    }.toString()

    override fun getRootResource(vararg path: String): IoSupplier<InputStream>? = when (path[0]) {
        PACK_META -> IoSupplier { packMetadata.byteInputStream() }
        else -> return super.getRootResource(*path)
    }

    companion object {
        @JvmStatic
        val asPack: Pack? get() {
            val packInfo = PackLocationInfo(
                "${ModId}_generated",
                Component.literal("$ModId Directory Pack Resources"),
                PackSource.SERVER,
                Optional.empty()
            )

            val packPath = Path("${ModId}_generated")
            if (!packPath.exists())
                packPath.toFile().mkdirs()

            val resourceSupplier = object: Pack.ResourcesSupplier {
                override fun openPrimary(location: PackLocationInfo): PackResources = DimOreResourcePack(
                    location, packPath
                )

                override fun openFull(
                    location: PackLocationInfo,
                    metadata: Pack.Metadata
                ): PackResources = DimOreResourcePack(location, packPath)
            }

            return Pack.readMetaAndCreate(packInfo, resourceSupplier, PackType.SERVER_DATA,
                PackSelectionConfig(true, Pack.Position.TOP, true)
            )
        }
    }
}
