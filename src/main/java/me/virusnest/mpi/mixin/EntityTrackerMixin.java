package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.virusnest.mpi.Util.ResetHiddenPlayerFor;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerMixin {


    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "Lnet/minecraft/server/network/EntityTrackerEntry;startTracking(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"), cancellable = true)
    private void onSendChunkData(ServerPlayerEntity player, CallbackInfo ci) {
        if (entity.isPlayer()&& Mpi.CONFIG.hidePlayerList) {

            ResetHiddenPlayerFor(player.networkHandler, (ServerPlayerEntity) entity);
            ResetHiddenPlayerFor(((ServerPlayerEntity) entity).networkHandler, player);
        }

    }
}
