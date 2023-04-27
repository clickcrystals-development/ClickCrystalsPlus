package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.events.listeners.ChatEventListener;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class DebugModeCommand extends Command {
    public DebugModeCommand() {
        super("debugmode","§7Logs actions in chat.","/debugmode [on|off]");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(literal("on").executes(context -> {
            ChatUtils.setDebugMode(true);
            ChatUtils.sendPrefixMessage("Debug mode is now set to " + ChatUtils.debugMode);
            return SINGLE_SUCCESS;
        }));
        builder.then(literal("off").executes(context -> {
            ChatUtils.setDebugMode(false);
            ChatUtils.sendPrefixMessage("Debug mode is now set to " + ChatUtils.debugMode);
            return SINGLE_SUCCESS;
        }));
        builder.executes(context -> {
            ChatUtils.setDebugMode(!ChatUtils.debugMode);
            ChatUtils.sendPrefixMessage("Debug mode is now set to " + ChatUtils.debugMode);
            return SINGLE_SUCCESS;
        });
    }
}
