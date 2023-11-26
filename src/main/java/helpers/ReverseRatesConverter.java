package helpers;

import Services.ExchangeRateService;
import models.ExchangeRate;

public class ReverseRatesConverter implements Converter {
    private ExchangeRate baseCurr;
    private boolean isValid;

    public ReverseRatesConverter(String baseCurr, String targetCurr) {
        try {
            this.baseCurr = ExchangeRateService.getExchangeRate(targetCurr+baseCurr);
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
    }
    @Override
    public double convert(int amount) {
        double baseRate = 1.0;
        double rates = baseCurr.getRate();
        double ratesDiff = baseRate - rates;
        double reversedRates = baseRate + ratesDiff;

        return amount * reversedRates;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }
}
