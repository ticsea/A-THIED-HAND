package github.ticsea.quickpickme.events;

import github.ticsea.quickpickme.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class PlayerOpenContainerHandler {
    private static String menuClassName;

    public static void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();
        menuClassName = menu.getClass().getName();

        if (ModConfig.DISPLAYCLASSNAME.get()) {
            sendClassName(player, menuClassName);
        }
        if (!shouldHandleEvent(player)) return;
        transferMatchingItems(menu, player);
    }

    private static boolean shouldHandleEvent(Player player) {
        if (!ModConfig.ENABLE_MOD.get()) return false;

        BooleanValue booleanValue = ModConfig.getBooleanValueByClassName(menuClassName);
        if (booleanValue!=null && booleanValue.get()) {
            return player!=null && !player.level().isClientSide;
        }
        return false;
    }

    private static void sendClassName(Player player, String menuClassName) {
        Component canClickText = Component.literal(menuClassName)
                .withStyle(
                        Style.EMPTY
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, menuClassName))
                                .withUnderlined(true)
                                .withColor(ChatFormatting.AQUA)
                );
        player.sendSystemMessage(canClickText);
    }

    private static void transferMatchingItems(AbstractContainerMenu menu, Player player) {

        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) continue; // 跳过玩家自身的物品栏

            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            if (player.getInventory().hasAnyMatching(invStack -> ItemStack.isSameItemSameTags(invStack, stack))) {
                menu.quickMoveStack(player, slot.index);
            }
        }
    }
}