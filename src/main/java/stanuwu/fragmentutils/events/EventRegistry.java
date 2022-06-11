package stanuwu.fragmentutils.events;

import stanuwu.fragmentutils.events.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventRegistry {
    private EventType type;
    private final List<Consumer<Event>> consumers = new ArrayList<>();

    public EventRegistry(EventType type) {
        this.type = type;
    }

    public void add(Consumer<Event> function) {
        consumers.add(function);
    }

    public void fire(Event event) {
        for (Consumer<Event> function : consumers) {
            function.accept(event);
        }
    }
}
