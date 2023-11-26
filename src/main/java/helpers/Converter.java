package helpers;

import models.Currency;
import models.ExchangeRate;

public interface Converter {
    double convert(int amount);
    boolean isValid();
}
