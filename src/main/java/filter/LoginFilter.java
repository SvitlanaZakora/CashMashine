package filter;

import entity.Role;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter()
public class LoginFilter implements Filter {

    private static final String CASHIER_PAGE = "/homePageCashier";
    private static final String SENIOR_CASHIER_PAGE = "/homePageSeniorCashier";
    private static final String COMMODITY_EXPERT_PAGE = "/homePageCommodityExpert";


    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String loginURI = request.getContextPath() + "/login";
        String requestURI = request.getRequestURI();
        User user  = (User) request.getSession().getAttribute("user");

        if(user==null){
            response.sendRedirect(loginURI);
            return;
        }

        if(Role.CASHIER.equals(user.getRole()) && CASHIER_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else if(Role.SENIOR_CASHIER.equals(user.getRole()) && SENIOR_CASHIER_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else if (Role.COMMODITY_EXPERT.equals(user.getRole()) && COMMODITY_EXPERT_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else {
            response.sendRedirect(loginURI);
        }


    }

    @Override
    public void destroy() {

    }
}
