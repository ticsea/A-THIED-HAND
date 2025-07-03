package github.ticsea.quickpickme.events;

import github.ticsea.quickpickme.SupportedMod;
import github.ticsea.quickpickme.util.ConfigHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class PlayerOpenContainerHandler {

    public static void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        if (!shouldHandleEvent(player)) return;

        for (SupportedMod supported : SupportedMod.values()) {
            if (supported.matchesSupportedMod(menu)) {
                if (!supported.isEnabled()) return; // 已匹配但未启用，跳过
                transferMatchingItems(menu, player);
                return;
            }
        }

        // 未命中任何特殊菜单，默认启用处理
        transferMatchingItems(menu, player);
    }

    private static boolean shouldHandleEvent(Player player) {
        return ConfigHelper.ModQuickPickMe.isModEnabled() && player!=null && !player.level().isClientSide;
    }

    private static void transferMatchingItems(AbstractContainerMenu menu, Player player) {
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) continue;
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            if (player.getInventory().hasAnyMatching(invStack -> ItemStack.isSameItemSameTags(invStack, stack))) {
                menu.quickMoveStack(player, slot.index);
            }
        }
    }
}
