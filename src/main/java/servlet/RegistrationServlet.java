package servlet;

import entity.Role;
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

@WebServlet()
public class RegistrationServlet extends HttpServlet {

    private static UserService service;

    public RegistrationServlet() {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String lang = String.valueOf(req.getSession().getAttribute("lang"));
        if (Objects.isNull(service.getUserByLogin(login,lang))){
            String pass = req.getParameter("pass");
            int roleId = Integer.parseInt(req.getParameter("roleId"));

            User user = new User(login,pass,Role.builder().id(roleId).build());   //builder pattern (optional params)
            service.createUser(user);

            req.getRequestDispatcher("registration.jsp").forward(req, resp);




//            req.setAttribute("login", login);
//            req.setAttribute("pass", pass);
//            req.setAttribute("role", role);
        }

    }
}
