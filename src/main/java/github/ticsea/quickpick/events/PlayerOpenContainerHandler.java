package github.ticsea.quickpick.events;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import com.mojang.logging.LogUtils;
import github.ticsea.quickpick.util.ConfigHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import org.slf4j.Logger;
import se.mickelus.tetra.blocks.workbench.WorkbenchContainer;

public class PlayerOpenContainerHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void onPlayerOpenChest(final PlayerContainerEvent.Open event) {
        if (!ConfigHelper.isMODEnable() ||
                event.getEntity()==null ||
                event.getEntity().level().isClientSide) {
            return;
        }

        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();
        if (menu instanceof WorkbenchContainer) return;
        if (menu instanceof BackpackContainer) {
            if (ConfigHelper.isBackpackEnable()) {
                moveItem(menu, player);
//                LOGGER.debug("Debug:backpackstatus {}", ModConfig.getBackpackState());
            }
        } else if (menu instanceof MaidMainContainer) {
            if (ConfigHelper.isLittleMaidEnable()) {
                moveItem(menu, player);
//                LOGGER.debug("Debug:littlemaidstatus {}", ModConfig.getLittleMaidState());
            }
        } else {
            moveItem(menu, player);
        }
    }

    private static void moveItem(AbstractContainerMenu menu, Player player) {
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory) continue; // 过滤玩家背包槽位

            ItemStack containerItem = slot.getItem();
            if (containerItem.isEmpty()) continue; //过滤空槽位

            boolean matching = player.getInventory().hasAnyMatching(
                    p -> ItemStack.isSameItemSameTags(p, containerItem));
            if (matching) {
                menu.quickMoveStack(player, slot.index);
            }
        }
    }
}
