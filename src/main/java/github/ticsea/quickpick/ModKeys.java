package github.ticsea.quickpick;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeys {

    //  mod开关按键
    public static final Lazy<KeyMapping> MOD_ON_OFF_KEY = Lazy.of(() ->
            new KeyMapping(
                    "key.quickpickme.mod_on_off",
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    "key.categories.quickpickme"
            ));
    //  mod的GUI打开按键
    public static final Lazy<KeyMapping> MOD_SCREEN_KEY = Lazy.of(() ->
            new KeyMapping(
                    "key.quickpickme.mod_sreen",
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    "key.categories.quickpickme"
            ));

    //  注册按键到游戏
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(MOD_ON_OFF_KEY.get());
        event.register(MOD_SCREEN_KEY.get());
    }
}
