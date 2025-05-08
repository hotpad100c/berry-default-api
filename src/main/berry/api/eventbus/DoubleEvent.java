package berry.api.eventbus;

/**
 * When an event is fired twice, e.g. ServerLevelTickEvent
 */
public interface DoubleEvent {
    public boolean isPreEvent ();
    default boolean isPostEvent () {
        return ! isPreEvent ();
    }
}
