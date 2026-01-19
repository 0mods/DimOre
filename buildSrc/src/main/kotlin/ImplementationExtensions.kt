import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderConvertible
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.modImplementation(dependency: String) = "modImplementation"(dependency)
fun <T: ModuleDependency> DependencyHandlerScope.modImplementation(dependency: T, configuration: T.() -> Unit) = "modImplementation"(dependency, configuration)
fun DependencyHandlerScope.modImplementation(dependency: Any) = "modImplementation"(dependency)
fun DependencyHandlerScope.modImplementation(dependency: String, dependencyConfiguration: ExternalModuleDependency.() -> Unit) = "modImplementation"(dependency, dependencyConfiguration)
fun <T: Any> DependencyHandlerScope.modImplementation(dependency: Provider<T>) = "modImplementation"(dependency)
fun <T: Any> DependencyHandlerScope.modImplementation(dependency: Provider<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) = "modImplementation"(dependency, dependencyConfiguration)
fun <T: Any> DependencyHandlerScope.modImplementation(dependency: ProviderConvertible<T>) = "modImplementation"(dependency)
fun <T: Any> DependencyHandlerScope.modImplementation(dependency: ProviderConvertible<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) = "modImplementation"(dependency, dependencyConfiguration)

fun DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: String) {
    if (sc.modPlatform == platform)
        "implementation"(dependency)
}
fun <T: ModuleDependency> DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: T, configuration: T.() -> Unit) {
    if (sc.modPlatform == platform)
        "implementation"(dependency, configuration)
}
fun DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Any) {
    if (sc.modPlatform == platform)
        "implementation"(dependency)
}
fun DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: String, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform)
        "implementation"(dependency, dependencyConfiguration)
}
fun <T: Any> DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Provider<T>) {
    if (sc.modPlatform == platform)
        "implementation"(dependency)
}
fun <T: Any> DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Provider<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform)
        "implementation"(dependency, dependencyConfiguration)
}
fun <T: Any> DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: ProviderConvertible<T>) {
    if (sc.modPlatform == platform)
        "implementation"(dependency)
}

fun <T: Any> DependencyHandlerScope.platformImplementation(sc: StonecutterBuildExtension, platform: String, dependency: ProviderConvertible<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform)
        "implementation"(dependency, dependencyConfiguration)
}

fun DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: String) {
    if (sc.modPlatform == platform) modImplementation(dependency)
}
fun <T: ModuleDependency> DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: T, configuration: T.() -> Unit) {
    if (sc.modPlatform == platform) modImplementation(dependency, configuration)
}
fun DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Any) {
    if (sc.modPlatform == platform) modImplementation(dependency)
}
fun DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: String, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform) modImplementation(dependency, dependencyConfiguration)
}
fun <T: Any> DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Provider<T>) {
    if (sc.modPlatform == platform) modImplementation(dependency)
}
fun <T: Any> DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: Provider<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform) modImplementation(dependency, dependencyConfiguration)
}
fun <T: Any> DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: ProviderConvertible<T>) {
    if (sc.modPlatform == platform) modImplementation(dependency)
}

fun <T: Any> DependencyHandlerScope.platformModImplementation(sc: StonecutterBuildExtension, platform: String, dependency: ProviderConvertible<T>, dependencyConfiguration: ExternalModuleDependency.() -> Unit) {
    if (sc.modPlatform == platform) modImplementation(dependency, dependencyConfiguration)
}

fun DependencyHandlerScope.fabricImplementation(sc: StonecutterBuildExtension, dependency: String) = platformImplementation(sc, "fabric", dependency)
fun DependencyHandlerScope.forgeImplementation(sc: StonecutterBuildExtension, dependency: String) = platformImplementation(sc, "forge", dependency)
fun DependencyHandlerScope.neoforgeImplementation(sc: StonecutterBuildExtension, dependency: String) = platformImplementation(sc, "neoforge", dependency)

fun DependencyHandlerScope.fabricModImplementation(sc: StonecutterBuildExtension, dependency: String) = platformModImplementation(sc, "fabric", dependency)
fun DependencyHandlerScope.forgeModImplementation(sc: StonecutterBuildExtension, dependency: String) = platformModImplementation(sc, "forge", dependency)
fun DependencyHandlerScope.neoforgeModImplementation(sc: StonecutterBuildExtension, dependency: String) = platformModImplementation(sc, "neoforge", dependency)

fun DependencyHandlerScope.compileOnlyMinecraft(dependency: Any) {
    "compileOnly"(dependency)
    "minecraftRuntimeLibraries"(dependency)
}
