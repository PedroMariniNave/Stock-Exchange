package com.zpedroo.stockexchange.managers.cache;

import com.zpedroo.stockexchange.enums.BusinessType;
import com.zpedroo.stockexchange.objects.Business;
import com.zpedroo.stockexchange.objects.Exchange;
import com.zpedroo.stockexchange.utils.config.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DataCache {

    private final Map<BusinessType, Business> businessValues;
    private final List<Exchange> oldExchanges;
    private int oldValue = 0;
    private int actualValue = ThreadLocalRandom.current().nextInt(1, 100);
    private long nextUpdate = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(Settings.UPDATE_INTERVAL);

    public DataCache() {
        this.businessValues = new HashMap<>(BusinessType.values().length);
        this.oldExchanges = new ArrayList<>(9);

        for (BusinessType businessType : BusinessType.values()) {
            this.businessValues.put(businessType, new Business());
        }
    }

    public Map<BusinessType, Business> getBusinessValues() {
        return businessValues;
    }

    public List<Exchange> getOldExchanges() {
        return oldExchanges;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getActualValue() {
        return actualValue;
    }

    public long getNextUpdate() {
        return nextUpdate;
    }

    public void setActualValue(int value) {
        this.oldValue = this.actualValue;
        this.actualValue = value;
    }

    public void setNextUpdate(long nextUpdate) {
        this.nextUpdate = nextUpdate;
    }
}