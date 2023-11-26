package models;

import java.util.Objects;

public class ExchangeRate implements WithId {
    private static int idCounter = 0;
    private final int id;
    private final int baseCurrencyId;
    private final int targetCurrencyId;
    private final double rate;

    public ExchangeRate(int baseCurrencyId, int targetCurrencyId, double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public int getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public int getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return baseCurrencyId == that.baseCurrencyId && targetCurrencyId == that.targetCurrencyId && Double.compare(rate, that.rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrencyId, targetCurrencyId, rate);
    }
}
