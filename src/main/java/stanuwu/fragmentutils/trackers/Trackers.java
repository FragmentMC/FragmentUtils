package stanuwu.fragmentutils.trackers;

public class Trackers {
    public static void init() {
        TpsTracker.init();
        PingTracker.init();
        EntityTracker.init();
    }
}
