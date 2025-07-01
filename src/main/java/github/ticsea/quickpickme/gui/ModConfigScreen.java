package github.ticsea.quickpickme.gui;

import github.ticsea.quickpickme.util.ConfigHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ModConfigScreen extends Screen {

    private final Screen parentScreen;

    private Button backpackButton;
    private Button littleMaidButton;

    public ModConfigScreen(Component title, Screen parent) {
        super(title);
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int startY = height / 2 - 40;

        // Back Button
        this.addRenderableWidget(Button.builder(Component.translatable("gui.quickpickme.back"), b -> this.onClose())
                .pos(centerX - 100, height - 30)
                .size(200, 20)
                .build());

        // Backpack Toggle
        backpackButton = buildToggleButton(
                centerX, startY + 30,
                "gui.quickpickme.backpack",
                ConfigHelper.SophisticatedBackpacks::isBackpackSupportEnabled,
                ConfigHelper.SophisticatedBackpacks::toggleBackpackSupport
        );
        this.addRenderableWidget(backpackButton);


        // Little Maid Toggle
        littleMaidButton = buildToggleButton(
                centerX, startY,
                "gui.quickpickme.touhou_little_maid",
                ConfigHelper.TouhouLittleMaid::isLittleMaidSupportEnabled,
                ConfigHelper.TouhouLittleMaid::toggleLittleMaidSupport
        );
        this.addRenderableWidget(littleMaidButton);
    }

    private Button buildToggleButton(
            int x, int y,
            String key,
            java.util.function.BooleanSupplier getter,
            Runnable toggler
    ) {
        return Button.builder(getToggleMessage(key, getter.getAsBoolean()), b -> {
                    toggler.run();
                    b.setMessage(getToggleMessage(key, getter.getAsBoolean()));
                })
                .pos(x - 100, y)
                .size(200, 20)
                .build();
    }

    private Component getToggleMessage(String key, boolean state) {
        return Component.translatable(key)
                .append(": ")
                .append(Component.translatable(state ? "gui.quickpickme.state.on":"gui.quickpickme.state.off"));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        if (this.minecraft!=null) {
            this.minecraft.setScreen(parentScreen);
        }
    }
}
