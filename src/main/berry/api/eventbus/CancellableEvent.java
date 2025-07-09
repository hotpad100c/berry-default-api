package berry.api.eventbus;

/**
 * Mark the event as cancellable.
 */
public interface CancellableEvent {
    boolean cancelled;
    default void cancel () {
        setCancelled (true);
    }
    default void setCancelled (boolean c) {
        this.cancelled = c;
    }
    default boolean cancelled () {
        return cancelled;
    }
}
