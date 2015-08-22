package com.propmgr.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.propmgr.hibernate.HibernateConnection;


public class SecurityFilter implements Filter {
	public final static Logger logger = (Logger)LogManager.getLogger(SecurityFilter.class);
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
        	/*Boolean isAutheticated = false;
        	HttpSession httpSession = ((HttpServletRequest)request).getSession(false);
        	if (httpSession != null) {
	        	isAutheticated = (Boolean)httpSession.getAttribute("isAutheticated");
        	} 
        	
        	if(isAutheticated) {
        		chain.doFilter(request, response);
        	} else {
        		((HttpServletResponse)response).sendRedirect("home.jsp");
        	}*/
        	
        	chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("", e);
        } finally { 

        }
    }

    public void destroy() {
        
    }
}