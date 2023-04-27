package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.ClientTickStartEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

/**
 * ClickCrystal module
 */
public class CWCrystal extends Module implements Listener {

    private final ScheduledTask breakCrystal = new ScheduledTask(InteractionUtils::doAttack);

    /**
     * Module constructor
     */
    public CWCrystal() {
        super("CWCrystal", Categories.CRYSTALLING,"Automatically breaks crystals you place");
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
    private void onTick(ClientTickStartEvent e) {
        if (mc.options.useKey.isPressed()) this.attackCrystal();
    }

    private void attackCrystal() {
        HitResult hit = mc.crosshairTarget;
        if (hit == null) return;
        if (hit.getType() != HitResult.Type.ENTITY) return;
        Entity ent = ((EntityHitResult)hit).getEntity();
        if (ent.getType() != EntityType.END_CRYSTAL) return;
        InteractionUtils.doAttack();
    }
}
