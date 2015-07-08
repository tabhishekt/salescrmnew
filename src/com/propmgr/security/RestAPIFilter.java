package com.propmgr.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.propmgr.hibernate.HibernateConnection;
import com.propmgr.security.SecurityFilter;


public class RestAPIFilter implements Filter {
	
	private final static Logger logger = Logger.getLogger(SecurityFilter.class);
	
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