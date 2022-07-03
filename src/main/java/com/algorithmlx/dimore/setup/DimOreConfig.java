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
    }

    static {
        DIMORE_CONFIG.pop();
        GEN = DIMORE_CONFIG.build();
    }
}
