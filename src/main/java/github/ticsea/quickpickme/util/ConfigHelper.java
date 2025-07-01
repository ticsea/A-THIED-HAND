package github.ticsea.quickpickme.util;

import github.ticsea.quickpickme.config.ModConfig;

public class ConfigHelper {

    public static class ModQuickPickMe {
        public static boolean isModEnabled() {
            return ModConfig.ENABLE_MOD.get();
        }

        public static void toggleMod() {
            ModConfig.ENABLE_MOD.set(!ModConfig.ENABLE_MOD.get());
        }
    }

    public static class TouhouLittleMaid {
        public static boolean isLittleMaidSupportEnabled() {
            return ModConfig.ENABLE_TOUHOU_LITTLE_MAID_SUPPORT.get();
        }

        public static void toggleLittleMaidSupport() {
            ModConfig.ENABLE_TOUHOU_LITTLE_MAID_SUPPORT.set(!ModConfig.ENABLE_TOUHOU_LITTLE_MAID_SUPPORT.get());
        }
    }

    public static class SophisticatedBackpacks {
        public static boolean isBackpackSupportEnabled() {
            return ModConfig.ENABLE_BACKPACK_SUPPORT.get();
        }

        public static void toggleBackpackSupport() {
            ModConfig.ENABLE_BACKPACK_SUPPORT.set(!ModConfig.ENABLE_BACKPACK_SUPPORT.get());
        }
    }
}
