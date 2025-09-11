package github.ticsea.athirdhand.events;

import static github.ticsea.athirdhand.client.ModKeybind.MOD_TOGGLE_KEY;

import com.mojang.blaze3d.platform.Window;
import github.ticsea.athirdhand.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;

public class KeybindHandler {

    private static final Component MSG_ENABLED = Component.translatable("athirdhand.state.on");
    private static final Component MSG_DISABLED = Component.translatable("athirdhand.state.off");

    private static final int DISPLAY_DURATION_TICKS = 60;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int TEXT_SHADOW = 1;

    private static long showMessageUntilTick = 0;

    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if (event.phase!=TickEvent.Phase.END) return;

        while (MOD_TOGGLE_KEY.get().consumeClick()) {
            ModConfig.HAND.set(!ModConfig.HAND.get());
            Minecraft mc = Minecraft.getInstance();

            if (mc.level!=null) {
                showMessageUntilTick = mc.level.getGameTime() + DISPLAY_DURATION_TICKS;
            }
        }
    }

    public static void renderToggleMessage(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level==null || mc.level.getGameTime() >= showMessageUntilTick) return;

        Font font = mc.font;
        Window window = mc.getWindow();

        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int messageX = screenWidth / 2 - 22;
        int messageY = screenHeight - 56;

        Component message = ModConfig.HAND.get() ? MSG_ENABLED:MSG_DISABLED;
        event.getGuiGraphics().drawString(font, message, messageX, messageY, TEXT_COLOR, TEXT_SHADOW!=0);
    }
}
