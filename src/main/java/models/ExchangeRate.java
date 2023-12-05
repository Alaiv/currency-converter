package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExchangeRate implements WithSearchIdentificator, WithRequiredFields {
    private static int idCounter = 0;
    private final int id;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private Double rate;

    @Override
    public String getSearchIdentificator() {
        if (baseCurrency == null || targetCurrency == null)
            return null;
        return baseCurrency.getSearchIdentificator() + targetCurrency.getSearchIdentificator();
    }

    public ExchangeRate(Currency baseCurrencyId, Currency targetCurrencyId, Double rate) {
        this.baseCurrency = baseCurrencyId;
        this.targetCurrency = targetCurrencyId;
        this.rate = rate;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }


    public Currency getTargetCurrency() {
        return targetCurrency;
    }
    public Currency getBaseCurrency() {
        return baseCurrency;
    }
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Double.compare(rate, that.rate) == 0 && Objects.equals(baseCurrency, that.baseCurrency)
                && Objects.equals(targetCurrency, that.targetCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, targetCurrency, rate);
    }

    @Override
    public List<Object> getAllRequiredFields() {
        List<Object> t = new ArrayList<>();
        t.add(baseCurrency);
        t.add(targetCurrency);
        t.add(rate);
        return t;
    }
}
