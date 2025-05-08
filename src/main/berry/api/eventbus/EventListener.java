package berry.api.eventbus;

public interface EventListener <T extends Event> {
    public void listen (T event);
}
