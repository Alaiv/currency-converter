import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.WithSearchIdentificator;

import static io.restassured.RestAssured.given;

public class CurrencyCrud implements CrudEntity{
    @Override
    public Response getAll(ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .get("/currencies")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    @Override
    public <T extends WithSearchIdentificator> Response getOne(T currency, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .get("/currency/" + currency.getSearchIdentificator())
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    @Override
    public Response add(String jsonBody, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .body(jsonBody)
                .post("/currencies")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    @Override
    public <T> Response update(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return null;
    }

    @Override
    public <T> Response delete(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return null;
    }
}
