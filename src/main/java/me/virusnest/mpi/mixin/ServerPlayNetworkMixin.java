package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;
import java.util.Collection;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "sendChatMessage", at = @At(value = "HEAD"),cancellable = true)
    private void onChatMessage(SignedMessage message, MessageType.Parameters params, CallbackInfo ci) {
            if (Mpi.CONFIG.hidePlayerChatMessages) {
                ci.cancel();
            }

    }

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
