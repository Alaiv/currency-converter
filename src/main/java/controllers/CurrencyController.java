package controllers;

import Services.CurrencyService;
import com.google.gson.Gson;
import helpers.MyValidator;
import helpers.Serializer;
import models.Currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyController {
    Gson gson = new Gson();
    Serializer serializer = new Serializer();

    public void getCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        PrintWriter out = resp.getWriter();
        String code = pathInfo.substring(1);
        Currency currency = CurrencyService.getCurrency(code);
        if (currency != null) {
            resp.setStatus(200);
            out.write(serializer.convertToJson(currency));
        } else {
            resp.setStatus(404);
            out.write("Валюта не найдена!");
        }

    }

    public void getAllCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<Currency> currencies = CurrencyService.getAllCurrency();

        String currenciesJson = serializer.convertToJson(currencies);
        resp.setStatus(200);
        out.write(currenciesJson);

    }

    public void addCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String body = getRequestBody(req);
        Currency currency = serializer.extractFrom(body, Currency.class);

        if (!MyValidator.allFieldsAreValid(currency.getAllRequiredFields())) {
            resp.setStatus(400);
            out.write("Переданы не все параметры!");
            return;
        }

        try {
            Currency addedCurrency = CurrencyService
                    .addCurrency(currency.getFullName(), currency.getCode(), currency.getSign());
            resp.setStatus(201);
            out.write(gson.toJson(addedCurrency));
        } catch (Exception e) {
            resp.setStatus(409);
            out.write(e.getMessage());
        }

    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
