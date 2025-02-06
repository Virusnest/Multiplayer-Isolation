package me.virusnest.mpi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;

public class Configs {

    public Configs(){}

    public boolean hidePlayerList = true;
    public boolean hidePlayerCount = true;
    public boolean hideJoinMessage = true;
    public boolean hideLeaveMessage = true;
    public boolean hideChatMessages = true;
    public boolean localisedChat = true;
    public boolean hidePlayerNames = true;
    public int maxPlayerDistance = -1;

    public void SaveConfig(File ConfigFile){
        GsonBuilder gsonBuilder  = new GsonBuilder();
        // Creates a Gson instance based on the current configuration
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this);
        try {
            FileUtils.writeStringToFile(ConfigFile, json, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Configs LoadConfig(File ConfigFile){
        GsonBuilder gsonBuilder  = new GsonBuilder();
        // Creates a Gson instance based on the current configuration
        Gson gson = gsonBuilder.create();
        try {
            String json = FileUtils.readFileToString(ConfigFile, "UTF-8");
            Configs config = gson.fromJson(json, Configs.class);
            System.out.println("Loaded Config");
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            SaveConfig(ConfigFile);
            return this;
        }
    }
}
