package github.ticsea.quickpick.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.BooleanValue MOD_SWITCH = BUILDER
            .comment("mod的开关")
            .define("mod_swith", true);


    public static final ForgeConfigSpec CONFIG = BUILDER.build();

    public static boolean getValue() {
        return MOD_SWITCH.get();
    }
}
