package helpers;

import Services.ExchangeRateService;
import models.Currency;
import models.ExchangeRate;

public class BasicConverter implements Converter {
    private ExchangeRate baseCurr;
    private boolean isValid;

    public BasicConverter(String baseCurr, String targetCurr) {
        try {
            this.baseCurr = ExchangeRateService.getExchangeRate(baseCurr+targetCurr);
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }

    }
    @Override
    public double convert(int amount) {
        double rates = baseCurr.getRate();
        return amount * rates;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }
}
