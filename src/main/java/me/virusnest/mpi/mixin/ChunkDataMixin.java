package me.virusnest.mpi.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ChunkDataSender;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.PlayerChunkWatchingManager;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerEntityManager;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.entity.EntityTrackingStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static me.virusnest.mpi.Util.ResetHiddenPlayerFor;

@Mixin(EntityTrackerEntry.class)
public class ChunkDataMixin {


    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "Lnet/minecraft/server/network/EntityTrackerEntry;startTracking(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"), cancellable = true)
    private void onSendChunkData(ServerPlayerEntity player, CallbackInfo ci) {
        if (entity.isPlayer()) {

            ResetHiddenPlayerFor(player.networkHandler, (ServerPlayerEntity) entity);
            ResetHiddenPlayerFor(((ServerPlayerEntity) entity).networkHandler, player);
        }

    }
}
