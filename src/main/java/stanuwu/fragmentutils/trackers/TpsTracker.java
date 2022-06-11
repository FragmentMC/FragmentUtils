package stanuwu.fragmentutils.trackers;

import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import stanuwu.fragmentutils.events.EventHandler;
import stanuwu.fragmentutils.events.EventType;
import stanuwu.fragmentutils.events.events.Event;
import stanuwu.fragmentutils.events.events.ReceivePacketEvent;

import java.util.ArrayList;
import java.util.List;

public class TpsTracker {
    private static final List<Double> TICKS = new ArrayList<>();
    private static int refreshRate = 10;
    private static long prevTime = 0;
    private static int refresh = refreshRate;
    private static double cachedTps = 0;

    public static void init() {
        EventHandler.getInstance().register(EventType.ReceivePacket, TpsTracker::onTimeUpdate);
    }

    private static void onTimeUpdate(Event e) {
        ReceivePacketEvent event = (ReceivePacketEvent) e;
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
            long now = System.currentTimeMillis();
            if (prevTime != 0) {
                long diff = now - prevTime;
                pushValue(20 / (diff / 1000d));
            }
            prevTime = now;
        }
    }

    private static void pushValue(double tps) {
        TICKS.add(tps);
        refresh++;
        if (TICKS.size() > 20) {
            TICKS.remove(0);
        }
    }

    public static double getTps() {
        if (refresh > refreshRate) {
            refresh = 0;
            cachedTps = MathHelper.clamp(TICKS.stream().mapToDouble(n -> n).average().orElse(0.0), 0, 20);
        }
        return cachedTps;
    }
}