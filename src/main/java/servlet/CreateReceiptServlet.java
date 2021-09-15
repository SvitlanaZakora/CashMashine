package servlet;

import entity.Receipt;
import entity.User;
import service.ReceiptService;
import service.impl.ReceiptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CreateReceiptServlet extends HttpServlet {

    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user  = (User) req.getSession().getAttribute("user");
        Receipt receipt = receiptService.create(user.getId());
        req.getSession().setAttribute("receipt", receipt);
        req.getSession().setAttribute("receiptId", receipt.getId());
        req.getSession().setAttribute("visibility", "\"visibility: visible;\"");
        resp.sendRedirect("/homePageCashier");

    }
}
