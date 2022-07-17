package com.zpedroo.stockexchange.tasks;

import com.zpedroo.stockexchange.managers.StockExchangeManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.zpedroo.stockexchange.utils.config.Settings.UPDATE_INTERVAL;

public class UpdateTask extends BukkitRunnable {

    public UpdateTask(Plugin plugin) {
        this.runTaskTimerAsynchronously(plugin, UPDATE_INTERVAL * 20, UPDATE_INTERVAL * 20);
    }

    @Override
    public void run() {
        StockExchangeManager.getInstance().updateStockExchangeValue();
    }
}