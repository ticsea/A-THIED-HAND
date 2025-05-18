package github.ticsea.quickpick.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MARKED_ITEM_IDS = BUILDER
            .comment("List of marked item IDs")
            .defineListAllowEmpty("marked_items", List.of(), obj ->
                    obj instanceof String s && ResourceLocation.isValidResourceLocation(s));

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // 实际标记的物品（运行时）
    public static Set<Item> MARKED_ITEMS;

    public static void load() {
        MARKED_ITEMS = MARKED_ITEM_IDS.get().stream()
                .map(id -> BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(id)))
                .filter(item -> item != null && item != BuiltInRegistries.ITEM.get((ResourceLocation) null))
                .collect(Collectors.toSet());
    }

    public static void save(Set<Item> items) {
        List<String> ids = items.stream()
                .map(item -> BuiltInRegistries.ITEM.getKey(item).toString())
                .toList();
        MARKED_ITEM_IDS.set(ids);
    }
}
