package stanuwu.fragmentutils.events.events;

public abstract class Event {
    private boolean cancelled;

    public Event() {
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
