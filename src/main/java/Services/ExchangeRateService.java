package Services;

import dal.ExchangeRateDAL;
import models.Currency;
import models.ExchangeRate;

import java.util.List;

public class ExchangeRateService {
    public static List<ExchangeRate> getAllExchangeRates() {
        return ExchangeRateDAL.getAllExchangeRates();
    }

    public static ExchangeRate getExchangeRate(String baseAndTargetCode) throws Exception {
        String baseCurrencyCode = CurrencyCodes.getBaseCurrencyCode(baseAndTargetCode);
        String targetCurrencyCode = CurrencyCodes.getTargetCurrencyCode(baseAndTargetCode);

        ExchangeRate rate = ExchangeRateDAL.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
        if (rate == null) throw new Exception("Обменный курс валют не найден");

        return rate;
    }

    public static ExchangeRate addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, double rate) throws Exception {
        Currency baseCurrency = CurrencyService.getCurrency(baseCurrencyCode);
        Currency targetCurrency = CurrencyService.getCurrency(targetCurrencyCode);

        if (baseCurrency == null || targetCurrency == null) throw new Exception("Указанные валюты отсутствуют");

        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
        ExchangeRateDAL.addExchangeRate(exchangeRate);
        return exchangeRate;
    }

    public static ExchangeRate updateExchangeRate(double rate, String baseAndTargetCode) throws Exception {
        ExchangeRate exchangeRate = getExchangeRate(baseAndTargetCode);
        String baseCurrencyCode = CurrencyCodes.getBaseCurrencyCode(baseAndTargetCode);
        String targetCurrencyCode = CurrencyCodes.getTargetCurrencyCode(baseAndTargetCode);

         ExchangeRateDAL.updateExchangeRate(rate, baseCurrencyCode, targetCurrencyCode);

        return exchangeRate;

    }

    public static class CurrencyCodes {
        public static String getBaseCurrencyCode(String baseAndTargetCode) {
            return baseAndTargetCode.substring(0, 3);
        }

        public static String getTargetCurrencyCode(String baseAndTargetCode) {
            return baseAndTargetCode.substring(3);
        }
    }


}
