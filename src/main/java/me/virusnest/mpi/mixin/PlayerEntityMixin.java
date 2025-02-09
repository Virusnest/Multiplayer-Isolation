package me.virusnest.mpi.mixin;


import me.virusnest.mpi.Mpi;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyVariable(method = "onDeath", at = @At(value = "STORE"), ordinal = 0)
    private boolean onDeath2(boolean value) {
        if (Mpi.CONFIG.hidePlayerDeathMessages) {
            return false;
        }
        return value;
    }

}
