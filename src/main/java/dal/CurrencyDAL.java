package dal;

import models.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDAL {
    private static final List<Currency> currencies = new ArrayList<>();

    public static Currency getCurrency(String code) {
        return currencies
                .stream()
                .filter(cur -> cur.getCode().equals(code))
                .findAny().orElse(null);
    }

    public static List<Currency> getAllCurrency() {
        return currencies;
    }

    public static void addCurrency(Currency currency) throws Exception {
        if (currencies.contains(currency)) {
            throw new Exception("Такая валюта уже существует");
        }

        currencies.add(currency);
    }
}
