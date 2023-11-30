package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Currency implements WithSearchIdentificator, WithRequiredFields{
    @JsonIgnore
    static int idCounter = 0;
    @JsonIgnore
    private final transient int id;
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

    public String getSearchIdentificator() {
        return getCode();
    }

    public List<Object> getAllRequiredFields() {
        List<Object> t = new ArrayList<>();
        t.add(code);
        t.add(fullName);
        t.add(sign);
        return t;
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
