package github.ticsea.quickpick.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ModConfig {
    public static final BooleanValue MODSTATUS_TOGGLE, BACKPACKSTATUS, TOUHOU_LITTLE_MAID_STATUS;
    public static final ForgeConfigSpec CONFIG;
    private static final ForgeConfigSpec.Builder BUILDER;

    public static boolean getBackpackStatus() {
        return BACKPACKSTATUS.get();
    }

    public static boolean getLittleMaidStatus() {
        return TOUHOU_LITTLE_MAID_STATUS.get();
    }

    public static void backpackToggle() {
        BACKPACKSTATUS.set(!BACKPACKSTATUS.get());
    }

    public static void littleMaidToggle() {
        TOUHOU_LITTLE_MAID_STATUS.set(!TOUHOU_LITTLE_MAID_STATUS.get());
    }

    private static BooleanValue register(String comment, String name) {
        return BUILDER
                .comment(comment)
                .define(name, true);
    }

    static {
        BUILDER = new ForgeConfigSpec.Builder();
        MODSTATUS_TOGGLE = register(
                "控制此mod的启用与否\n[true, false]",
                "mod_toggle"
        );
        BACKPACKSTATUS = register(
                "是否适用于精致存储\n[true, false]",
                        "backpack"
        );
        TOUHOU_LITTLE_MAID_STATUS = register(
                "是否适用于车万女仆\n[true, false]",
                "touhou-little-maid"
        );

        CONFIG = BUILDER.build();
    }
}
