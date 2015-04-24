package com.propmgr.jersey;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.propmgr.dao.Amenity;
import com.propmgr.dao.AmenityDAO;
import com.propmgr.dao.Bankaccounttype;
import com.propmgr.dao.BankaccounttypeDAO;
import com.propmgr.dao.Citymaster;
import com.propmgr.dao.CitymasterDAO;
import com.propmgr.dao.Paymenttype;
import com.propmgr.dao.PaymenttypeDAO;
import com.propmgr.dao.Sourcemaster;
import com.propmgr.dao.SourcemasterDAO;
import com.propmgr.dao.Statemaster;
import com.propmgr.dao.StatemasterDAO;
import com.propmgr.dao.Unittype;
import com.propmgr.dao.UnittypeDAO;
import com.propmgr.resource.CodeTableResource;
import com.propmgr.resource.ResourceUtil;
 
 
@Path("/json/data/codetable")
public class CodeTableService {
	private final static Logger logger = Logger.getLogger(CodeTableService.class);
	
	@GET
	@Path("/city/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCities() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		CitymasterDAO citymasterDAO = new CitymasterDAO();
		
		List<Citymaster> allCities = citymasterDAO.findAll(); 
		for (Citymaster aCity : allCities) {
			result.add(new CodeTableResource(aCity.getCitymasterid(), aCity.getCitycode(), aCity.getCityname()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/city/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(@QueryParam("rowId") String rowId) {
		CitymasterDAO citymasterDAO = new CitymasterDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Citymaster aCity = citymasterDAO.findById(id);
		if (aCity != null) {
			result = new CodeTableResource(aCity.getCitymasterid(), aCity.getCitycode(), aCity.getCityname());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/city/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCity(@QueryParam("rowId") String rowId) {		
		CitymasterDAO citymasterDAO = new CitymasterDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Citymaster city = citymasterDAO.findById(id);
			if (city != null) {
				citymasterDAO.delete(city);
				citymasterDAO.flushSession();
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
	@Path("/city/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyCity(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		CitymasterDAO citymasterDAO = new CitymasterDAO();
		Citymaster city = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				city = citymasterDAO.findById(Long.parseLong(rowId)); 
			} else {
				city = new Citymaster();
			}
			city.setCitycode(code);
			city.setCityname(name);
			citymasterDAO.save(city);
			citymasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/state/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStates() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		StatemasterDAO statemasterDAO = new StatemasterDAO();
		
		List<Statemaster> allStates = statemasterDAO.findAll();
		for (Statemaster aState : allStates) {
			result.add(new CodeTableResource(aState.getStatemasterid(), aState.getStatecode(), aState.getStatename()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/state/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getState(@QueryParam("rowId") String rowId) {
		StatemasterDAO statemasterDAO = new StatemasterDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Statemaster aState = statemasterDAO.findById(id);
		if (aState != null) {
			result = new CodeTableResource(aState.getStatemasterid(), aState.getStatecode(), aState.getStatename());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/state/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteState(@QueryParam("rowId") String rowId) {		
		StatemasterDAO statemasterDAO = new StatemasterDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Statemaster state = statemasterDAO.findById(id);
			if (state != null) {
				statemasterDAO.delete(state);
				statemasterDAO.flushSession();
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
	@Path("/state/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyState(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		StatemasterDAO statemasterDAO = new StatemasterDAO();
		Statemaster state = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				state = statemasterDAO.findById(Long.parseLong(rowId)); 
			} else {
				state = new Statemaster();
			}
			state.setStatecode(code);
			state.setStatename(name);
			statemasterDAO.save(state);
			statemasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/amenity/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAmenities() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		AmenityDAO amenityDAO = new AmenityDAO();
		
		List<Amenity> allCities = amenityDAO.findAll(); 
		for (Amenity aAmenity : allCities) {
			result.add(new CodeTableResource(aAmenity.getAmenityid(), aAmenity.getAmenitycode(), aAmenity.getAmenitydescription()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/amenity/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmenity(@QueryParam("rowId") String rowId) {
		AmenityDAO amenityDAO = new AmenityDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Amenity aAmenity = amenityDAO.findById(id);
		if (aAmenity != null) {
			result = new CodeTableResource(aAmenity.getAmenityid(), aAmenity.getAmenitycode(), aAmenity.getAmenitydescription());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/amenity/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAmenity(@QueryParam("rowId") String rowId) {		
		AmenityDAO amenityDAO = new AmenityDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Amenity amenity = amenityDAO.findById(id);
			if (amenity != null) {
				amenityDAO.delete(amenity);
				amenityDAO.flushSession();
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
	@Path("/amenity/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyAmenity(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		AmenityDAO amenityDAO = new AmenityDAO();
		Amenity amenity = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				amenity = amenityDAO.findById(Long.parseLong(rowId)); 
			} else {
				amenity = new Amenity();
			}
			amenity.setAmenitycode(code);
			amenity.setAmenitydescription(name);
			amenityDAO.save(amenity);
			amenityDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/unittype/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUnittypes() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		UnittypeDAO unittypeDAO = new UnittypeDAO();
		
		List<Unittype> allCities = unittypeDAO.findAll(); 
		for (Unittype aUnittype : allCities) {
			result.add(new CodeTableResource(aUnittype.getUnittypeid(), aUnittype.getUnittypecode(), aUnittype.getUnittypename()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/unittype/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnittype(@QueryParam("rowId") String rowId) {
		UnittypeDAO unittypeDAO = new UnittypeDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Unittype aUnittype = unittypeDAO.findById(id);
		if (aUnittype != null) {
			result = new CodeTableResource(aUnittype.getUnittypeid(), aUnittype.getUnittypecode(), aUnittype.getUnittypename());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/unittype/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUnittype(@QueryParam("rowId") String rowId) {		
		UnittypeDAO unittypeDAO = new UnittypeDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Unittype unittype = unittypeDAO.findById(id);
			if (unittype != null) {
				unittypeDAO.delete(unittype);
				unittypeDAO.flushSession();
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
	@Path("/unittype/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUnittype(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		UnittypeDAO unittypeDAO = new UnittypeDAO();
		Unittype unittype = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				unittype = unittypeDAO.findById(Long.parseLong(rowId)); 
			} else {
				unittype = new Unittype();
			}
			unittype.setUnittypecode(code);
			unittype.setUnittypename(name);
			unittypeDAO.save(unittype);
			unittypeDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}

	@GET
	@Path("/source/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSources() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		SourcemasterDAO sourcemasterDAO = new SourcemasterDAO();
		
		List<Sourcemaster> allCities = sourcemasterDAO.findAll(); 
		for (Sourcemaster aSource : allCities) {
			result.add(new CodeTableResource(aSource.getSourcemasterid(), aSource.getSourcecode(), aSource.getSourcename()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/source/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSource(@QueryParam("rowId") String rowId) {
		SourcemasterDAO sourcemasterDAO = new SourcemasterDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Sourcemaster aSource = sourcemasterDAO.findById(id);
		if (aSource != null) {
			result = new CodeTableResource(aSource.getSourcemasterid(), aSource.getSourcecode(), aSource.getSourcename());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/source/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSource(@QueryParam("rowId") String rowId) {		
		SourcemasterDAO sourcemasterDAO = new SourcemasterDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Sourcemaster source = sourcemasterDAO.findById(id);
			if (source != null) {
				sourcemasterDAO.delete(source);
				sourcemasterDAO.flushSession();
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
	@Path("/source/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySource(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		SourcemasterDAO sourcemasterDAO = new SourcemasterDAO();
		Sourcemaster source = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				source = sourcemasterDAO.findById(Long.parseLong(rowId)); 
			} else {
				source = new Sourcemaster();
			}
			source.setSourcecode(code);
			source.setSourcename(name);
			sourcemasterDAO.save(source);
			sourcemasterDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/paymenttype/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPaymenttypes() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		PaymenttypeDAO paymenttypeDAO = new PaymenttypeDAO();
		try {
			List<Paymenttype> allPaymenttypes = paymenttypeDAO.findAll(); 
			for (Paymenttype aPaymenttype : allPaymenttypes) {
				result.add(new CodeTableResource(aPaymenttype.getPaymenttypeid(), aPaymenttype.getPaymenttypecode(), 
						ResourceUtil.convertClobToString(aPaymenttype.getPaymenttypedescription())));
			}
		} catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/paymenttype/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymenttype(@QueryParam("rowId") String rowId) {
		PaymenttypeDAO paymenttypeDAO = new PaymenttypeDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		try {
			Paymenttype aPaymenttype = paymenttypeDAO.findById(id);
			if (aPaymenttype != null) {
				result = new CodeTableResource(aPaymenttype.getPaymenttypeid(), aPaymenttype.getPaymenttypecode(), 
						ResourceUtil.convertClobToString(aPaymenttype.getPaymenttypedescription()));
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
	@Path("/paymenttype/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePaymenttype(@QueryParam("rowId") String rowId) {		
		PaymenttypeDAO paymenttypeDAO = new PaymenttypeDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Paymenttype paymenttype = paymenttypeDAO.findById(id);
			if (paymenttype != null) {
				paymenttypeDAO.delete(paymenttype);
				paymenttypeDAO.flushSession();
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
	@Path("/paymenttype/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyPaymenttype(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		PaymenttypeDAO paymenttypeDAO = new PaymenttypeDAO();
		Paymenttype paymenttype = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				paymenttype = paymenttypeDAO.findById(Long.parseLong(rowId)); 
			} else {
				paymenttype = new Paymenttype();
			}
			paymenttype.setPaymenttypecode(code);
			paymenttype.setPaymenttypedescription(ResourceUtil.convertStringToClob(name));
			paymenttypeDAO.save(paymenttype);
			paymenttypeDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
	
	@GET
	@Path("/bankaccounttype/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBankAccountTypes() {
		List<CodeTableResource> result = new ArrayList<CodeTableResource>();
		BankaccounttypeDAO bankaccounttypeDAO = new BankaccounttypeDAO();
		
		List<Bankaccounttype> allBankaccounttypes = bankaccounttypeDAO.findAll(); 
		for (Bankaccounttype aBankaccounttype : allBankaccounttypes) {
			result.add(new CodeTableResource(aBankaccounttype.getBankaccounttypeid(), aBankaccounttype.getAccounttypecode(), aBankaccounttype.getAccounttypename()));
		}
		int size = result.size();
		
	    return Response.ok(result).header("Content-Range", "items 0-" + (size - 1) + "/" + size).build();
	}
	
	@GET
	@Path("/bankaccounttype/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBankAccountType(@QueryParam("rowId") String rowId) {
		BankaccounttypeDAO bankaccounttypeDAO = new BankaccounttypeDAO();
		long id = Long.parseLong(rowId);
		CodeTableResource result = null;
		
		Bankaccounttype aBankaccounttype = bankaccounttypeDAO.findById(id);
		if (aBankaccounttype != null) {
			result = new CodeTableResource(aBankaccounttype.getBankaccounttypeid(), aBankaccounttype.getAccounttypecode(), aBankaccounttype.getAccounttypename());
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(new ApplicationException("entity with id " + rowId + " not found.")).build();
		}

	    return Response.ok(result).build();
	}
	
	@DELETE
	@Path("/bankaccounttype/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBankAccountType(@QueryParam("rowId") String rowId) {		
		BankaccounttypeDAO bankaccounttypeDAO = new BankaccounttypeDAO();
		long id = Long.parseLong(rowId);
		
		try {
			Bankaccounttype aBankaccounttype = bankaccounttypeDAO.findById(id);
			if (aBankaccounttype != null) {
				bankaccounttypeDAO.delete(aBankaccounttype);
				bankaccounttypeDAO.flushSession();
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
	@Path("/bankaccounttype/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyBankAccountType(@FormParam("rowId") String rowId, @FormParam("code") String code, @FormParam("name") String name) {
		BankaccounttypeDAO bankaccounttypeDAO = new BankaccounttypeDAO();
		Bankaccounttype aBankaccounttype = null;
		try {
			if (rowId != null && rowId.length() > 0) {
				aBankaccounttype = bankaccounttypeDAO.findById(Long.parseLong(rowId)); 
			} else {
				aBankaccounttype = new Bankaccounttype();
			}
			aBankaccounttype.setAccounttypecode(code);
			aBankaccounttype.setAccounttypename(name);
			bankaccounttypeDAO.save(aBankaccounttype);
			bankaccounttypeDAO.flushSession();
		}
		catch (Exception e) {
			logger.error("", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e.getMessage())).build();
		}
	    return Response.ok().build();
	}
}