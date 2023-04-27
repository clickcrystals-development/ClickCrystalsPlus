package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.ClientTickStartEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.util.HotbarUtils;
import io.github.itzispyder.clickcrystals.util.InteractionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import static net.minecraft.entity.effect.StatusEffects.WEAKNESS;

/**
 * ClickCrystal module
 */
public class AntiWeakness extends Module implements Listener {

    /**
     * Module constructor
     */
    public AntiWeakness() {
        super("AntiWeakness", Categories.CRYSTALLING,"Allows you to break end crystals even when you have weakness.");
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
        if (!mc.player.hasStatusEffect(WEAKNESS)) return;
        if (mc.options.attackKey.isPressed()) this.bypassHit();
    }
    private void bypassHit() {
        HitResult hit = mc.crosshairTarget;
        if (hit == null) return;
        if (hit.getType() != HitResult.Type.ENTITY) return;
        Entity ent = ((EntityHitResult)hit).getEntity();
        if (ent.getType() != EntityType.END_CRYSTAL) return;
        HotbarUtils.search(Items.NETHERITE_SWORD);
        InteractionUtils.doAttack();
        HotbarUtils.search(Items.END_CRYSTAL);
    }
}
