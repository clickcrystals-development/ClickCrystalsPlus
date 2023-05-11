package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.modules.modules.anchoring.InstaAnchor;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class SetExplodeDelayCommand extends Command {
    public SetExplodeDelayCommand() {
        super("setexplodeelay","§7Sets the delays for Exploding anchors.","/sed [int short delay] [int long delay]", "scd");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(argument("ShortDelay", IntegerArgumentType.integer()).then(argument("LongDelay", IntegerArgumentType.integer()).executes(context -> {
            int shortDelay = context.getArgument("ShortDelay", Integer.class);
            int longDelay = context.getArgument("LongDelay", Integer.class);
            InstaAnchor.setClickDelays(shortDelay, longDelay);
            ChatUtils.sendPrefixMessage("§3Set the explode delay to §a" + shortDelay + " §3through §a" + longDelay);
            return SINGLE_SUCCESS;
        })));
    }
}
