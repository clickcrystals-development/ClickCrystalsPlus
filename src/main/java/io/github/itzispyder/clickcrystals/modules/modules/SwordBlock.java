package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PacketSendEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

/**
 * Sword2Pearl module
 */
public class SwordBlock extends Module implements Listener {

    private static long cooldown;

    /**
     * Module constructor
     */
    public SwordBlock() {
        super("SwordBlock", Categories.CRYSTALLING,"Right click your sword to raise your shield. (Not Compatible with TP Blade)");
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
    private void onRightClick(PacketSendEvent e) {
        if (e.getPacket() instanceof PlayerInteractItemC2SPacket) {
            if (!HotbarUtils.nameContains("sword")) return;
            if (!HotbarUtils.has(Items.SHIELD)) return;
            if (cooldown > System.currentTimeMillis()) return;
            cooldown = System.currentTimeMillis() + (50 * 4);
            int s = mc.player.getInventory().selectedSlot;
            HotbarUtils.search(Items.SHIELD);
            InteractionUtils.doUse();
            mc.player.getInventory().selectedSlot = s;
        }
    }
}
