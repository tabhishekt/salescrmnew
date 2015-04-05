package com.propmgr.jersey;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import com.propmgr.dao.Person;
import com.propmgr.dao.Rolemaster;
import com.propmgr.dao.RolemasterDAO;
import com.propmgr.dao.Usermaster;
import com.propmgr.dao.UsermasterDAO;
import com.propmgr.dao.Userrole;
import com.propmgr.resource.LoginResponse;
import com.propmgr.resource.ResourceUtil;
import com.propmgr.resource.RoleResource;
 
 
@Path("/json/data/userrole")
public class UserRoleService {
	private final static Logger logger = Logger.getLogger(UserRoleService.class);
	
	@GET
	@Path("/validuser/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isValidUserCredentials(@QueryParam("loginName") String loginName, @QueryParam("password") String password) {
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		
		boolean isValid = usermasterDAO.isValidUserCredentials(loginName, password);
	    return Response.ok(isValid).build();
	}
	
	@POST
	@Path("/login/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@FormParam("username") String username, @FormParam("password") String password) {
		LoginResponse loginResponse = null;
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		
		try {
			Usermaster user = usermasterDAO.findByUserLoginCredentials(username, password);
			boolean isAdmin = false;
			boolean status = false;
			
	    	if (user != null) {
	    		status = true;
		    	Person person = user.getPerson();
		    	String userName = person.getFirstname() + " " + person.getMiddlename() + " " + person.getLastname();
		    	Set<Userrole> roles = user.getUserroles();
		    	for (Userrole role : roles) {
		    		if (role.getRolemaster().isIsadmin()) {
		    			isAdmin = true;
		    		}
		    	}
		    	loginResponse = new LoginResponse(user.getUsermasterid(), status, isAdmin, userName);
	    	} else {
	    		return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("User with id " + username + " and given password not found.")).build();
	    	}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
        
	    return Response.ok(loginResponse).build(); 
	}
	
	@GET
	@Path("/role/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCities() {
		List<RoleResource> result = new ArrayList<RoleResource>();
		RolemasterDAO rolemasterDAO = new RolemasterDAO();
		
		try {
			List<Rolemaster> allRoles = rolemasterDAO.findAll(); 
			for (Rolemaster role : allRoles) {
				result.add(new RoleResource(role.getRolemasterid(), role.getRolename(), 
						ResourceUtil.convertClobToString(role.getRoledescription()), role.isIsadmin()));
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/role/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRole(@QueryParam("rowId") String rowId) {
		RolemasterDAO rolemasterDAO = new RolemasterDAO();
		RoleResource result = null;
		
		try {
			Rolemaster role = rolemasterDAO.findById(Long.parseLong(rowId));
			if (role != null) {
				result = new RoleResource(role.getRolemasterid(), role.getRolename(), 
						ResourceUtil.convertClobToString(role.getRoledescription()), role.isIsadmin());
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
	@Path("/role/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRole(@QueryParam("rowId") String rowId) {		
		RolemasterDAO rolemasterDAO = new RolemasterDAO();
		
		try {
			Rolemaster role = rolemasterDAO.findById(Long.parseLong(rowId));
			if (role != null) {
				rolemasterDAO.delete(role);
				rolemasterDAO.flushSession();
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
	@Path("/role/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyRole(MultivaluedMap<String, String> formData) {
		RolemasterDAO rolemasterDAO = new RolemasterDAO();
		Rolemaster role = null;
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				role = rolemasterDAO.findById(Long.parseLong(rowId)); 
			} else {
				role = new Rolemaster();
			}
			
			role.setRolename(ResourceUtil.getFormDataValue(formData, "name"));
			role.setRoledescription(ResourceUtil.getFormDataValueAsClob(formData, "description"));
			role.setIsadmin(ResourceUtil.getFormDataValueAsBoolean(formData, "admin"));
			
			rolemasterDAO.save(role);
			rolemasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
}