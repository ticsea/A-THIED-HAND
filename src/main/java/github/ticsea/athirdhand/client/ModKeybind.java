package github.ticsea.athirdhand.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeybind {
    public static final Lazy<KeyMapping> MOD_TOGGLE_KEY;

    static {
        MOD_TOGGLE_KEY = keyFactory(
                "key.athirdhand.switch",
                GLFW.GLFW_KEY_H
        );
    }

    public static void registerKeybind(RegisterKeyMappingsEvent event) {
        event.register(MOD_TOGGLE_KEY.get());
    }

    private static Lazy<KeyMapping> keyFactory(String description, int key) {
        return Lazy.of(() ->
                new KeyMapping(
                        description,
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        key,
                        "key.categories.athirdhand"
                ));
    }
}
