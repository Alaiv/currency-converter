package helpers;

import Services.ExchangeRateService;
import models.ExchangeRate;

public class NoRatesConverter implements Converter {
    private ExchangeRate firstUSDRatePair;
    private ExchangeRate secondUSDRatePair;
    private boolean isValid;

    public NoRatesConverter(String baseCurr, String targetCurr) {
        try {
            this.firstUSDRatePair = ExchangeRateService.getExchangeRate("USD"+baseCurr);
            this.secondUSDRatePair = ExchangeRateService.getExchangeRate("USD"+targetCurr);
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }

    }
    @Override
    public double convert(int amount) {
        double firstPairRate = firstUSDRatePair.getRate();
        double secondPairRate = secondUSDRatePair.getRate();
        double calculatedRate = firstPairRate / secondPairRate;
        return amount * calculatedRate;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }
}
