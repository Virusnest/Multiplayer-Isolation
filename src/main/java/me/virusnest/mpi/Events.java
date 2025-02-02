package me.virusnest.mpi;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class Events {

    public static Event<PacketSent> PACKET_SENT = EventFactory.createArrayBacked(PacketSent.class, callbacks -> () -> {
        for (PacketSent callback : callbacks) {
            callback.onPacketSent();
        }
    });

    public static Event<PacketReceived> PACKET_RECEIVED = EventFactory.createArrayBacked(PacketReceived.class, callbacks -> () -> {
        for (PacketReceived callback : callbacks) {
            callback.onPacketReceived();
        }
    });

    public interface PacketReceived {
        void onPacketReceived();
    }

    public interface PacketSent {
        void onPacketSent();
    }
}
