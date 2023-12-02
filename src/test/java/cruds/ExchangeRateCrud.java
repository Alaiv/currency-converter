package cruds;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.WithSearchIdentificator;

import static io.restassured.RestAssured.given;

public class ExchangeRateCrud implements CrudEntity{

    @Override
    public Response getAll(ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return null;
    }

    @Override
    public <T extends WithSearchIdentificator> Response getOne(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return null;
    }

    @Override
    public Response add(String jsonBody, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .body(jsonBody)
                .post("/exchangeRates")
                .then()
                .spec(responseSpec)
                .extract().response();
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
