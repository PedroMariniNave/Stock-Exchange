package com.zpedroo.stockexchange.utils.config;

import com.zpedroo.stockexchange.utils.FileUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public static final List<String> NEW_VALUE = getColored(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Messages.new-value"));

    public static final String INVALID_NUMBER = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.invalid-number"));

    private static List<String> getColored(List<String> list) {
        List<String> coloredList = new ArrayList<>(list.size());
        for (String str : list) {
            coloredList.add(getColored(str));
        }

        return coloredList;
    }

    private static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}