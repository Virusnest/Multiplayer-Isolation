package me.virusnest.mpi.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.virusnest.mpi.Mpi;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(EntitySelector.class)
public abstract class CommandMixins {

    @ModifyReturnValue(method = "getPlayers", at = @At("RETURN"))
    public List<ServerPlayerEntity> removePlayers(List<ServerPlayerEntity> original, ServerCommandSource src) {
        if(!Mpi.CONFIG.hidePlayerList) {
            return original;
        }
        List<ServerPlayerEntity> players = new ArrayList<>(original);
        players.removeIf(player -> true);

        return players;
    }



}
