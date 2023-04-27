package io.github.itzispyder.clickcrystals.modules.modules;

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

public class AutoEZ extends Module implements Listener {

    private static final Map<String,Integer> totemPops = new HashMap<>();
    private static final Random random = new Random();
    public AutoEZ() {
        super("AutoEZ", Categories.CHAT, "Automatically taunts players in chat.");
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
            boolean isWithinDist = ent.getBlockPos().isWithinDistance(mc.player.getBlockPos(), 10);
            if (!isWithinDist) return;
            if (name.equals(mc.player.getDisplayName().getString())) return;
            switch (packet.getStatus()) {
                case 35 -> {
                    setPops(ent,getPops(ent) + 1);
                    String[] messages = {
                            "<POPS> on <NAME> already...",
                            "POP!",
                            "I Popped <NAME> <POPS> times!",
                            "Another pop on <NAME>",
                            "<NAME> there is no need for <POPS> totems",
                            "Thanks for the fireworks <NAME>",
                            "<NAME> GOT POPPED BY CC+",
                            "Another pop on <NAME>",
                            "EZ POP <NAME>!",
                            "Pop more, <NAME>!",
                            "Get popped by CC+ EZ <NAME>"
                    };
                    String message = messages[(int) (Math.random() * messages.length)];
                    message = message.replace("<NAME>", name).replace("<POPS>", String.format("%d", getPops(ent)));
                    ChatUtils.sendChatMessage(message);
                }
                case 3 -> {
                    String[] messages = {
                            "EZ <NAME>! Imagine needing <POPS> totems :skull:",
                            "<NAME> was so ez lmao", "Imagine being <NAME> and having <POPS> totems :skull:",
                            "<NAME>, You should download ClickCrystals!",
                            "Despite having <POPS> totems, <NAME> still died to CC+",
                            "LMAOOOO <NAME> GET GOOD :skull:",
                            "Another kill on <NAME>! EZZZZZ",
                            "EZ BEAM <NAME>!",
                            "UR SO TRASH <NAME>",
                            "PFFTTT :skull: YOU HAD <POPS> TOTEMS LMAOO"
                    };
                    String message = messages[(int) (Math.random() * messages.length)];
                    message = message.replace("<NAME>", name).replace("<POPS>", String.format("%d", getPops(ent)));
                    ChatUtils.sendChatMessage(message);
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