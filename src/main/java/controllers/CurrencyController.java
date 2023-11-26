package controllers;

import Services.CurrencyService;
import com.google.gson.Gson;
import models.Currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CurrencyController {
    Gson gson = new Gson();

    public void getCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("code");
        Currency currency = CurrencyService.getCurrency(id);
        PrintWriter out = resp.getWriter();

        if (currency == null) {
            resp.setStatus(404);
            out.write("Валюта не найдена!");
        } else {
            resp.setStatus(200);
            out.write(gson.toJson(currency));
        }
    }

    public void getAllCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<Currency> currencies = CurrencyService.getAllCurrency();


        if (!currencies.isEmpty()) {
            String currenciesJson = gson.toJson(currencies);
            resp.setStatus(200);
            out.write(currenciesJson);
        } else {
            resp.setStatus(404);
            out.write("Список валют пуст!");
        }
    }

    public void addCurrency(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        PrintWriter out = resp.getWriter();

        if (!fieldsAreValid(name, code, sign)) {
            resp.setStatus(400);
            out.write("Переданы не все параметры!");
            return;
        }

        boolean currencyIsAdded = CurrencyService.addCurrency(name, code, sign);

        if (!currencyIsAdded) {
            resp.setStatus(409);
            out.write("Такая валюта уже существует!");
            return;
        }

        resp.setStatus(201);
        out.write("Валюта успешно добавлена!");

    }

    private boolean fieldsAreValid(String name, String code, String sign) {
        return name != null && code != null && sign != null;
    }
}