package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.modules.modules.AutoTotem;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class  SetAutoTotemDelayCommand extends Command {
    public SetAutoTotemDelayCommand() {
        super("setautototemdelay","§7Sets the delays for AutoTotem.","/satd [int short delay] [int long delay]", "satd");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(argument("ShortDelay", IntegerArgumentType.integer()).then(argument("LongDelay", IntegerArgumentType.integer()).executes(context -> {
            int shortDelay = context.getArgument("ShortDelay", Integer.class);
            int longDelay = context.getArgument("LongDelay", Integer.class);
            AutoTotem.setAutoTotemDelay(shortDelay, longDelay);
            ChatUtils.sendPrefixMessage("§3Set the random min and max to §a" + shortDelay + " §3and §a" + longDelay);
            return SINGLE_SUCCESS;
        })));
    }
}
