package com.propmgr.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.propmgr.hibernate.HibernateConnection;


public class SecurityFilter implements Filter {
   private final static Logger logger = Logger.getLogger(SecurityFilter.class);
   private ServletContext servletContext = null;

    public void init(FilterConfig cfg) throws ServletException {
    	HibernateConnection.getSessionFactory(); //This will make the session factory
    	
        servletContext = cfg.getServletContext();
    }
    
	public String getPageName(String requestURI) {
		String pageName = requestURI;
		pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
		return pageName;
	}
    
	
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
//            String pageName = getPageName(((HttpServletRequest) request).getRequestURI());    
//            if(pageName.endsWith(".jsp") && !pageName.equalsIgnoreCase("home.jsp")) {
//            	String url = ((HttpServletRequest)request).getContextPath() + "/jsp/home.jsp"; 
//            	((HttpServletResponse)response).sendRedirect(url);
//            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("", e);
        } finally { 

        }
    }

    public void destroy() {
        
    }
}