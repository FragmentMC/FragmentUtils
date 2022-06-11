package stanuwu.fragmentutils.events;

import stanuwu.fragmentutils.events.events.Event;
import stanuwu.fragmentutils.events.events.ReceivePacketEvent;

public enum EventType {
    ReceivePacket(ReceivePacketEvent.class);

    public final Class<? extends Event> event;

    EventType(Class<? extends Event> event) {
        this.event = event;
    }
}
