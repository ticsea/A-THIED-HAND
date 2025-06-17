package github.ticsea.quickpick.gui;

import github.ticsea.quickpick.config.ModConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen extends Screen {
    private final Screen PARENSCREEN;
    private Button littleMaidButton, backpackButton;

    public ModConfigScreen(Component pTitle, Screen paren) {
        super(pTitle);
        PARENSCREEN = paren;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                Button.builder(Component.literal("Back"), b -> this.onClose())
                        .pos(width / 2 - 100, height - 30)
                        .size(200, 20)
                        .build()
        );

        littleMaidButton = Button.builder(Component.literal("Touhou Little Maid: " + status(ModConfig.getLittleMaidStatus())),
                        b -> {
                            ModConfig.littleMaidToggle();
                            littleMaidButton.setMessage(Component.literal("Touhou Little Maid: " + status(ModConfig.getLittleMaidStatus())));
                        })
                .pos(width / 2 - 50, height - 140)
                .size(100, 20)
                .build();
        addRenderableWidget(littleMaidButton);

        backpackButton = Button.builder(Component.literal("backpack: " + status(ModConfig.getBackpackStatus())),
                        b -> {
                            ModConfig.backpackToggle();
                            backpackButton.setMessage(Component.literal("backpack: " + status(ModConfig.getBackpackStatus())));
                        })
                .pos(width / 2 - 50, height - 100)
                .size(100, 20)
                .build();
        this.addRenderableWidget(backpackButton);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderDirtBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        if (this.minecraft!=null) {
            this.minecraft.setScreen(PARENSCREEN);
        }
    }

    private String status(boolean status) {
        return status? "ON" : "OFF";
    }


}
