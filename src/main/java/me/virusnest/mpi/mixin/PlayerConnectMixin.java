package me.virusnest.mpi.mixin;


import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.*;

@Mixin(PlayerManager.class)
public abstract class PlayerConnectMixin {


    @Redirect(method = "Lnet/minecraft/server/PlayerManager;onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/server/network/ConnectedClientData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V", remap = false))
    private void DissablePlayJoin(PlayerManager instance, Text message, boolean overlay) {
        // Do nothing
    }


    private PlayerListS2CPacket HidePlayers(Collection<PlayerEntity> players) {
        List<PlayerListS2CPacket.Entry> entries = new ArrayList<>();
        for (PlayerEntity player : players) {
            entries.add(new PlayerListS2CPacket.Entry(
                    player.getUuid(),
                    null,
                    true, 0, GameMode.SURVIVAL, null, null
            ));
        }

        return null;
    }


}


