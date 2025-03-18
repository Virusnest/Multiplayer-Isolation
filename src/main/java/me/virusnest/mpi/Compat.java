package me.virusnest.mpi;

import net.fabricmc.loader.api.FabricLoader;

public class Compat {

    public static boolean SVC = FabricLoader.getInstance().isModLoaded("voicechat");

}
