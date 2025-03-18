package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(AbstractClientPlayerEntity.class)
public class DefaultSkinMixin {

    @Inject(method = "getSkinTextures", at = @At("HEAD"), cancellable = true)
    private void getSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        UUID uuid = ((AbstractClientPlayerEntity) (Object) this).getUuid();
        PlayerListEntry playerListEntry = Mpi.PlayerListCache.get(uuid);
        var skin = playerListEntry == null ? DefaultSkinHelper.getSkinTextures(uuid) : playerListEntry.getSkinTextures();

        if (uuid != null) {
            cir.setReturnValue(skin);
        }

    }
}
