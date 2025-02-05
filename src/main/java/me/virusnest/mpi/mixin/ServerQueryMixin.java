package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;

@Mixin(ServerQueryNetworkHandler.class)
public abstract class ServerQueryMixin {

    @Shadow @Final private ServerMetadata metadata;

    @Shadow @Final private ClientConnection connection;

    @Redirect(method = "onRequest" , at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/packet/Packet;)V"))
    private void onSendPacket(ClientConnection instance, Packet<?> packet) {
        if (!Mpi.CONFIG.hidePlayerCount) {
            connection.send(packet);
            return;
        }
        if (!metadata.players().isPresent()) {
            connection.send(packet);
            return;
        }
        ServerMetadata md = new ServerMetadata(metadata.description(), Optional.of(new ServerMetadata.Players(metadata.players().get().max(),0, List.of())), metadata.version(), metadata.favicon(),metadata.secureChatEnforced());
        connection.send(new QueryResponseS2CPacket(md));
    }


}





