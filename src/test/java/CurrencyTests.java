import io.restassured.response.Response;
import models.Currency;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CurrencyTests extends BaseTests {
    Serializer serializer = new Serializer();
    private Response addCurrency(Currency currency) {
        return given()
                .spec(specs.requestSpecification)
                .formParam("name", currency.getFullName())
                .formParam("code", currency.getCode())
                .formParam("sign", currency.getSign())
                .post("/currencies")
                .then()
                .spec(specs.responseSpecification)
                .extract()
                .response();

    }


    @Test
    public void basicTest() {
        Currency currency = new Currency("EUR", "RUBLES", "!");
        addCurrency(currency);
    }
}
