package com.propmgr.jersey;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.propmgr.dao.Address;
import com.propmgr.dao.Contactinfo;
import com.propmgr.dao.Customermaster;
import com.propmgr.dao.Enquiry;
import com.propmgr.dao.EnquiryDAO;
import com.propmgr.dao.Enquirycomment;
import com.propmgr.dao.EnquirycommentDAO;
import com.propmgr.dao.Organization;
import com.propmgr.dao.OrganizationDAO;
import com.propmgr.dao.Paymentmaster;
import com.propmgr.dao.PaymentmasterDAO;
import com.propmgr.dao.Paymentstage;
import com.propmgr.dao.PaymentstageDAO;
import com.propmgr.dao.Paymentstate;
import com.propmgr.dao.PaymentstateDAO;
import com.propmgr.dao.Paymentstatus;
import com.propmgr.dao.PaymentstatusDAO;
import com.propmgr.dao.Paymenttype;
import com.propmgr.dao.Person;
import com.propmgr.dao.Projectbuilding;
import com.propmgr.dao.ProjectbuildingDAO;
import com.propmgr.dao.Projectmaster;
import com.propmgr.dao.ProjectmasterDAO;
import com.propmgr.dao.Projectphase;
import com.propmgr.dao.ProjectphaseDAO;
import com.propmgr.dao.Refundmaster;
import com.propmgr.dao.RefundmasterDAO;
import com.propmgr.dao.Sourcemaster;
import com.propmgr.dao.Unitamenity;
import com.propmgr.dao.UnitamenityDAO;
import com.propmgr.dao.Unitbooking;
import com.propmgr.dao.UnitbookingDAO;
import com.propmgr.dao.Unitmaster;
import com.propmgr.dao.UnitmasterDAO;
import com.propmgr.dao.Unitpaymentschedule;
import com.propmgr.dao.UnitpaymentscheduleDAO;
import com.propmgr.dao.Unitpricepolicy;
import com.propmgr.dao.UnitpricepolicyDAO;
import com.propmgr.dao.Unittype;
import com.propmgr.dao.Usermaster;
import com.propmgr.resource.CodeTableResource;
import com.propmgr.resource.CustomerResource;
import com.propmgr.resource.EnquiryCommentResource;
import com.propmgr.resource.EnquiryResource;
import com.propmgr.resource.OrganizationResource;
import com.propmgr.resource.PaymentResource;
import com.propmgr.resource.PaymentStateResource;
import com.propmgr.resource.PrintBookingResource;
import com.propmgr.resource.ProjectBuildingResource;
import com.propmgr.resource.ProjectPhaseResource;
import com.propmgr.resource.ProjectResource;
import com.propmgr.resource.ResourceUtil;
import com.propmgr.resource.UnitBookingResource;
import com.propmgr.resource.UnitPaymentScheduleDetailsResource;
import com.propmgr.resource.UnitPaymentScheduleResource;
import com.propmgr.resource.UnitPriceDetailResource;
import com.propmgr.resource.UnitPricePolicyResource;
import com.propmgr.resource.UnitResource;
 
 
@Path("/json/data/inventory")
public class InventoryService {
	private final static Logger logger = Logger.getLogger(InventoryService.class);
	
	@GET
	@Path("/organization/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrganization() {
		OrganizationDAO organizationDAO = new OrganizationDAO();
		List<OrganizationResource> result = new ArrayList<OrganizationResource>();
		
		try {
			List<Organization> allorganizations = organizationDAO.findAll();
			for (Organization org : allorganizations) {
				result.add(ResourceUtil.getOrganizationFromDAO(org));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/organization/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganization(@QueryParam("rowId") String rowId) {
		OrganizationDAO organizationDAO = new OrganizationDAO();
		OrganizationResource result = null;
		
		try {
			Organization org = organizationDAO.findById(Long.parseLong(rowId));
			if (org != null) {
				result = ResourceUtil.getOrganizationFromDAO(org);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/organization/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOrganization(@QueryParam("rowId") String rowId) {		
		OrganizationDAO organizationDAO = new OrganizationDAO();
		
		try {
			long id = Long.parseLong(rowId);
			Organization organization = organizationDAO.findById(id);
			if (organization != null) {
				organizationDAO.delete(organization);
				ResourceUtil.deleteContactInfo(organization.getContactinfo());
				ResourceUtil.deleteAddress(organization.getAddress());
				ResourceUtil.deletePerson(organization.getPerson());
				ResourceUtil.deleteContactInfo(organization.getPerson().getContactinfo());
				organizationDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/organization/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyOrganization(MultivaluedMap<String, String> formData) {
		OrganizationDAO organizationDAO = new OrganizationDAO();
		Organization organization = new Organization();
		Address address = new Address();
		Contactinfo contactInfo = new Contactinfo();
		Person person = new Person();
		Contactinfo personContactinfo = new Contactinfo();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				organization = organizationDAO.findById(Long.parseLong(rowId));
				if (organization == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
				address = organization.getAddress();
				contactInfo = organization.getContactinfo();
				person = organization.getPerson();
				personContactinfo = person.getContactinfo();
			} 
			
			organization.setOrgname(ResourceUtil.getFormDataValue(formData, "orgname"));
			
			ResourceUtil.saveAddress(formData, address);
			organization.setAddress(address);
			
			ResourceUtil.saveContactInfo(formData, contactInfo);
			organization.setContactinfo(contactInfo);
			
			ResourceUtil.savePerson(formData, person, personContactinfo);
			organization.setPerson(person);
			
			organizationDAO.save(organization);
			organizationDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}

	@GET
	@Path("/project/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjects() {
		ProjectmasterDAO projectDAO = new ProjectmasterDAO();
		List<ProjectResource> result = new ArrayList<ProjectResource>();
		
		try {
			List<Projectmaster> allprojects = projectDAO.findAll();
			for (Projectmaster project : allprojects) {
				result.add(ResourceUtil.getProjectFromDAO(project));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/project/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProject(@QueryParam("rowId") String rowId) {
		ProjectmasterDAO projectDAO = new ProjectmasterDAO();
		ProjectResource result = null;
		
		try {
			Projectmaster project = projectDAO.findById(Long.parseLong(rowId));
			if (project != null) {
				result = ResourceUtil.getProjectFromDAO(project);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/project/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProject(@QueryParam("rowId") String rowId) {		
		ProjectmasterDAO projectDAO = new ProjectmasterDAO();
		
		try {
			Projectmaster project = projectDAO.findById(Long.parseLong(rowId));
			if (project != null) {
				projectDAO.delete(project);
				ResourceUtil.deleteAddress(project.getAddressByAddress());
				ResourceUtil.deleteAddress(project.getAddressByBankaddress());
				projectDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/project/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyProject(MultivaluedMap<String, String> formData) {
		ProjectmasterDAO projectDAO = new ProjectmasterDAO();
		Projectmaster project = new Projectmaster();
		Address address = new Address();
		Address bankAddress = new Address();
		
		try {
			Organization organization = ResourceUtil.getOrganizationPOJO(formData);
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				project = projectDAO.findById(Long.parseLong(rowId));
				if (project == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} 
			
			ResourceUtil.saveAddress(formData, address);
			project.setAddressByAddress(address);
			
			ResourceUtil.saveBankAddress(formData, bankAddress);
			project.setAddressByBankaddress(bankAddress);
			
			project.setProjectname(ResourceUtil.getFormDataValue(formData, "name"));
			project.setProjectdescription(ResourceUtil.getFormDataValueAsClob(formData, "description"));
			project.setOrganization(organization);
			project.setTotalphases(ResourceUtil.getFormDataValueAsLong(formData, "totalphases"));
			
			project.setBankname(ResourceUtil.getFormDataValue(formData, "bankname"));
			project.setAccountnumber(ResourceUtil.getFormDataValue(formData, "accountnumber"));
			project.setAccountholdername(ResourceUtil.getFormDataValue(formData, "accountholdername"));
			project.setIfsccode(ResourceUtil.getFormDataValue(formData, "ifsccode"));
			project.setMicrcode(ResourceUtil.getFormDataValue(formData, "micrcode"));
			project.setTermsandconditions(ResourceUtil.getFormDataValueAsClob(formData, "termsandconditions"));
			
			projectDAO.save(project);
			projectDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/projectphase/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectPhases() {
		ProjectphaseDAO projectphaseDAO = new ProjectphaseDAO();
		List<ProjectPhaseResource> result = new ArrayList<ProjectPhaseResource>();
		
		try {
			List<Projectphase> allprojectphases = projectphaseDAO.findAll();
			for (Projectphase projectPhase : allprojectphases) {
				result.add(ResourceUtil.getProjectPhaseFromDAO(projectPhase));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/projectphase/get/byproject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectPhasesForProject(@QueryParam("projectId") String projectId) {
		ProjectphaseDAO projectphaseDAO = new ProjectphaseDAO();
		List<ProjectPhaseResource> result = new ArrayList<ProjectPhaseResource>();
		
		try {
			List<Projectphase> allprojectphases = projectphaseDAO.findByProject(Long.parseLong(projectId));
			for (Projectphase projectPhase : allprojectphases) {
				result.add(ResourceUtil.getProjectPhaseFromDAO(projectPhase));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/projectphase/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectPhase(@QueryParam("rowId") String rowId) {
		ProjectphaseDAO projectphaseDAO = new ProjectphaseDAO();
		ProjectPhaseResource result = null;
		
		try {
			Projectphase projectPhase = projectphaseDAO.findById(Long.parseLong(rowId));
			if (projectPhase != null) {
				result = ResourceUtil.getProjectPhaseFromDAO(projectPhase);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/projectphase/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProjectPhase(@QueryParam("rowId") String rowId) {		
		ProjectphaseDAO projectphaseDAO = new ProjectphaseDAO();
		
		try {
			Projectphase projectphase = projectphaseDAO.findById(Long.parseLong(rowId));
			if (projectphase != null) {
				projectphaseDAO.delete(projectphase);
				projectphaseDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/projectphase/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyProjectPhase(MultivaluedMap<String, String> formData) {
		ProjectphaseDAO projectphaseDAO = new ProjectphaseDAO();
		Projectphase projectphase = new Projectphase();
		
		try {
			Projectmaster project = ResourceUtil.getProjectPOJO(formData);
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				projectphase = projectphaseDAO.findById(Long.parseLong(rowId));
				if (projectphase == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} 
			
			projectphase.setProjectphasename(ResourceUtil.getFormDataValue(formData, "name"));
			projectphase.setProjectphasedescription(ResourceUtil.getFormDataValueAsClob(formData, "description"));
			projectphase.setProjectmaster(project);
			projectphaseDAO.save(projectphase);
			projectphaseDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/projectbuilding/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectBuildingsForProject(@QueryParam("projectId") String projectId) {
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		List<ProjectBuildingResource> result = new ArrayList<ProjectBuildingResource>();
		
		try {
			List<Projectbuilding> allprojectbuildings = projectbuildingDAO.findByProject(Long.parseLong(projectId));
			for (Projectbuilding projectBuilding : allprojectbuildings) {
				result.add(ResourceUtil.getProjectBuildingFromDAO(projectBuilding));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/projectbuilding/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectBuilding(@QueryParam("rowId") String rowId) {
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		ProjectBuildingResource result = null;
		
		try {
			Projectbuilding projectBuilding = projectbuildingDAO.findById(Long.parseLong(rowId));
			if (projectBuilding != null) {
				result = ResourceUtil.getProjectBuildingFromDAO(projectBuilding);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/projectbuilding/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProjectBuilding(@QueryParam("rowId") String rowId) {		
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		
		try {
			Projectbuilding projectbuilding = projectbuildingDAO.findById(Long.parseLong(rowId));
			if (projectbuilding != null) {
				projectbuildingDAO.delete(projectbuilding);
				projectbuildingDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/projectbuilding/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyProjectBuilding(MultivaluedMap<String, String> formData) {
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		Projectbuilding projectbuilding = new Projectbuilding();
		
		try {
			Projectphase projectPhase = ResourceUtil.getProjectPhasePOJO(formData);
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				projectbuilding = projectbuildingDAO.findById(Long.parseLong(rowId));
				if (projectbuilding == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
				projectbuilding.setCurrentstatus(ResourceUtil.getFormDataValueAsInt(formData, "currentstatus"));
				projectbuilding.setExpectedcompletiondate(ResourceUtil.getFormDataValueAsDate(formData, "expectedcompletiondate"));
			} 
			
			projectbuilding.setBuildingname(ResourceUtil.getFormDataValue(formData, "name"));
			projectbuilding.setBuildingtype(ResourceUtil.getFormDataValue(formData, "type"));
			projectbuilding.setFloorcount(ResourceUtil.getFormDataValueAsLong(formData, "floorcount"));
			projectbuilding.setProjectphase(projectPhase);
			projectbuilding.setRemarks(ResourceUtil.getFormDataValueAsClob(formData, "remarks"));
			projectbuilding.setWingname(ResourceUtil.getFormDataValue(formData, "wingname"));
			projectbuildingDAO.save(projectbuilding);
			projectbuildingDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/projectbuilding/get/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatusForBuilding(@QueryParam("rowId") String rowId) {
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			Projectbuilding projectbuilding = projectbuildingDAO.findById(Long.parseLong(rowId));
			int floorCount = (int)projectbuilding.getFloorcount();
			
			result.add(new CodeTableResource(0, "PLI", "Registration in progress"));
			result.add(new CodeTableResource(1, "PLI", "Plinth in progress"));
			for (int i=0; i<floorCount; i++) {
				int slab = i+1;
				result.add(new CodeTableResource(slab + 1, "SLB"+ slab, "Slab " + slab + "  in progress"));
			}
			result.add(new CodeTableResource(floorCount+2, "BRI", "Brickwork in progress"));
			result.add(new CodeTableResource(floorCount+3, "PLA", "Plastering in progress"));
			result.add(new CodeTableResource(floorCount+4, "FLO", "Flooring in progress"));
			result.add(new CodeTableResource(floorCount+5, "POS", "Ready for Possession"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@POST
	@Path("/projectbuilding/post/paymentschedule")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyProjectBuildingDefinePaymentSchedule(MultivaluedMap<String, String> formData) {
		try {
			Projectbuilding projectBuilding = ResourceUtil.getProjectBuildingPOJO(formData);
			
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_reg", "percentamount_reg", "date_reg", "desc_reg");
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_pli", "percentamount_pli", "date_pli", "desc_pli");
			
			int floorCount = (int)projectBuilding.getFloorcount();
			for (int i=0; i< floorCount; i++) {
				ResourceUtil.saveUnitPaymentSchedule(formData, "type_" + (i+1), "percentamount_" + (i+1), "date_" + (i+1), "desc_" + (i+1));
			}
			
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_bri", "percentamount_bri", "date_bri", "desc_bri");
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_pla", "percentamount_pla", "date_pla", "desc_pla");
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_flo", "percentamount_flo", "date_flo", "desc_flo");
			ResourceUtil.saveUnitPaymentSchedule(formData, "type_pos", "percentamount_pos", "date_pos", "desc_pos");
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/unit/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUnitsForBuilding(@QueryParam("buildingId") String buildingId) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		List<UnitResource> result = new ArrayList<UnitResource>();
		
		try {
			List<Unitmaster> allUnits = unitmasterDAO.findByProjectBuilding(Long.parseLong(buildingId));
			for (Unitmaster unit : allUnits) {
				result.add(ResourceUtil.getUnitFromDAO(unit));
			} 
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/unit/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnit(@QueryParam("rowId") String rowId) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitResource result = null;
		
		try {
			Unitmaster unit = unitmasterDAO.findById(Long.parseLong(rowId));
			if (unit != null) {
				result = ResourceUtil.getUnitFromDAO(unit);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/unit/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUnit(@QueryParam("rowId") String rowId) {		
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitamenityDAO unitamenityDAO = new UnitamenityDAO();
		
		try {
			Unitmaster unit = unitmasterDAO.findById(Long.parseLong(rowId));
			if (unit != null) {
				Iterator<Unitamenity> iterator = unit.getUnitamenities().iterator();
				while(iterator.hasNext()) {
					unitamenityDAO.delete(iterator.next()); 
				}
				
				unitmasterDAO.delete(unit);
				unitmasterDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/unit/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnit(MultivaluedMap<String, String> formData) {
		Unitmaster unit = null;
		
		try {
			Projectbuilding projectBuilding = ResourceUtil.getProjectBuildingPOJO(formData);
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			String unitNumber = ResourceUtil.getFormDataValue(formData, "number");
			int floorNumber = ResourceUtil.getFormDataValueAsInt(formData, "floornumber");
			int floorType = floorNumber%2;
			
			if (rowId != null && rowId.length() > 0) {
				UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
				unit = unitmasterDAO.findById(Long.parseLong(rowId));
				if (unit == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
				ResourceUtil.saveUnit(formData, unit, projectBuilding, unitNumber, floorNumber, floorType);
			} else {
				boolean createmultipleeven = ResourceUtil.getFormDataValueAsBoolean(formData, "createmultipleeven");
				boolean createmultipleodd = ResourceUtil.getFormDataValueAsBoolean(formData, "createmultipleodd");
				int floorCount = (int)projectBuilding.getFloorcount();
				String unitNumberSuffix = unitNumber.substring(1);
				
				ResourceUtil.saveUnit(formData, null, projectBuilding, unitNumber, floorNumber, floorType);
				
				if (createmultipleeven) {
					for (int i=1; i<=floorCount; i++) {
						unitNumber = i +  unitNumberSuffix;
						if (i%2 == 0 && i != floorNumber) {
							ResourceUtil.saveUnit(formData, null, projectBuilding, unitNumber, i, 0);
						} 
					}
				}
				
				if (createmultipleodd) {
					for (int i=1; i<=floorCount; i++) {
						unitNumber = i +  unitNumberSuffix;
						if (i%2 != 0 && i != floorNumber) {
							ResourceUtil.saveUnit(formData, null, projectBuilding, unitNumber, i, 1);
						}
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/unit/get/floortype")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFloorTypeForUnit() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			result.add(new CodeTableResource(0, "EVEN", "Even"));
			result.add(new CodeTableResource(1, "ODD", "Odd"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}

	@GET
	@Path("/unit/get/pricedetail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPriceDetail(@QueryParam("rowId") String rowId, @QueryParam("discount") String discount, 
			@QueryParam("deductiononothercharges") String deductiononothercharges) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitPriceDetailResource result = null;
		
		try {
			Unitmaster unit = unitmasterDAO.findById(Long.parseLong(rowId));
			if (unit != null) {
				result = ResourceUtil.getUnitPriceDetailResource(unit, Long.parseLong(discount), Long.parseLong(deductiononothercharges));
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}

	@GET
	@Path("/unit/get/paymentschedule")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPaymentScheduleDetails(@QueryParam("rowId") String rowId) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitPaymentScheduleDetailsResource result = null;
		
		try {
			Unitmaster unit = unitmasterDAO.findById(Long.parseLong(rowId));
			if (unit != null) {
				Projectbuilding projectbuilding = unit.getProjectbuilding();
				UnitPriceDetailResource priceDetails = ResourceUtil.getUnitPriceDetailResource(unit, 0, 0);
				Set<Unitpaymentschedule> paymentSchedules = projectbuilding.getUnitpaymentschedules();
				Set<UnitPaymentScheduleResource> scheduleList = new TreeSet<UnitPaymentScheduleResource>(new ScheduleComp());
				double totalCost = (unit.getTotalcost() == null) ? 0 : unit.getTotalcost();
				double bookingAmount =  (unit.getBookingamount() == null) ? 0.0 : unit.getBookingamount();
				
				for (Unitpaymentschedule paymentSchedule : paymentSchedules) {
					double percentAmount = paymentSchedule.getPercentamount();
					double amount = (percentAmount*totalCost) / 100;
					
					if (paymentSchedule.getPaymentscheduletype().equalsIgnoreCase("Registration payment")) {
						amount -= bookingAmount; 
					}
					UnitPaymentScheduleResource paymentScheduleRes = ResourceUtil.getUnitPaymentScheduleFromDAO(paymentSchedule);
					paymentScheduleRes.setAmount(amount);
					scheduleList.add(paymentScheduleRes);
				}
				
				result = new UnitPaymentScheduleDetailsResource(unit.getUnitid(), unit.getUnitnumber(), priceDetails.getDisplayProjectInfo(), 
						bookingAmount, priceDetails.getAgreementvalue(), priceDetails.getTotalTax(), priceDetails.getTotalCost(), 
						scheduleList);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	class ScheduleComp implements Comparator<UnitPaymentScheduleResource>{
		 
		@Override
		public int compare(UnitPaymentScheduleResource o1,
				UnitPaymentScheduleResource o2) {
			return (o1.getId() > o2.getId()) ? 1 : -1;
		}
	}  
	
	@POST
	@Path("/unit/post/floorrise")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnitsUpdateFloorRise(MultivaluedMap<String, String> formData) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		boolean updateMade = false;
		
		try {
			Projectbuilding projectBuilding = ResourceUtil.getProjectBuildingPOJO(formData);
			List<Unitmaster> units = unitmasterDAO.findUnbookedByProjectBuilding(projectBuilding.getProjectbuildingid());
			
			for (Unitmaster unit : units) {
				Unitpricepolicy unitpricepolicy = unit.getUnitpricepolicy();
				
				int floorNumber = unit.getFloornumber();
				double floorRise = ResourceUtil.getFormDataValueAsDouble(formData, "floorrise_" + floorNumber);
				double unitFloorRise = (unit.getFloorrise() == null) ? 0 : unit.getFloorrise();
				if (unitpricepolicy != null && floorRise != unitFloorRise) {
					updateMade = true;
					ResourceUtil.saveUnitPriceInformation(unit, unitpricepolicy, floorRise);
					unit.setFloorrise(floorRise);
					unitmasterDAO.save(unit);
				} 
			}
			
			if (!updateMade) {
				return Response.status(Response.Status.NOT_IMPLEMENTED).entity(new ApplicationException("No updates has been made. "
						+ "This could be due to the fact that no unit of building qualifies for updation of floor rise.")).build();
			}
			unitmasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@POST
	@Path("/unit/post/unitpricepolicy")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnitsUpdatePricePolicy(MultivaluedMap<String, String> formData) {
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		
		try {
			Projectbuilding projectBuilding = ResourceUtil.getProjectBuildingPOJO(formData);
			Unitpricepolicy unitpricepolicy = ResourceUtil.getUnitpricepolicyPOJO(formData);
			List<Unitmaster> units = unitmasterDAO.findUnbookedByProjectBuilding(projectBuilding.getProjectbuildingid());
			
			if (units.size() == 0) {
				return Response.status(Response.Status.NOT_IMPLEMENTED).entity(new ApplicationException("No updates has been made. "
						+ "Building does not have any units defined.")).build();
			}
			for (Unitmaster unit : units) {
				unit.setUnitpricepolicy(unitpricepolicy);
				double floorRise = (unit.getFloorrise() == null) ? 0 : unit.getFloorrise();
				ResourceUtil.saveUnitPriceInformation(unit, unitpricepolicy, floorRise);
				unitmasterDAO.save(unit);
			}
			
			unitmasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/unitpricepolicy/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPricePolicies() {
		UnitpricepolicyDAO unitpricepolicyDAO = new UnitpricepolicyDAO();
		List<UnitPricePolicyResource> result = new ArrayList<UnitPricePolicyResource>();
		
		try {
			List<Unitpricepolicy> unitPricePolicyList = unitpricepolicyDAO.findAll();
			for (Unitpricepolicy unitPricePolicy : unitPricePolicyList) {
				result.add(ResourceUtil.getUnitPricePolicyFromDAO(unitPricePolicy));
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/unitpricepolicy/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPricePolicy(@QueryParam("rowId") String rowId) {
		UnitpricepolicyDAO unitpricepolicyDAO = new UnitpricepolicyDAO();
		UnitPricePolicyResource result = null;
		
		try {
			Unitpricepolicy unitPricePolicy = unitpricepolicyDAO.findById(Long.parseLong(rowId));
			if (unitPricePolicy != null) {
				result = ResourceUtil.getUnitPricePolicyFromDAO(unitPricePolicy);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/unitpricepolicy/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUnitPricePolicy(@QueryParam("rowId") String rowId) {		
		UnitpricepolicyDAO unitpricepolicyDAO = new UnitpricepolicyDAO();
		
		try {
			Unitpricepolicy unitPricePolicy = unitpricepolicyDAO.findById(Long.parseLong(rowId));
			if (unitPricePolicy != null) {
				unitpricepolicyDAO.delete(unitPricePolicy);
				unitpricepolicyDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/unitpricepolicy/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnitPricePolicy(MultivaluedMap<String, String> formData) {
		UnitpricepolicyDAO unitpricepolicyDAO = new UnitpricepolicyDAO();
		Unitpricepolicy  unitPricePolicy = new Unitpricepolicy();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			String policyName = ResourceUtil.getFormDataValue(formData, "policyname");
			
			if (rowId != null && rowId.length() > 0) {
				unitPricePolicy = unitpricepolicyDAO.findById(Long.parseLong(rowId));
				if (unitPricePolicy == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} else {
				if (unitpricepolicyDAO.findByPolicyName(policyName) != null) {
					return Response.status(Response.Status.CONFLICT).entity(new ApplicationException("Price policy with name " + policyName + " already exists.")).build();
				}
			}
			
			unitPricePolicy.setPolicyname(policyName);
			unitPricePolicy.setBaserate(ResourceUtil.getFormDataValueAsDouble(formData, "baserate"));
			
			unitPricePolicy.setServicetax(ResourceUtil.getFormDataValueAsDouble(formData, "servicetax"));
			unitPricePolicy.setValueaddedtax(ResourceUtil.getFormDataValueAsDouble(formData, "valueaddedtax"));
			unitPricePolicy.setStampduty(ResourceUtil.getFormDataValueAsDouble(formData, "stampduty"));
			unitPricePolicy.setRegistrationcharge(ResourceUtil.getFormDataValueAsDouble(formData, "registrationcharge"));
			
			unitPricePolicy.setMaintenancecharge1(ResourceUtil.getFormDataValueAsDouble(formData, "maintenancecharge1"));
			unitPricePolicy.setMaintenancecharge2(ResourceUtil.getFormDataValueAsDouble(formData, "maintenancecharge2"));
			unitPricePolicy.setLegalcharge(ResourceUtil.getFormDataValueAsDouble(formData, "legalcharge"));
			
			unitpricepolicyDAO.save(unitPricePolicy);
			unitpricepolicyDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();	
	}
	
	@GET
	@Path("/unitpaymentschedule/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPaymentScheduleForBuilding(@QueryParam("buildingId") String buildingId) {
		UnitpaymentscheduleDAO unitpaymentscheduleDAO = new UnitpaymentscheduleDAO();
		List<UnitPaymentScheduleResource> result = new ArrayList<UnitPaymentScheduleResource>();
		
		try {
			List<Unitpaymentschedule> unitpaymentscheduleList = unitpaymentscheduleDAO.findByProjectBuilding(Long.parseLong(buildingId));
			if (unitpaymentscheduleList != null) {
				for (Unitpaymentschedule paymentSchedule : unitpaymentscheduleList) {
					result.add(ResourceUtil.getUnitPaymentScheduleFromDAO(paymentSchedule));
				}
			}
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/unitpaymentschedule/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitPaymentSchedule(@QueryParam("rowId") String rowId) {
		UnitpaymentscheduleDAO unitpaymentscheduleDAO = new UnitpaymentscheduleDAO();
		UnitPaymentScheduleResource result = null;
		
		try {
			Unitpaymentschedule paymentSchedule = unitpaymentscheduleDAO.findById(Long.parseLong(rowId));
			if (paymentSchedule != null) {
				result = ResourceUtil.getUnitPaymentScheduleFromDAO(paymentSchedule);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/unitpaymentschedule/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUnitPaymentSchedule(@QueryParam("rowId") String rowId) {		
		UnitpaymentscheduleDAO unitpaymentscheduleDAO = new UnitpaymentscheduleDAO();
		
		try {
			Unitpaymentschedule paymentSchedule = unitpaymentscheduleDAO.findById(Long.parseLong(rowId));
			if (paymentSchedule != null) {
				unitpaymentscheduleDAO.delete(paymentSchedule);
				unitpaymentscheduleDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/unitpaymentschedule/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnitPaymentSchedule(MultivaluedMap<String, String> formData) {
		UnitpaymentscheduleDAO unitpaymentscheduleDAO = new UnitpaymentscheduleDAO();
		Unitpaymentschedule paymentSchedule = new Unitpaymentschedule();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			Projectbuilding projectbuilding = ResourceUtil.getProjectBuildingPOJO(formData);
			if (rowId != null && rowId.length() > 0) {
				paymentSchedule = unitpaymentscheduleDAO.findById(Long.parseLong(rowId));
				if (paymentSchedule == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} 
			
			String paymentScheduleType = ResourceUtil.getFormDataValue(formData, "type");
			paymentSchedule.setPaymentscheduleposition(ResourceUtil.getPaymentSchedulePosition(paymentScheduleType, (int)projectbuilding.getFloorcount()));
			paymentSchedule.setPaymentscheduledate(ResourceUtil.getFormDataValueAsDate(formData, "scheduledate"));
			paymentSchedule.setPaymentscheduledescription(ResourceUtil.getFormDataValueAsClob(formData, "description"));
			paymentSchedule.setPaymentscheduletype(paymentScheduleType);
			paymentSchedule.setPercentamount(ResourceUtil.getFormDataValueAsDouble(formData, "percentamount"));
			paymentSchedule.setProjectbuilding(projectbuilding);
			
			unitpaymentscheduleDAO.save(paymentSchedule);
			unitpaymentscheduleDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/unitbooking/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllActiveUnitbookings(@QueryParam("buildingId") String buildingId) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		List<UnitBookingResource> result = new ArrayList<UnitBookingResource>();

		try {
			List<Unitbooking> unitbookingList = unitbookingDAO.findAllBookingsByCancelFlag(false, Long.parseLong(buildingId));
			for (Unitbooking unitbooking : unitbookingList) {
				result.add(ResourceUtil.getUnitbookingFromDAO(unitbooking));
			}
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/cancelledbooking/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCancelledUnitbookings(@QueryParam("buildingId") String buildingId) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		List<UnitBookingResource> result = new ArrayList<UnitBookingResource>();

		try {
			List<Unitbooking> unitbookingList = unitbookingDAO.findAllBookingsByCancelFlag(true, Long.parseLong(buildingId));
			for (Unitbooking unitbooking : unitbookingList) {
				result.add(ResourceUtil.getUnitbookingFromDAO(unitbooking));
			}
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/unitbooking/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitbooking(@QueryParam("rowId") String rowId) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		UnitBookingResource result = null;
		
		try {
				Unitbooking unitbooking = unitbookingDAO.findById(Long.parseLong(rowId));
				if (unitbooking != null) {
					result = ResourceUtil.getUnitbookingFromDAO(unitbooking);
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		return Response.ok(result).build();
	}
	
	@GET
	@Path("/unitbooking/get/print")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitbookingForPrint(@QueryParam("rowId") String rowId) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		PrintBookingResource result = null;
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		CustomerResource customer = null;
		List<PaymentResource> paymentList = new ArrayList<PaymentResource>();
		Set<UnitPaymentScheduleResource> scheduleList = new TreeSet<UnitPaymentScheduleResource>(new ScheduleComp());
		
		try {
				Unitbooking unitbooking = unitbookingDAO.findById(Long.parseLong(rowId));
				if (unitbooking != null) {
					Unitmaster unit = unitbooking.getUnitmaster();
					Projectbuilding projectbuilding = unit.getProjectbuilding();
					Projectmaster project = projectbuilding.getProjectphase().getProjectmaster();
					Organization org = projectbuilding.getProjectphase().getProjectmaster().getOrganization();
					Set<Unitpaymentschedule> paymentSchedules = projectbuilding.getUnitpaymentschedules();
					Usermaster user = unitbooking.getUsermasterByBookedby();
					double bookingDiscount = (unitbooking.getBookingdiscount() == null) ? 0 : unitbooking.getBookingdiscount();
					double deductionOnOtherCharges = (unitbooking.getDeductiononothercharges() == null) ? 0 : unitbooking.getDeductiononothercharges();
					double totalCost = (unit.getTotalcost() == null) ? 0 : unit.getTotalcost();
					double bookingAmount =  (unit.getBookingamount() == null) ? 0.0 : unit.getBookingamount();
					UnitPriceDetailResource priceWithDiscount = ResourceUtil.getUnitPriceDetailResource(unit, bookingDiscount, deductionOnOtherCharges);
					CodeTableResource buildingCurrentStatus = ResourceUtil.getBuildingCurrentStatus(projectbuilding);
					
					Customermaster customerPOJO = unitbooking.getCustomermaster();
					if (customerPOJO != null) {
						customer = ResourceUtil.getCustomerFromDAO(customerPOJO);
					}
					
					List<Paymentmaster> payments = paymentmasterDAO.findByUnitbooking(unitbooking.getUnitbookingid());
					if (payments != null) {
						for (Paymentmaster payment : payments) {
							paymentList.add(ResourceUtil.getPaymentFromDAO(payment));
						}
					}
					
					for (Unitpaymentschedule paymentSchedule : paymentSchedules) {
						double percentAmount = paymentSchedule.getPercentamount();
						double amount = (percentAmount*totalCost) / 100;
						
						if (paymentSchedule.getPaymentscheduletype().equalsIgnoreCase("Registration payment")) {
							amount -= bookingAmount; 
						}
						UnitPaymentScheduleResource paymentScheduleRes = ResourceUtil.getUnitPaymentScheduleFromDAO(paymentSchedule);
						paymentScheduleRes.setAmount(amount);
						scheduleList.add(paymentScheduleRes);
					}
					
					double totalPaymentReceived = ResourceUtil.getTotalPaymentReceivedForBooking(unitbooking);
					double balancePaymentForCurrentStatus = ResourceUtil.getBalancePaymentForCurrentStatus(scheduleList, bookingAmount, totalPaymentReceived,
							unitbooking, (int)buildingCurrentStatus.getId());
					
					String displayBankInformation = project.getAccountholdername() + ", " + project.getBankname();
					displayBankInformation += ", Account # " + project.getAccountnumber();
					displayBankInformation += ", IFSC Code " + project.getIfsccode();
					displayBankInformation += ", MICR Code " + project.getMicrcode();
					displayBankInformation += ", " + ResourceUtil.getDisplayAddressFromAddressResource(ResourceUtil.getAddressFromDAO(project.getAddressByBankaddress()));
					String termsAndConditions = ResourceUtil.convertClobToString(project.getTermsandconditions());		
							
					result = new PrintBookingResource(unitbooking.getUnitbookingid(), unitbooking.getBookingformnumber(), 
							ResourceUtil.getUserDisplayName(user), ResourceUtil.convertDateToString(unitbooking.getBookingdate()), 
							buildingCurrentStatus, balancePaymentForCurrentStatus, 
							ResourceUtil.getOrganizationFromDAO(org), customer, ResourceUtil.getUnitFromDAO(unit), bookingDiscount, deductionOnOtherCharges,
							ResourceUtil.convertClobToString(unitbooking.getBookingcomment()), priceWithDiscount, paymentList, totalPaymentReceived, 
							displayBankInformation, termsAndConditions, scheduleList);
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/unitbooking/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUnitbooking(@QueryParam("rowId") String rowId) {		
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		
		try {
			Unitbooking unitbooking = unitbookingDAO.findById(Long.parseLong(rowId));
			if (unitbooking != null) {
				unitbookingDAO.delete(unitbooking);
				unitbookingDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/unitbooking/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBooking(MultivaluedMap<String, String> formData) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		Unitbooking unitbooking = new Unitbooking();
		UnitBookingResource result = null;
		
		try {
			Unitmaster unit = ResourceUtil.getUnitPOJO(formData);
			Usermaster user = ResourceUtil.getBookingUserPOJO(formData);
			Customermaster customer = ResourceUtil.getCustomerPOJO(formData);
			
			Unitbooking existingBooking = unitbookingDAO.findByUnit(unit);
			if (existingBooking != null) {
				String errorMessage = "Unit " + ResourceUtil.getUnitDisplayName(unit)
						+ " is already booked by " + ResourceUtil.getCustomerDisplayName(existingBooking.getCustomermaster());
				return Response.status(Response.Status.CONFLICT).entity(new ApplicationException(errorMessage)).build();
			}
			
			long bookingFormNumber = unitbookingDAO.getMaxBookingFormNumber();
			if (bookingFormNumber == -1) {
				bookingFormNumber = 10000; 
			} else {
				bookingFormNumber++;
			}
			unitbooking.setBookingdate(Calendar.getInstance().getTime());
			unitbooking.setBookingformnumber(bookingFormNumber);
			unitbooking.setUnitmaster(unit );
			unitbooking.setCustomermaster(customer);
			unitbooking.setUsermasterByBookedby(user);;
			unitbooking.setBookingcomment(ResourceUtil.getFormDataValueAsClob(formData, "comment"));
			unitbooking.setBookingdiscount(ResourceUtil.getFormDataValueAsDouble(formData, "discount"));
			unitbooking.setDeductiononothercharges(ResourceUtil.getFormDataValueAsDouble(formData, "deductiononothercharges"));
			unitbooking.setIscancelled(false);
			
			unitbookingDAO.save(unitbooking);
			unitbookingDAO.flushSession();
			
			result = ResourceUtil.getUnitbookingFromDAO(unitbooking);
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		} 
	    return Response.ok(result).build();
	}
	
	@POST
	@Path("/unitbooking/post/cancel")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelBooking(MultivaluedMap<String, String> formData) {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		RefundmasterDAO refundmasterDAO = new RefundmasterDAO();
		Refundmaster refundmaster = new Refundmaster();
		
		try {
			Unitbooking unitbooking = ResourceUtil.getUnitbookingPOJO(formData);
			UnitBookingResource unitBookingResource = ResourceUtil.getUnitbookingFromDAO(unitbooking);
			Usermaster user = ResourceUtil.getUserPOJO(formData);
			double deduction = ResourceUtil.getFormDataValueAsDouble(formData, "canceldeduction");
			double refundamount = unitBookingResource.getTotalPaymentReceived() - unitBookingResource.getTotalUnitCostWithDiscount()* (deduction/100);
			
			if (refundamount > 0) {
				refundmaster.setBankname(ResourceUtil.getFormDataValue(formData, "bankname"));
				refundmaster.setBankbranch(ResourceUtil.getFormDataValue(formData, "bankbranch"));
				refundmaster.setChequenumber(ResourceUtil.getFormDataValue(formData, "chequenumber"));
				refundmaster.setChequedate(ResourceUtil.getFormDataValueAsDate(formData, "chequedate"));
				refundmaster.setRefundamount(refundamount);
				refundmaster.setRefunddate(Calendar.getInstance().getTime());
				refundmaster.setUnitbooking(unitbooking);
				refundmasterDAO.save(refundmaster);
			}
			
			unitbooking.setCanceldeduction(deduction);
			unitbooking.setCancellationcomment(ResourceUtil.getFormDataValueAsClob(formData, "cancellationcomment"));
			unitbooking.setCancellationdate(Calendar.getInstance().getTime());
			unitbooking.setIscancelled(true);
			unitbooking.setUsermasterByCancelledby(user);
			
			unitbookingDAO.save(unitbooking);
			unitbookingDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		} 
	    return Response.ok().build();
	}
	
	@GET
	@Path("/payment/get/receiptnumber")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReceiptNumberForPayment() {
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		long receiptNumber = paymentmasterDAO.getMaxReceiptNumber();
		if (receiptNumber == -1) {
			receiptNumber = 10000; 
		} else {
			receiptNumber++;
		}
		
		return Response.ok(receiptNumber).build();
	}
	
	@GET
	@Path("/payment/get/states")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPaymentStates() {
		List<PaymentStateResource> result = new ArrayList<PaymentStateResource>();
		PaymentstateDAO paymentstateDAO = new PaymentstateDAO();
		
		List<Paymentstate> allPaymentStates = paymentstateDAO.findAll(); 
		for (Paymentstate aPaymentState : allPaymentStates) {
			result.add(new PaymentStateResource(aPaymentState.getPaymentstateid(), aPaymentState.getPaymentstatename(), aPaymentState.isAllowstatechange()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/payment/get/stages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPaymentStages(@QueryParam("rowId") String rowId) {
		ProjectbuildingDAO projectbuildingDAO = new ProjectbuildingDAO();
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			Projectbuilding projectbuilding = projectbuildingDAO.findById(Long.parseLong(rowId));
			int floorCount = (int)projectbuilding.getFloorcount();
			
			result.add(new CodeTableResource(0, "REG", "Registration Payment"));
			result.add(new CodeTableResource(1, "PLI", "Plinth Payment"));
			for (int i=0; i<floorCount; i++) {
				int slab = i+1;
				result.add(new CodeTableResource(slab + 1, "SLB"+ slab, "Slab " + slab + "  payment"));
			}
			result.add(new CodeTableResource(floorCount+2, "BRI", "Brickwork payment"));
			result.add(new CodeTableResource(floorCount+3, "PLA", "Plastering payment"));
			result.add(new CodeTableResource(floorCount+4, "FLO", "Flooring payment"));
			result.add(new CodeTableResource(floorCount+5, "POS", "Possession payment"));
			result.add(new CodeTableResource(floorCount+6, "TAX1", "Stampduty payment"));
			result.add(new CodeTableResource(floorCount+7, "TAX2", "Registration charges payment"));
			result.add(new CodeTableResource(floorCount+8, "TAX3", "Service tax payment"));
			result.add(new CodeTableResource(floorCount+9, "TAX4", "MVAT payment"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@GET
	@Path("/payment/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentForUnitbooking(@QueryParam("unitbookingId") String unitbookingId) {
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		List<PaymentResource> result = new ArrayList<PaymentResource>();
		
		try {
			List<Paymentmaster> payments = paymentmasterDAO.findByUnitbooking(Long.parseLong(unitbookingId));
			if (payments != null && payments.size() > 0) {
				for (Paymentmaster payment : payments) {
					result.add(ResourceUtil.getPaymentFromDAO(payment));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/payment/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPayment(@QueryParam("rowId") String rowId) {
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		PaymentResource result = null;
		
		try {
			Paymentmaster payment = paymentmasterDAO.findById(Long.parseLong(rowId));
			if (payment != null) {
				result = ResourceUtil.getPaymentFromDAO(payment);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/payment/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePayment(@QueryParam("rowId") String rowId) {		
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		PaymentstageDAO paymentstageDAO = new PaymentstageDAO();
		
		try {
			Paymentmaster payment = paymentmasterDAO.findById(Long.parseLong(rowId));
			if (payment != null) {
				Iterator<Paymentstatus> iterator = payment.getPaymentstatuses().iterator();
				while(iterator.hasNext()) {
					paymentstatusDAO.delete(iterator.next());
				}
				
				Iterator<Paymentstage> iterator1 = payment.getPaymentstages().iterator();
				while(iterator.hasNext()) {
					paymentstageDAO.delete(iterator1.next()); 
				}
				
				paymentmasterDAO.delete(payment);
				paymentmasterDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/payment/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyPayment(MultivaluedMap<String, String> formData) {
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		Paymentmaster payment = new Paymentmaster();
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		PaymentstateDAO paymentstateDAO = new PaymentstateDAO();
		PaymentstageDAO paymentstageDAO = new PaymentstageDAO();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			Unitbooking unitbooking = ResourceUtil.getUnitbookingPOJO(formData);
			Usermaster user = ResourceUtil.getUserPOJO(formData);
			Paymenttype paymenttype = ResourceUtil.getPaymenttypePOJO(formData);
			
			if (rowId != null && rowId.length() > 0) {
				payment = paymentmasterDAO.findById(Long.parseLong(rowId));
				if (payment == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} 
			
			payment.setUnitbooking(unitbooking);
			payment.setUsermaster(user);
			
			payment.setReceiptnumber(ResourceUtil.getFormDataValueAsLong(formData, "receiptnumber"));
			payment.setAltreceiptnumber(ResourceUtil.getFormDataValueAsLong(formData, "altreceiptnumber"));
			payment.setPaymentamount(ResourceUtil.getFormDataValueAsDouble(formData, "receiptamount"));
			payment.setPaymentdescription(ResourceUtil.getFormDataValueAsClob(formData, "description"));
			payment.setPaymentreceiveddate(ResourceUtil.getFormDataValueAsDate(formData, "receiptdate"));
			payment.setPaymenttype(paymenttype);
			
			payment.setBankname(ResourceUtil.getFormDataValue(formData, "bankname"));
			payment.setBankbranch(ResourceUtil.getFormDataValue(formData, "bankbranch"));
			payment.setChequenumber(ResourceUtil.getFormDataValue(formData, "chequenumber"));
			payment.setChequedate(ResourceUtil.getFormDataValueAsDate(formData, "chequedate"));
			
			payment.setCardnumber(ResourceUtil.getFormDataValue(formData, "cardnumber"));
			payment.setCardexpirydate(ResourceUtil.getFormDataValue(formData, "cardexpirydate"));
			payment.setCardholdername(ResourceUtil.getFormDataValue(formData, "cardholdername"));
			payment.setCardtype(ResourceUtil.getFormDataValue(formData, "cardtype"));
			
			paymentmasterDAO.save(payment);
			
			String paymentStatusFormValue = ResourceUtil.getFormDataValue(formData, "paymentstatus");
			if (paymentStatusFormValue != null && paymentStatusFormValue.length() > 0) {
				Paymentstate paymentstate = paymentstateDAO.findById(ResourceUtil.getFormDataValueAsLong(formData, "paymentstatus"));
				Paymentstatus paymentstatus = paymentstatusDAO.findLatestByPaymentIdAndState(payment.getPaymentid(), paymentstate);
				if (paymentstatus == null) {
					paymentstatus = new Paymentstatus();
				}
				
				paymentstatus.setPaymentmaster(payment);
				paymentstatus.setPaymentstate(paymentstate);
				paymentstatus.setStatusdate(Calendar.getInstance().getTime());
				paymentstatus.setStatuscomment(ResourceUtil.getFormDataValueAsClob(formData, "paymentstatuscomment"));
				paymentstatus.setUsermaster(user);
				paymentstatusDAO.save(paymentstatus);
			}
			 
			 List<String> values = formData.get("paymentstage");
			 if (values != null && values.size()  > 0) {
				 for (String value : values) {
					 Paymentstage paymentstage = new Paymentstage();
					 paymentstage.setPaymentstagetype(Integer.parseInt(value));
					 paymentstage.setPaymentmaster(payment);
					 paymentstageDAO.save(paymentstage);
				 }
			 }
			 
			paymentmasterDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/enquiry/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEnquiries(@QueryParam("customerId") String customerId) {
		EnquiryDAO enquiryDAO = new EnquiryDAO();
		List<EnquiryResource> result = new ArrayList<EnquiryResource>();
		List<Enquiry> enquiryList = null;
		
		try {
			enquiryList = (customerId == null) ? enquiryDAO.findAll() : 
				enquiryDAO.findByCustomer(Long.parseLong(customerId));
			for (Enquiry enquiry : enquiryList) {
				result.add(ResourceUtil.getEnquiryFromDAO(enquiry));
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/enquiry/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEnquiry(@QueryParam("rowId") String rowId) {
		EnquiryDAO enquiryDAO = new EnquiryDAO();
		EnquiryResource result = null;
		
		try {
			Enquiry enquiry = enquiryDAO.findById(Long.parseLong(rowId));
			if (enquiry != null) {
				result = ResourceUtil.getEnquiryFromDAO(enquiry);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@GET
	@Path("/enquiry/get/budget")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBudgetForEnquiry() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			result.add(new CodeTableResource(0, "10-15LACS", "10 to 15 Lacs"));
			result.add(new CodeTableResource(1, "15-20LACS", "15 to 20 Lacs"));
			result.add(new CodeTableResource(2, "20-25LACS", "20 to 25 Lacs"));
			result.add(new CodeTableResource(3, "25-30LACS", "25 to 30 Lacs"));
			result.add(new CodeTableResource(4, "30-35LACS", "30 to 35 Lacs"));
			result.add(new CodeTableResource(5, "35-40LACS", "35 to 40 Lacs"));
			result.add(new CodeTableResource(6, "40-45LACS", "40 to 45 Lacs"));
			result.add(new CodeTableResource(7, "45-50LACS", "45 to 50 Lacs"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}

	@GET
	@Path("/enquiry/get/probability")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProbabilityForEnquiry() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			result.add(new CodeTableResource(0, "HOT", "Hot"));
			result.add(new CodeTableResource(1, "WARM", "Warm"));
			result.add(new CodeTableResource(2, "COLD", "Cold"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@GET
	@Path("/enquiry/get/currenthomestatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentHomeStatusForEnquiry() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		
		try {
			result.add(new CodeTableResource(0, "RENTED", "Rented"));
			result.add(new CodeTableResource(1, "OWNED", "Owned"));
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/enquiry/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEnquiry(@QueryParam("rowId") String rowId) {		
		EnquiryDAO enquiryDAO = new EnquiryDAO();
		EnquirycommentDAO enquirycommentDAO = new EnquirycommentDAO();
		
		try {
			Enquiry enquiry = enquiryDAO.findById(Long.parseLong(rowId));
			if (enquiry != null) {
				List<Enquirycomment> enquiryCommentList = enquirycommentDAO.findByEnquiry(enquiry.getEnquiryid());
				for (Enquirycomment enquirycomment : enquiryCommentList) {
					enquirycommentDAO.delete(enquirycomment);
				}
				enquiryDAO.delete(enquiry);
				enquiryDAO.flushSession();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
			}
		} catch (ConstraintViolationException cve) {
			logger.error("", cve);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new 
					ApplicationException("Record could not be deleted as it is being referenced by other data present on system. " + cve.getMessage())).build();
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
	    return Response.ok().build();
	}
	
	@POST
	@Path("/enquiry/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyEnquiry(MultivaluedMap<String, String> formData) {
		EnquiryDAO enquiryDAO = new EnquiryDAO();
		EnquirycommentDAO enquirycommentDAO = new EnquirycommentDAO();
		Enquiry enquiry = new Enquiry();
		Enquirycomment enquirycomment = new Enquirycomment();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			Sourcemaster source = ResourceUtil.getSourcePOJO(formData);
			Unittype interest = ResourceUtil.getUnittypePOJO(formData);
			Usermaster user = ResourceUtil.getUserPOJO(formData);
			Customermaster customer = ResourceUtil.getCustomerPOJO(formData);
			if (rowId != null && rowId.length() > 0) {
				enquiry = enquiryDAO.findById(Long.parseLong(rowId));
				if (enquiry == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
			} else {
				enquiry.setOriginalenquirydate(Calendar.getInstance().getTime());
				enquiry.setSourcemaster(source);
				enquiry.setUsermaster(user);
				enquiry.setCustomermaster(customer);
				enquiry.setBudget(ResourceUtil.getFormDataValueAsInt(formData, "budget"));
				enquiry.setCurrenthomestatus(ResourceUtil.getFormDataValueAsInt(formData, "currenthomestatus"));
				enquiry.setUnittype(interest);
				enquiry.setProbability(ResourceUtil.getFormDataValueAsInt(formData, "probability"));
				
				enquiryDAO.save(enquiry);
			}
			
			enquirycomment.setVisitdate(Calendar.getInstance().getTime());
			enquirycomment.setEnquiry(enquiry);
			enquirycomment.setVisitcomment(ResourceUtil.getFormDataValueAsClob(formData, "commenttext"));
			enquirycomment.setFollowupdate(ResourceUtil.getFormDataValueAsDate(formData, "followupdate"));
			enquirycomment.setUsermaster(user);
			
			enquirycommentDAO.save(enquirycomment);
			enquiryDAO.flushSession();
			
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/enquirycomment/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEnquiryComments(@QueryParam("enquiryId") String enquiryId) {
		EnquiryDAO enquiryDAO = new EnquiryDAO();
		EnquirycommentDAO enquirycommentDAO = new EnquirycommentDAO();
		List<EnquiryCommentResource> result = new ArrayList<EnquiryCommentResource>();
		
		try {	
			if (enquiryId != null && enquiryId.length() > 0) {
				Enquiry enquiry = enquiryDAO.findById(Long.parseLong(enquiryId));
				if (enquiry == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + enquiryId + " not found.")).build();
				}
				
				List<Enquirycomment> enquiryCommentList = enquirycommentDAO.findByEnquiry(enquiry.getEnquiryid());
				for (Enquirycomment enquirycomment : enquiryCommentList) {
					result.add(ResourceUtil.getEnquiryCommentFromDAO(enquirycomment));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}

		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
}