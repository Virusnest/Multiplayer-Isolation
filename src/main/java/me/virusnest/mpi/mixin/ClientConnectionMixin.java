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
        if (packet instanceof CommandTreeS2CPacket) {
            ci.cancel();
        }
        if( packet instanceof EntitySpawnS2CPacket) {
            //print out the packet
            if (((EntitySpawnS2CPacket) packet).getEntityType()!=EntityType.PLAYER)
                return;

            System.out.println("Packet Sent: " + packet);
            ci.cancel();

        }
        if (packet instanceof PlayerListS2CPacket) {
            //print out the packet
            //modify private static fieild of packet
            Field field;
            try {
                field = PlayerListS2CPacket.class.getDeclaredField("entries");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            List<PlayerListS2CPacket.Entry> entries = new ArrayList<>();
            for (PlayerListS2CPacket.Entry entry : ((PlayerListS2CPacket) packet).getEntries()) {
                entries.add(new PlayerListS2CPacket.Entry(entry.profileId(), entry.profile(), false, 0, entry.gameMode(), null, null));
            }
            try {
                field.set(packet, entries);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            //System.out.println("Packet Sent: " + packet);
        }
    }
}





