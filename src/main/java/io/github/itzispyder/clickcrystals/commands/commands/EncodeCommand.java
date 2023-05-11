package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import io.github.itzispyder.clickcrystals.util.WolfUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class EncodeCommand extends Command {
    public EncodeCommand() {
        super("encode64","§7Encodes the given text in b64 then copies it to your clipboard.","/encode64 [text]", "b64");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(argument("text", StringArgumentType.string()).executes(context -> {
            String text = context.getArgument("text", String.class);
            ChatUtils.sendPrefixMessage("§bEnoding text: §7" + text);
            ChatUtils.sendPrefixMessage(text + "§aEncoded text copied to your clipboard: §7" + WolfUtils.encode64(text));
            WolfUtils.copy(WolfUtils.encode64(text));
            return SINGLE_SUCCESS;
        }));
    }
}
