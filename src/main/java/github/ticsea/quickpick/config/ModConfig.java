package github.ticsea.quickpick.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ModConfig {
    public static final BooleanValue MODTOGGLE_STATE, BACKPACK_STATE, TOUHOU_LITTLE_MAID_STATE;
    public static final ForgeConfigSpec CONFIG;
    private static final ForgeConfigSpec.Builder BUILDER;

    static {
        BUILDER = new ForgeConfigSpec.Builder();
        MODTOGGLE_STATE = register(
                "是否启用mod\n[true, false]",
                "mod_toggle"
        );
        BACKPACK_STATE = register(
                "是否适用于精致背包\n[true, false]",
                        "backpack"
        );
        TOUHOU_LITTLE_MAID_STATE = register(
                "是否适用于车万女仆\n[true, false]",
                "touhou-little-maid"
        );

        CONFIG = BUILDER.build();
    }

    private static BooleanValue register(String comment, String name) {
        return BUILDER
                .comment(comment)
                .define(name, true);
    }
}
