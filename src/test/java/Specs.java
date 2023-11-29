import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {
    private LogConfig logConfig
            = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    private RestAssuredConfig config = RestAssuredConfig.config().logConfig(logConfig);

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(MyConfig.getUrl())
            .setPort(MyConfig.getPort())
            .setContentType(ContentType.JSON)
            .setConfig(config)
            .build();

    ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .build();
}
