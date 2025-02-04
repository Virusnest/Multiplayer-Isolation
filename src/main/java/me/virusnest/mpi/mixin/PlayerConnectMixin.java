package me.virusnest.mpi.mixin;


import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Collectors;

import static me.virusnest.mpi.Util.ResetHiddenPlayerFor;

@Mixin(PlayerManager.class)
public abstract class PlayerConnectMixin {


    @Shadow public abstract void remove(ServerPlayerEntity player);

    @Shadow public abstract void sendToAll(Packet<?> packet);

    @Shadow public abstract List<ServerPlayerEntity> getPlayerList();

    @Shadow @Final private MinecraftServer server;

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Shadow @Final private Map<UUID, ServerPlayerEntity> playerMap;

    @Redirect(method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/server/network/ConnectedClientData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V", remap = false))
    private void DissablePlayJoin(PlayerManager instance, Text message, boolean overlay) {
        // Do nothing
    }


//    @Inject(method = "respawnPlayer", at = @At("RETURN"))
//    private void d(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
//        for(ServerPlayerEntity serverPlayerEntity : this.players) {
//            if (serverPlayerEntity.getId() == player.getId()) {
//                continue;
//            }
//            ResetHiddenPlayerFor(serverPlayerEntity.networkHandler, player);
//            ResetHiddenPlayerFor(player.networkHandler, serverPlayerEntity);
//
//        }
//    }

    private void sendToAllExcept(ServerPlayerEntity player) {
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (serverPlayerEntity != player) {
                //serverPlayerEntity.networkHandler.sendPacket(packet);
            }
        }
    }


}


