package com.zpedroo.stockexchange;

import com.zpedroo.stockexchange.commands.StockExchangeCmd;
import com.zpedroo.stockexchange.listeners.BusinessListeners;
import com.zpedroo.stockexchange.managers.StockExchangeManager;
import com.zpedroo.stockexchange.tasks.UpdateTask;
import com.zpedroo.stockexchange.utils.FileUtils;
import com.zpedroo.stockexchange.utils.formatter.NumberFormatter;
import com.zpedroo.stockexchange.utils.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import static com.zpedroo.stockexchange.utils.config.Settings.ALIASES;
import static com.zpedroo.stockexchange.utils.config.Settings.COMMAND;

public class StockExchange extends JavaPlugin {

    private static StockExchange instance;
    public static StockExchange get() { return instance; }

    public void onEnable() {
        instance = this;

        new FileUtils(this);
        new UpdateTask(this);
        new StockExchangeManager();
        new Menus();
        new NumberFormatter(getConfig());

        registerListeners();
        registerCommand(COMMAND, ALIASES, new StockExchangeCmd());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BusinessListeners(), this);
    }

    private void registerCommand(String command, List<String> aliases, CommandExecutor executor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(executor);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}