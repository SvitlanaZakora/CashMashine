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
import java.util.Objects;

@WebServlet
public class LoginServlet extends HttpServlet {

    private static UserService service;

    public LoginServlet() {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = service.getUserByLogin(login);
        if (!Objects.isNull(user)) {
            String pass = req.getParameter("pass");
            if (pass.equals(user.getPass())) {
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
            }
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
