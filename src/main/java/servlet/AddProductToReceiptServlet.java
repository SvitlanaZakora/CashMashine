package servlet;

import entity.Product;
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
public class AddProductToReceiptServlet extends HttpServlet {

    ProductService productService= new ProductServiceImpl();
    ReceiptService receiptService= new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product;
        try {
            int code = Integer.parseInt(req.getParameter("codeOrName"));
            product = productService.getProductByCode(String.valueOf(req.getParameter("codeOrName")));
        }catch (NumberFormatException e){
            String name = req.getParameter("codeOrName");
            product = productService.getProductByName(name);
        }
        if (product==null){
            req.getSession().setAttribute("error", "No such product in storage");
            resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
            return;
        }

        double capacity;
        try {
            capacity = Double.parseDouble(req.getParameter("capacity"));
        }catch (NumberFormatException e){
            req.getSession().setAttribute("error", "Invalid value capacity");
            resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
            return;
        }
        int receiptId = Integer.parseInt(req.getParameter("receiptId"));
        req.getSession().setAttribute("receiptId", receiptId);
        receiptService.addProductToReceipt(product.getId(), receiptId, capacity);
        req.getSession().removeAttribute("error");
        resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
    }
}
