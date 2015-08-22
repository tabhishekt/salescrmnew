package com.propmgr.jersey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * API to handle requests to generate reports
 */
@Path("/report")
public class ReportService {
    //The logger instance
	public final static Logger logger = (Logger)LogManager.getLogger(ReportService.class);
    //The folder name where the report templates are placed
    private final static String REPORTS_TEMPLATE_FOLDER = "/report";
    /////////////////***The report template names****/////////////////////////////////////////
    private final static String ALLOCATION_REPORT = "allocation_report.jrxml";

    /**
     * this function will generate the report for the allocation master
     * @param context
     * @return
     */
    @GET
    @Path("/allocation")
    @Produces({"application/pdf"})
    public Response allocationReport(@Context ServletContext context) {
        //get the report path
        String reportTemplate = getTemplatePath(context, ALLOCATION_REPORT);
        try {
            InputStream inputStream = new FileInputStream(reportTemplate);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(),new JREmptyDataSource());
            //Get the tmp Folder
            String tmpFolder = getTemporaryStorageFolder(context);
            JasperExportManager.exportReportToPdfFile(jasperPrint, tmpFolder + "allocationReport.pdf");
            File file = new File(tmpFolder + "allocationReport.pdf");
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment; filename=allocationReport.pdf");
            return response.build();
        } catch (FileNotFoundException e) {
            throw new WebServiceException(e);
        } catch (JRException e) {
            throw new WebServiceException(e);
        }
    }

    /**
     * The report first needs to be created and stored on the disk at a temp location.
     * this function will return the relative path of the temporary location
     * @param context
     * @return
     */
    private String getTemporaryStorageFolder (ServletContext context) {
        String ctxPath = context.getRealPath("WEB-INF");
        String fPath = ctxPath + "/" +REPORTS_TEMPLATE_FOLDER + "/tmp";
        File f = new File(fPath);
        if (!f.exists()) {
            //Folder seems to be absent, lets create it
            f.mkdir();
        }
        return fPath;
    }

    /**
     * This function will return the relative path of the specified
     * report.
     * Note : I have create a function so that in future if the location of the report templates
     * needs to be changed this is the only function that will need modification.
     * @return
     */
    private String getTemplatePath(ServletContext context, String report) {
        //Lets first create the report template file path
        String ctxPath = context.getRealPath("WEB-INF");
        return ctxPath + "/" +REPORTS_TEMPLATE_FOLDER + "/" + report;
    }

}
