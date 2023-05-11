package io.github.itzispyder.clickcrystals.modules.modules.chat;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketReceiveEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.math.Direction;

public class AutoCope extends Module implements Listener {

    private final ScheduledTask swapTask = new ScheduledTask(this::swap);

    public AutoCope() {
        super("AutoCope", Categories.CHAT, "Automatically copes for you.");
    }

    @Override
    protected void onEnable() {
        system.addListener(this);
    }

    @Override
    protected void onDisable() {
        system.removeListener(this);
    }

    @EventHandler
    private void onReceiveStatus(PacketReceiveEvent e) {
        if (e.getPacket() instanceof EntityStatusS2CPacket packet) {
            final Entity ent = packet.getEntity(mc.player.getWorld());
            if (ent == null) return;
            if (ent.getType() != EntityType.PLAYER) return;
            String name = ent.getDisplayName().getString();
            switch (packet.getStatus()) {
                case 3 -> {
                    if (name.equals(mc.player.getDisplayName().getString())) {
                        String[] messages = {
                                "bro you have to be hacking",
                                "AAAAAA WHY DONT THEY JUST DISABLE ITEMS",
                                "BRO WHY DOES MY INV ALWAYS GET CLOGGED WITH GRANITE",
                                "dude its always the cobble",
                                "BRO UR SO TRASH",
                                "UR LITTERALY USING CW",
                                "omfg why dont they just /gamerule doTileDrops false already",
                                "WHYYYyyy GRAVEL ALWAYS CLOGS THE INVVV",
                                "OMG BRO ITS ALWAYS THE COBBLESTONE"
                        };
                        String message = messages[(int) (Math.random() * messages.length)];
                        ChatUtils.sendChatMessage(message);
                    }
                }
            }
        }
    }
    private void swap(){
        PlayerActionC2SPacket swap = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND,mc.player.getBlockPos(), Direction.UP);
        mc.player.networkHandler.sendPacket(swap);
    }


    private <T> T getOrDefault(T value, T def) {
        return value != null ? value : def;
    }

}