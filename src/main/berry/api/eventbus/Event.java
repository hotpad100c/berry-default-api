package berry.api.eventbus;

import berry.api.eventbus.CancellableEvent;
public abstract class Event {
    protected boolean cancelled = false;
    public boolean cancellable () {
        //Only cancellable when marked.
        return this instanceof CancellableEvent;
    }
    public void cancel() {
        if (this instanceof CancellableEvent cancellableEvent) cancellableEvent.setCancelled(true);
    }
    public boolean cancelled () {
      return (this instanceof CancellableEvent cancellableEvent) ? cancellableEvent.isCancelled():false;
    }
}
