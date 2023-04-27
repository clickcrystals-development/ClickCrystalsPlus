package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.events.listeners.ChatEventListener;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class OptOutCommand extends Command {
    public OptOutCommand() {
        super("optout","§7If set to false (You are not opted out) People can control your computer with various commands.","/optout [on|off]");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(literal("on").executes(context -> {
            ChatEventListener.setOptout(true);
            ChatUtils.sendPrefixMessage("Your OptOut is now set to " + ChatEventListener.isOptout());
            return SINGLE_SUCCESS;
        }));
        builder.then(literal("off").executes(context -> {
            ChatEventListener.setOptout(false);
            ChatUtils.sendPrefixMessage("Your OptOut is now set to " + ChatEventListener.isOptout());
            return SINGLE_SUCCESS;
        }));
        builder.executes(context -> {
            ChatEventListener.setOptout(!ChatEventListener.isOptout());
            ChatUtils.sendPrefixMessage("Your OptOut is now set to " + ChatEventListener.isOptout());
            return SINGLE_SUCCESS;
        });
    }
}
