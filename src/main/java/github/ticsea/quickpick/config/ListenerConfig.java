package github.ticsea.quickpick.config;

import github.ticsea.quickpick.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ListenerConfig {
//    public static boolean ;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

//    private static final ForgeConfigSpec.BooleanValue  = BUILDER
//            .comment("控制mod启用与否")
//            .define("isQuickPickEnabled", true);

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }

    public static final ForgeConfigSpec INIT = BUILDER.build();
}
