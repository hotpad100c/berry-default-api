package berry.api.eventbus;

import berry.api.eventbus.CancellableEvent;
import berry.api.eventbus.EventBus;
public abstract class Event {
    protected boolean cancelled = false;
    public boolean cancellable () {
        //Only cancellable when marked.
        return this instanceof CancellableEvent;
    }
    public void cancel() {
        if (this instanceof CancellableEvent cancellableEvent) cancellableEvent.setCancelled(true);
        else throw new IllegalStateException(getUncancellableReason());
    }
    protected string getUncancellableReason(){
        return "Event cannot be cancelled!"
    }
    public boolean cancelled () {
      return (this instanceof CancellableEvent cancellableEvent && cancellableEvent.isCancelled())
    }
    @SuppressWarnings("unchecked")
    public <T extends Event> void register(EventListener<T> listener) {
        EventBus.addListener((Class<T>) this.getClass(), listener);
    }
}
