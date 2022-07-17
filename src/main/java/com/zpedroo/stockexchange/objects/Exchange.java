package com.zpedroo.stockexchange.objects;

import com.zpedroo.stockexchange.enums.BusinessType;

import java.util.Map;

public class Exchange {

    private final int oldValue;
    private final int newValue;
    private final Map<BusinessType, Business> businessValues;
    private final long timestamp;

    public Exchange(int oldValue, int newValue, Map<BusinessType, Business> businessValues) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.businessValues = businessValues;
        this.timestamp = System.currentTimeMillis();
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public Map<BusinessType, Business> getBusinessValues() {
        return businessValues;
    }

    public long getTimestamp() {
        return timestamp;
    }
}