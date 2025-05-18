package github.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import github.ticsea.quickpick.Main;
import github.ticsea.quickpick.gui.ModScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerOpenContainerEvent {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void quickMoveStack(final PlayerContainerEvent.Open event) {
        if (!KeyTriggerEvents.isEnabled() ||
                event.getEntity() == null ||
                event.getEntity().level().isClientSide) return;

        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        Set<Item> selectedItems = ModScreen.getSelectedItems();

        for (Slot slot : menu.slots) {
            // 跳过玩家背包槽位
            if (slot.container instanceof Inventory) continue;

            ItemStack containerItem = slot.getItem();

            if (containerItem.isEmpty()) continue;

            boolean inBackpack = player.getInventory().hasAnyMatching(p -> ItemStack.isSameItemSameTags(p, containerItem));
            boolean isSelected = selectedItems.contains(containerItem.getItem());

            // 满足：玩家背包中有 或 GUI 选中
            if (inBackpack || isSelected) {
                menu.quickMoveStack(player, slot.index);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("转移物品: {}", containerItem.getDisplayName());
                }
            }
        }
    }
}
