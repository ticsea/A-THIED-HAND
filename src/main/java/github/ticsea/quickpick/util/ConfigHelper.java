package github.ticsea.quickpick.util;

import static github.ticsea.quickpick.config.ModConfig.BACKPACK_STATE;
import static github.ticsea.quickpick.config.ModConfig.MODTOGGLE_STATE;
import static github.ticsea.quickpick.config.ModConfig.TOUHOU_LITTLE_MAID_STATE;

public class ConfigHelper {

    public static boolean isMODEnable() {
        return MODTOGGLE_STATE.get();
    }

    public static boolean isBackpackEnable() {
        return BACKPACK_STATE.get();
    }

    public static boolean isLittleMaidEnable() {
        return TOUHOU_LITTLE_MAID_STATE.get();
    }

    public static void modToggle() {
        MODTOGGLE_STATE.set(!MODTOGGLE_STATE.get());
    }

    public static void backpackToggle() {
        BACKPACK_STATE.set(!BACKPACK_STATE.get());
    }

    public static void littleMaidToggle() {
        TOUHOU_LITTLE_MAID_STATE.set(!TOUHOU_LITTLE_MAID_STATE.get());
    }
}
