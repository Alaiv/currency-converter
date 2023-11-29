import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.checkerframework.checker.units.qual.C;

import static io.restassured.RestAssured.given;

public class CurrencyCrud implements CrudEntity{
    @Override
    public Response get(ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return null;
    }

    @Override
    public <T> Response add(T currency, ResponseSpecification responseSpec, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .body(currency)
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
