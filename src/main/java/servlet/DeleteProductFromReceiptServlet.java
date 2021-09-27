package servlet;

import entity.Receipt;
import service.ProductService;
import service.ReceiptService;
import service.impl.ProductServiceImpl;
import service.impl.ReceiptServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class DeleteProductFromReceiptServlet extends HttpServlet {

    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("prodId"));
        int receiptId = Integer.parseInt(req.getParameter("recId"));
        receiptService.deleteProduct(productId, receiptId);
        resp.sendRedirect("/updateReceipt?recId="+ receiptId);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
