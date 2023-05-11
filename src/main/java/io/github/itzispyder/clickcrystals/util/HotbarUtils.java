package io.github.itzispyder.clickcrystals.util;

import io.github.itzispyder.clickcrystals.scheduler.ScheduledTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.github.itzispyder.clickcrystals.ClickCrystals.mc;

/**
 * Client hot bar utils
 */
public abstract class HotbarUtils {

    /**
     * Search of the item in a player's hot bar.
     * Scrolls to the item.
     * @param item item to search
     */
    public static boolean search(Item item) {
        final PlayerInventory inv = mc.player.getInventory();
        for (int i = 0; i <= 8; i ++) {
            if (inv.getStack(i).isOf(item)) {
                inv.selectedSlot = i;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an item is in your offhand.
     * @param item
     * @return
     */
    public static boolean isInOffhand(Item item) {
        if (mc.player.getInventory().offHand.contains(item)) return true;
        return false;
    }

    /**
     * Attempts to switch an item into your offhand, if you have it
     * will try until success, runs out of attempts
     * @param item
     * @return
     */
    private static final ScheduledTask swapTask = new ScheduledTask(HotbarUtils::sendSwapPacket);
    public static boolean toOffHand(Item item, int shortDelay, int longDelay, int maxAttempts) {
        if (!has(item)) return false;
        int attempts = 0;
        while (attempts < maxAttempts) {
            if (isInOffhand(item)) {
                swapTask.runDelayedTask(Randomizer.rand(shortDelay, longDelay));
                return true;
            }
            search(item);
            attempts++;
        }
        return false;
    }
    public static void sendSwapPacket() {
        PlayerActionC2SPacket packet = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND,mc.player.getBlockPos(), Direction.UP);
        mc.player.networkHandler.sendPacket(packet);
    }

    /**
     * Checks of the hotbar has an item
     * @param item item
     * @return has item
     */
    public static boolean has(Item item) {
        final PlayerInventory inv = mc.player.getInventory();
        for (int i = 0; i <= 8; i ++) {
            if (inv.getStack(i).isOf(item)) return true;
        }
        return false;
    }

    /**
     * If the player's held item matches the provided item type
     * @param item item type
     * @return match
     */
    public static boolean isHolding(Item item) {
        return isHolding(item,Hand.MAIN_HAND);
    }

    /**
     * If the players held item in the specified hand matches the provided type
     * @param item item type
     * @param hand hand to check
     * @return match
     */
    public static boolean isHolding(Item item, Hand hand) {
        return mc.player.getStackInHand(hand).isOf(item);
    }

    /**
     * If the player's held item's name contains the following string
     * @param contains string to check
     * @return match
     */
    public static boolean nameContains(String contains) {
        return nameContains(contains,Hand.MAIN_HAND);
    }

    /**
     * If the player's held item in thee specified hand's name contains the following string
     * @param contains string to check
     * @param hand hand to check
     * @return match
     */
    public static boolean nameContains(String contains, Hand hand) {
        ItemStack item = mc.player.getStackInHand(hand);
        return item != null && item.getTranslationKey().toLowerCase().contains(contains.toLowerCase());
    }

    public static void forEachItem(Consumer<ItemStack> run) {
        for (int i = 0; i < 9; i ++) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item == null) continue;
            if (item.isEmpty()) continue;
            run.accept(item);
        }
    }

    public static void forEachItem(BiConsumer<Integer,ItemStack> run) {
        for (int i = 0; i < 9; i ++) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item == null) continue;
            if (item.isEmpty()) continue;
            run.accept(i,item);
        }
    }

    public static ItemStack[] getContents() {
        PlayerInventory inv = mc.player.getInventory();
        ItemStack[] stacks = new ItemStack[]{};
        for (int i = 0; i < 9; i++) {
            stacks[i] = inv.getStack(i);
        }
        return stacks;
    }

    public static boolean isForClickCrystal() {
        return nameContains("sword")
                || isHolding(Items.END_CRYSTAL)
                || isHolding(Items.OBSIDIAN)
                || isHolding(Items.TOTEM_OF_UNDYING)
                || isHolding(Items.GLOWSTONE)
                || isHolding(Items.RESPAWN_ANCHOR);
    }
    public static double getFullHealthRatio() {
        final PlayerEntity p = mc.player;
        if (p == null) return 0.0;
        return p.getHealth() / p.getMaxHealth();
    }
    public static boolean isHoldingTool() {
        return nameContains("_sword") ||
                nameContains("_pickaxe") ||
                nameContains("_axe") ||
                nameContains("_hoe") ||
                nameContains("_shovel") ||
                nameContains("trident");
    }
    public static boolean isTotemed() {
        if (mc.player == null) return false;

        final ItemStack main = mc.player.getStackInHand(Hand.MAIN_HAND);
        final ItemStack off = mc.player.getStackInHand(Hand.OFF_HAND);
        final Item totem = Items.TOTEM_OF_UNDYING;

        return (main != null && main.getItem() == totem) || (off != null && off.getItem() == totem);
    }
}
