import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public interface CrudEntity {

    public  Response get(ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T> Response add(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T> Response update(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);
    public <T> Response delete(T object, ResponseSpecification responseSpec, RequestSpecification requestSpec);
}
