package berry.api.eventbus.events;

import com.mojang.brigadier.CommandDispatcher;

import berry.api.eventbus.Event;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class RegisterCommandsEvent extends Event {
    @Override
    public boolean cancellable () {
        return false;
    }
    public final CommandDispatcher <CommandSourceStack> dispatcher;
    public final Commands.CommandSelection environment;
    public final CommandBuildContext context;
    public RegisterCommandsEvent (CommandDispatcher <CommandSourceStack> dispatcher, Commands.CommandSelection environment, CommandBuildContext context) {
        this.dispatcher = dispatcher;
        this.environment = environment;
        this.context = context;
    }
}
