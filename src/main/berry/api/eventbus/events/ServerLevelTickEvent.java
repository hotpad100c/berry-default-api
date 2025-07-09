package berry.api.eventbus.events;

import berry.api.eventbus.Event;
import berry.api.eventbus.MultiPhaseEvent;
import net.minecraft.server.level.ServerLevel;
import java.util.function.BooleanSupplier;
import java.util.ArrayList;
import java.util.List;

public abstract sealed class ServerLevelTickEvent extends Event implements MultiPhaseEvent 
        permits ServerLevelTickEvent.Pre, ServerLevelTickEvent.Post {
    
    public final ServerLevel level;
    public final BooleanSupplier time;
    private final List<EventPhase> phases = new ArrayList<>();
    private EventPhase currentPhase;

    public ServerLevelTickEvent(ServerLevel level, BooleanSupplier time) {
        this.level = level;
        this.time = time;
        registerPhase(new EventPhase("Pre", true, null));
        registerPhase(new EventPhase("Post", false, "it is after the end of current server tick."));
        this.currentPhase = phases.get(0);
    }

    @Override
    public List<EventPhase> getPhases() {
        return phases;
    }

    @Override
    public EventPhase getCurrentPhase() {
        return currentPhase;
    }

    @Override
    public void setCurrentPhase(EventPhase phase) {
        if (phases.contains(phase)) {
            this.currentPhase = phase;
        } else {
            throw new IllegalArgumentException("Phase " + phase.name() + " is not registered!");
        }
    }

    public static final class Pre extends ServerLevelTickEvent {
        public Pre(ServerLevel level, BooleanSupplier time) {
            super(level, time);
        }

        public boolean isPreEvent() {
            return true;
        }
    }

    public static final class Post extends ServerLevelTickEvent {
        public Post(ServerLevel level, BooleanSupplier time) {
            super(level, time);
            nextPhase();
        }

        public boolean isPreEvent() {
            return false;
        }
    }
}
