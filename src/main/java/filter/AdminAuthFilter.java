package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("adminObj") != null);
        boolean loginRequest = req.getRequestURI().endsWith("login.jsp")
                            || req.getRequestURI().endsWith("AdminLoginServlet");

        if (loggedIn || loginRequest)
        {
        	System.out.println(" admin  session start ");
            chain.doFilter(request, response); // allow request
        } else
        {
            resp.sendRedirect(req.getContextPath()
                    + "/admin/login.jsp?error=Please+login+first");
        }
    }
}
