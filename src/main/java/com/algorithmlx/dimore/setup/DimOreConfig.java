package com.algorithmlx.dimore.setup;

import net.minecraftforge.common.ForgeConfigSpec;

public class DimOreConfig {
    public static final ForgeConfigSpec.Builder DIMORE_CONFIG = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec GEN;

    static {
        DIMORE_CONFIG.push("DimOre Config");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherOres = DIMORE_CONFIG.comment("Generation Nether ores").define("genNetherOres", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndOres = DIMORE_CONFIG.comment("Generation End ores").define("genEndOres", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Nether Ores");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherCoalOre = DIMORE_CONFIG.comment("Generation Nether Coal ore").define("genNetherCoalOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherIronOre = DIMORE_CONFIG.comment("Generation Nether Iron ore").define("genNetherIronOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherRedstoneOre = DIMORE_CONFIG.comment("Generation Nether Redstone ore").define("genNetherRedstoneOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherDiamondOre = DIMORE_CONFIG.comment("Generation Nether Diamond ore").define("genNetherDiamondOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> getNetherEmeraldOre = DIMORE_CONFIG.comment("Generation Nether Emerald Ore").define("genNetherEmeraldOre", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: End Ores");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndCoalOre = DIMORE_CONFIG.comment("Generation End Coal ore").define("genEndCoalOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndIronOre = DIMORE_CONFIG.comment("Generation End Iron ore").define("genEndIronOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndGoldenOre = DIMORE_CONFIG.comment("Generation End Golden ore").define("genEndGoldenOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndRedstoneOre = DIMORE_CONFIG.comment("Generation End Redstone ore").define("genEndRedstoneOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndDiamondOre = DIMORE_CONFIG.comment("Generation End Diamond ore").define("genEndDiamondOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndEmeraldOre = DIMORE_CONFIG.comment("Generation End Emerald Ore").define("genEndEmeraldOre", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Integration");
    }
    public static final ForgeConfigSpec.ConfigValue<Boolean> allowIntegration = DIMORE_CONFIG.comment("Allow Mods Integration").define("allowIntegration", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherCopperOre = DIMORE_CONFIG.comment("Generation Nether Copper Ore (Works only if allowIntegration = true)").define("genNetherCopperOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherTinOre = DIMORE_CONFIG.comment("Generation Nether Tin Ore (Works only if allowIntegration = true)").define("genNetherCopperOre", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndCopperOre = DIMORE_CONFIG.comment("Generation End Copper Ore (Works only if allowIntegration = true)").define("genEndCopperOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndTinOre = DIMORE_CONFIG.comment("Generation End Copper Ore (Works only if allowIntegration = true)").define("genEndTinOre", true);

    static {
        DIMORE_CONFIG.pop();
        GEN = DIMORE_CONFIG.build();
    }
}
