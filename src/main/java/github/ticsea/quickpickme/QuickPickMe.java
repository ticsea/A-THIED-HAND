// Copyright © 2025 ticsea. ALL RIGHTS RESERVED.
// Unauthorized use, copying or modification is prohibited.

package github.ticsea.quickpickme;

import static github.ticsea.quickpickme.config.ModConfig.CONFIG;

import github.ticsea.quickpickme.events.KeybindHandler;
import github.ticsea.quickpickme.events.PlayerOpenContainerHandler;
import github.ticsea.quickpickme.gui.ModConfigScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(QuickPickMe.MODID)
public class QuickPickMe {
    public static final String MODID = "quickpickme";

    public QuickPickMe(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        IEventBus forgeEvenBus = MinecraftForge.EVENT_BUS;
        if (FMLEnvironment.dist==Dist.CLIENT) {
            modEventBus.addListener(ModKeybind::registerKeybind);
            forgeEvenBus.addListener(KeybindHandler::onKeyInput);
            forgeEvenBus.addListener(KeybindHandler::renderToggleMessage);

            buildConfigScreen(context);
        }
        forgeEvenBus.addListener(PlayerOpenContainerHandler::onPlayerOpenContainer);

        context.registerConfig(ModConfig.Type.CLIENT, CONFIG);
    }

    private static void buildConfigScreen(FMLJavaModLoadingContext context) {
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, parent) -> new ModConfigScreen(Component.literal("Config"), parent)
                )
        );
    }
}
