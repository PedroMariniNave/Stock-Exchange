package com.zpedroo.stockexchange.utils.menu;

import com.zpedroo.stockexchange.StockExchange;
import com.zpedroo.stockexchange.api.StockExchangeAPI;
import com.zpedroo.stockexchange.enums.BusinessType;
import com.zpedroo.stockexchange.managers.StatisticManager;
import com.zpedroo.stockexchange.managers.StockExchangeManager;
import com.zpedroo.stockexchange.objects.Business;
import com.zpedroo.stockexchange.objects.Exchange;
import com.zpedroo.stockexchange.utils.FileUtils;
import com.zpedroo.stockexchange.utils.builder.InventoryBuilder;
import com.zpedroo.stockexchange.utils.builder.InventoryUtils;
import com.zpedroo.stockexchange.utils.builder.ItemBuilder;
import com.zpedroo.stockexchange.utils.formatter.NumberFormatter;
import com.zpedroo.stockexchange.utils.formatter.TimeFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Menus extends InventoryUtils {

    private static Menus instance;
    public static Menus getInstance() { return instance; }

    public Menus() {
        instance = this;
    }

    public void openStockExchangeMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.STOCK_EXCHANGE;

        String title = ChatColor.translateAlternateColorCodes('&', FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);

        Business machinesBusiness = StockExchangeManager.getInstance().getBusinessValues(BusinessType.MACHINES);
        Business spawnersBusiness = StockExchangeManager.getInstance().getBusinessValues(BusinessType.SPAWNERS);

        ItemStack actualExchangeItem = ItemBuilder.build(FileUtils.get().getFile(file).getFileConfiguration(), "Actual-Exchange-Item", new String[]{
                "{machines_sold}",
                "{machines_spent}",
                "{machines_drops}",
                "{spawners_sold}",
                "{spawners_spent}",
                "{spawners_drops}",
                "{actual_value}",
                "{old_value}",
                "{statistics}",
                "{expiration}"
        }, new String[]{
                NumberFormatter.getInstance().format(machinesBusiness.getSoldAmount()),
                NumberFormatter.getInstance().format(machinesBusiness.getSpentAmount()),
                NumberFormatter.getInstance().format(machinesBusiness.getDropsAmount()),
                NumberFormatter.getInstance().format(spawnersBusiness.getSoldAmount()),
                NumberFormatter.getInstance().format(spawnersBusiness.getSpentAmount()),
                NumberFormatter.getInstance().format(spawnersBusiness.getDropsAmount()),
                String.valueOf(StockExchangeAPI.getActualValue()),
                String.valueOf(StockExchangeAPI.getOldValue()),
                StatisticManager.getStatistics(StockExchangeAPI.getActualValue(), StockExchangeAPI.getOldValue()),
                TimeFormatter.millisToFormattedTime(StockExchangeManager.getInstance().getCache().getNextUpdate() - System.currentTimeMillis())
        }).build();
        int actualExchangeSlot = FileUtils.get().getInt(file, "Actual-Exchange-Item.slot");

        inventory.addItem(actualExchangeItem, actualExchangeSlot);

        String[] slots = FileUtils.get().getString(file, "Inventory.slots").replace(" ", "").split(",");
        int i = -1;

        List<Exchange> exchanges = new ArrayList<>(StockExchangeManager.getInstance().getCache().getOldExchanges());
        Collections.reverse(exchanges);

        for (Exchange exchange : exchanges) {
            if (++i >= slots.length) i = 0;

            machinesBusiness = exchange.getBusinessValues().get(BusinessType.MACHINES);
            spawnersBusiness = exchange.getBusinessValues().get(BusinessType.SPAWNERS);

            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(file).getFileConfiguration(), "Old-Exchange-Item", new String[]{
                    "{machines_sold}",
                    "{machines_spent}",
                    "{machines_drops}",
                    "{spawners_sold}",
                    "{spawners_spent}",
                    "{spawners_drops}",
                    "{new_value}",
                    "{old_value}",
                    "{statistics}",
                    "{timestamp}"
            }, new String[]{
                    NumberFormatter.getInstance().format(machinesBusiness.getSoldAmount()),
                    NumberFormatter.getInstance().format(machinesBusiness.getSpentAmount()),
                    NumberFormatter.getInstance().format(machinesBusiness.getDropsAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getSoldAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getSpentAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getDropsAmount()),
                    String.valueOf(exchange.getNewValue()),
                    String.valueOf(exchange.getOldValue()),
                    StatisticManager.getStatistics(exchange.getNewValue(), exchange.getOldValue()),
                    TimeFormatter.millisToFormattedDate(exchange.getTimestamp())
            }).build();
            int slot = Integer.parseInt(slots[i]);

            inventory.addItem(item, slot);
        }

        inventory.open(player);
    }
}