package io.github.itzispyder.clickcrystals.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.itzispyder.clickcrystals.commands.Command;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import io.github.itzispyder.clickcrystals.util.WolfUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public class HWIDCommand extends Command {
    public HWIDCommand() {
        super("hwid","§7Gets your HWID then copies it to your clipboard.","/hwid", "hwid");
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.executes(context -> {
            ChatUtils.sendPrefixMessage("§aYour HWID is: §7" + WolfUtils.getHWID());
            WolfUtils.copy(WolfUtils.getHWID());
            return SINGLE_SUCCESS;
        });
    }
}
