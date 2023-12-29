package com.algorithmlx.dimore.api.config

import net.minecraftforge.fml.ModList
import org.objectweb.asm.Type
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object ConfigFinder {
    val logger: Logger = LoggerFactory.getLogger("Dimensional Ores Config System")

    @JvmStatic
    fun getConfigList(): List<ICfg> = findInstances(CfgFile::class.java, ICfg::class.java)

    private fun <T> findInstances(annotation: Class<*>, instance: Class<T>): List<T> {
        val annotType = Type.getType(annotation)
        val scanDatas = ModList.get().allScanData
        val configNames: MutableSet<String> = linkedSetOf()

        for (data in scanDatas) {
            val annots = data.annotations
            for (annot in annots) {
                if (Objects.equals(annot.annotationType, annotType)) {
                    val name = annot.memberName
                    configNames.add(name)
                }
            }
        }

        val instances: MutableList<T> = arrayListOf()
        for (clazzName in configNames) {
            try {
                val clazz = Class.forName(clazzName)
                val instanceClass = clazz.asSubclass(instance)
                val inst: T = instanceClass.getDeclaredConstructor().newInstance()
                instances.add(inst)
            } catch (e: ReflectiveOperationException) {
                logger.error("Failed to load: {}", clazzName, e)
            } catch (e: LinkageError) {
                logger.error("Failed to load: {}", clazzName, e)
            }
        }

        return instances
    }
}