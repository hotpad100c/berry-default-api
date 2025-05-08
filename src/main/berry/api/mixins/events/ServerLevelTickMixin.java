package berry.api.mixins.events;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import berry.api.eventbus.EventBus;
import berry.api.eventbus.events.ServerLevelTickEvent;
import net.minecraft.server.level.ServerLevel;

@Mixin (ServerLevel.class)
public abstract class ServerLevelTickMixin {
    @Inject (method = "tick", at = @At ("HEAD"), cancellable = true, remap = false)
    private void pre (BooleanSupplier time, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (EventBus.MINECRAFT.fire (new ServerLevelTickEvent.Pre (level, time)) .cancelled ()) ci.cancel ();
    }
    @Inject (method = "tick", at = @At ("RETURN"), remap = false)
    private void post (BooleanSupplier time, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        EventBus.MINECRAFT.fire (new ServerLevelTickEvent.Post (level, time));
    }
}
