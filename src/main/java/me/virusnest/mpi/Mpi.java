package me.virusnest.mpi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.S2CConfigurationChannelEvents;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.PathUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Mpi implements ModInitializer {
    public static Team DummyTeam;
    public static String TEAM_NAME = "HIDDEN";
    public static Configs CONFIG = new Configs();
    @Override
    public void onInitialize() {


        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            PathUtil.getPath(server.getRunDirectory(), List.of("config","MPI.json"));
            CONFIG = CONFIG.LoadConfig(PathUtil.getPath(server.getRunDirectory(), List.of("config","MPI.json")).toFile());
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            DummyTeam = new Team(server.getScoreboard(),TEAM_NAME);
            DummyTeam.setNameTagVisibilityRule(AbstractTeam.VisibilityRule.NEVER);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if(CONFIG.hidePlayerNames) {
                DummyTeam.getPlayerList().add(handler.player.getNameForScoreboard());
                sender.sendPacket(TeamS2CPacket.updateTeam(DummyTeam, true));
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    if (player != handler.player) {
                        player.networkHandler.sendPacket(TeamS2CPacket.changePlayerTeam(DummyTeam, handler.player.getNameForScoreboard(), TeamS2CPacket.Operation.ADD));

                    }
                }
            }

        });

    }


}
