package io.github.itzispyder.clickcrystals.modules.modules;

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

/**
 * Anchor2Glowstone module
 */
public class AutoCharge extends Module implements Listener {
    private final ScheduledTask charge = new ScheduledTask(this::autoCharge);

    /**
     * Module constructor
     */
    public AutoCharge() {
        super("AutoCharge", Categories.ANCHORING,"Automatically charges your anchors.");
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

            try {
                if (HotbarUtils.isHolding(Items.RESPAWN_ANCHOR)) {
                    if (state.isOf(Blocks.RESPAWN_ANCHOR)) {
                        int charges = state.get(RespawnAnchorBlock.CHARGES);
                        if (charges >= 1) return;
                    }
                    charge.runDelayedTask(Randomizer.rand(InstaAnchor.getChargeShortDelay(),InstaAnchor.getChargeLongDelay()));
                }
            } catch (Exception ignore) {}
        }
    }

    private void autoCharge() {
        HotbarUtils.search(Items.GLOWSTONE);
        mc.player.swingHand(Hand.MAIN_HAND);
        InteractionUtils.doUse();
        HotbarUtils.search(Items.RESPAWN_ANCHOR);
    }
}
