package berry.api.mixins.events;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import berry.api.utils.respack.PackInfoCommand;
import net.minecraft.server.MinecraftServer.ServerResourcePackInfo;
import net.minecraft.server.dedicated.DedicatedServer;

@Mixin (DedicatedServer.class)
public abstract class DedicatedServerMixin {
    @Inject (method = "getServerResourcePack", at = @At ("HEAD"), cancellable = true, remap = false)
    private void gsrp (CallbackInfoReturnable <Optional <ServerResourcePackInfo>> cir) {
        cir.setReturnValue (PackInfoCommand.current);
    }
}
