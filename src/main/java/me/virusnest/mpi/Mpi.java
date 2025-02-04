package me.virusnest.mpi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.S2CConfigurationChannelEvents;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

import java.util.List;

public class Mpi implements ModInitializer {
    public static Team playerTeam;
    public static String TEAM_NAME = "MPI";
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            System.out.println("Server started");
            if(server.getScoreboard().getTeamNames().contains(TEAM_NAME)){
                playerTeam = server.getScoreboard().getTeam(TEAM_NAME);
            } else {
                playerTeam = server.getScoreboard().addTeam(TEAM_NAME);
            }

            assert playerTeam != null;
            playerTeam.setNameTagVisibilityRule(Team.VisibilityRule.NEVER);
            playerTeam.setDisplayName(Text.of("HIDDEN"));
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.getScoreboard().addScoreHolderToTeam(handler.player.getNameForScoreboard(), playerTeam);

        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
            if(entity instanceof PlayerEntity){
                System.out.println("Player Loaded " + entity.getName());
            }
        });

    }


}
