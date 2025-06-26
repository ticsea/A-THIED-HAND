package github.ticsea.quickpick.events;

import com.mojang.blaze3d.platform.Window;
import github.ticsea.quickpick.util.ConfigHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import static github.ticsea.quickpick.Main.MODID;
import static github.ticsea.quickpick.ModKeybind.MOD_TOGGLE_KEY;

@EventBusSubscriber(modid = MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class KeybindHandler {
    private static final Component MSGTRUE = Component.literal("Quick Pick: ON");
    private static final Component MSGFALSE = Component.literal("Quick Pick: OFF");
    private static long showMsgUntilTick = 0; // 到哪个 tick 前显示
    private static final int DURATION = 60;  // 显示时长，单位 tick（60 = 3 秒左右）

    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if (event.phase==TickEvent.Phase.END && MOD_TOGGLE_KEY.get().consumeClick()) {
            ConfigHelper.modToggle();

            assert Minecraft.getInstance().level!=null;
            showMsgUntilTick = Minecraft.getInstance().level.getGameTime() + DURATION;
        }
    }

    public static void renderToggle(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        assert mc.level!=null;
        long currentTick = mc.level.getGameTime();
        if (currentTick >= showMsgUntilTick) return;

        Font font = mc.font;
        Window window = mc.getWindow();
        int width = window.getGuiScaledWidth(), height = window.getGuiScaledHeight();
        int x = width / 2 - 22, y = height - 56;
        Component msg = ConfigHelper.isMODEnable() ? MSGTRUE:MSGFALSE;
        event.getGuiGraphics().drawString(font, msg, x, y, 0xffffff, true);
    }
}