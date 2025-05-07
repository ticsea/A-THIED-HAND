package net.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ticsea.quickpick.Main;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerOpenContainerEvent {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void quickMoveStack(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        AbstractContainerMenu menu = event.getContainer();

        for (Slot slot : menu.slots) {
            // 跳过玩家背包槽位
            if (slot.container instanceof net.minecraft.world.entity.player.Inventory) {
                continue;
            }

            ItemStack containerItems = slot.getItem();
            //跳过空槽位，并匹配物品
            if (!containerItems.isEmpty() && player.getInventory().hasAnyMatching(p ->
                    ItemStack.isSameItemSameTags(p,containerItems))) {
                //转移物品
                menu.quickMoveStack(player, slot.index);

                LOGGER.debug("已转移物品: {} x{}",
                        containerItems.getItem().getName(containerItems).getString(),
                        containerItems.getCount());
            }
        }
    }
}
