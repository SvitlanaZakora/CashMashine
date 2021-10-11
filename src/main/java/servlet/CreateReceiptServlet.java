package servlet;

import entity.Receipt;
import entity.User;
import service.ReceiptService;
import service.impl.ReceiptServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet
public class CreateReceiptServlet extends HttpServlet {

    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("homePageCashier.jsp").forward(req, resp);
    }

    /**
     * Creating receipt if not exists.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("receiptId")==null) {
            User user  = (User) req.getSession().getAttribute("user");
            List<Receipt> receiptList = receiptService.getAllReceiptsByUserIdAndActive(user.getId(), true);
            if (receiptList.size() > 0){
                req.getSession().setAttribute("error", "You have active receipts. Please, close it!");
                req.getSession().setAttribute("receipt", receiptList.get(0));
                req.getSession().setAttribute("receiptId", receiptList.get(0).getId());
            }else {
                Receipt receipt = receiptService.create(user.getId());
                req.getSession().setAttribute("receipt", receipt);
                req.getSession().setAttribute("receiptId", receipt.getId());
            }
        }
        req.getSession().setAttribute("visibilityProds", "\"display: none;\"");
        req.getSession().setAttribute("visibilityReceipt", "\"display: block;\"");
        resp.sendRedirect("/homePageCashier?page=0&size=" + SQLConstants.PAGE_SIZE);

    }
}
