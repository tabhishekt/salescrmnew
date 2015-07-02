package com.propmgr.jersey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.propmgr.dao.CustomermasterDAO;
import com.propmgr.dao.Person;
import com.propmgr.dao.Rolemaster;
import com.propmgr.dao.Usermaster;
import com.propmgr.dao.UsermasterDAO;
import com.propmgr.dao.Userrole;
import com.propmgr.dao.UserroleDAO;
import com.propmgr.resource.AddressResource;
import com.propmgr.resource.CustomerResource;
import com.propmgr.resource.PersonResource;
import com.propmgr.resource.ResourceUtil;
import com.propmgr.resource.RoleResource;
import com.propmgr.resource.UserResource;
 
 
@Path("/json/data/person")
public class PersonService {
	private final static Logger logger = Logger.getLogger(PersonService.class);
	
	@GET
	@Path("/customer/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomer() {
		CustomermasterDAO customermasterDAO = new CustomermasterDAO();
		List<CustomerResource> result = new ArrayList<CustomerResource>();
		
		try {
			List<Customermaster> allCustomers = customermasterDAO.findAll();
			for (Customermaster customer : allCustomers) {
				result.add(ResourceUtil.getCustomerFromDAO(customer));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/customer/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@QueryParam("rowId") String rowId) {
		CustomermasterDAO customermasterDAO = new CustomermasterDAO();
		CustomerResource result = null;
		
		try {
			long id = Long.parseLong(rowId);
			Customermaster customer = customermasterDAO.findById(id);
			if (customer != null) {
				result = ResourceUtil.getCustomerFromDAO(customer);
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
	@Path("/customer/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@QueryParam("rowId") String rowId) {		
		CustomermasterDAO customermasterDAO = new CustomermasterDAO();
		
		try {
			long id = Long.parseLong(rowId);
			Customermaster customer = customermasterDAO.findById(id);
			if (customer != null) {
				customermasterDAO.delete(customer);
				ResourceUtil.deleteAddress(customer.getAddress());
				ResourceUtil.deletePerson(customer.getPersonByPersondetail());
				ResourceUtil.deletePerson(customer.getPersonByCoownerdetail());
				ResourceUtil.deleteContactInfo(customer.getPersonByPersondetail().getContactinfo());
				customermasterDAO.flushSession();
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
	@Path("/customer/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyCustomer(MultivaluedMap<String, String> formData) {
		CustomermasterDAO customermasterDAO = new CustomermasterDAO();
		Customermaster customer = new Customermaster();
		Address address = new Address();
		Person person = new Person();
		Person coOwner = new Person();
		Contactinfo personContactinfo = new Contactinfo();
		Contactinfo coOwnerContactinfo = new Contactinfo();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			Usermaster user = ResourceUtil.getUserPOJO(formData);
			if (rowId != null && rowId.length() > 0) {
				customer = customermasterDAO.findById(Long.parseLong(rowId));
				if (customer == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
				address = customer.getAddress();
				person = customer.getPersonByPersondetail();
				personContactinfo = person.getContactinfo();
				coOwner = customer.getPersonByCoownerdetail();
				if (coOwner != null) {
					coOwnerContactinfo = coOwner.getContactinfo();
				}
			} else {
				String firstName = ResourceUtil.getFormDataValue(formData, "firstname");
				String middleName = ResourceUtil.getFormDataValue(formData, "middlename");
				String lastName = ResourceUtil.getFormDataValue(formData, "lastname");
				String emailID = ResourceUtil.getFormDataValue(formData, "personemailid");
				String phoneNumber = ResourceUtil.getFormDataValue(formData, "personphone");
				String mobileNumber = ResourceUtil.getFormDataValue(formData, "personmobile");
				
				if (customermasterDAO.isDuplicateCustomer(firstName, middleName, lastName, emailID, phoneNumber, mobileNumber)) {
					return Response.status(Response.Status.NOT_IMPLEMENTED).entity(new ApplicationException("No updates has been made. "
							+ "Customer with same name, email and phone alredy exists.")).build();
				}
				
				customer.setUsermaster(user);
			}
			
			if (coOwner == null) {
				coOwner = new Person();
				coOwnerContactinfo = new Contactinfo();
			}
			
			ResourceUtil.saveAddress(formData, address);
			customer.setAddress(address);
			
			ResourceUtil.savePerson(formData, person, personContactinfo);
			customer.setPersonByPersondetail(person);
			
			if (ResourceUtil.getFormDataValue(formData, "firstname1") != null) {
				ResourceUtil.savePerson1(formData, coOwner, coOwnerContactinfo);
				customer.setPersonByCoownerdetail(coOwner);
			}
			
			customermasterDAO.save(customer);
			customermasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/user/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		List<UserResource> result = new ArrayList<UserResource>();
		
		try {
			List<Usermaster> allUsers = usermasterDAO.findAll();
			for (Usermaster user : allUsers) {
				List<RoleResource> roles = new ArrayList<RoleResource>();
				AddressResource address = ResourceUtil.getAddressFromDAO(user.getAddress());
				PersonResource person = ResourceUtil.getPersonResourceFromDAO(user.getPerson());
				Iterator<Userrole> userroleIterator = user.getUserroles().iterator();
				StringBuffer displayRoles = new StringBuffer();
				while(userroleIterator.hasNext()) {
					Rolemaster role = userroleIterator.next().getRolemaster(); 
					displayRoles.append(role.getRolename() + ", ");
					roles.add(new RoleResource(role.getRolemasterid(), role.getRolename(), 
							ResourceUtil.convertClobToString(role.getRoledescription()), role.isIsadmin()));
				}
				
				result.add(new UserResource(user.getUsermasterid(), person, address, 
						ResourceUtil.getDisplayNameFromPersonResource(person), ResourceUtil.getDisplayAddressFromAddressResource(address),
						user.getLoginname(), user.getPassword(), roles, displayRoles.toString(), user.isIsactive()));
			} 
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		
		int size = result.size();
		return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/user/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("rowId") String rowId) {
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		UserResource result = null;
		
		try {
			Usermaster user = usermasterDAO.findById(Long.parseLong(rowId));
			if (user != null) {
				List<RoleResource> roles = new ArrayList<RoleResource>();
				AddressResource address = ResourceUtil.getAddressFromDAO(user.getAddress());
				PersonResource person = ResourceUtil.getPersonResourceFromDAO(user.getPerson());
				Iterator<Userrole> userroleIterator = user.getUserroles().iterator();
				StringBuffer displayRoles = new StringBuffer();
				
				while(userroleIterator.hasNext()) {
					Rolemaster role = userroleIterator.next().getRolemaster();
					displayRoles.append(role.getRolename() + ", ");
					roles.add(new RoleResource(role.getRolemasterid(), role.getRolename(), 
							ResourceUtil.convertClobToString(role.getRoledescription()), role.isIsadmin()));
				}
				
				result = new UserResource(user.getUsermasterid(), person, address, 
						ResourceUtil.getDisplayNameFromPersonResource(person), ResourceUtil.getDisplayAddressFromAddressResource(address),
						user.getLoginname(), user.getPassword(), roles, displayRoles.toString(), user.isIsactive());
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
	@Path("/user/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@QueryParam("rowId") String rowId) {		
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		UserroleDAO userroleDAO = new UserroleDAO();
		
		try {
			Usermaster user = usermasterDAO.findById(Long.parseLong(rowId));
			if (user != null) {
				Iterator<Userrole> userroleIterator = user.getUserroles().iterator();
				
				while(userroleIterator.hasNext()) {
					userroleDAO.delete(userroleIterator.next());
				}
				
				usermasterDAO.delete(user);
				ResourceUtil.deleteAddress(user.getAddress());
				ResourceUtil.deletePerson(user.getPerson());
				ResourceUtil.deleteContactInfo(user.getPerson().getContactinfo());
				usermasterDAO.flushSession();
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
	@Path("/user/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUser(MultivaluedMap<String, String> formData) {
		UserroleDAO userroleDAO = new UserroleDAO();
		UsermasterDAO usermasterDAO = new UsermasterDAO();
		Usermaster user = new Usermaster();
		Address address = new Address();
		Person person = new Person();
		Contactinfo personContactinfo = new Contactinfo();
		
		try {
			String rowId = ResourceUtil.getFormDataValue(formData, "rowId");
			if (rowId != null && rowId.length() > 0) {
				user = usermasterDAO.findById(Long.parseLong(rowId));
				if (user == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
				}
				address = user.getAddress();
				person = user.getPerson();
				personContactinfo = person.getContactinfo();
			} 
			
			ResourceUtil.saveAddress(formData, address);
			user.setAddress(address);
			
			ResourceUtil.savePerson(formData, person, personContactinfo);
			user.setPerson(person);
			user.setLoginname(ResourceUtil.getFormDataValue(formData, "loginname"));
			user.setPassword(ResourceUtil.getFormDataValue(formData, "password"));
			usermasterDAO.save(user);
			
			List<Rolemaster> roles = ResourceUtil.getRolePOJOList(formData);
			for (Rolemaster role : roles) {
				Userrole userrole = new Userrole();
				userrole.setUsermaster(user);
				userrole.setRolemaster(role);
				userroleDAO.save(userrole);
			}
			
			usermasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
}