package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.data.ConfigSection;
import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import io.github.itzispyder.clickcrystals.util.Randomizer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import static io.github.itzispyder.clickcrystals.ClickCrystals.config;

/**
 * Anchor2Glowstone module
 */
public class InstaAnchor extends Module implements Listener {

    private static int chargeShortDelay = config.getOrDefault("plus.chargeShortDelay", Integer.class, 10);
    private static int chargeLongDelay = config.getOrDefault("plus.chargeLongDelay", Integer.class, 50);
    private static int explodeShortDelay = config.getOrDefault("plus.explodeShortDelay", Integer.class, 50);
    private static int explodeLongDelay = config.getOrDefault("plus.explodeLongDelay", Integer.class, 100);

    public static int getChargeShortDelay() {
        return chargeShortDelay;
    }
    public static int getChargeLongDelay() {
        return chargeLongDelay;
    }
    public static int getExplodeShortDelay() {
        return explodeShortDelay;
    }
    public static int getExplodeLongDelay() {
        return explodeShortDelay;
    }
    private final ScheduledTask charge = new ScheduledTask(this::autoCharge);

    private final ScheduledTask explode = new ScheduledTask(this::autoExplode);

    public static void setChargeDelay(int chargeShortDelay, int chargeLongDelay) {
        InstaAnchor.chargeShortDelay = chargeShortDelay;
        InstaAnchor.chargeLongDelay = chargeLongDelay;
        config.set("plus.chargeShortDelay", new ConfigSection<>(chargeShortDelay));
        config.set("plus.chargeLongDelay", new ConfigSection<>(chargeLongDelay));
        config.save();
    }
    public static void setExplodeDelay(int explodeShortDelay, int explodeLongDelay) {
        InstaAnchor.explodeShortDelay = explodeShortDelay;
        InstaAnchor.explodeLongDelay = explodeLongDelay;
        config.set("plus.explodeShortDelay", new ConfigSection<>(explodeShortDelay));
        config.set("plus.explodeLongDelay", new ConfigSection<>(explodeLongDelay));
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
            BlockPos pos = packet.getBlockHitResult().getBlockPos();
            BlockState state = mc.player.getWorld().getBlockState(pos);
            if (state == null) return;
            if (!HotbarUtils.has(Items.RESPAWN_ANCHOR)) return;
            if (!HotbarUtils.has(Items.GLOWSTONE)) return;
            if (HotbarUtils.isHolding(Items.RESPAWN_ANCHOR)) {
                if (state.isOf(Blocks.RESPAWN_ANCHOR)) {
                    int charges = state.get(RespawnAnchorBlock.CHARGES);
                    if (charges >= 1) return;
                }
                charge.runDelayedTask(Randomizer.rand(chargeShortDelay,chargeLongDelay));
                explode.runDelayedTask(Randomizer.rand(explodeShortDelay,explodeLongDelay));
            }
        }
    }

    private void autoExplode() {
        HotbarUtils.search(Items.RESPAWN_ANCHOR);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();
    }

    private void autoCharge() {
        HotbarUtils.search(Items.GLOWSTONE);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();
        HotbarUtils.search(Items.RESPAWN_ANCHOR);
    }
}
