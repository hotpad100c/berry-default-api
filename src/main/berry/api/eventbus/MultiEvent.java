package berry.api.eventbus;

public abstract class MultiEvent {
    public abstract class EventPhase{
        
    }
    protected boolean cancelled = false;
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
