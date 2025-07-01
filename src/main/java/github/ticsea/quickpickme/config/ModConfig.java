package github.ticsea.quickpickme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ModConfig {

    public static final ForgeConfigSpec CONFIG;

    public static final BooleanValue ENABLE_MOD;
    public static final BooleanValue ENABLE_BACKPACK_SUPPORT;
    public static final BooleanValue ENABLE_TOUHOU_LITTLE_MAID_SUPPORT;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("QuickPick Settings");

        ENABLE_MOD = builder
                .comment("Enable or disable the QuickPick mod globally.\n[true | false]")
                .define("enable_mod", true);

        ENABLE_BACKPACK_SUPPORT = builder
                .comment("Enable support for Sophisticated Backpacks.\n[true | false]")
                .define("enable_backpack_support", true);

        ENABLE_TOUHOU_LITTLE_MAID_SUPPORT = builder
                .comment("Enable support for Touhou Little Maid mod.\n[true | false]")
                .define("enable_touhou_little_maid_support", true);

        builder.pop();

        CONFIG = builder.build();
    }
}
