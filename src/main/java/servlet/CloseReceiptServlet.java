package servlet;

import entity.Product;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@WebServlet
public class CloseReceiptServlet extends HttpServlet {
    ReceiptService receiptService = new ReceiptServiceImpl();
    ProductService productService = new ProductServiceImpl();


    /**
     * Closing receipt. Setting active receipt as false, if receipt was opened.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Receipt receipt = receiptService.getById(Integer.parseInt(req.getParameter("receiptId")));
        if (receipt.isActive()){
            List<Product> productReceiptList = productService.getProductsByReceiptId(receipt.getId());
            double finalPrice = productReceiptList.stream().map(e->e.getPrice()*e.getCapacity()).reduce(0.0, (a, b)->a+b);
            for (Product p:productReceiptList) {
                Product productInStorage = productService.getProductById(p.getId());
                if (productInStorage.getCapacity() - p.getCapacity() < 0) {
                    req.getSession().setAttribute("error", productInStorage.getName() +
                            " is over. Available count = " + productInStorage.getCapacity());
                    resp.sendRedirect(req.getContextPath() + "/homePageCashier?page=0&size=" + SQLConstants.PAGE_SIZE);
                    return;
                } else {
                    productInStorage.setCapacity(productInStorage.getCapacity() - p.getCapacity());
                    productService.updateProduct(productInStorage);
                }
            }
            receiptService.close(Integer.parseInt(req.getParameter("receiptId")), finalPrice);
            HttpSession session = req.getSession();
            session.removeAttribute("receipt");
            session.removeAttribute("receiptId");
            session.removeAttribute("receiptProds");
            req.getSession().setAttribute("visibilityProds", "\"display: block;\"");
            req.getSession().setAttribute("visibilityReceipt", "\"display: none;\"");
        }
        req.getSession().removeAttribute("error");
        resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
    }

}
