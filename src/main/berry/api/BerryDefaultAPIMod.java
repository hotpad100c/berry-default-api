package berry.api;

import berry.api.eventbus.EventBus;
import berry.api.eventbus.events.*;
import berry.api.utils.respack.PackInfoCommand;
import berry.loader.BerryLoader;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

@Mod (modid = "defaultapi", version = "0.3.0")
public class BerryDefaultAPIMod implements BerryMod {
    public BerryDefaultAPIMod () {
        EventBus.MINECRAFT
            .registerEventType (LivingJumpEvent.class)
            .registerEventType (ServerLevelTickEvent.Pre.class)
            .registerEventType (ServerLevelTickEvent.Post.class)
            .registerEventType (RegisterCommandsEvent.class)

            .addListener (
                event -> {
                    final String version = this.getModVersion ();
                    event.dispatcher.register (
                        Commands.literal ("berryver") .executes (
                            ctx -> {
                                var player = ctx.getSource () .getPlayer ();
                                if (player != null) player.sendSystemMessage (
                                    Component.literal ("Berry Default API version ")
                                    .append (Component.literal (version) .withColor (0xff80))
                                );
                                return 1;
                            }
                        )
                    );
                }, RegisterCommandsEvent.class
            )
        ;
        if (BerryLoader.getSide () .equals ("SERVER")) {
            // Take over server resourcepack management
            EventBus.MINECRAFT.addListener (event -> PackInfoCommand.register (event.dispatcher), RegisterCommandsEvent.class);
        }
    }
}
