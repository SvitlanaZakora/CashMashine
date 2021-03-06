package servlet;

import entity.Product;
import entity.Receipt;
import entity.User;
import service.ProductService;
import service.impl.ProductServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet
public class CreateProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    /**
     * Creating product on storage if not exists.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");

        if (Objects.isNull(productService.getProductByCode(code)) && Objects.isNull(productService.getProductByName(name))){
            String capacityType = req.getParameter("capacityType");
            double capacity = Double.parseDouble(req.getParameter("capacity"));
            double price = Double.parseDouble(req.getParameter("price"));

            Product product = new Product(code,name,capacityType,capacity,price);
            productService.createProduct(product);
        }else{
            req.getSession().setAttribute("error", "Product already exist");
            resp.sendRedirect("/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
            return;
        }

        req.getSession().removeAttribute("error");
        resp.sendRedirect("/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
    }
}
