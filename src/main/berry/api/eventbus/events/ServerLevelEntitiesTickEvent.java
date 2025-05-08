package berry.api.eventbus.events;

import java.util.function.BooleanSupplier;

import berry.api.eventbus.DoubleEvent;
import berry.api.eventbus.Event;
import net.minecraft.server.level.ServerLevel;

public abstract sealed class ServerLevelEntitiesTickEvent extends Event implements DoubleEvent permits ServerLevelEntitiesTickEvent.Pre, ServerLevelEntitiesTickEvent.Post {
    public final ServerLevel level;
    public final BooleanSupplier time;
    public ServerLevelEntitiesTickEvent (ServerLevel level, BooleanSupplier time) {
        this.level = level;
        this.time = time;
    }
    public static final class Pre extends ServerLevelEntitiesTickEvent {
        public Pre (ServerLevel level, BooleanSupplier time) {
            super (level, time);
        }
        public boolean isPreEvent () {
            return true;
        }
    }
    public static final class Post extends ServerLevelEntitiesTickEvent {
        public Post (ServerLevel level, BooleanSupplier time) {
            super (level, time);
        }
        public boolean isPreEvent () {
            return false;
        }
    }
}
