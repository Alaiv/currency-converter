package controllers;

import Services.ExchangeRateService;
import com.google.gson.Gson;
import helpers.MyValidator;
import helpers.Serializer;
import models.ExchangeRate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeRateController extends Controller{
    public void getAllExchangeRates(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        List<ExchangeRate> allRate = ExchangeRateService.getAllExchangeRates();

        String allRateJson = gson.toJson(allRate);
        resp.setStatus(200);
        out.write(allRateJson);
    }

    public void getExchangeRate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String rateCodes = req.getPathInfo();

        if (rateCodes.length() != 7) {
            resp.setStatus(400);
            out.write("Указаны некорректные коды валют");
            return;
        }

        try {
           ExchangeRate exchangeRate = ExchangeRateService.getExchangeRate(rateCodes.substring(1));
            String allRateJson = gson.toJson(exchangeRate);
            resp.setStatus(200);
            out.write(allRateJson);
        } catch (Exception e) {
            resp.setStatus(400);
            out.write(e.getMessage());
        }

    }

    public void addExchangeRate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = getRequestBody(req);
        ExchangeRate exchangeRateDto = serializer.extractFrom(body, ExchangeRate.class);
        PrintWriter out = resp.getWriter();

        boolean fieldsAreValid = MyValidator.allFieldsAreValid(exchangeRateDto.getAllRequiredFields());
        boolean validExchangeRateLength = exchangeRateDto.getSearchIdentificator() != null
                && exchangeRateDto.getSearchIdentificator().length() == 6;

        resp.setStatus(400);

        if (!fieldsAreValid || !validExchangeRateLength) {
            out.write(!fieldsAreValid ? "Переданы не все параметры!" : "Указаны не валидные коды валют!");
            return;
        }

        if (exchangeRateDto.getRate() < 0) {
            out.write("Ставка не может быть отрицательной!");
            return;
        }

        try {
            ExchangeRate exchangeRate = ExchangeRateService
                    .addExchangeRate(
                            exchangeRateDto.getBaseCurrency().getCode(),
                            exchangeRateDto.getTargetCurrency().getCode(),
                            exchangeRateDto.getRate());
            resp.setStatus(201);
            out.write(gson.toJson(exchangeRate));
        } catch (Exception e) {
            resp.setStatus(409);
            out.write(e.getMessage());
        }
    }


    public void updateExchangeRate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rate = req.getParameter("rate");
        PrintWriter out = resp.getWriter();

        if (!MyValidator.allFieldsAreValid(List.of(rate))) {
            resp.setStatus(400);
            out.write("Переданы не все параметры!");
            return;
        }

        String rateCodes = req.getPathInfo();

        if (rateCodes.length() != 7) {
            resp.setStatus(400);
            out.write("Указаны некорректные коды валют");
            return;
        }

        rateCodes = rateCodes.substring(1);


        try {
            ExchangeRate exchangeRate = ExchangeRateService.updateExchangeRate(Double.parseDouble(rate), rateCodes);
            resp.setStatus(201);
            out.write(gson.toJson(exchangeRate));
        } catch (Exception e) {
            resp.setStatus(404);
            out.write(e.getMessage());
        }
    }




}
