package com.zpedroo.stockexchange.managers;

import com.zpedroo.stockexchange.enums.BusinessType;
import com.zpedroo.stockexchange.managers.cache.DataCache;
import com.zpedroo.stockexchange.objects.Business;
import com.zpedroo.stockexchange.objects.Exchange;
import com.zpedroo.stockexchange.utils.config.Messages;
import com.zpedroo.stockexchange.utils.config.Settings;
import com.zpedroo.stockexchange.utils.formatter.NumberFormatter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class StockExchangeManager {

    private static StockExchangeManager instance;
    public static StockExchangeManager getInstance() { return instance; }

    private final DataCache dataCache;

    public StockExchangeManager() {
        instance = this;
        this.dataCache = new DataCache();
    }

    public void updateStockExchangeValue() {
        /*
        int points = 0;
        Exchange oldExchangeValues = StockExchangeAPI.getOldExchangeValues();
        if (oldExchangeValues != null) {
            for (BusinessType businessType : BusinessType.values()) {
                Business actualBusiness = StockExchangeAPI.getBusinessValues(businessType);
                Business oldBusiness = oldExchangeValues.getBusinessValues().get(businessType);

                if (actualBusiness.getSoldAmount().compareTo(oldBusiness.getSoldAmount()) > 0) ++points;
                if (actualBusiness.getSpentAmount().compareTo(oldBusiness.getSpentAmount()) > 0) points += 2;
                if (actualBusiness.getDropsAmount().compareTo(oldBusiness.getSoldAmount()) > 0) ++points;
            }
        } else {
            points = 999;
        }

        int actualValue = StockExchangeAPI.getActualValue();

        int min = points >= 2 ? actualValue + 1 : 1;
        int max = points >= 2 ? 100 : actualValue - 1;
        if (max <= min) {
            min = 1;
            max = 100;
        }
         */

        int min = 1;
        int max = 100;
        int newValue = ThreadLocalRandom.current().nextInt(min, max);
        setExchangeValueAndAnnounceNewValue(newValue);
        resetBusinessValues();
    }

    public Business getBusinessValues(BusinessType businessType) {
        return dataCache.getBusinessValues().get(businessType);
    }

    public void setExchangeValueAndAnnounceNewValue(int newValue) {
        final int oldValue = dataCache.getActualValue();
        saveOldExchangeValues(dataCache.getOldValue(), dataCache.getActualValue());
        dataCache.setActualValue(newValue);
        dataCache.setNextUpdate(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(Settings.UPDATE_INTERVAL));

        Business machinesBusiness = StockExchangeManager.getInstance().getBusinessValues(BusinessType.MACHINES);
        Business spawnersBusiness = StockExchangeManager.getInstance().getBusinessValues(BusinessType.SPAWNERS);

        for (String msg : Messages.NEW_VALUE) {
            Bukkit.broadcastMessage(StringUtils.replaceEach(msg, new String[]{
                    "{machines_sold}",
                    "{machines_spent}",
                    "{machines_drops}",
                    "{spawners_sold}",
                    "{spawners_spent}",
                    "{spawners_drops}",
                    "{old_value}",
                    "{new_value}",
                    "{statistics}"
            }, new String[]{
                    NumberFormatter.getInstance().format(machinesBusiness.getSoldAmount()),
                    NumberFormatter.getInstance().format(machinesBusiness.getSpentAmount()),
                    NumberFormatter.getInstance().format(machinesBusiness.getDropsAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getSoldAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getSpentAmount()),
                    NumberFormatter.getInstance().format(spawnersBusiness.getDropsAmount()),
                    String.valueOf(oldValue),
                    String.valueOf(newValue),
                    StatisticManager.getStatistics(newValue, oldValue)
            }));
        }
    }

    public void resetBusinessValues() {
        for (BusinessType businessType : BusinessType.values()) {
            dataCache.getBusinessValues().put(businessType, new Business());
        }
    }

    public void saveOldExchangeValues(int oldValue, int newValue) {
        if (dataCache.getOldValue() <= 0) return; // undefined stock exchange

        Exchange exchange = new Exchange(oldValue, newValue, new HashMap<>(dataCache.getBusinessValues()));
        if (dataCache.getOldExchanges().size() >= 9) {
            dataCache.getOldExchanges().remove(0);
        }

        dataCache.getOldExchanges().add(exchange);
    }

    public DataCache getCache() {
        return dataCache;
    }
}