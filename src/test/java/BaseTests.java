import helpers.Serializer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTests {
    Random random = new Random();
    Serializer serializer = new Serializer();
    Specs specs;
    @BeforeAll
    public void prepare() {
        specs = new Specs();
    }

    @AfterAll
    public void tearDown() {
        RestAssured.reset();
    }

    protected String getRandomCurrencyCode() {
        return Integer.toString(random.nextInt(100, 500000)).substring(0, 3);
    }
}
