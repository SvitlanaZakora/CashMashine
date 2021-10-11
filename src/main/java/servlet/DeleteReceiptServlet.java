package servlet;

import service.ReceiptService;
import service.impl.ReceiptServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class DeleteReceiptServlet extends HttpServlet {
    ReceiptService receiptService = new ReceiptServiceImpl();

    /**
     * Deleting receipt if exists by id of receipt.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int receiptId = Integer.parseInt(req.getParameter("recId"));
        receiptService.deleteById(receiptId);
        req.getSession().removeAttribute("receipt");
        req.getSession().removeAttribute("receiptProducts");
        resp.sendRedirect("/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
    }
}
