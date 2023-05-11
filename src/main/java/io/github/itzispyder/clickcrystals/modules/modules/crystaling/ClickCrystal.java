package io.github.itzispyder.clickcrystals.modules.modules.crystaling;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.BlockUtils;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;

/**
 * ClickCrystal module
 */
public class ClickCrystal extends Module implements Listener {

    /**
     * Module constructor
     */
    public ClickCrystal() {
        super("ClickCrystal", Categories.CRYSTALLING,"Binds end crystal place to left click.");
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
    private void onSendPacket(PacketSendEvent e) {
        if (e.getPacket() instanceof PlayerActionC2SPacket packet) {
            if (packet.getAction() != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) return;
            final BlockPos pos = packet.getPos();
            if (!BlockUtils.isCrystallabe(pos)) return;

            if (HotbarUtils.isHolding(Items.END_CRYSTAL)) {
                e.setCancelled(true);
                BlockUtils.interact(pos,packet.getDirection());
            }
        }
    }
}
