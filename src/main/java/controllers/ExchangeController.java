package controllers;

import Services.ExchangeService;
import helpers.MyValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExchangeController extends Controller{
    public void exchange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fromCurrency = req.getParameter("from");
        String toCurrency = req.getParameter("to");
        String amount = req.getParameter("amount");
        PrintWriter out = resp.getWriter();

        if (!MyValidator.allFieldsAreValid(List.of(fromCurrency, toCurrency, amount))) {
            resp.setStatus(400);
            out.write("Переданы не все параметры!");
            return;
        }

        //todo change response write to object with amount and converted fields
        try {
            double exchangedCurrency = ExchangeService.exchangeCurrencies(fromCurrency, toCurrency, Integer.parseInt(amount));
            resp.setStatus(200);
            out.write("{\"amount\": " + exchangedCurrency + "}");
        } catch (Exception e) {
            resp.setStatus(404);
            out.write(e.getMessage());
        }


    }
}
