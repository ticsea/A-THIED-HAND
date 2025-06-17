// Copyright © 2025 ticsea. ALL RIGHTS RESERVED.
// Unauthorized use, copying or modification is prohibited.

package github.ticsea.quickpick;

import static github.ticsea.quickpick.config.ModConfig.CONFIG;

import github.ticsea.quickpick.events.KeybindHandler;
import github.ticsea.quickpick.events.PlayerOpenContainerHandler;
import github.ticsea.quickpick.gui.ModConfigScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "quickpickme";

    public Main(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        IEventBus forgeEvenBus = MinecraftForge.EVENT_BUS;
        if (FMLEnvironment.dist==Dist.CLIENT) {
            modEventBus.addListener(ModKeybind::registerKeybind);
            forgeEvenBus.addListener(KeybindHandler::onKeyInput);
            forgeEvenBus.addListener(KeybindHandler::renderToggle);

            buildConfigScreen(context);
        }
        forgeEvenBus.addListener(PlayerOpenContainerHandler::onPlayerOpenChest);

        context.registerConfig(ModConfig.Type.CLIENT, CONFIG);
    }

    private static void buildConfigScreen(FMLJavaModLoadingContext context) {
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, paren) -> new ModConfigScreen(Component.literal("Config"), paren)
                )
        );
    }

}
