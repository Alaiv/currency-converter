import cruds.CurrencyCrud;
import helpers.Serializer;
import io.restassured.response.Response;
import models.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Random;

public class CurrencyTests extends BaseTests {

    CurrencyCrud currencyCrud = new CurrencyCrud();

    @ParameterizedTest
    @ValueSource(strings = {"UASDASDKASJDALSKDASDASDASDASDAS", "$", ""})
    public void addCurrencyTest(String val) {
        String fillerCode = getRandomCurrencyCode();
        Currency currency = new Currency(fillerCode, val, val);
        String currencyJson = serializer.convertToJson(currency);
        Response response = currencyCrud.add(currencyJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(201, response.statusCode());

        Currency addedCurrency = getAddedObjectFromResponse(response);

        Assertions.assertEquals(currency, addedCurrency);
    }
    @ParameterizedTest
    @MethodSource("currencyProvider")
    public void addCurrencyWithEmptyCode(Currency currency) {
        String currencyJson = serializer.convertToJson(currency);
        Response response = currencyCrud.add(currencyJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Переданы не все параметры!", response.getBody().asString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"UA", "UADO"})
    public void addCurrencyWithInvalidLengthCode(String code) {
        Currency currency = new Currency(code, "test", "$");
        String currencyJson = serializer.convertToJson(currency);
        Response response = currencyCrud.add(currencyJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Указан не валидный код валюты!", response.getBody().asString());
    }

    @Test
    public void addCurrencyWithEmptyName() {
        Currency currency = new Currency(null, null, "$");
        String currencyJson = serializer.convertToJson(currency);
        Response response = currencyCrud.add(currencyJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Переданы не все параметры!", response.getBody().asString());
    }


    @Test
    public void addExistingCurrencyTest() {
        Currency currency1 = new Currency("USD", "USEDERS", "!");
        int statusCode = currencyCrud.getOne(currency1, specs.responseSpecification, specs.requestSpecification).statusCode();
        if (statusCode == 404) {
            String currencyJson = serializer.convertToJson(currency1);
            currencyCrud.add(currencyJson, specs.responseSpecification, specs.requestSpecification);
        }

        Currency currency2 = new Currency("USD", "USEDERS1", "!!");
        String currency2Json = serializer.convertToJson(currency2);
        Response response = currencyCrud.add(currency2Json, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(409, response.statusCode());
        Assertions.assertEquals("Такая валюта уже существует", response.getBody().asString());
    }

    private List<Currency> currencyProvider() {
        String fillerCode = getRandomCurrencyCode();
        String fillerCode2 = getRandomCurrencyCode();

        Currency withEmptyCode = new Currency(null, "test", "$");
        Currency withEmptyName = new Currency(fillerCode, null, "$");
        Currency withEmptySign = new Currency(fillerCode2, "test", null);
        Currency withAllEmptyFields = new Currency(null, null, null);

        return List.of(withEmptyCode, withEmptyName, withEmptySign, withAllEmptyFields);
    }



    private Currency getAddedObjectFromResponse(Response response) {
        return serializer.extractFrom(response.getBody().asString(), Currency.class);
    }

}
