package github.ticsea.quickpick.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import github.ticsea.quickpick.config.ModConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModScreen extends Screen {
    // 全部物品，排除 AIR，按注册名排序
    private final List<Item> allItems = ForgeRegistries.ITEMS.getValues().stream()
            .filter(item -> !ForgeRegistries.ITEMS.getKey(item).toString().equals("minecraft:air"))
            .sorted(Comparator.comparing(item -> ForgeRegistries.ITEMS.getKey(item).toString()))
            .toList();

    // 运行时标记集合
    private static final Set<Item> selectedItems = new HashSet<>();

    // 用于搜索过滤
    private List<Item> filteredItems = allItems;

    // 滚动偏移 & 搜索框
    private int scrollOffset = 0;
    private EditBox searchBox;

    public ModScreen() {
        super(Component.literal("Item Grid"));
    }

    @Override
    protected void init() {
        // 1. 从配置加载已标记物品
        ModConfigs.load();
        selectedItems.clear();
        selectedItems.addAll(ModConfigs.MARKED_ITEMS);

        // 2. 初始化搜索框
        searchBox = new EditBox(this.font, 20, 10, 160, 16, Component.literal("Search"));
        searchBox.setResponder(this::onSearchChanged);
        this.addRenderableWidget(searchBox);
        this.setFocused(searchBox);

        super.init();
    }

    private void onSearchChanged(String query) {
        String lower = query.toLowerCase();
        filteredItems = allItems.stream()
                .filter(item -> ForgeRegistries.ITEMS.getKey(item).toString().contains(lower)
                        || item.getDescriptionId().toLowerCase().contains(lower))
                .collect(Collectors.toList());
        scrollOffset = 0;
    }

    @Override
    public void tick() {
        super.tick();
        searchBox.tick();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        searchBox.render(graphics, mouseX, mouseY, partialTick);

        int cols = 10, size = 18, startX = 20, startY = 30;
        Item hovered = null;

        // 绘制主网格
        for (int i = 0; i < filteredItems.size(); i++) {
            Item item = filteredItems.get(i);
            int row = i / cols, col = i % cols;
            int x = startX + col * size;
            int y = startY + row * size - scrollOffset;

            if (y + size >= 0 && y <= this.height) {
                graphics.renderItem(new ItemStack(item), x, y);
                if (selectedItems.contains(item)) {
                    graphics.drawString(this.font, "✔", x + 12, y + 2, 0x00FF00);
                }
                if (mouseX >= x && mouseX < x + size && mouseY >= y && mouseY < y + size)
                    hovered = item;
            }
        }

        // 绘制右侧已标记列表
        int rightCols = 3, rightX = this.width - 20 - rightCols * size, rightY = 30;
        graphics.drawString(this.font, "Marked:", rightX, rightY - 15, 0xFFFFFF);
        int idx = 0;
        for (Item item : selectedItems) {
            int col = idx % rightCols, row = idx / rightCols;
            int x = rightX + col * size, y = rightY + row * size;
            graphics.renderItem(new ItemStack(item), x, y);
            if (mouseX >= x && mouseX < x + size && mouseY >= y && mouseY < y + size)
                hovered = item;
            idx++;
        }

        // 悬停提示
        if (hovered != null) {
            graphics.renderTooltip(this.font, new ItemStack(hovered), mouseX, mouseY);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (searchBox.mouseClicked(mx, my, button)) return true;

        int cols = 10, size = 18, startX = 20, startY = 30;
        for (int i = 0; i < filteredItems.size(); i++) {
            Item item = filteredItems.get(i);
            int row = i / cols, col = i % cols;
            int x = startX + col * size;
            int y = startY + row * size - scrollOffset;
            if (mx >= x && mx < x + size && my >= y && my < y + size) {
                // 切换标记状态
                if (!selectedItems.remove(item)) selectedItems.add(item);
                // 立即保存到配置
                ModConfigs.save(selectedItems);
                return true;
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        int cols = 10, size = 18;
        int rows = (filteredItems.size() + cols - 1) / cols;
        int max = Math.max(0, rows * size - (this.height - 40));
        scrollOffset = Math.max(0, Math.min(scrollOffset - (int)(delta * size), max));
        return true;
    }

    @Override
    public boolean charTyped(char c, int mods) {
        if (searchBox.charTyped(c, mods)) return true;
        return super.charTyped(c, mods);
    }

    @Override
    public boolean keyPressed(int kc, int sc, int mods) {
        if (searchBox.keyPressed(kc, sc, mods)) return true;
        return super.keyPressed(kc, sc, mods);
    }

    @Override
    public void onClose() {
        // 关闭时再保存一次（可选）
        ModConfigs.save(selectedItems);
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static Set<Item> getSelectedItems() {
        return selectedItems;
    }
}
