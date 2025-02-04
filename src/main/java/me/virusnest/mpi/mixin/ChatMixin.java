package me.virusnest.mpi.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.command.argument.EntityArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(EntityArgumentType.class)
public class ChatMixin {
    @ModifyReturnValue(method = "listSuggestions", at = @At(value = "RETURN"))
    private CompletableFuture<Suggestions> getPlayerNames(CompletableFuture<Suggestions> original) {
        System.out.println("Hiding player names");
        return Suggestions.empty();
    }
}
