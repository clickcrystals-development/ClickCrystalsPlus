package io.github.itzispyder.clickcrystals.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WolfUtils {
    public WolfUtils() {
    }
    public static String cleanName(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static void copy(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }
    public static String decode64(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
    public static String encode64(String text) {
        byte[] encodeBytes = Base64.getEncoder().encode(text.getBytes());
        String encodedString = new String(encodeBytes);
        return encodedString;
    }
    public static String isolateString(String original, String known) {
        int index = original.indexOf(known);
        String result = original.substring(index + known.length());
        return result;
    }
    public static void shellCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException var3) {
            var3.printStackTrace();
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
    }
    public static String getHWID() {
        String command = "wmic csproduct get uuid";
        String hwid = "";

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\b[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}\\b");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    hwid = matcher.group(0);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hwid;
    }
    public static List<String> hwids(List<String> strings) {
        return strings.stream().filter(string -> string.contains("<p>")).toList();
    }
    public static List<String> readLines(BufferedReader reader) {
        try {
            List<String> lines = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
            return lines;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}