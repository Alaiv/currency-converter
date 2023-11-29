import com.fasterxml.jackson.core.JsonProcessingException;
import helpers.Serializer;
import io.restassured.response.Response;
import models.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CurrencyTests extends BaseTests {
    Serializer serializer = new Serializer();
    CurrencyCrud currencyCrud = new CurrencyCrud();

    @Test
    public void addCurrencyTest()  {
        Currency currency = new Currency("UdSdx", "USEDERS", "!");
        Response response = currencyCrud.add(currency, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(201, response.statusCode());

        Currency addedCurrency = getAddedObjectFromResponse(response);

        Assertions.assertEquals(currency, addedCurrency);
    }

    @Test
    public void addExistingCurrencyTest()  {
        //todo check for existence of first currency with get method
        Currency currency1 = new Currency("USD", "USEDERS", "!");
        currencyCrud.add(currency1, specs.responseSpecification, specs.requestSpecification);
        Currency currency2 = new Currency("USD", "USEDERS1", "!!");
        Response response = currencyCrud.add(currency2, specs.responseSpecification, specs.requestSpecification);

        Assertions.assertEquals(409, response.statusCode());
    }

    private Currency getAddedObjectFromResponse(Response response)  {
        return serializer.extractFrom(response.getBody().asString(), Currency.class);
    }

}
