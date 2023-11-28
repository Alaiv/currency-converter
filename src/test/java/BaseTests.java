import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTests {
    Specs specs;
    @BeforeAll
    public void prepare() {
        specs = new Specs();
    }

    @AfterAll
    public void tearDown() {
        RestAssured.reset();
    }
}
