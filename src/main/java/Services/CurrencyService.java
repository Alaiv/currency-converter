package Services;

import dal.CurrencyDAL;
import models.Currency;

import java.security.InvalidParameterException;
import java.util.List;

public class CurrencyService {

    public static Currency getCurrency(String code)  {
        return CurrencyDAL.getCurrency(code);
    }

    public static List<Currency> getAllCurrency() {
        return CurrencyDAL.getAllCurrency();
    }

    public static Currency addCurrency(String name, String code, String sign) throws Exception {
        Currency existingCurrency = getCurrency(code);

        if (existingCurrency != null) {
            throw new Exception("Такая валюта уже существует");
        }

        Currency currency = new Currency(code, name, sign);
        CurrencyDAL.addCurrency(currency);
        return currency;
    }

}
