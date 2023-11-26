package models;

import java.util.Objects;

public class Currency implements WithId {
    static int idCounter = 0;
    private final int id;
    private final String code;
    private final String fullName;
    private final String sign;

    public Currency(String code, String fullName, String sign) {
        id = idCounter++;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code)
                && Objects.equals(fullName, currency.fullName)
                && Objects.equals(sign, currency.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, fullName, sign);
    }

}
