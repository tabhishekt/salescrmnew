package com.propmgr.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.propmgr.hibernate.HibernateConnection;


public class RestAPIFilter implements Filter {
	
	public final static Logger logger = (Logger)LogManager.getLogger(RestAPIFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
        	chain.doFilter(request, response);
        	// So that every request release the connection
        	HibernateConnection.closeSession();
        } catch (Exception e) {
            logger.error("", e);
        } finally { 

        }
    }
	@Override
	public void destroy() {
		// Nothing to do
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// Nothing to do
		
	}
}