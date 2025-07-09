package berry.api.eventbus;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface MultiPhaseEvent {

    public record EventPhase(String name, boolean cancellable, @Nullable String reason) {
        public boolean cancelable() {
            return this.cancellable;
        }

        public String whyCantCancel() {
            return "Cannot cancel event on " + this.name + (this.reason == null ? " phase!" : " phase because " + this.reason + "!");
        }
    }

    List<EventPhase> getPhases();

    EventPhase getCurrentPhase();

    default void registerPhase(EventPhase phase) {
        getPhases().add(phase);
    }

    // to the next phase
    default boolean nextPhase() {
        List<EventPhase> phases = getPhases();
        int currentIndex = phases.indexOf(getCurrentPhase());
        if (currentIndex < phases.size() - 1) {
            setCurrentPhase(phases.get(currentIndex + 1));
            return true;
        }
        return false;//This is the lase phase.
    }

    void setCurrentPhase(EventPhase phase);

    // Check if the current phase is cancelable
    default boolean isCancelable() {
        EventPhase currentPhase = getCurrentPhase();
        return currentPhase != null && currentPhase.cancelable();
    }

    // Get reason
    default String whyCantCancel() {
        EventPhase currentPhase = getCurrentPhase();
        return currentPhase != null ? currentPhase.whyCantCancel() : "No current phase set!";
    }
}
