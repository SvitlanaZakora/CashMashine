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
import java.util.Objects;

@WebServlet
public class UpdateProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();
    ReceiptService receiptService = new ReceiptServiceImpl();

    /**
     * Throwing error messages if product not exists.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        int receiptId = Integer.parseInt(req.getSession().getAttribute("receiptId").toString());
        double capacity = Double.parseDouble(req.getParameter("capacity"));
        String productCode = req.getParameter("productCode");
        Product product = productService.getProductByCode(productCode);
        if(product==null){
            System.out.println("no product");
            return;
        }
        if(!receiptService.updateProduct(product.getId(), receiptId, capacity)){
            System.out.println("no product in receipt");
        }

    }

    /**
     * Updating product if code or name do not match with already existing product.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        Product product = productService.getProductByCode(code);
        if (product!=null && product.getId()!=id) {
            req.getSession().setAttribute("error", "Dublicate code " + code);
            resp.sendRedirect("/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
            return;
        }
        product = productService.getProductByName(name);
        if (product!=null && product.getId()!=id){
            req.getSession().setAttribute("error", "Dublicate name " + name);
            resp.sendRedirect("/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
            return;
        }
        String capacityType = req.getParameter("capacityType");
        double capacity = Double.parseDouble(req.getParameter("capacity"));
        double price = Double.parseDouble(req.getParameter("price"));
        product = new Product(id,name,capacityType,capacity,price,code);
        productService.updateProduct(product);
        req.getSession().removeAttribute("error");
        resp.sendRedirect("/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
    }
}
