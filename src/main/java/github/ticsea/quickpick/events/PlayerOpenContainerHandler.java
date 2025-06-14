package github.ticsea.quickpick.events;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;

public class PlayerOpenContainerHandler {

///*//    private static final Logger LOGGER = LogUtils.getLogger();*/

    public static void onPlayerOpenChest(final PlayerContainerEvent.Open event) {
        if (!KeybindHandler.isEnabled() ||
                event.getEntity()==null ||
                event.getEntity().level().isClientSide) {
            return;
        }

        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();
        if (menu instanceof BackpackContainer) return;
        moveItem(menu, player);
    }

    private static void moveItem(AbstractContainerMenu menu, Player player) {
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) continue; // 过滤玩家背包槽位

            ItemStack containerItem = slot.getItem();
            if (containerItem.isEmpty()) continue; //过滤空槽位

            boolean matching = player.getInventory().hasAnyMatching(
                    p -> ItemStack.isSameItemSameTags(p, containerItem));
            // 核心：执行转移
            if (matching) {
                menu.quickMoveStack(player, slot.index);
            }
        }
    }
}
