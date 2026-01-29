import ModProject.ModPlatform

val defaultModLoaderVersions = mutableMapOf(
    ModPlatform.FABRIC to mutableMapOf(
        "loader" to "0.17.0", // or can use {mc-ver}-loader
        "1.19.2-api" to "0.77.0+1.19.2",
        "1.20.1-api" to "0.92.2+1.20.1",
        "1.21-api" to "0.102.0+1.21",
        "1.21.1-api" to "0.116.4+1.21.1",
        "1.21.2-api" to "0.106.1+1.21.2",
        "1.21.3-api" to "0.114.1+1.21.3",
        "1.21.4-api" to "0.119.4+1.21.4",
        "1.21.5-api" to "0.128.2+1.21.5",
        "1.21.6-api" to "0.128.2+1.21.6",
        "1.21.7-api" to "0.129.0+1.21.7",
        "1.21.8-api" to "0.136.1+1.21.8",
        "1.21.9-api" to "0.134.1+1.21.9",
        "1.21.10-api" to "0.138.4+1.21.10",
        "1.21.11-api" to "0.141.1+1.21.11"
    ),
    // forge is dead lol. no more versions need
    ModPlatform.FORGE to mutableMapOf(
        "1.19.2" to "1.19.2-43.4.2",
        "1.20.1" to "1.20.1-47.4.3",
        "1.21" to "1.21-51.0.8",
    ),
    ModPlatform.NEOFORGE to mutableMapOf(
        "1.21" to "21.0.167",
        "1.21.1" to "21.1.197",
        "1.21.2" to "21.2.1-beta",
        "1.21.3" to "21.3.95",
        "1.21.4" to "21.4.156",
        "1.21.5" to "21.5.96",
        "1.21.6" to "21.6.20-beta",
        "1.21.7" to "21.7.25-beta",
        "1.21.8" to "21.8.52",
        "1.21.9" to "21.9.16-beta",
        "1.21.10" to "21.10.64",
        "1.21.11" to "21.11.36-beta"
    )
)

val defaultMappingsVersion = mutableMapOf(
    "1.19.2" to "2022.11.27",
    "1.20.1" to "2023.09.03",
    "1.21" to "2024.07.28",
    "1.21.1" to "2024.11.17",
    "1.21.3" to "2024.12.07",
    "1.21.4" to "2025.03.23",
    "1.21.5" to "2025.06.15",
    "1.21.6" to "2025.06.29",
    "1.21.7" to "2025.07.18",
    "1.21.8" to "2025.09.14",
    "1.21.9" to "2025.10.05",
    "1.21.10" to "2025.10.12"
)

class ModProject(
    val modId: String,
    val modName: String,
    val modVersion: String,
    val license: String,

    val description: String = "",
    val authors: List<String> = emptyList(),

    val entryPoints: Map<String, List<String>>,
    val dependencies: Map<String, Map<String, String>>,

    val modLoaderVersions: Map<ModPlatform, Map<String, String>> = defaultModLoaderVersions,
    val mappingsVersion: Map<String, String> = defaultMappingsVersion,
    val forgeLoaderName: String = "javafml",
    val forgeLoaderVersion: String = "[47,)",

    val neoforgeLoaderName: String = "javafml",
    val neoforgeLoaderVersion: String = "[2,)",

    val username: String = "Player259"
) {
    enum class ModPlatform {
        FABRIC, NEOFORGE, FORGE
    }
}
