package berry.apitest;

import berry.api.BerryMod;
import berry.api.Mod;
import berry.api.eventbus.EventBus;
import berry.api.eventbus.events.*;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

@Mod (modid = "apitestmod", version = "1.0.0")
public class APITestMod implements BerryMod {
    @SuppressWarnings ("unchecked")
    public APITestMod () {
        EventBus.MINECRAFT
            .addListener (
                event -> {
                    event.setPower (event.original);
                }, LivingJumpEvent.class
            )
            .addListener (
                event -> {
                    event.dispatcher.register (
                        Commands.literal ("hello") .executes (
                            ctx -> {
                                var player = ctx.getSource () .getPlayer ();
                                if (player != null) player.sendSystemMessage (
                                    Component.literal ("Hello from ")
                                    .append (Component.literal ("Berry Test Mod") .withColor (0xff80))
                                    .append (Component.literal ("!"))
                                );
                                return 1;
                            }
                        )
                    );
                    event.dispatcher.register (
                        Commands.literal ("assigndata") .then (
                            Commands.argument ("id", ResourceLocationArgument.id ()) .executes (
                                ctx -> {
                                    ServerPlayer player = ctx.getSource () .getPlayer ();
                                    if (player == null) return 0;
                                    ItemStack src = player.getItemInHand (InteractionHand.OFF_HAND), dst = player.getItemInHand (InteractionHand.MAIN_HAND);
                                    ResourceLocation loc = ResourceLocationArgument.getId (ctx, "id");
                                    DataComponentType type = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue (loc);
                                    if (type == null) {
                                        player.sendSystemMessage (Component.literal ("Cannot find component type " + loc.toString ()) .withColor (0xff0000));
                                        return 0;
                                    }
                                    dst.set (type, src.get (type));
                                    return 1;
                                }
                            )
                        )
                    );
                }, RegisterCommandsEvent.class
            )
        ;
    }
}
