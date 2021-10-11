package servlet;

import dao.impl.ReceiptDaoImpl;
import entity.Receipt;
import org.apache.log4j.Logger;
import service.ReceiptService;
import service.impl.ReceiptServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet
public class CreateReportZServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ReceiptDaoImpl.class);

    ReceiptService receiptService = new ReceiptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime1 = localDate.atStartOfDay().plusSeconds(1);
        LocalDateTime localDateTime2 = localDate.atStartOfDay().plusDays(1).minusSeconds(1);
        List<Receipt> receiptList = receiptService.getAllClosedReceiptsBetweenDate(localDateTime1,localDateTime2);

        try {
            File file = new File("Z-report_"+localDate+".txt");
            if (!file.createNewFile()) {
                double total = 0.0;
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.printf("%20s%30s%15s\n","Number of receipt", "Date of creation", "Total");
                for (Receipt receipt:receiptList) {
                    total += receipt.getTotal();
                    writer.printf("%20d%30s%15s\n", receipt.getId(),receipt.getCreationDateTime(),receipt.getTotal());
                    receiptService.deleteById(receipt.getId());
                }
                writer.println("Total = " + total);
                writer.println("All receipts was deleted!");
                writer.flush();
                writer.close();
                LOG.info("File created: " + file.getName());
                sendFile(req, resp, file);
            } else {
                LOG.error("File already exists.");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath()+"/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);

    }

    public static void sendFile(HttpServletRequest req, HttpServletResponse resp, File file) throws IOException {
        FileInputStream inStream = new FileInputStream(file);

        String mimeType = req.getServletContext().getMimeType(file.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int) file.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        resp.setHeader(headerKey, headerValue);

        OutputStream outStream = resp.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }
}
