package com.propmgr.hibernate;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final Log log = LogFactory.getLog(HibernateUtil.class);

    private static final String INTERCEPTOR_CLASS = "hibernate.util.interceptor_class";

    private static Configuration configuration;
    private static SessionFactory sessionFactory;

    static {
        // Create the initial SessionFactory from the default configuration files
        try {
            configuration = new Configuration(); 
            
            //configure using hibernate.cfg.xml from the root of the classpath
            configuration.configure();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            
        } catch (Throwable ex) {
            // We have to catch Throwable, otherwise we will miss
            // NoClassDefFoundError and other subclasses of Error
            log.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the original Hibernate configuration.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration() {
        return configuration;
    }
    
    /**
     * Returns the global SessionFactory.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        SessionFactory sf = null;
        String sfName = configuration.getProperty(Environment.SESSION_FACTORY_NAME);
        
        if ( sfName != null) {
           log.debug("Looking up SessionFactory ["+sfName+"] in JNDI.");
            try {
                sf = (SessionFactory) new InitialContext().lookup(sfName);
            } catch (NamingException ex) {
                //throw new RuntimeException(ex);
            }
        } else {
            sf = sessionFactory;
        }
        if (sf == null)
            throw new IllegalStateException("SessionFactory not available.");
        return sf;
    }

    /**
     * Closes the current SessionFactory and releases all resources.
     * <p>
     * The only other method that can be called on HibernateUtil
     * after this one is rebuildSessionFactory(Configuration).
     */
    public static void shutdown() {
        log.info("Shutting down Hibernate.");
        // Close caches and connection pools
        getSessionFactory().close();

        // Clear static variables
        configuration = null;
        sessionFactory = null;
    }


    /**
     * Rebuild the SessionFactory with the static Configuration.
     * <p>
     * This method also closes the old SessionFactory before, if still open.
     * Note that this method should only be used with static SessionFactory
     * management, not with JNDI or any other external registry.
     */
     public static void rebuildSessionFactory() {
        log.debug("Using current Configuration for rebuild.");
        rebuildSessionFactory(configuration);
     }

    /**
     * Rebuild the SessionFactory with the given Hibernate Configuration.
     * <p>
     * HibernateUtil does not configure() the given Configuration object,
     * it directly calls buildSessionFactory(). This method also closes
     * the old SessionFactory before, if still open.
     *
     * @param cfg
     */
     public static void rebuildSessionFactory(Configuration cfg) {
        log.debug("Rebuilding the SessionFactory from given Configuration.");
        synchronized(sessionFactory) {
            if (sessionFactory != null && !sessionFactory.isClosed())
                sessionFactory.close();
            if (cfg.getProperty(Environment.SESSION_FACTORY_NAME) != null)
                cfg.buildSessionFactory();
            else
                sessionFactory = cfg.buildSessionFactory();
            configuration = cfg;
        }
     }

    /**
     * Register a Hibernate interceptor with the current SessionFactory.
     * <p>
     * Every Session opened is opened with this interceptor after
     * registration. Has no effect if the current Session of the
     * thread is already open, effective on next close()/getCurrentSession().
     * <p>
     * Attention: This method effectively restarts Hibernate. If you
     * need an interceptor active on static startup of HibernateUtil, set
     * the <tt>hibernateutil.interceptor</tt> system property to its
     * fully qualified class name.
     */
    public static SessionFactory registerInterceptorAndRebuild(Interceptor interceptor) {
        log.debug("Setting new global Hibernate interceptor and restarting.");
     //   setInterceptor(configuration, interceptor);
        rebuildSessionFactory();
        return getSessionFactory();
    }

    public static Interceptor getInterceptor() {
        return configuration.getInterceptor();
    }

    /**
     * Resets global interceptor to default state.
     */
    public static void resetInterceptor() {
        log.debug("Resetting global interceptor to configuration setting");
       // setInterceptor(configuration, null);
    }

}

