package stanuwu.fragmentutils.events;

import stanuwu.fragmentutils.events.events.Event;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventHandler {
    private HashMap<EventType, EventRegistry> EVENTS = new HashMap<>();

    private static EventHandler INSTANCE = new EventHandler();

    public static EventHandler getInstance() {
        return INSTANCE;
    }

    public void register(EventType eventType, Consumer<Event> function) {
        EVENTS.putIfAbsent(eventType, new EventRegistry(eventType));
        EVENTS.get(eventType).add(function);
    }

    public void fire(EventType eventType, Event event) {
        if (EVENTS.containsKey(eventType)) {
            EVENTS.get(eventType).fire(event);
        }
    }
}