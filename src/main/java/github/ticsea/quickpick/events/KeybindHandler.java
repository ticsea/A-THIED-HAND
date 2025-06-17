package github.ticsea.quickpick.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import static github.ticsea.quickpick.ModKeybind.MOD_TOGGLE_KEY;
import static github.ticsea.quickpick.config.ModConfig.MODSTATUS_TOGGLE;

public class KeybindHandler {
    private static final Component MSGTRUE = Component.literal("Quick Pick: ON");
    private static final Component MSGFALSE = Component.literal("Quick Pick: OFF");
    private static long showMsgUntilTick = 0; // 到哪个 tick 前显示
    private static final int DURATION = 60;  // 显示时长，单位 tick（60 = 3 秒左右）

    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if (event.phase==TickEvent.Phase.END && MOD_TOGGLE_KEY.get().consumeClick()) {
            toggleState();

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
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        int x = width / 2 - 22;
        int y = height - 56;
        Component msg = isEnabled() ? MSGTRUE:MSGFALSE;
        event.getGuiGraphics().drawString(font, msg, x, y, 0xffffff, true);
    }

    private static void toggleState() {
        MODSTATUS_TOGGLE.set(!MODSTATUS_TOGGLE.get());
    }

    public static boolean isEnabled() {
        return MODSTATUS_TOGGLE.get();
    }
}