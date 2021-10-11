package servlet;

import dao.impl.ReceiptDaoImpl;
import entity.Receipt;
import org.apache.log4j.Logger;
import service.ReceiptService;
import service.impl.ReceiptServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet
public class CreateReportXServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ReceiptDaoImpl.class);

    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime1 = localDate.atStartOfDay().plusSeconds(1);
        LocalDateTime localDateTime2 = localDate.atStartOfDay().plusDays(1).minusSeconds(1);
        List<Receipt> receiptList = receiptService.getAllClosedReceiptsBetweenDate(localDateTime1,localDateTime2);

        try {
            File file = new File("X-report_"+localDate+".txt");
            if (file.createNewFile()) {
                double total = 0.0;
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.printf("%20s%30s%15s\n","Number of receipt", "Date of creation", "Total");
                for (Receipt receipt:receiptList) {
                    total += receipt.getTotal();
                    writer.printf("%20d%30s%15s\n", receipt.getId(),receipt.getCreationDateTime(),receipt.getTotal());
                }
                writer.println("Total = " + total);
                writer.flush();
                writer.close();
                LOG.info("File created: " + file.getName());
                CreateReportZServlet.sendFile(req,resp,file);
            } else {
                LOG.error("File already exists.");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath()+"/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);

    }
}
