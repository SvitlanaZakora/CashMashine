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
public class UpdateReceiptServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int receiptId = Integer.parseInt(req.getParameter("recId"));
        if (productService.getProductsByReceiptId(receiptId) != null){
            req.getSession().setAttribute("receipt", receiptService.getByIdWithOwner(receiptId));
            req.getSession().setAttribute("receiptProducts", productService.getProductsByReceiptId(receiptId));
            req.getSession().setAttribute("visibilityUpdateReceipt", "\"display: block;\"");
            req.getSession().setAttribute("visibilityReceipts", "\"display: none;\"");
            resp.sendRedirect("/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
