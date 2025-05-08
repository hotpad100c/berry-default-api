package berry.api.eventbus;

public abstract class Event {
    protected boolean cancelled = false;
    public boolean cancellable () {
        // Cancellable by default, except for post events.
        if (this instanceof DoubleEvent event) return event.isPreEvent ();
        else return true;
    }
    public void cancel () {
        setCancelled (true);
    }
    public void setCancelled (boolean c) {
        if (!this.cancellable () && c) throw new IllegalStateException ("Event cannot be cancelled!");
        else this.cancelled = c;
    }
    public boolean cancelled () {
        return cancelled;
    }
}
