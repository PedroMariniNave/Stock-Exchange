package com.zpedroo.stockexchange.commands;

import com.zpedroo.stockexchange.managers.StockExchangeManager;
import com.zpedroo.stockexchange.utils.config.Messages;
import com.zpedroo.stockexchange.utils.config.Settings;
import com.zpedroo.stockexchange.utils.menu.Menus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StockExchangeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandStr, String[] args) {
        if (args.length > 0 && sender.hasPermission(Settings.ADMIN_PERMISSION)) {
            switch (args[0].toUpperCase()) {
                case "UPDATE":
                    StockExchangeManager.getInstance().updateStockExchangeValue();
                    return true;
                default:
                    if (!isValidNumber(args[0])) {
                        sender.sendMessage(Messages.INVALID_NUMBER);
                        return true;
                    }

                    int newValue = Integer.parseInt(args[0]);
                    StockExchangeManager.getInstance().setExchangeValueAndAnnounceNewValue(newValue);
                    StockExchangeManager.getInstance().resetBusinessValues();
                    return true;
            }
        }

        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null) return true;

        Menus.getInstance().openStockExchangeMenu(player);
        return false;
    }

    private boolean isValidNumber(String str) {
        int number = 0;
        try {
            number = Integer.parseInt(str);
        } catch (Exception ex) {
            // ignore
        }

        return number > 0;
    }
}