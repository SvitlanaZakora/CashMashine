package servlet;

import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet
public class UpdateProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String capacityType = req.getParameter("capacityType");
        double capacity = Double.parseDouble(req.getParameter("capacity"));
        double price = Double.parseDouble(req.getParameter("price"));
            Product product = new Product(id,name,capacityType,capacity,price,code);
            productService.updateProduct(product);
        resp.sendRedirect("/homePageCommodityExpert");
    }
}
