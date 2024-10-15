package com.cs21.csp;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CspResponseFilter implements Filter {

    private final String headerName = "Content-Security-Policy";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        response.addHeader(headerName, "*");

        filterChain.doFilter(servletRequest, response);
    }
}
