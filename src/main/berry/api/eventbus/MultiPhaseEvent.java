package berry.api.eventbus;

import berry.api.eventbus.Event;

public interface class MultiPhaseEvent{
    
    public record EventPhase(string name, boolean cancellable,@Nullable string reason){
        public boolean cancelable(){
            return this.cancellable;
        }
        public string whyCantCancel(){
            return "Cannot cancel event on " + this.name + this.reason == null? "phase!" : "phase because " + this.reason +" !"
        }
    }

}
