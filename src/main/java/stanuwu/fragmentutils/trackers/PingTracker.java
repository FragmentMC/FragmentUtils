package stanuwu.fragmentutils.trackers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.Event;
import stanuwu.fragmentutils.events.events.ReceivePacketEvent;

public class PingTracker {
    private static int ping = 0;

    public static void init() {
        EventHandler.getInstance().register(EventType.ReceivePacket, PingTracker::onLatencyUpdate);
    }

    private static void onLatencyUpdate(Event e) {
        ReceivePacketEvent event = (ReceivePacketEvent) e;
        if (event.getPacket() instanceof PlayerListS2CPacket plPacket) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                for (PlayerListS2CPacket.Entry entry : plPacket.getEntries()) {
                    if (entry.getProfile().getId().equals(player.getUuid())) {
                        ping = entry.getLatency();
                    }
                }
            }
        } else if (event.getPacket() instanceof GameJoinS2CPacket) {
            ping = 0;
        }
    }

    public static int getPing() {
        return ping;
    }
}
