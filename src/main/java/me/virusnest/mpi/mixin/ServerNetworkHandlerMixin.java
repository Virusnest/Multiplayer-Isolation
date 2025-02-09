package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public class ServerNetworkHandlerMixin {

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    public void cancelPackets(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo ci) {

    }
}
