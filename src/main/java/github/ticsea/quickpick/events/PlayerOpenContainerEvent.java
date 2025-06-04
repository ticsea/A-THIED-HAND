package github.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import github.ticsea.quickpick.Main;
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

//    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void quickMoveStack(final PlayerContainerEvent.Open event) {
        if (!KeyTriggerEvents.isEnabled() ||
                event.getEntity()==null ||
                event.getEntity().level().isClientSide) {
            return;
        }

        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        moveItem(menu, player);
    }

    private static void moveItem(AbstractContainerMenu menu, Player player) {
        for (Slot slot : menu.slots) {
            // 过滤玩家背包槽位
            if (slot.container instanceof Inventory) continue;

//          容器里的item
            ItemStack containerItem = slot.getItem();
            if (containerItem.isEmpty()) continue; //过滤空槽位

//          匹配物品，对比容器与背包的item
            boolean inBackpack = player.getInventory().hasAnyMatching(p -> ItemStack.isSameItemSameTags(p, containerItem));

            // 核心：执行转移
            if (inBackpack) {
                menu.quickMoveStack(player, slot.index);

/*
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("转移物品: {}", containerItem.getDisplayName());
                }
*/
            }
        }
    }
}
