package Services;

import dal.CurrencyDAL;
import models.Currency;

import java.util.List;

public class CurrencyService {

    public static Currency getCurrency(String code) {
        return CurrencyDAL.getCurrency(code);
    }

    public static List<Currency> getAllCurrency() {
        return CurrencyDAL.getAllCurrency();
    }

    public static boolean addCurrency(String name, String code, String sign){
        Currency currency = new Currency(code, name, sign);
        try {
            CurrencyDAL.addCurrency(currency);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
