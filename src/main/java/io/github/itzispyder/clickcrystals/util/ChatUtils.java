package io.github.itzispyder.clickcrystals.util;

import net.minecraft.text.Text;

import static io.github.itzispyder.clickcrystals.ClickCrystals.*;

/**
 * Client chat utils
 */
public abstract class ChatUtils {
    public static boolean debugMode = false;

    public static String debugPrefix = "§7[§bC§3C§a+§7] §e§lDEBUG:§f ";

    public static void setDebugMode(boolean setDebugMode) {
        debugMode = setDebugMode;
    }
    /**
     * Sends a message to the player client-side
     * @param message message
     */
    public static void sendMessage(String message) {
         if (message == null) return;
         if  (mc.player == null) return;
        mc.player.sendMessage(Text.literal(message));
    }

    /**
     * Sends a message to the player client-sided, but with the mod prefix
     * @param message message
     */
    public static void sendDebugMessage(String message) {if (debugMode) sendMessage(debugPrefix + message);}
    public static void sendPrefixMessage(String message) {
        sendMessage(STARTER + message);
    }

    /**
     * Sends the chat command as if the player has typed it
     * @param cmd command
     */
    public static void sendChatCommand(String cmd) {
        mc.player.networkHandler.sendCommand(cmd);
    }

    /**
     * Sends a chat message as if the player has typed it
     * @param msg message
     */
    public static void sendChatMessage(String msg) {
        mc.player.networkHandler.sendChatMessage(msg);
    }
}
