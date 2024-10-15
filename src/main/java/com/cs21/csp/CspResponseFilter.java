package com.cs21.csp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CspResponseFilter implements Filter {

    private final String headerName = "Content-Security-Policy";
    private final CspResourceService cspResourceService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        cspResourceService.getAll();

        response.addHeader(headerName, "*");

        filterChain.doFilter(servletRequest, response);
    }
}
