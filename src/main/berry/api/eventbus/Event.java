package berry.api.eventbus;

public abstract class Event {
    protected boolean cancelled = false;
    public boolean cancellable () {
        // Cancellable by default, except for post events.
        return this instanceof CancellableEvent;
    }
    public boolean cancelled () {
        if(this instanceof CancellableEvent)
            return this.cancelled;
        else
            return false;
    }
}
