package github.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import github.ticsea.quickpick.Main;
import org.slf4j.Logger;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerOpenContainerEvent {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void quickMoveStack(final PlayerContainerEvent.Open event) {
        //LOGGER.debug("---运行正常---");

        if (!KeyTriggerEvents.isEnabled() ||
                event.getEntity() == null ||
                event.getEntity().level().isClientSide) return;

        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        for (Slot slot : menu.slots) {
            // 跳过玩家背包槽位
            if (slot.container instanceof net.minecraft.world.entity.player.Inventory) continue;

            ItemStack containerItems = slot.getItem();

            //跳过空槽位，并匹配物品
            if (!containerItems.isEmpty() && player.getInventory().hasAnyMatching(p ->
                    ItemStack.isSameItemSameTags(p,containerItems))) {
                    //转移物品
                    menu.quickMoveStack(player, slot.index);

                    if (LOGGER.isDebugEnabled()) { // 避免字符串拼接开销
                        LOGGER.debug("转移物品: {}", containerItems.getDisplayName());
                    }

            }
        }
    }
}
