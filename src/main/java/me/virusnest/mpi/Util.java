package me.virusnest.mpi;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Util {

    public static void ResetHiddenPlayerFor(ServerPlayNetworkHandler serverPlayNetworkHandler, ServerPlayerEntity target) {
        serverPlayNetworkHandler.send(PlayerListS2CPacket.entryFromPlayer(List.of(target)),null);
        serverPlayNetworkHandler.send( new EntitySpawnS2CPacket(target.getId(), target.getUuid(), target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch(),target.getType(),target.getId(), target.getVelocity(), target.getHeadYaw()),null);
        serverPlayNetworkHandler.send(new PlayerRemoveS2CPacket(List.of(target.getUuid())),null);
    }


}
