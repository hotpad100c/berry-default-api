package berry.api.mixins.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import berry.api.eventbus.EventBus;
import berry.api.eventbus.events.RegisterCommandsEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;

@Mixin (Commands.class)
public abstract class CommandsRegisterMixin {
    @Accessor (remap = false)
    public abstract CommandDispatcher <CommandSourceStack> getDispatcher ();
    @Inject (method = "<init>", at = @At (value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), remap = false)
    private void inject (CommandSelection env, CommandBuildContext ctx, CallbackInfo ci) {
        EventBus.MINECRAFT.fire (new RegisterCommandsEvent (this.getDispatcher (), env, ctx));
    }
}
