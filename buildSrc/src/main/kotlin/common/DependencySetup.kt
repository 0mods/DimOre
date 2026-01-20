package common

import ModProject
import org.gradle.kotlin.dsl.DependencyHandlerScope

interface DependencySetup {
    fun DependencyHandlerScope.setup(minecraftVersion: String, modProject: ModProject)
}