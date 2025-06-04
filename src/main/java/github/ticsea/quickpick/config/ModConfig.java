package github.ticsea.quickpick.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.BooleanValue MOD_TOGGLE = BUILDER
            .comment("控制此mod的启用与否\n[true, false]")
            .define("mod_toggle", true);

    public static final ForgeConfigSpec CONFIG = BUILDER.build();
}
