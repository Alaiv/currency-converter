package helpers;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

public class MyValidator {
    public static boolean allFieldsAreValid(List<String> fields) {
        System.out.println(fields.stream().noneMatch(Objects::isNull));
        return fields.stream().noneMatch(Objects::isNull);
    }
}
