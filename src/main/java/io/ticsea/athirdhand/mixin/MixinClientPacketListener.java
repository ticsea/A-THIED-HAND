package io.ticsea.athirdhand.mixin;

import io.ticsea.athirdhand.config.ModConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private boolean ath$shouldProcessor = false;

    @Inject(method = "handleOpenScreen", at = @At("TAIL"))
    private void setBLTrue(ClientboundOpenScreenPacket pPacket, CallbackInfo ci) {
        if (pPacket.getType() == MenuType.GENERIC_9x3 || pPacket.getType() == MenuType.GENERIC_9x6 || pPacket.getType() == MenuType.GENERIC_9x1) {
            ath$shouldProcessor =true;
        }
    }

    @Inject(method = "handleContainerContent", at = @At("TAIL"))
    private void moveItem(ClientboundContainerSetContentPacket pPacket, CallbackInfo ci) {
       var athirdhand2_0$mc = minecraft;
       var items = pPacket.getItems();
        Player player = athirdhand2_0$mc.player;
        MultiPlayerGameMode gameMode = athirdhand2_0$mc.gameMode;

        if (!ModConfigs.isModEnbale() || !ath$shouldProcessor || player == null || gameMode == null) return;

        ath$shouldProcessor =false;

        Map<Item, CompoundTag> playerInventoryItems = new HashMap<>();
        player.getInventory().items.forEach(itemStack -> playerInventoryItems.put(itemStack.getItem(), itemStack.getTag()));

        for (int i = 0; i < items.size() - 36; ++i) {
            ItemStack itemStack = items.get(i);
            Item item = itemStack.getItem();

            if (item == Items.AIR || !playerInventoryItems.containsKey(item)) continue;

            if (ModConfigs.isCheckNBTEable() && itemStack.hasTag()) {
                if (!playerInventoryItems.get(item).equals(itemStack.getTag())) {
                    continue;
                }
            }

            int finalI = i;
            athirdhand2_0$mc.execute(() -> gameMode.handleInventoryMouseClick(pPacket.getContainerId(), finalI,  0, ClickType.QUICK_MOVE, player));


        }
    }
}
