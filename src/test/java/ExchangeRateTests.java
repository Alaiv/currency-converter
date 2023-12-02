import cruds.CurrencyCrud;
import cruds.ExchangeRateCrud;
import helpers.Serializer;
import io.restassured.response.Response;
import models.Currency;
import models.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void addExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, 0.9);
        String exchangeRateJson = serializer.convertToJson(exchangeRate);

        Response response = exchangeRateCrud
                .add(exchangeRateJson, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(201, response.statusCode());

        ExchangeRate addedExchangeRate = getAddedObjectFromResponse(response);

        Assertions.assertEquals(exchangeRate, addedExchangeRate);
    }

    private ExchangeRate getAddedObjectFromResponse(Response response) {
        return serializer.extractFrom(response.getBody().asString(), ExchangeRate.class);
    }
}
