package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.data.ConfigSection;
import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketReceiveEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.Randomizer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.math.Direction;

import static io.github.itzispyder.clickcrystals.ClickCrystals.config;

public class AutoTotem extends Module implements Listener {

    private static int autoTotemShortDelay = config.getOrDefault("plus.autoTotemShortDelay", Integer.class, 50);
    private static int autoTotemLongDelay = config.getOrDefault("plus.autoTotemLongDelay", Integer.class, 100);

    public static int getAutoTotemLongDelay() {
        return autoTotemLongDelay;
    }
    public static int getAutoTotemShortDelay() {
        return autoTotemShortDelay;
    }

    public static void setAutoTotemDelay(int autoTotemShortDelay, int autoTotemLongDelay) {
        AutoTotem.autoTotemShortDelay = autoTotemShortDelay;
        AutoTotem.autoTotemLongDelay = autoTotemLongDelay;
        config.set("plus.autoTotemShortDelay", new ConfigSection<>(autoTotemShortDelay));
        config.set("plus.autoTotemLongDelay", new ConfigSection<>(autoTotemLongDelay));
        config.save();
    }

    private final ScheduledTask swapTask = new ScheduledTask(this::swap);

    public AutoTotem() {
        super("AutoTotem", Categories.OTHER, "Automatically swaps a new totem to your offhand.");
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
            if (!HotbarUtils.has(Items.TOTEM_OF_UNDYING)) return;
            String name = ent.getDisplayName().getString();
            switch (packet.getStatus()) {
                case 35 -> {
                    if (!name.equals(mc.player.getDisplayName().getString())) return;
                    if (!HotbarUtils.has(Items.TOTEM_OF_UNDYING)) return;
                    int attempts = 0;
                    int maxAttempts = 10;
                    ChatUtils.sendDebugMessage("§aTrying to retotem, Attempts: §b" + attempts + " §aMax Attempts: §b" + maxAttempts);
                    while (attempts < maxAttempts) {
                        if (HotbarUtils.isHolding(Items.TOTEM_OF_UNDYING)) {
                            ChatUtils.sendDebugMessage("§aSuccessfully finished after §b" + attempts + " §aAttempts!");
                            swapTask.runDelayedTask(Randomizer.rand(autoTotemShortDelay, autoTotemLongDelay));
                            return;
                        }
                        HotbarUtils.search(Items.TOTEM_OF_UNDYING);
                        attempts++;
                        ChatUtils.sendDebugMessage("§aAttempt §b" + attempts);
                    }
                }
            }
        }
    }
    private void swap(){
        if (!HotbarUtils.isHolding(Items.TOTEM_OF_UNDYING)) return;
        PlayerActionC2SPacket swap = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND,mc.player.getBlockPos(), Direction.UP);
        mc.player.networkHandler.sendPacket(swap);
    }
}