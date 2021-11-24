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
    public static final ForgeConfigSpec.ConfigValue<Boolean> allowIntegration = DIMORE_CONFIG.comment("Allow Mods Integration").define("allowIntegration", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> checkUpdates = DIMORE_CONFIG.comment("Turn On Checking mod Updates").define("checkUpdates", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Nether Ores");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherCoalOre = DIMORE_CONFIG.comment("Generation Nether Coal ore").define("genNetherCoalOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherIronOre = DIMORE_CONFIG.comment("Generation Nether Iron ore").define("genNetherIronOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherLapisOre = DIMORE_CONFIG.comment("Generation Nether Lapis Lazuli ore").define("genNetherLapisOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherRedstoneOre = DIMORE_CONFIG.comment("Generation Nether Redstone ore").define("genNetherRedstoneOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherDiamondOre = DIMORE_CONFIG.comment("Generation Nether Diamond ore").define("genNetherDiamondOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> getNetherEmeraldOre = DIMORE_CONFIG.comment("Generation Nether Emerald Ore").define("genNetherEmeraldOre", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: End Ores");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndQuartzOre = DIMORE_CONFIG.comment("Generation Quartz ore").define("genEndQuartzOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndCoalOre = DIMORE_CONFIG.comment("Generation End Coal ore").define("genEndCoalOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndIronOre = DIMORE_CONFIG.comment("Generation End Iron ore").define("genEndIronOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndGoldenOre = DIMORE_CONFIG.comment("Generation End Golden ore").define("genEndGoldenOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndLapisOre = DIMORE_CONFIG.comment("Generation End Lapis Lazuli ore").define("genEndLapisOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndRedstoneOre = DIMORE_CONFIG.comment("Generation End Redstone ore").define("genEndRedstoneOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndDiamondOre = DIMORE_CONFIG.comment("Generation End Diamond ore").define("genEndDiamondOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndEmeraldOre = DIMORE_CONFIG.comment("Generation End Emerald Ore").define("genEndEmeraldOre", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Generation Range");
    }

    public static final ForgeConfigSpec.IntValue genQuartzRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Quartz ore").defineInRange("genQuartzRange", 15, 1, 100);
    public static final ForgeConfigSpec.IntValue genCoalRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Coal ore").defineInRange("genCoalRange", 40, 1, 100);
    public static final ForgeConfigSpec.IntValue genIronRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Iron ore").defineInRange("genIronRange", 35, 1, 100);
    public static final ForgeConfigSpec.IntValue genGoldenRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Iron ore").defineInRange("genGoldenRange", 25, 1, 100);
    public static final ForgeConfigSpec.IntValue genLapisRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Lapis Lazuli ore").defineInRange("genLapisRange", 29,1, 100);
    public static final ForgeConfigSpec.IntValue genRedstoneRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Redstone ore").defineInRange("genRedstoneRange", 20, 1, 100);
    public static final ForgeConfigSpec.IntValue genDiamondRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Diamond ore").defineInRange("genDiamondRange", 8, 1, 100);
    public static final ForgeConfigSpec.IntValue genEmeraldRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Emerald ore").defineInRange("genEmeraldRange", 5, 1, 100);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Integration");
    }

    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherCopperOre = DIMORE_CONFIG.comment("Generation Nether Copper Ore (Works only if allowIntegration = true)").define("genNetherCopperOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genNetherTinOre = DIMORE_CONFIG.comment("Generation Nether Tin Ore (Works only if allowIntegration = true)").define("genNetherTinOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndCopperOre = DIMORE_CONFIG.comment("Generation End Copper Ore (Works only if allowIntegration = true)").define("genEndCopperOre", true);
    public static final ForgeConfigSpec.ConfigValue<Boolean> genEndTinOre = DIMORE_CONFIG.comment("Generation End Copper Ore (Works only if allowIntegration = true)").define("genEndTinOre", true);

    static {
        DIMORE_CONFIG.pop();
        DIMORE_CONFIG.push("DimOre Config: Range Integration Gen");
    }

    public static final ForgeConfigSpec.IntValue genCopperRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Copper ore").defineInRange("genCopperRange", 25, 1, 100);
    public static final ForgeConfigSpec.IntValue genTinRange = DIMORE_CONFIG.comment("Setting number in generation DimOre Tin ore").defineInRange("genTinRange", 25, 1, 100);

    static {
        DIMORE_CONFIG.pop();
        GEN = DIMORE_CONFIG.build();
    }
}
