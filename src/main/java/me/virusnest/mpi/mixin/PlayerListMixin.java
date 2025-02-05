package me.virusnest.mpi.mixin;

import com.mojang.authlib.GameProfile;
import me.virusnest.mpi.Mpi;
import net.minecraft.network.encryption.PublicPlayerSession;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public class PlayerListMixin {

    @Redirect(method = "cleanUp()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"), remap = false)
    private void onPlayerLeave(PlayerManager instance, Text message, boolean overlay) {
        if (Mpi.CONFIG.hideLeaveMessage) {
            // Do nothing
        } else {
            instance.broadcast(message, overlay);
        }
        // Do nothing
    }
}
