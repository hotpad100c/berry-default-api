package berry.api.eventbus;

public abstract class Event {
    protected boolean cancelled = false;
    public boolean cancellable () {
        //Only cancellable when marked.
        return this instanceof CancellableEvent;
    }
    public boolean cancelled () {
      return this instanceof CancellableEvent cancellableEvent ? ((cancellableEvent)this).isCancelled():false;
    }
}
