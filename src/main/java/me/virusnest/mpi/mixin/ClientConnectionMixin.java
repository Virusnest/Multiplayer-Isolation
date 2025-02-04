package me.virusnest.mpi.mixin;

import com.mojang.authlib.GameProfile;
import me.virusnest.mpi.Events;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.mixin.networking.ServerCommonNetworkHandlerMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.virusnest.mpi.Util.ResetHiddenPlayerFor;

@Mixin(ServerCommonNetworkHandler.class)
public abstract class ClientConnectionMixin {


    @Shadow public abstract void send(Packet<?> packet, @Nullable PacketCallbacks callbacks);

    @Shadow @Final protected MinecraftServer server;

    @Shadow protected abstract GameProfile getProfile();

    @Inject(method = "Lnet/minecraft/server/network/ServerCommonNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacketHead(Packet<?> packet, CallbackInfo ci) {
        // Do nothing
    }
}





