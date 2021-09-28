package servlet;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class LanguageServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang") == null ? "en" : req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            user = userService.getUserById(user.getId(), lang);
            req.getSession().setAttribute("user", user);
        }
        resp.sendRedirect(req.getHeader("referer"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
