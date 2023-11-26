package dal;

import models.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAL {

    private static final List<ExchangeRate> exchangeRates = new ArrayList<>();

    public static ExchangeRate getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRates
                .stream()
                .filter(cur -> cur.getBaseCurrency().getCode().equals(baseCurrencyCode)
                        && cur.getTargetCurrency().getCode().equals(targetCurrencyCode))
                .findAny().orElse(null);
    }

    public static List<ExchangeRate> getAllExchangeRates() {
        return exchangeRates;
    }

    public static void addExchangeRate(ExchangeRate exchangeRate) {
        if (exchangeRates.contains(exchangeRate)) {
            throw new IllegalArgumentException("Такая сущность уже существует");
        }

        exchangeRates.add(exchangeRate);
    }

    public static void updateExchangeRate(double rate, String baseCurrencyCode, String targetCurrencyCode) {
         ExchangeRate exchangeRate = getExchangeRate(baseCurrencyCode, targetCurrencyCode);
         exchangeRate.setRate(rate);
    }
}
