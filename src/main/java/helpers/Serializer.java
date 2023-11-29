package helpers;

import com.google.gson.Gson;

public class Serializer {
    Gson gson = new Gson();
    public <T> T  extractFrom(String body, Class<T> type){
        return gson.fromJson(body, type);
    }

    public <T> String convertToJson(T object) {
        return gson.toJson(object);
    }
}
