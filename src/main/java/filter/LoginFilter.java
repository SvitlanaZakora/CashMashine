package filter;

import entity.Role;
import entity.RoleShortNames;
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

        if(RoleShortNames.C.name().equals(user.getRole().getShort_name()) && CASHIER_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else if(RoleShortNames.SC.name().equals(user.getRole().getShort_name()) && SENIOR_CASHIER_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else if (RoleShortNames.CE.name().equals(user.getRole().getShort_name()) && COMMODITY_EXPERT_PAGE.equals(requestURI)){
            filterChain.doFilter(request,response);
        } else {
            response.sendRedirect(loginURI);
        }


    }

    @Override
    public void destroy() {

    }
}
