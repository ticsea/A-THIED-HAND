
package github.ticsea.athirdhand.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ModConfig {
    public static final BooleanValue HAND;
    public static final ForgeConfigSpec CONFIG;
    private static final ForgeConfigSpec.Builder BUILDER;

    static {
        BUILDER = new ForgeConfigSpec.Builder();
        HAND = register(
                "是否启用mod\n[true, false]",
                "mod_toggle"
        );

        CONFIG = BUILDER.build();
    }

    private static BooleanValue register(String comment, String name) {
        return BUILDER
                .comment(comment)
                .define(name, true);
    }
}
