package servlet;

import entity.Role;
import entity.RoleShortNames;
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
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@WebServlet
public class LoginServlet extends HttpServlet {

    private static UserService service;

    public LoginServlet() {
        service = new UserServiceImpl();
    }

    /**
     * Demonstration roles in current language.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionLang = (String) req.getSession().getAttribute("lang");
        req.getSession().setAttribute("lang", sessionLang == null?"en":sessionLang);
        List<Role> roleList =  service.getAllRolesByLang(req.getSession().getAttribute("lang").toString());
        req.getSession().setAttribute("roleList" , roleList);
        req.getSession().removeAttribute("error");
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    /**
     * Entering in user`s account if pass and role match.
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang") == null ? "en" : req.getParameter("lang");
        String login = req.getParameter("login");
        User user = service.getUserByLogin(login, lang);
        if (!Objects.isNull(user)) {
            String pass = req.getParameter("pass");
            if (pass.equals(user.getPass())) {
                req.getSession().setAttribute("user", user);
                switch (RoleShortNames.valueOf(user.getRole().getShort_name())) {
                    case C:
                        resp.sendRedirect(req.getContextPath()+"/homePageCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
                        req.getSession().removeAttribute("error");
                        break;
                    case SC:
                        resp.sendRedirect(req.getContextPath()+"/homePageSeniorCashier?page=0&size="+ SQLConstants.PAGE_SIZE);
                        req.getSession().removeAttribute("error");
                        break;
                    case CE:
                        resp.sendRedirect(req.getContextPath()+"/homePageCommodityExpert?page=0&size="+ SQLConstants.PAGE_SIZE);
                        req.getSession().removeAttribute("error");
                        break;
                }
            }else {
                req.getSession().setAttribute("error", "Invalid login or password");
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
            }
        }else {
            req.getSession().setAttribute("error", "Invalid login or password");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        }
    }
}
