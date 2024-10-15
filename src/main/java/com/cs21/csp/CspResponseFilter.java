package com.cs21.csp;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class CspResponseFilter implements Filter {

    private final CspResourceService cspResourceService;

    public CspResponseFilter(String driverClassName, String url, String username, String password, String dialect) {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, driverClassName);
        properties.put(Environment.URL, url);
        properties.put(Environment.USER, username);
        properties.put(Environment.PASS, password);

        if (StringUtils.hasText(dialect)) properties.put(Environment.DIALECT, dialect);

        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "create-drop");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("csp", properties);

        this.cspResourceService = new CspResourceService(entityManagerFactory);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.addHeader("Content-Security-Policy", cspResourceService.getAll().toArray().toString());

        filterChain.doFilter(servletRequest, response);
    }
}
