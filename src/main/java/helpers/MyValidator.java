package helpers;

import java.util.List;
import java.util.Objects;

public class MyValidator {
    public static boolean allFieldsAreValid(List<Object> fields) {

        return fields.stream().noneMatch(Objects::isNull);
    }
}
