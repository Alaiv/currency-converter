import cruds.CurrencyCrud;
import cruds.ExchangeRateCrud;
import helpers.Serializer;
import io.restassured.response.Response;
import models.Currency;
import models.ExchangeRate;
import org.checkerframework.checker.units.qual.C;
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
        baseCurrency = getCurrencyWithCode(fillerCode1);
        targetCurrency = getCurrencyWithCode(fillerCode2);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, 0.0})
    public void addExchangeRate(double rate) {
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
    @MethodSource("prepareBaseAndTargetCurrency")
    public void addExchangeRateWhenTargetOrBaseExists(ExchangeRate exchangeRate) {
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);
        Assertions.assertEquals(exchangeRate, addedExchangeRate);
    }

    private List<ExchangeRate> prepareBaseAndTargetCurrency() {
        Currency base = getCurrencyWithCode(getRandomCurrencyCode());
        Currency target = getCurrencyWithCode(getRandomCurrencyCode());
        ExchangeRate exchangeRateDefault = new ExchangeRate(base, target, 0.3);
        addExchangeRate(exchangeRateDefault);


        Currency firstCurrency = getCurrencyWithCode(getRandomCurrencyCode());
        Currency secondCurrency = getCurrencyWithCode(getRandomCurrencyCode());

        ExchangeRate rateWithBaseExist = new ExchangeRate(base, firstCurrency, 0.4);
        ExchangeRate rateWithTargetExist = new ExchangeRate(secondCurrency, target, 1.1);


        return List.of(rateWithBaseExist, rateWithTargetExist);
    }

    @Test
    public void addExistingExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, 0.3);
        addExchangeRate(exchangeRate);

        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(409, response.statusCode());
        Assertions.assertEquals("Указанный курс обмена уже существует!", response.getBody().asString());
    }

    @ParameterizedTest
    @MethodSource("getRateWithNonExistingCurrency")
    public void addExchangeRateWhenSomeCurrencyDoNotExist(ExchangeRate exchangeRate) {
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(409, response.statusCode());
        Assertions.assertEquals("Указанные валюты отсутствуют", response.getBody().asString());
    }

    private List<ExchangeRate> getRateWithNonExistingCurrency() {
        Currency base = getCurrencyWithCode(getRandomCurrencyCode());
        Currency target = getCurrencyWithCode(getRandomCurrencyCode());
        Currency currency1 = new Currency(getRandomCurrencyCode(), "@323dsd", "@");

        ExchangeRate rateWithTargetNonExist = new ExchangeRate(base, currency1, 0.4);
        ExchangeRate rateWithBaseNonExist = new ExchangeRate(currency1, target, 1.1);
        return List.of(rateWithTargetNonExist, rateWithBaseNonExist);
    }

    @ParameterizedTest
    @MethodSource("getInvalidCodes")
    public void addExchangeRateWithInvalidCodes(String invalidCode) {
        Currency base = getCurrencyWithCode(getRandomCurrencyCode());
        Currency target = getCurrencyWithCode(invalidCode);
        ExchangeRate exchangeRate = new ExchangeRate(base, target, 24.3);
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Указаны не валидные коды валют!", response.getBody().asString());
    }

    @ParameterizedTest
    @MethodSource("exchangeRateProvider")
    public void addExchangeRateWithoutRequiredFields(ExchangeRate exchangeRate) {
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Переданы не все параметры!", response.getBody().asString());
    }

    private List<ExchangeRate> exchangeRateProvider() {
        Currency currency = getCurrencyWithCode(getRandomCurrencyCode());

        ExchangeRate withEmptyBase = new ExchangeRate(null, currency, 2.2);
        ExchangeRate withEmptyTarget = new ExchangeRate(currency, null, 2.2);
        ExchangeRate withAllEmptyFields = new ExchangeRate(null, null, null);

        return List.of(withEmptyBase, withEmptyTarget,  withAllEmptyFields);
    }


    @Test
    public void addExchangeRateWithNegativeRates() {
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, -1.0);
        Response response = addExchangeRate(exchangeRate);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Ставка не может быть отрицательной!", response.getBody().asString());
    }


    private List<String> getInvalidCodes() {
        String shortCode = getRandomCurrencyCode().substring(0, 2);
        String longCode = getRandomCurrencyCode() + "A";

        return List.of(shortCode, longCode);
    }

    private Currency getCurrencyWithCode(String code) {
        Currency currency = new Currency(code, "@323dsd", "@");
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
