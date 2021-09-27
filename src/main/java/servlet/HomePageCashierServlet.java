package servlet;

import service.ProductService;
import service.UserService;
import service.impl.ProductServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet()
public class HomePageCashierServlet extends HttpServlet {

    private static UserService service;

    public HomePageCashierServlet() {
        service = new UserServiceImpl();
    }

    ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object receiptId = req.getSession().getAttribute("receiptId");
        if(receiptId==null) {
            req.getSession().setAttribute("visibilityProds", "\"display: block;\"");
            req.getSession().setAttribute("visibilityReceipt", "\"display: none;\"");
        }else{
            req.getSession().setAttribute("receiptProds", productService.getProductsByReceiptId(Integer.parseInt(receiptId.toString())));
        }
        String searchReq = req.getParameter("searchReq");
        if (!Objects.isNull(searchReq)) {
            req.setAttribute("searchReq",searchReq);
            searchReq = "%"+searchReq+"%";
            try {
                req.setAttribute("productList", productService.getPagingSearchProductsByCode(
                        Integer.parseInt(req.getParameter("page")),
                        Integer.parseInt(req.getParameter("size")),
                        String.valueOf(Integer.parseInt(searchReq))));
                int pageCount= (int) Math.ceil((double) productService.getProductsCountByCode(searchReq)/ Integer.parseInt(req.getParameter("size")));
                req.setAttribute("pageCount", pageCount==0?1:pageCount);
                req.setAttribute("pageSize", req.getParameter("size"));
                req.getRequestDispatcher("homePageCashier.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                req.setAttribute("productList", productService.getPagingSearchProductsByName(
                        Integer.parseInt(req.getParameter("page")),
                        Integer.parseInt(req.getParameter("size")), searchReq));
                int pageCount= (int) Math.ceil((double) productService.getProductsCountByName(searchReq)/ Integer.parseInt(req.getParameter("size")));
                req.setAttribute("pageCount", pageCount==0?1:pageCount);
                req.setAttribute("pageSize", req.getParameter("size"));
                req.getRequestDispatcher("homePageCashier.jsp").forward(req, resp);
            }
        }else {
            req.setAttribute("productList", productService.getPagingProducts(
                    Integer.parseInt(req.getParameter("page")),
                    Integer.parseInt(req.getParameter("size"))));
            int pageCount = (int) Math.ceil((double) productService.getProductsCount() / Integer.parseInt(req.getParameter("size")));
            req.setAttribute("pageCount", pageCount == 0 ? 1 : pageCount);
            req.setAttribute("pageSize", req.getParameter("size"));
            req.getRequestDispatcher("homePageCashier.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("homePageCashier.jsp").forward(req, resp);
    }
}
