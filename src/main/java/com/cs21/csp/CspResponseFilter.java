package com.cs21.csp;

import lombok.Getter;
import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.tool.schema.Action;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class CspResponseFilter implements Filter {

    @Getter
    private static CspResourceService service;
    private final CspResourceService cspResourceService;

    public CspResponseFilter(String className, String url, String username, String password, String dialect) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(className);

        Properties properties = new Properties();
        if (StringUtils.hasText(dialect)) properties.put(Environment.DIALECT, dialect);
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, Action.CREATE_ONLY);

        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setPersistenceUnitName("arimoa-csp");
        managerFactoryBean.setDataSource(dataSource);
        managerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        managerFactoryBean.setPackagesToScan("com.cs21.csp");
        managerFactoryBean.setJpaProperties(properties);
        managerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        managerFactoryBean.afterPropertiesSet();

        this.cspResourceService = new CspResourceService(managerFactoryBean.getObject());
        service = this.cspResourceService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.addHeader("Content-Security-Policy", CsCspResources.getResponseHeaderFormat(getService().getAll()));

        filterChain.doFilter(servletRequest, response);
    }
}