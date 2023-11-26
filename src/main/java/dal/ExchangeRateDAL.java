package dal;

import models.Currency;
import models.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAL {

    private static final List<ExchangeRate> exchangeRates = new ArrayList<>();

    public ExchangeRate getExchangeRateById(int id) {
        return exchangeRates
                .stream()
                .filter(cur -> cur.getId() == id)
                .findAny().orElse(null);
    }

    public List<ExchangeRate> getAllCurrency() {
        return exchangeRates;
    }

    public void addCurrency(ExchangeRate exchangeRate) {
        if (exchangeRates.contains(exchangeRate)) {
            throw new IllegalArgumentException("Такая сущность уже существует");
        }

        exchangeRates.add(exchangeRate);
    }
}
