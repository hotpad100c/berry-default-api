package berry.api.eventbus;

import berry.api.eventbus.Event;

public abstract class MultiPhaseEvent extends Event{
    List<MultiPhaseEvent.Phase> phases = new ArrayList<>();
    MultiPhaseEvent.Phase currentLhase
    public abstract class EventPhase{
        private final cancellable;
        private final reason;
        public EventPhase(boolean cancellable,@Nullable string reason){
            this.cancellable = cancellable;
            this.reason = reason;
        }
    }
    protected boolean cancelled = false;
    @Override
    public boolean cancellable (){
        return true;
    }
    public void cancel () {
        setCancelled (true);
    }
    public void setCancelled (boolean c) {
        if (!this.cancellable () && c) throw new IllegalStateException ("Event cannot be cancelled!");
        else this.cancelled = c;
    }
    public boolean cancelled () {
        return cancelled;
    }
}
