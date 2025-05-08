package berry.api.eventbus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berry.utils.ReflectionUtil;

public class EventBus {
    private final Map <Class <? extends Event>, List <EventListener <? extends Event>>> types = new HashMap <> ();
    public EventBus registerEventType (Class <? extends Event> type) {
        if (! types.containsKey (type)) types.put (type, new ArrayList <> ());
        return this;
    }
    // THIS DOES NOOOOOT WORK
    @SuppressWarnings ("unchecked")
    public <T extends Event> EventBus addListener (EventListener <T> listener) {
        Type generic = ReflectionUtil.getGenerics (listener.getClass ()) [0];
        Class <T> type = (Class <T>) generic;
        return addListener (listener, type);
    }
    public <T extends Event> EventBus addListener (EventListener <T> listener, Class <T> type) {
        if (types.containsKey (type)) types.get (type) .add (listener);
        return this;
    }
    @SuppressWarnings ("unchecked")
    public <T extends Event> T fire (T event) {
        if (! types.containsKey (event.getClass ())) throw new IllegalArgumentException ("Event class " + event.getClass () .getCanonicalName () + " not registered!");
        for (EventListener <? extends Event> listener : types.get (event.getClass ())) {
            ((EventListener <T>) listener) .listen (event);
        }
        return event;
    }
    public static final EventBus MINECRAFT = new EventBus ();
}
