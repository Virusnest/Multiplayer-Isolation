package me.virusnest.mpi.mixin;

import me.virusnest.mpi.Mpi;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkMixin {

    @Inject(method = "onPlayerList" , at = @At("HEAD"), cancellable = true)
    public void onPlayerList(PlayerListS2CPacket packet, CallbackInfo ci) {
        for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
            if (Mpi.PlayerListCache.containsKey(entry.profileId())){
                
            }else{
                Mpi.PlayerListCache.put(entry.profileId(), new PlayerListEntry(entry.profile(),false));
            }
        }
    }

}
