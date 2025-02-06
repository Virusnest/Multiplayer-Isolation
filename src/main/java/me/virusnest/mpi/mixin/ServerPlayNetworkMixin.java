package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import me.virusnest.mpi.Util;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkMixin {

    @Shadow public ServerPlayerEntity player;

    @Redirect(method = "sendChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"))
    private void onChatMessage(ServerPlayNetworkHandler instance, Packet packet) {
        if (Mpi.CONFIG.hideChatMessages) {
            return;
        }
        for(ServerPlayerEntity player : instance.player.server.getPlayerManager().getPlayerList()) {
            instance.player.getServerWorld().sendToPlayerIfNearby(player,);
        }
    }

    public final boolean sendToPlayerIfNearby(ServerPlayerEntity player, boolean force, double x, double y, double z, Packet<?> packet) {

            BlockPos blockPos = player.getBlockPos();
            if (blockPos.isWithinDistance(new Vec3d(x, y, z), force ? 512.0 : 32.0)) {
                player.networkHandler.sendPacket(packet);
                return true;
            } else {
                return false;
            }

    }
}
