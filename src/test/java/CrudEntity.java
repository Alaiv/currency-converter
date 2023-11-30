import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.WithSearchIdentificator;

public interface CrudEntity {

    public  Response getAll(ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T extends WithSearchIdentificator> Response getOne(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);

    public Response add(String jsonBody, ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T> Response update(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T> Response delete(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);
}
