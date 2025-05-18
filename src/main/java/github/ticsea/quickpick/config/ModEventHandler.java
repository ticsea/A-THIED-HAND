package github.ticsea.quickpick.config;

import github.ticsea.quickpick.Main;
import github.ticsea.quickpick.config.ModConfigs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        ModConfigs.load();
    }

    @SubscribeEvent
    public static void onSave(final ModConfigEvent.Reloading event) {
        // 自动处理，如果有变更再保存
    }
}
