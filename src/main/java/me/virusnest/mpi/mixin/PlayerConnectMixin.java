package me.virusnest.mpi.mixin;


import me.virusnest.mpi.Mpi;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(PlayerManager.class)
public abstract class PlayerConnectMixin {



    @Redirect(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V", remap = false))
    private void DissablePlayJoinMessage(PlayerManager instance, Text message, boolean overlay) {
        if (Mpi.CONFIG.hideJoinMessage) {
            // Do nothing
        } else {
            instance.broadcast(message, overlay);
        }
        // Do nothing
    }



}


