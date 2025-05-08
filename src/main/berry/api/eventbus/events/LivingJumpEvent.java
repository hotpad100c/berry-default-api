package berry.api.eventbus.events;

import berry.api.eventbus.Event;
import net.minecraft.world.entity.LivingEntity;

public class LivingJumpEvent extends Event {
    @Override
    public boolean cancellable () {
        return false; // To cancel, set power to 0
    }
    private double power;
    public final double original;
    public final LivingEntity entity;
    public LivingJumpEvent (double power, double original, LivingEntity le) {
        this.power = power;
        this.original = original;
        this.entity = le;
    }
    public double getPower () {
        return power;
    }
    public void setPower (double power) {
        this.power = power;
    }
}
