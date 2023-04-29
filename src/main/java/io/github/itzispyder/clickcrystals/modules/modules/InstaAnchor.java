package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.data.ConfigSection;
import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import io.github.itzispyder.clickcrystals.util.BlockUtils;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import io.github.itzispyder.clickcrystals.util.Randomizer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import static io.github.itzispyder.clickcrystals.ClickCrystals.config;

/**
 * Anchor2Glowstone module
 */
public class InstaAnchor extends Module implements Listener {

    private static int clickDelayMin = config.getOrDefault("plus.chargeShortDelay", Integer.class, 10);
    private static int clickDelayMax = config.getOrDefault("plus.chargeLongDelay", Integer.class, 50);
    private static final ScheduledTask detonation = new ScheduledTask(InstaAnchor::detonate);
    private static final ScheduledTask charge = new ScheduledTask(InstaAnchor::charge);
    private static long cooldown;

    public static int getClickDelayMin() {
        return clickDelayMin;
    }

    public static int getClickDelayMax() {
        return clickDelayMax;
    }

    public static int getDelay() {
        return Randomizer.rand(getClickDelayMin(), getClickDelayMax());
    }

    public static void setClickDelays(int clickDelayMin, int clickDelayMax) {
        InstaAnchor.clickDelayMin = clickDelayMin;
        InstaAnchor.clickDelayMax = clickDelayMax;
        config.set("plus.chargeShortDelay", new ConfigSection<>(clickDelayMin));
        config.set("plus.chargeLongDelay", new ConfigSection<>(clickDelayMax));
        config.save();
    }

    /**
     * Module constructor
     */
    public InstaAnchor() {
        super("InstaAnchor", Categories.ANCHORING,"Automatically charges AND explodes your anchors.");
    }

    @Override
    protected void onEnable() {
        system.addListener(this);
    }

    @Override
    protected void onDisable() {
        system.removeListener(this);
    }

    /**
     * Module function
     * @param e packet send event
     */
    @EventHandler
    private void onPacketSend(PacketSendEvent e) {
        if (e.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
            final BlockHitResult hit = packet.getBlockHitResult();
            final BlockPos pos = hit.getBlockPos();
            final BlockState clicked = mc.player.getWorld().getBlockState(pos);

            if (clicked == null) return;
            if (!HotbarUtils.has(Items.RESPAWN_ANCHOR)) return;
            if (!HotbarUtils.has(Items.GLOWSTONE)) return;

            if (HotbarUtils.isHolding(Items.RESPAWN_ANCHOR)) {

                if (clicked.isOf(Blocks.RESPAWN_ANCHOR)) {
                    int charges = clicked.get(RespawnAnchorBlock.CHARGES);
                    // if the anchor is already charged
                    if (charges > 0) {
                        detonation.runDelayedTask(getDelay());
                        return;
                    }
                }

                if (cooldown > System.currentTimeMillis()) {
                    e.setCancelled(true);
                    return;
                }
                cooldown = System.currentTimeMillis() + 200;

                // charge
                // block up
                charge.runDelayedTask(getDelay());
            }
        }
    }

    private static void detonate() {
        final BlockState target = BlockUtils.getCrosshair();
        if (target == null) return;
        if (!target.isOf(Blocks.RESPAWN_ANCHOR)) return;

        HotbarUtils.search(Items.RESPAWN_ANCHOR);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();
    }

    private static void charge() {
        final BlockState target = BlockUtils.getCrosshair();
        if (target == null) return;
        if (!target.isOf(Blocks.RESPAWN_ANCHOR)) return;

        HotbarUtils.search(Items.GLOWSTONE);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();

        HotbarUtils.search(Items.RESPAWN_ANCHOR);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();
    }
}
