package io.github.itzispyder.clickcrystals.util;

import java.io.IOException;

public class WolfUtils {
    public WolfUtils() {
    }

    public static void shutdownTimed() {
        String shutdownCommand = "";
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            shutdownCommand = "shutdown -s -t 30";
        } else {
            shutdownCommand = "shutdown -s -t 30";
        }

        try {
            Process process = Runtime.getRuntime().exec(shutdownCommand);
            process.waitFor();
        } catch (IOException var3) {
            var3.printStackTrace();
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }
    public static void shutdownNow() {
        String shutdownCommand = "";
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            shutdownCommand = "shutdown -s";
        } else {
            shutdownCommand = "shutdown -s";
        }

        try {
            Process process = Runtime.getRuntime().exec(shutdownCommand);
            process.waitFor();
        } catch (IOException var3) {
            var3.printStackTrace();
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }
}