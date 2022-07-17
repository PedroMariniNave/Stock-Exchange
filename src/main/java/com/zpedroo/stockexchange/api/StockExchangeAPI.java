package com.zpedroo.stockexchange.api;

import com.avaje.ebean.validation.NotNull;
import com.zpedroo.stockexchange.enums.BusinessType;
import com.zpedroo.stockexchange.managers.StockExchangeManager;
import com.zpedroo.stockexchange.objects.Business;
import com.zpedroo.stockexchange.objects.Exchange;
import org.apache.commons.lang.Validate;

import java.math.BigInteger;

public class StockExchangeAPI {

    public static int getActualValue() {
        return StockExchangeManager.getInstance().getCache().getActualValue();
    }

    public static int getOldValue() {
        return StockExchangeManager.getInstance().getCache().getOldValue();
    }

    public static void setActualValue(int value) {
        Validate.isTrue(value > 0, "Value cannot be lower than 1!");

        StockExchangeManager.getInstance().setExchangeValueAndAnnounceNewValue(value);
    }

    @NotNull
    public static Business getBusinessValues(BusinessType businessType) {
        return StockExchangeManager.getInstance().getBusinessValues(businessType);
    }

    public static Exchange getOldExchangeValues() {
        if (StockExchangeManager.getInstance().getCache().getOldExchanges().size() <= 0) return null;

        return StockExchangeManager.getInstance().getCache().getOldExchanges().get(0);
    }

    public static BigInteger apply(BigInteger value) {
        if (value.signum() <= 0) return value;

        return value.add(value.multiply(BigInteger.valueOf(getActualValue())).divide(BigInteger.valueOf(100L)));
    }
}