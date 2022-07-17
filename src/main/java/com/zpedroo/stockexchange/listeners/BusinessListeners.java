package com.zpedroo.stockexchange.listeners;

import com.zpedroo.stockexchange.api.StockExchangeAPI;
import com.zpedroo.stockexchange.enums.BusinessType;
import com.zpedroo.voltzmachines.api.MachineBuyEvent;
import com.zpedroo.voltzmachines.api.MachineDropSellEvent;
import com.zpedroo.voltzspawners.api.SpawnerBuyEvent;
import com.zpedroo.voltzspawners.api.SpawnerDropSellEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigInteger;

public class BusinessListeners implements Listener {

    @EventHandler
    public void onMachineDropSell(MachineDropSellEvent event) {
        BigInteger profitAmount = event.getProfitAmount();

        StockExchangeAPI.getBusinessValues(BusinessType.MACHINES).addDropsAmount(profitAmount);
    }

    @EventHandler
    public void onMachineBuy(MachineBuyEvent event) {
        BigInteger price = event.getPrice();
        BigInteger amount = event.getAmount();

        StockExchangeAPI.getBusinessValues(BusinessType.MACHINES).addSoldAmount(amount);
        StockExchangeAPI.getBusinessValues(BusinessType.MACHINES).addSpentAmount(price);
    }

    @EventHandler
    public void onSpawnerDropSell(SpawnerDropSellEvent event) {
        BigInteger profitAmount = event.getProfitAmount();

        StockExchangeAPI.getBusinessValues(BusinessType.SPAWNERS).addDropsAmount(profitAmount);
    }

    @EventHandler
    public void onSpawnerBuy(SpawnerBuyEvent event) {
        BigInteger price = event.getPrice();
        BigInteger amount = event.getAmount();

        StockExchangeAPI.getBusinessValues(BusinessType.SPAWNERS).addSoldAmount(amount);
        StockExchangeAPI.getBusinessValues(BusinessType.SPAWNERS).addSpentAmount(price);
    }
}