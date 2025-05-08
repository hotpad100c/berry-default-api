package berry.api.mixins.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import berry.api.eventbus.EventBus;
import berry.api.eventbus.events.LivingJumpEvent;
import net.minecraft.world.entity.LivingEntity;

@Mixin (LivingEntity.class)
public abstract class LivingJumpMixin {
    // Original:
    // 1:power := LE jump power
    // 2:set dy to max (power, dy)
    // Now:
    // 1:power := max (LE jump power, dy)
    // 2:fire the event
    // 3:set dy to event.power
    @Redirect (method = "jumpFromGround", at = @At (value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"), remap = false)
    private double jump (double a, double b) {
        double power = Math.max (a, b);
        LivingEntity le = (LivingEntity) (Object) this;
        return EventBus.MINECRAFT.fire (new LivingJumpEvent (power, a, le)) .getPower ();
    }
}
