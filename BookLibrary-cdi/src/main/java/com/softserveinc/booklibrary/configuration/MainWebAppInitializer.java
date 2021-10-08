package com.softserveinc.booklibrary.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.out.println("1");
        return new Class[] {
                HibernateConfiguration.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("2");
        return new Class[] {
                MvcConfiguration.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        System.out.println("3");
        return new String[] {
                "/"
        };
    }
}