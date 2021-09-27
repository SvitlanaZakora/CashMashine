package servlet;

import entity.Role;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.SQLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@WebServlet
public class LoginServlet extends HttpServlet {

    private static UserService service;

    public LoginServlet() {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang") == null ? "en" : req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = service.getUserByLogin(login);
        if (!Objects.isNull(user)) {
            String pass = req.getParameter("pass");
            if (pass.equals(user.getPass())) {
                req.getSession().setAttribute("user", user);
                switch (user.getRole()) {
                    case CASHIER:
                        resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
                        break;
                    case SENIOR_CASHIER:
                        resp.sendRedirect(req.getContextPath()+"/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
                        break;
                    case COMMODITY_EXPERT:
                        resp.sendRedirect(req.getContextPath()+"/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
                        break;
                }
            }else
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }
}
