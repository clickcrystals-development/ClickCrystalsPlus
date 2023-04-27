package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.data.ConfigSection;
import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketReceiveEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.github.itzispyder.clickcrystals.ClickCrystals.config;

public class AutoPM extends Module implements Listener {

    private static String msgCommand = config.getOrDefault("plus.msgCommand", String.class, "msg");

    public static String getMsgCommand() {
        return msgCommand;
    }

    public static void setMsgCommand(String msgCommand) {
        AutoPM.msgCommand = msgCommand;
        config.set("plus.msgCommand", new ConfigSection<>(msgCommand));
        config.save();
    }

    private static final Map<String,Integer> totemPops = new HashMap<>();
    private static final Random random = new Random();
    public AutoPM() {
        super("AutoPM", Categories.OTHER, "Like auto EZ but in the player's DMS.");
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
            boolean isWithinDist = ent.getBlockPos().isWithinDistance(mc.player.getBlockPos(), 10);
            if (!isWithinDist) return;
            if (ent == null) return;
            if (ent.getType() != EntityType.PLAYER) return;
            String name = ent.getDisplayName().getString();
            if (name.equals(mc.player.getDisplayName().getString())) return;
            switch (packet.getStatus()) {
                case 35 -> {
                    setPops(ent,getPops(ent) + 1);
                    String[] messages = {
                            msgCommand + " <NAME> <POPS> on you already...",
                            msgCommand + " <NAME> POP!",
                            msgCommand + " <NAME> I Popped you <POPS> times!",
                            msgCommand + " <NAME> Another pop (<POPS>)",
                            msgCommand + " <NAME> bro there is no need for <POPS> totems",
                            msgCommand + " <NAME> Thanks for the fireworks!",
                            msgCommand + " <NAME> GET POPPED BY CC+",
                            msgCommand + " <NAME> Another pop on you",
                            msgCommand + " <NAME> EZ POP!",
                            msgCommand + " <NAME> Pop more!",
                            msgCommand + " <NAME> Get popped by CC+ EZ <NAME>"
                    };
                    String message = messages[(int) (Math.random() * messages.length)];
                    message = message.replace("<NAME>", name).replace("<POPS>", String.format("%d", getPops(ent)));
                    ChatUtils.sendChatCommand(message);
                }
                case 3 -> {
                    String[] messages = {
                            msgCommand + " <NAME> EZ! Imagine needing <POPS> totems :skull:",
                            msgCommand + " <NAME> you were so ez lmao",
                            msgCommand + " <NAME> Imagine having <POPS> totems :skull:",
                            msgCommand + " <NAME> You should download ClickCrystals!",
                            msgCommand + " <NAME> Despite having <POPS> totems, you still died to CC+",
                            msgCommand + " <NAME> LMAOOOO GET GOOD :skull:",
                            msgCommand + " <NAME> Another kill! EZZZZZ",
                            msgCommand + " <NAME> EZ BEAM!",
                            msgCommand + " <NAME> UR SO TRASH BRO",
                            msgCommand + " <NAME> PFFTTT :skull: YOU HAD <POPS> TOTEMS LMAOO"
                    };
                    String message = messages[(int) (Math.random() * messages.length)];
                    message = message.replace("<NAME>", name).replace("<POPS>", String.format("%d", getPops(ent)));
                    ChatUtils.sendChatCommand(message);
                    setPops(ent,0);

                }
            }
        }
    }

    private int getPops(Entity ent) {
        return getOrDefault(totemPops.get(ent.getDisplayName().getString()),0);
    }

    private void setPops(Entity ent, int pops) {
        totemPops.put(ent.getDisplayName().getString(), pops);
    }

    private <T> T getOrDefault(T value, T def) {
        return value != null ? value : def;
    }

}