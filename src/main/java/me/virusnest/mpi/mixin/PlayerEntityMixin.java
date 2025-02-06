package me.virusnest.mpi.mixin;


import me.virusnest.mpi.Mpi;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerEntity.class)
public class PlayerEntityMixin {


    @Redirect(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V"))
    private void onDeath(ServerPlayNetworkHandler instance, Packet packet, PacketCallbacks packetCallbacks) {
        // Do nothing
        if (Mpi.CONFIG.hidePlayerDeathMessages) {
            return;
        }
        instance.send(packet, packetCallbacks);
    }

}
