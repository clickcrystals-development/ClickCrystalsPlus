package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.modules.modules.chat.AutoPM;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class SetPMCommand extends Command {
    public SetPMCommand() {
        super("setpmcommand","§7Sets the PM command for AutoPM.","/setpmcomamnd [command]", "spc");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(argument("pmCommand", StringArgumentType.string()).executes(context -> {
            String pmCommand = context.getArgument("pmCommand", String.class);
            AutoPM.setMsgCommand(pmCommand);
            ChatUtils.sendPrefixMessage("§bSet the PM command to: §7" + pmCommand);
            return SINGLE_SUCCESS;
        }));
    }
}
