package berry.api.utils.respack;

import java.util.Optional;
import java.util.UUID;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer.ServerResourcePackInfo;

public class PackInfoCommand {
    public static Optional <ServerResourcePackInfo> current;
    static {
        current = PackInfoIO.read ();
    }
    public static void register (CommandDispatcher <CommandSourceStack> dispatcher) {
        dispatcher.register (
            Commands.literal ("setrespack") .requires (x -> x.hasPermission (4))
            .executes (ctx -> {
                PackInfoIO.write (current = Optional.empty ());
                return 1;
            })
            .then (
                Commands.argument ("url", StringArgumentType.string ()) .then (
                    Commands.argument ("hash", StringArgumentType.string ()) .executes (
                        ctx -> {
                            String url = StringArgumentType.getString (ctx, "url");
                            String hash = StringArgumentType.getString (ctx, "hash");
                            PackInfoIO.write (current = Optional.of (new ServerResourcePackInfo (UUID.randomUUID (), url, hash, true, null)));
                            return 1;
                        }
                    ) .then (
                        Commands.argument ("required", BoolArgumentType.bool ()) .executes (
                            ctx -> {
                                String url = StringArgumentType.getString (ctx, "url");
                                String hash = StringArgumentType.getString (ctx, "hash");
                                boolean required = BoolArgumentType.getBool (ctx, "required");
                                PackInfoIO.write (current = Optional.of (new ServerResourcePackInfo (UUID.randomUUID (), url, hash, required, null)));
                                return 1;
                            }
                        )
                    )
                )
            )
        );
    }
}
