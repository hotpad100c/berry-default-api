package berry.api.eventbus.events;

import java.util.function.BooleanSupplier;

import berry.api.eventbus.DoubleEvent;
import berry.api.eventbus.Event;
import net.minecraft.server.level.ServerLevel;

public abstract sealed class ServerLevelTickEvent extends Event implements DoubleEvent permits ServerLevelTickEvent.Pre, ServerLevelTickEvent.Post {
    public final ServerLevel level;
    public final BooleanSupplier time;
    public ServerLevelTickEvent (ServerLevel level, BooleanSupplier time) {
        this.level = level;
        this.time = time;
    }
    public static final class Pre extends ServerLevelTickEvent {
        public Pre (ServerLevel level, BooleanSupplier time) {
            super (level, time);
        }
        public boolean isPreEvent () {
            return true;
        }
    }
    public static final class Post extends ServerLevelTickEvent {
        public Post (ServerLevel level, BooleanSupplier time) {
            super (level, time);
        }
        public boolean isPreEvent () {
            return false;
        }
    }
}
