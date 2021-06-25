package us.spaceclouds42.anti.anvil.dupe.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.spaceclouds42.anti.anvil.dupe.Common;

import java.util.UUID;

@Mixin(AnvilScreenHandler.class)
abstract class AnvilScreenHandlerMixin {
    @Inject(
            method = "method_24922",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            )
    )
    private static void antiAnvilDupe(PlayerEntity player, ItemStack stack, World world, BlockPos pos, CallbackInfo ci) {
        if (
                Common.INSTANCE.getCONFIG().getEnableAntiDupe() &&
                player.getInventory().getEmptySlot() == -1 &&
                !player.currentScreenHandler.getCursorStack().isEmpty()
        ) {
            player.currentScreenHandler.setCursorStack(ItemStack.EMPTY);

            Common.INSTANCE.getReplacements()
                    .put("playername", player.getEntityName());
            Common.INSTANCE.getReplacements()
                    .put("coords", player.getBlockX() + " " + player.getBlockY() + " " + player.getBlockZ());
            Common.INSTANCE.getReplacements()
                    .put("dimension", player.world.getRegistryKey().getValue().getPath());
            Common.INSTANCE.getReplacements()
                    .put("dimensionId", player.world.getRegistryKey().getValue().toString());

            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");
            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");
            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");
            System.out.println(
                    Common.INSTANCE.getReplacements().get("playername") +
                    " attempted anvil dupe at " +
                    Common.INSTANCE.getReplacements().get("coords") +
                    " in the " +
                    Common.INSTANCE.getReplacements().get("dimension")
            );
            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");
            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");
            System.out.println("##################### ANTI DUPE CAUGHT SOMETHING #####################");

            if (Common.INSTANCE.getCONFIG().getSendMessageToStaff()) {
                if (player.getServer() == null) { return; }
                player.getServer().getPlayerManager().getPlayerList().forEach((p) -> {
                        if (p.hasPermissionLevel(2)) {
                            p.sendMessage(Common.INSTANCE.getStaffMsg(), false);
                        }
                    }
                );
            }

            if (Common.INSTANCE.getCONFIG().getSendMessageToAll()) {
                if (player.getServer() == null) { return; }
                player.getServer().sendSystemMessage(
                        Common.INSTANCE.getAllMsg(),
                        Util.NIL_UUID
                );
                player.getServer().getPlayerManager().getPlayerList().forEach((p) -> {
                    p.sendMessage(Common.INSTANCE.getAllMsg(), MessageType.CHAT, UUID.fromString("eb133898-e795-4893-953e-185649743df4")); // wamens
                });
            }
        }
    }
}