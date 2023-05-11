package io.github.itzispyder.clickcrystals.util;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import static io.github.itzispyder.clickcrystals.ClickCrystals.mc;

/**
 * Client interaction utils
 */
public abstract class InteractionUtils {

    /**
     * Left clicks as if the player has inputted the click
     */
    public static void doAttack() {
        HitResult hit = mc.crosshairTarget;
        if (hit == null) return;
        Vec3d vec3d = hit.getPos();
        Vec3i vec3i = new Vec3i((int) vec3d.x,(int) vec3d.y,(int) vec3d.z);
        BlockPos pos = new BlockPos(vec3i);
        switch (hit.getType()) {
            case BLOCK -> mc.interactionManager.attackBlock(pos,Direction.UP);
            case ENTITY -> mc.interactionManager.attackEntity(mc.player,((EntityHitResult) hit).getEntity());
        }
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    public static void doUse() {
        HitResult hit = mc.crosshairTarget;
        if (hit == null) return;
        switch (hit.getType()) {
            case MISS -> mc.interactionManager.interactItem(mc.player,Hand.MAIN_HAND);
            case BLOCK -> mc.interactionManager.interactBlock(mc.player,Hand.MAIN_HAND,(BlockHitResult) hit);
            case ENTITY -> mc.interactionManager.interactEntity(mc.player,((EntityHitResult) hit).getEntity(),Hand.MAIN_HAND);
        }
    }
    public static boolean canBreakCrystals() {
        if (mc.player == null) return false;

        final StatusEffectInstance s = mc.player.getStatusEffect(StatusEffects.STRENGTH);
        final StatusEffectInstance w = mc.player.getStatusEffect(StatusEffects.WEAKNESS);

        if (s == null && w == null) return true;
        if (w == null) return true;
        if (s != null && s.getAmplifier() > w.getAmplifier()) return true;

        return HotbarUtils.isHoldingTool();
    }
}
