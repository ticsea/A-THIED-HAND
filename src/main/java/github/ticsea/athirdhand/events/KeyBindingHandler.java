package github.ticsea.athirdhand.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import github.ticsea.athirdhand.client.ModKeyBinding;
import github.ticsea.athirdhand.config.ModConfig;
import github.ticsea.athirdhand.util.Progress;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FrameTimer;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Post;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import org.slf4j.Logger;

public class KeyBindingHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    //    private static final Progress MSG_PROGRESS = new Progress(60000);
    private static final KeyMapping KEY = ModKeyBinding.getKey();
    private static final BooleanValue SWITCH = ModConfig.getSwitch();

    private static final Component MSG_ENABLED = Component.translatable("athirdhand.state.on");
    private static final Component MSG_DISABLED = Component.translatable("athirdhand.state.off");
    private static final int MSG_COLOR = 0xFFFFFF;
    private static final int MSG_SHADOW = 1;
    private static final int MSG_DURATION = 60;

    static final Minecraft mc = Minecraft.getInstance();
    static Font font = mc.font;
    static Window window = mc.getWindow();
    static int messageX = window.getGuiScaledWidth() / 2 - 22;
    static int messageY = window.getGuiScaledHeight() - 56;

    static long time;
    static long showMessageUntilTick = 0;

    public static void onKeyPress(ClientTickEvent event) {
        if (event.phase!=Phase.END) return;

        while (KEY.consumeClick()) {
            SWITCH.set(!SWITCH.get());

            if (mc.level!=null) {
                showMessageUntilTick = mc.level.getGameTime() + MSG_DURATION;
                LOGGER.debug("TELL ME THE TIEM: {}", (mc.level.getGameTime() * 50L + mc.getDeltaFrameTime() * 50L));
            }
        }
    }

    public static void renderToggleMessage(Post event) {
        while (mc.level==null || mc.level.getGameTime() >= showMessageUntilTick) return;

        Component message = SWITCH.get() ? MSG_ENABLED:MSG_DISABLED;
        event.getGuiGraphics().drawString(font, message, messageX, messageY, MSG_COLOR, MSG_SHADOW!=0);
    }
}
