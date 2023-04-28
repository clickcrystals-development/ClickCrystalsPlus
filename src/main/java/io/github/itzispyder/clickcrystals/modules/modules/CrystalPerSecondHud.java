package io.github.itzispyder.clickcrystals.modules.modules;

import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.ClientTickEndEvent;
import io.github.itzispyder.clickcrystals.events.events.PlayerAttackEntityEvent;
import io.github.itzispyder.clickcrystals.modules.Categories;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.scheduler.ArrayQueue;
import io.github.itzispyder.clickcrystals.scheduler.Queue;
import io.github.itzispyder.clickcrystals.util.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class CrystalPerSecondHud extends Module implements Listener {

    private static int tempCps = 0;
    private static double cps = 0;
    private static int updateSpeed = 7;
    private static int timer = 0;
    private static final Queue<Integer> intQueue = new ArrayQueue<>(15);

    public CrystalPerSecondHud() {
        super("CrystalPerSecondHud", Categories.CRYSTALLING, "Crystals per second counter.");
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
    private void onAttackEntity(PlayerAttackEntityEvent e) {
        final Entity ent = e.getEntity();
        if (ent == null) return;
        if (ent.getType() != EntityType.END_CRYSTAL) return;

        tempCps ++;
    }

    @EventHandler
    private void onTick(ClientTickEndEvent e) {
        if (timer ++ >= 20 / updateSpeed) {
            intQueue.enqueue(tempCps);

            cps = MathUtils.round(MathUtils.avg(intQueue.getElements()) * updateSpeed,100);

            tempCps = 0;
            timer = 0;
        }
    }

    public static double getCrystalPerSecond() {
        return cps;
    }
}
