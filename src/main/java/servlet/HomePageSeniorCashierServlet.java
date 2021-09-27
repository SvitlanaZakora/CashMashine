package servlet;

import service.ReceiptService;
import service.impl.ReceiptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class HomePageSeniorCashierServlet extends HttpServlet {

    ReceiptService receiptService = new ReceiptServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("receipt") == null){
            req.getSession().setAttribute("visibilityUpdateReceipt", "\"display: none;\"");
            req.getSession().setAttribute("visibilityReceipts", "\"display: block;\"");
        }
        req.setAttribute("receiptList", receiptService.getPagingReceipts(
                Integer.parseInt(req.getParameter("page")),
                Integer.parseInt(req.getParameter("size"))));
        int pageCount = (int) Math.ceil((double) receiptService.getReceiptsCount() / Integer.parseInt(req.getParameter("size")));
        req.setAttribute("pageCount", pageCount == 0 ? 1 : pageCount);
        req.setAttribute("pageSize", req.getParameter("size"));
        req.getRequestDispatcher("homePageSeniorCashier2.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
