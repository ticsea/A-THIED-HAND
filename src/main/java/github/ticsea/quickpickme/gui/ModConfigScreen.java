package github.ticsea.quickpickme.gui;

import github.ticsea.quickpickme.compatible.SupportedModJsonProvider;
import github.ticsea.quickpickme.config.ModConfig;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModConfigScreen extends Screen {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModConfigScreen.class);
    private final Screen parentScreen;

    public ModConfigScreen(Component title, Screen parent) {
        super(title);
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        int screenWidth = width;
        int startY = height / 2 - 80;
        int buttonWidth = 150;
        int buttonHeight = 20;
        int horizontalSpacing = 5;
        int verticalSpacing = 5;
        int buttonsPerRow = 3;

        // Back Button
        this.addRenderableWidget(Button.builder(Component.translatable("gui.quickpickme.back"), b -> this.onClose())
                .pos(width / 2 - 75, height - 30)
                .size(150, 20)
                .build());

        Button displayButton = buildToggleButton(
                width / 2 - 225,
                height - 30,
                Language.getInstance().getOrDefault("gui.quickpickme.displayclassname"),
                ModConfig.DISPLAYCLASSNAME::get,
                () -> ModConfig.DISPLAYCLASSNAME.set(!ModConfig.DISPLAYCLASSNAME.get()));
        this.addRenderableWidget(displayButton);

        generateJsonButton(buttonsPerRow, screenWidth, buttonWidth, horizontalSpacing, startY, buttonHeight, verticalSpacing);
    }

    private void generateJsonButton(int buttonsPerRow, int screenWidth, int buttonWidth, int horizontalSpacing, int startY, int buttonHeight, int verticalSpacing) {
        List<SupportedModJsonProvider> buttonConfigs = SupportedModJsonProvider.getSupportedMods();
        for (int i = 0; i < buttonConfigs.size(); i++) {
            SupportedModJsonProvider config = buttonConfigs.get(i);
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;
            int x = screenWidth - (col + 1) * (buttonWidth + horizontalSpacing);
            int y = startY + row * (buttonHeight + verticalSpacing);
            Button button = buildToggleButton(
                    x, y,
                    config.getId(),
                    () -> {
                        BooleanValue value = ModConfig.idToBooleanValueMap.get(config.getId());
                        if (value==null) {
                            LOGGER.error("BooleanValue is null for key: {}", config.getId());
                        }
                        return value!=null ? value.get():false;
                    },
                    () -> {
                        BooleanValue value = ModConfig.idToBooleanValueMap.get(config.getId());
                        if (value!=null) {
                            value.set(!value.get());
                        } else {
                            LOGGER.error("BooleanValue is null for key: {}", config.getId());
                        }
                    }
            );
            this.addRenderableWidget(button);
        }
    }

    private Button buildToggleButton(
            int x, int y,
            String key,
            java.util.function.BooleanSupplier stateGetter,
            Runnable toggler
    ) {
        return Button.builder(getToggleMessage(key, stateGetter.getAsBoolean()), b -> {
                    toggler.run();
                    b.setMessage(getToggleMessage(key, stateGetter.getAsBoolean()));
                    LOGGER.info("Button toggled. New state: {}", stateGetter.getAsBoolean());
                })
                .pos(x, y)
                .size(150, 20)
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
