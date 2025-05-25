package github.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import github.ticsea.quickpick.Main;

import github.ticsea.quickpick.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import java.util.concurrent.atomic.AtomicBoolean;
import static github.ticsea.quickpick.ModKeys.MOD_ON_OFF_KEY;
import static github.ticsea.quickpick.config.ModConfig.MOD_SWITCH;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyTriggerEvents {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static int msgTick;
    private static final Component MSGTRUE = Component.literal("quickpick:已启用");
    private static final Component MSGFALSE = Component.literal("quickpick:已关闭");

    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && MOD_ON_OFF_KEY.get().consumeClick()) {
            MOD_SWITCH.set(!MOD_SWITCH.get());//切换开关
            msgTick = 6000;
            LOGGER.debug("QuickPick 功能状态切换: {}", MOD_SWITCH.get() ? "禁用" : "启用");
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            Font font = mc.font;
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            int x = width / 2 - 3 * 18;
            int y = height - 2 * 18;

        if (msgTick > 0) {
            Component msg = isEnabled() ? MSGTRUE : MSGFALSE;
            event.getGuiGraphics().drawString(font, msg, x, y, 0xffffff, false);
            msgTick = Math.max(0, msgTick - 1);
            LOGGER.debug("tick时间: " + msgTick);
        }
    }

    public static boolean isEnabled() {
        return MOD_SWITCH.get();
    }
}