package github.ticsea.athirdhand.events;

import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerOpenContainerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerOpenContainerHandler.class);

    private static final List<Class<? extends AbstractContainerMenu>> SUPPORT_CONTAINER_LIST = List.of(ChestMenu.class, ShulkerBoxMenu.class);

    public static void onPlayerOpenContainer(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        if (SUPPORT_CONTAINER_LIST.contains(menu.getClass())) {
            transferMatchingItems(menu, player);
            LOGGER.debug("WHAT IS THE MENU: {}", menu);
        }

    }

    private static void transferMatchingItems(AbstractContainerMenu menu, Player player) {
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) continue; // 跳过玩家自身的物品栏

            ItemStack stack = slot.getItem();
            if (player.getInventory().contains(stack)) {
                menu.quickMoveStack(player, slot.index);
            }
        }
    }
}