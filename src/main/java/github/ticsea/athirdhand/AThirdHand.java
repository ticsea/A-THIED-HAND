// Copyright © 2025 ticsea. ALL RIGHTS RESERVED.
// Unauthorized use, copying or modification is prohibited.

package github.ticsea.athirdhand;

import static github.ticsea.athirdhand.config.ModConfig.CONFIG;

import github.ticsea.athirdhand.client.ModKeybind;
import github.ticsea.athirdhand.events.KeybindHandler;
import github.ticsea.athirdhand.events.PlayerOpenContainerHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(AThirdHand.MODID)
public class AThirdHand {

    public static final String MODID = "athirdhand";

    public AThirdHand(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        IEventBus forgeEvenBus = MinecraftForge.EVENT_BUS;

        if (FMLEnvironment.dist==Dist.CLIENT) {
            modEventBus.addListener(ModKeybind::registerKeybind);
            forgeEvenBus.addListener(KeybindHandler::onKeyInput);
            forgeEvenBus.addListener(KeybindHandler::renderToggleMessage);
        }

        context.registerConfig(ModConfig.Type.CLIENT, CONFIG);
        forgeEvenBus.addListener(PlayerOpenContainerHandler::onPlayerOpenContainer);
    }
}
