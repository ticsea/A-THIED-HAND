package github.ticsea.quickpick.gui;

import github.ticsea.quickpick.util.ConfigHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ModConfigScreen extends Screen {
    private final Screen PARENTSCREEN;
    private Button littleMaidButton;
    private Button backpackButton;

    public ModConfigScreen(Component pTitle, Screen parent) {
        super(pTitle);
        PARENTSCREEN = parent;
    }

    @Override
    protected void init() {
        super.init();

        Button backButton = Button.builder(Component.literal("Back"), b -> this.onClose())
                .pos(width / 2 - 100, height - 30)
                .size(200, 20)
                .build();
        this.addRenderableWidget(backButton);

        littleMaidButton = Button.builder(Component.literal("Touhou Little Maid: " + state(ConfigHelper.isLittleMaidEnable())),
                        b -> {
                            ConfigHelper.littleMaidToggle();
                            littleMaidButton.setMessage(Component.literal("Touhou Little Maid: " + state(ConfigHelper.isLittleMaidEnable())));
                        })
                .pos(width / 2 - 50, height - 140)
                .size(100, 20)
                .build();
        this.addRenderableWidget(littleMaidButton);

        backpackButton = Button.builder(Component.literal("backpack: " + state(ConfigHelper.isBackpackEnable())),
                        b -> {
                            ConfigHelper.backpackToggle();
                            backpackButton.setMessage(Component.literal("backpack: " + state(ConfigHelper.isBackpackEnable())));
                        })
                .pos(width / 2 - 50, height - 100)
                .size(100, 20)
                .build();
        this.addRenderableWidget(backpackButton);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderDirtBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        if (this.minecraft!=null) {
            this.minecraft.setScreen(PARENTSCREEN);
        }
    }

    private String state(boolean state) {
        return state ? "ON":"OFF";
    }
}
