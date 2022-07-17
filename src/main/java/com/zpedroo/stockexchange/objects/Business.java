package com.zpedroo.stockexchange.objects;

import java.math.BigInteger;

public class Business {

    private BigInteger soldAmount;
    private BigInteger spentAmount;
    private BigInteger dropsAmount;

    public Business() {
        this(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);
    }

    public Business(BigInteger soldAmount, BigInteger spentAmount, BigInteger dropsAmount) {
        this.soldAmount = soldAmount;
        this.spentAmount = spentAmount;
        this.dropsAmount = dropsAmount;
    }

    public BigInteger getSoldAmount() {
        return soldAmount;
    }

    public BigInteger getDropsAmount() {
        return dropsAmount;
    }

    public BigInteger getSpentAmount() {
        return spentAmount;
    }

    public void addSoldAmount(BigInteger soldAmount) {
        this.setSoldAmount(this.soldAmount.add(soldAmount));
    }

    public void addSpentAmount(BigInteger spentAmount) {
        this.setSpentAmount(this.spentAmount.add(spentAmount));
    }

    public void addDropsAmount(BigInteger dropsAmount) {
        this.setDropsAmount(this.dropsAmount.add(dropsAmount));
    }

    public void setSoldAmount(BigInteger soldAmount) {
        this.soldAmount = soldAmount;
    }

    public void setSpentAmount(BigInteger spentAmount) {
        this.spentAmount = spentAmount;
    }

    public void setDropsAmount(BigInteger dropsAmount) {
        this.dropsAmount = dropsAmount;
    }
}