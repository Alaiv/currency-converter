package controllers;

import com.google.gson.Gson;
import helpers.Serializer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public abstract class Controller {
    protected Gson gson = new Gson();
    protected Serializer serializer = new Serializer();

    protected String getRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
