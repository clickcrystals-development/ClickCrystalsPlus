package io.github.itzispyder.clickcrystals.modules.modules.optimization;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.PlayerAttackEntityEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;

public class Optimizer extends Module implements Listener {

    public Optimizer() {
        super("COptimizer", Categories.CRYSTALLING, "Does not wait for a server side kill packet before removing an end crystal entity.");
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
    private void onPacketSend(PlayerAttackEntityEvent e) {
        final Entity ent = e.getEntity();

        if (ent == null) return;
        if (mc.player == null) return;
        if (mc.player.getWorld() == null) return;
        if (mc.isInSingleplayer()) return;
        if (!InteractionUtils.canBreakCrystals()) return;

        if (ent instanceof EndCrystalEntity crystal) {
            crystal.kill();
            crystal.remove(Entity.RemovalReason.KILLED);
            crystal.onRemoved();
        }
    }
}
