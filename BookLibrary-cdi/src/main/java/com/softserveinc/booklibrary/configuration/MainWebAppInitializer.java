package com.softserveinc.booklibrary.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private final Logger logger = LoggerFactory.getLogger(MainWebAppInitializer.class);

	@Override
	protected Class<?>[] getRootConfigClasses() {
		logger.info("Method getRootConfigClasses ............");
		return new Class[]{
				HibernateConfiguration.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.info("Method getServletConfigClasses ............");
		return new Class[]{
				MvcConfiguration.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		logger.info("Method getServletMappings ............");
		return new String[]{
				"/"
		};
	}
}