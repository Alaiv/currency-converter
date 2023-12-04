import cruds.CurrencyCrud;
import cruds.ExchangeRateCrud;
import helpers.Serializer;
import io.restassured.response.Response;
import models.Currency;
import models.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class ExchangeRateTests extends BaseTests{

    Serializer serializer = new Serializer();
    ExchangeRateCrud exchangeRateCrud = new ExchangeRateCrud();
    CurrencyCrud currencyCrud = new CurrencyCrud();
    Currency baseCurrency;
    Currency targetCurrency;
    @BeforeEach
    public void prepareCurrency() {
        String fillerCode1 = getRandomCurrencyCode();
        String fillerCode2 = getRandomCurrencyCode();
        baseCurrency = new Currency(fillerCode1, "test", "#");
        targetCurrency = new Currency(fillerCode2, "test", "$");

        String baseCurrencyJson = serializer.convertToJson(baseCurrency);
        String targetCurrencyJson = serializer.convertToJson(targetCurrency);

        currencyCrud.add(baseCurrencyJson, specs.responseSpecification, specs.requestSpecification);
        currencyCrud.add(targetCurrencyJson, specs.responseSpecification, specs.requestSpecification);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, 0.0})
    public void addExchangeRate(double rate) {
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
        String exchangeRateJson = serializer.convertToJson(exchangeRate);

        Response response = exchangeRateCrud
                .add(exchangeRateJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);

        Assertions.assertEquals(exchangeRate, addedExchangeRate);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 0})
    public void addExchangeRateWithInts(int rate) {
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
        Response response = addExchangeRate(exchangeRate);
        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);
        Assertions.assertEquals(exchangeRate, addedExchangeRate);
    }

    @Test
    public void addReversedRateWhenDefaultExists() {
        ExchangeRate exchangeRateDefault = new ExchangeRate(baseCurrency, targetCurrency, 0.3);
        addExchangeRate(exchangeRateDefault);

        ExchangeRate exchangeRateReversed = new ExchangeRate(targetCurrency, baseCurrency, 1.3);
        Response response = addExchangeRate(exchangeRateReversed);

        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);
        Assertions.assertEquals(exchangeRateReversed, addedExchangeRate);
    }

    @ParameterizedTest
    @MethodSource("prepareBaseCurrency")
    public void addExchangeRateWhenTargetOrBaseExists(ExchangeRate exchangeRate) {
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);
        Assertions.assertEquals(exchangeRate, addedExchangeRate);
    }

    private List<ExchangeRate> prepareBaseCurrency() {
        Currency base = getRandomCurrency();
        Currency target = getRandomCurrency();
        ExchangeRate exchangeRateDefault = new ExchangeRate(base, target, 0.3);
        addExchangeRate(exchangeRateDefault);


        Currency firstCurrency = getRandomCurrency();
        Currency secondCurrency = getRandomCurrency();

        ExchangeRate rateWithBaseExist = new ExchangeRate(base, firstCurrency, 0.4);
        ExchangeRate rateWithTargetExist = new ExchangeRate(secondCurrency, target, 1.1);

        return List.of(rateWithBaseExist, rateWithTargetExist);
    }

    private Currency getRandomCurrency() {
        Currency currency = new Currency(getRandomCurrencyCode(), "@323dsd", "@");
        currencyCrud.add(serializer.convertToJson(currency), specs.responseSpecification, specs.requestSpecification);
        return currency;
    }

    private Response addExchangeRate(ExchangeRate exchangeRateDefault) {
        String exchangeRateJson = serializer.convertToJson(exchangeRateDefault);
        return exchangeRateCrud
                .add(exchangeRateJson, specs.responseSpecification, specs.requestSpecification);
    }
    private ExchangeRate getAddedObjectFromResponse(Response response) {
        return serializer.extractFrom(response.getBody().asString(), ExchangeRate.class);
    }
}
