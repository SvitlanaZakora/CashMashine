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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet
public class HomePageCommodityExpertServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl();

    /**
     * Demonstrating products on storage. Searching product if searchReq not null.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
                req.getRequestDispatcher("homePageCommodityExpert.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                req.setAttribute("productList", productService.getPagingSearchProductsByName(
                        Integer.parseInt(req.getParameter("page")),
                        Integer.parseInt(req.getParameter("size")), searchReq));
                int pageCount= (int) Math.ceil((double) productService.getProductsCountByName(searchReq)/ Integer.parseInt(req.getParameter("size")));
                req.setAttribute("pageCount", pageCount==0?1:pageCount);
                req.setAttribute("pageSize", req.getParameter("size"));
                req.getRequestDispatcher("homePageCommodityExpert.jsp").forward(req, resp);
            }
        }else {
            req.setAttribute("productList", productService.getPagingProducts(
                    Integer.parseInt(req.getParameter("page")),
                    Integer.parseInt(req.getParameter("size"))));
            int pageCount = (int) Math.ceil((double) productService.getProductsCount() / Integer.parseInt(req.getParameter("size")));
            req.setAttribute("pageCount", pageCount == 0 ? 1 : pageCount);
            req.setAttribute("pageSize", req.getParameter("size"));
            req.getRequestDispatcher("homePageCommodityExpert.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("homePageCommodityExpert.jsp").forward(req, resp);
    }
}
