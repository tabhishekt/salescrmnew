package com.propmgr.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

public class HibernateConnection {

    // the logger for this class
    private static final Log logger = LogFactory.getLog(HibernateConnection.class);

    private static final ThreadLocal threadSession = new ThreadLocal();

    private static final ThreadLocal threadTransaction = new ThreadLocal();

	

    //Wrapper to get the session factory using the HibernateUtil
    public static SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    //Shutdown the hibernate Session factory.
    public static void releaseSessionFactory() {
        HibernateUtil.shutdown();
    }

    public static Session getSession() {
        Session s = (Session) threadSession.get();
        // Open a new, if this thread has none yet
        try {
            if (s == null) {
                s = getSessionFactory().openSession();
                setIsolationLevel(s); // Set the isolation from the configuration
                threadSession.set(s);
            }else { //This was commented for some reason
                if (!s.isConnected()) {
                   throw new HibernateException("Error in application code Application code closed the session by explicit session.close() call");
                }
            }
        } catch (Exception ex) {
            logger.error("getSession: EXCEPTION [ " + ex.getMessage() + " ]");
            throw new HibernateException(ex);
        }
        return s;
    }
    
    
    
    private static int setIsolationLevel(Session s) {
    	String isolationLevel = HibernateUtil.getConfiguration().getProperty("hibernate.connection.isolation");
        int il = 1; //default is dirty read. can be changed if required.
        try {
        	il = Integer.parseInt(isolationLevel);
        	s.doWork(new Work() {
        	    @Override
        	    public void execute(Connection connection) throws SQLException {
        	        //got a connection, finally!
        	    	connection.getTransactionIsolation();
        	    }
        	});
        }catch(Exception e) {
        	logger.warn("EXCEPTION: Could not set the isolation level ["+e.getMessage()+"]");
        }
        
        return il;
    }
    
    public static void setSerializableIsolationLevel(Session s) {
    	 try {
        	s.doWork(new Work() {
        	    @Override
        	    public void execute(Connection connection) throws SQLException {
        	        //connection, finally!
        	    	connection.setTransactionIsolation(8);
        	    	logger.info("session isolation level is : " + connection.getTransactionIsolation());
        	    }
        	});
        }catch(Exception e) {
        	logger.warn("EXCEPTION: Could not set the isolation level ["+e.getMessage()+"]");
        }       
        
    }

    public static void closeSession() {
        try {
            Session s = (Session) threadSession.get();
            threadSession.set(null);
            if (s != null && s.isOpen())
                s.close();
        } catch (Exception ex) {
            logger.warn("closeSession : HibernateException: "
                            + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void beginTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx == null) {
                tx = getSession().beginTransaction();
                threadTransaction.set(tx);
            }
        } catch (Exception ex) {
            throw new HibernateException(ex);
        }
    }

    public static void commitTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();
            }
            threadTransaction.set(null);
        } catch (Exception ex) {
            logger.error("commitTransaction: HibernateException :"
                    + ex.getMessage());
            rollbackTransaction();

        }
    }

    public static void rollbackTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();
            }
        } catch (Exception ex) {
            logger.error("rollbackTransaction: HibernateException: "
                    + ex.getMessage());
            throw new HibernateException(ex);
        } finally {
            closeSession();
        }
    }
}