package org.example.phantommarket.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String translateLegacy(String message) {
        if (message == null) return "";

        // Обработка HEX цветов (&#FFFFFF)
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        matcher.appendTail(buffer);

        // Обработка обычных кодов (&a, &l и т.д.)
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}