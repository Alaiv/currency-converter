import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {
    ObjectMapper objectMapper = new ObjectMapper();
    public <T> T  extractFrom(String jsonValue, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(jsonValue, type);
    }

    public <T> JsonNode convertToJson(T object) {
        return objectMapper.convertValue(object, JsonNode.class);
    }
}
