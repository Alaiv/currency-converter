package servlets;

import controllers.CurrencyController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    CurrencyController controller = new CurrencyController();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        controller.getCurrency(req, resp);
    }
}
