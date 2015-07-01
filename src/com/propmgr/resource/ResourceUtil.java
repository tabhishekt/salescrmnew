package com.propmgr.resource;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialClob;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.propmgr.dao.Address;
import com.propmgr.dao.AddressDAO;
import com.propmgr.dao.Amenity;
import com.propmgr.dao.AmenityDAO;
import com.propmgr.dao.Amenitypricepolicy;
import com.propmgr.dao.AmenitypricepolicyDAO;
import com.propmgr.dao.Bankaccounttype;
import com.propmgr.dao.BankaccounttypeDAO;
import com.propmgr.dao.Citymaster;
import com.propmgr.dao.CitymasterDAO;
import com.propmgr.dao.Contactinfo;
import com.propmgr.dao.ContactinfoDAO;
import com.propmgr.dao.Customermaster;
import com.propmgr.dao.CustomermasterDAO;
import com.propmgr.dao.Enquiry;
import com.propmgr.dao.Enquirycomment;
import com.propmgr.dao.EnquirycommentDAO;
import com.propmgr.dao.Organization;
import com.propmgr.dao.OrganizationDAO;
import com.propmgr.dao.Parkingmaster;
import com.propmgr.dao.ParkingmasterDAO;
import com.propmgr.dao.Parkingtype;
import com.propmgr.dao.ParkingtypeDAO;
import com.propmgr.dao.Paymentmaster;
import com.propmgr.dao.PaymentmasterDAO;
import com.propmgr.dao.Paymentstage;
import com.propmgr.dao.Paymentstate;
import com.propmgr.dao.Paymentstatus;
import com.propmgr.dao.PaymentstatusDAO;
import com.propmgr.dao.Paymenttype;
import com.propmgr.dao.PaymenttypeDAO;
import com.propmgr.dao.Person;
import com.propmgr.dao.PersonDAO;
import com.propmgr.dao.Projectbankaccount;
import com.propmgr.dao.Projectbuilding;
import com.propmgr.dao.ProjectbuildingDAO;
import com.propmgr.dao.Projectmaster;
import com.propmgr.dao.ProjectmasterDAO;
import com.propmgr.dao.Projectphase;
import com.propmgr.dao.ProjectphaseDAO;
import com.propmgr.dao.Refundmaster;
import com.propmgr.dao.Rolemaster;
import com.propmgr.dao.RolemasterDAO;
import com.propmgr.dao.Sourcemaster;
import com.propmgr.dao.SourcemasterDAO;
import com.propmgr.dao.Statemaster;
import com.propmgr.dao.StatemasterDAO;
import com.propmgr.dao.UnitClassificationmasterDAO;
import com.propmgr.dao.Unitamenity;
import com.propmgr.dao.UnitamenityDAO;
import com.propmgr.dao.Unitbooking;
import com.propmgr.dao.UnitbookingDAO;
import com.propmgr.dao.Unitclassificationmaster;
import com.propmgr.dao.Unitmaster;
import com.propmgr.dao.UnitmasterDAO;
import com.propmgr.dao.Unitmodificationstate;
import com.propmgr.dao.Unitmodificationstatus;
import com.propmgr.dao.UnitmodificationstatusDAO;
import com.propmgr.dao.Unitpaymentschedule;
import com.propmgr.dao.UnitpaymentscheduleDAO;
import com.propmgr.dao.Unitpricepolicy;
import com.propmgr.dao.UnitpricepolicyDAO;
import com.propmgr.dao.Unittype;
import com.propmgr.dao.UnittypeDAO;
import com.propmgr.dao.Usermaster;
import com.propmgr.dao.UsermasterDAO;

public class ResourceUtil {
	private final static Logger logger = Logger.getLogger(ResourceUtil.class);
	private static final int MAX_REGISTRATION_CHARGE = 30000;
	
	public static OrganizationResource getOrganizationFromDAO(Organization org)  throws SQLException, IOException {
		AddressResource address = getAddressFromDAO(org.getAddress());
		ContactInfoResource contactInfo = getContactInfoFromDAO(org.getContactinfo());
		PersonResource contactPerson = getPersonResourceFromDAO(org.getPerson());

		return new OrganizationResource(org.getOrgid(), org.getOrgname(), org.getLogofilename(),
				contactInfo, address, contactPerson, getDisplayAddressFromAddressResource(address));
	}
	
	public static ProjectResource getProjectFromDAO(Projectmaster project)  throws SQLException, IOException {
		Organization org = project.getOrganization();
		AddressResource address = getAddressFromDAO(project.getAddress());
		
		return new ProjectResource(project.getProjectid(), 
				getOrganizationFromDAO(org), org.getOrgname(), project.getProjectname(), 
				project.getLogofilename(), convertClobToString(project.getProjectdescription()), 
				address, project.getTotalphases(), getDisplayAddressFromAddressResource(address),
				convertClobToString(project.getTermsandconditions()));
	}
	
	public static ProjectBankAccountResource getProjectBankAccountFromDAO(Projectbankaccount projectbankaccount) throws SQLException, IOException {
		AddressResource address = getAddressFromDAO(projectbankaccount.getAddress());
		CodeTableResource bankAccountType = getBankAccountTypeResourceFromDAO(projectbankaccount.getBankaccounttype());
		String displayBankInformation = projectbankaccount.getAccountholdername() + ", " + projectbankaccount.getBankname();
		displayBankInformation += ", Account # " + projectbankaccount.getAccountnumber();
		displayBankInformation += ", IFSC Code " + projectbankaccount.getIfsccode();
		displayBankInformation += ", MICR Code " + projectbankaccount.getMicrcode();
		displayBankInformation += ", " + getDisplayAddressFromAddressResource(address);
		
		return new ProjectBankAccountResource(projectbankaccount.getProjectbankaccountid(), 
				projectbankaccount.getProjectmaster().getProjectid(), address, 
				bankAccountType, bankAccountType.getName(), 
				projectbankaccount.getBankname(), projectbankaccount.getAccountnumber(), 
				projectbankaccount.getAccountholdername(), projectbankaccount.getIfsccode(), 
				projectbankaccount.getMicrcode(), displayBankInformation);
	}
	
	public static ProjectPhaseResource getProjectPhaseFromDAO(Projectphase projectPhase)  throws SQLException, IOException {
		Projectmaster project = projectPhase.getProjectmaster();
		
		return new ProjectPhaseResource(projectPhase.getProjectphaseid(), getProjectFromDAO(project), 
				project.getProjectname(), projectPhase.getProjectphasename(), 
				convertClobToString(projectPhase.getProjectphasedescription()));
	}
	
	public static ProjectBuildingResource getProjectBuildingFromDAO(Projectbuilding projectBuilding)  throws SQLException, IOException {
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		UnittypeDAO unittypeDAO = new UnittypeDAO();
		ParkingmasterDAO parkingmasterDAO = new ParkingmasterDAO();
		ParkingtypeDAO parkingtypeDAO = new ParkingtypeDAO();
		
		Projectphase projectPhase = projectBuilding.getProjectphase();
		long projectId = projectPhase.getProjectmaster().getProjectid();
		int floorCount = (int)projectBuilding.getFloorcount();
		Map<Integer, Double> floorRise = null;
		Map<Integer, List<UnitAvailabilityResource>> availability = null;
		Map<String, UnitPaymentScheduleResource> paymentSchedule = new HashMap<String, UnitPaymentScheduleResource>();
		Map<String, ParkingResource> parking = new HashMap<String, ParkingResource>();
		Map<String, Map<String, Double>> unitCharges = null;
		CodeTableResource currentStatus = getBuildingCurrentStatus(projectBuilding);
			
		Iterator<Unitpaymentschedule> iterator = projectBuilding.getUnitpaymentschedules().iterator();
		while(iterator.hasNext()) {
			Unitpaymentschedule aSchedule = iterator.next();
			paymentSchedule.put(aSchedule.getPaymentscheduletype() + "_" + aSchedule.getApplicableto(), 
					getUnitPaymentScheduleFromDAO(aSchedule));
		}
		
		List<Parkingtype> allParkingtypes = parkingtypeDAO.findAll(); 
		for (Parkingtype aParkingtype : allParkingtypes) {
			Parkingmaster parkingmaster = parkingmasterDAO.findByProjectBuildingAndType(projectBuilding.getProjectbuildingid(), aParkingtype.getParkingcode());
			ParkingResource res = (parkingmaster == null) ? new ParkingResource(-1, projectBuilding.getProjectbuildingid(), 
					new CodeTableResource(aParkingtype.getParkingtypeid(), aParkingtype.getParkingcode(), aParkingtype.getParkingname()), 
					0, 0, 0) : getParkingFromDAO(parkingmaster);
			parking.put(res.getParkingType().getName(), res);
		}
		
		if (projectBuilding.getUnitmasters().size() > 0) {
			floorRise = new HashMap<Integer, Double>();
			availability = new HashMap<Integer, List<UnitAvailabilityResource>>();
			unitCharges = new HashMap<String, Map<String, Double>>();
			
			for (int i=0; i<floorCount; i++) {
				List<UnitAvailabilityResource> floorAvailability = new ArrayList<UnitAvailabilityResource>();
				List<Unitmaster> allUnitsForFloor = unitmasterDAO.findByProjectBuildingAndFloorNumber(projectBuilding.getProjectbuildingid(), i+1);
				for (Unitmaster unit : allUnitsForFloor) {
					Unitbooking unitbooking = unitbookingDAO.findByUnit(unit);
					boolean unitAvailable = (unitbooking == null && "REG".equalsIgnoreCase(unit.getUnitclassificationmaster().getUnitclasscode())) ? true : false;
					UnitAvailabilityResource res = null; 
					if (unitAvailable) {
						res = new UnitAvailabilityResource(unit.getUnitnumber(), unit.getUnittype().getUnittypename(), 
								unit.getUnitclassificationmaster().getUnitclassdesc(), null, null, null, unitAvailable);
					} else {
						if(unitbooking != null){
							Parkingmaster parkingForBooking = unitbooking.getParkingmaster();
							String parkingName = (parkingForBooking == null) ? "No parking chosen" : parkingForBooking.getParkingtype().getParkingname();
							res = new UnitAvailabilityResource(unit.getUnitnumber(), unit.getUnittype().getUnittypename(),
									unit.getUnitclassificationmaster().getUnitclassdesc(),parkingName, getCustomerDisplayName(unitbooking.getCustomermaster()), 
									getUserDisplayName(unitbooking.getUsermasterByBookedby()), unitAvailable);
						}else{
							res = new UnitAvailabilityResource(unit.getUnitnumber(), unit.getUnittype().getUnittypename(),
									unit.getUnitclassificationmaster().getUnitclassdesc(),"Not Booked", "Not Booked", 
									"Not Booked", unitAvailable);
						}
						
						
					}
					floorAvailability.add(res);
				}
				floorRise.put(i+1, unitmasterDAO.findMaxFloorRiseByFloorNumberAndBuilding(i+1, projectBuilding.getProjectbuildingid()));
				availability.put(i+1, floorAvailability);
			}
			
			List<Unittype> allUnittypes = unittypeDAO.findAll(); 
			for (Unittype aUnittype : allUnittypes) {
				Map<String, Double> charges = new HashMap<String, Double>();
				if (unitmasterDAO.hasUnbookedByUnittypeAndProjectBuilding(aUnittype.getUnittypeid(), projectBuilding.getProjectbuildingid())) {
					charges.put("bookingAmount", unitmasterDAO.findMaxBookingAmountByUnitTypeAndBuilding(aUnittype.getUnittypeid(), projectBuilding.getProjectbuildingid()));
					charges.put("otherCharges", unitmasterDAO.findMaxOtherChargesByUnitTypeAndBuilding(aUnittype.getUnittypeid(), projectBuilding.getProjectbuildingid()));
					unitCharges.put(aUnittype.getUnittypename(), charges);
				}
			}
		}
		
		return new ProjectBuildingResource(projectBuilding.getProjectbuildingid(), projectId, getProjectPhaseFromDAO(projectPhase), 
				projectPhase.getProjectphasename(), projectBuilding.getBuildingname(), projectBuilding.getWingname(),
				projectBuilding.getFloorcount(), projectBuilding.getBuildingtype(), currentStatus,
				convertDateToString(projectBuilding.getExpectedcompletiondate()), convertClobToString(projectBuilding.getRemarks()), 
				projectBuilding.isHasmultiplepaymentschedules(), paymentSchedule, floorRise, availability, unitCharges, parking);
	}
	
	public static UnitResource getUnitFromDAO(Unitmaster unit)  throws SQLException, IOException {
		UnitResource result = null;
		if (unit != null) {
			String displayProjectInfo = getProjectDisplayName(unit);
			CodeTableResource unitType = getUnittypeResourceFromDAO(unit.getUnittype());
			CodeTableResource floorType = (unit.getFloortype() == 0) ? 
					new CodeTableResource(0, "EVEN", "Even") : new CodeTableResource(1, "ODD", "Odd");
			List<CodeTableResource> amenities = new ArrayList<CodeTableResource>();
			CodeTableResource unitClassification = getUnitClassificationResourceFromDAO(unit.getUnitclassificationmaster());
			double floorRise = (unit.getFloorrise() == null) ? 0 : unit.getFloorrise();
			double bookingAmount =  (unit.getBookingamount() == null) ? 0.0 : unit.getBookingamount();
			double otherCharges =  (unit.getOthercharges() == null) ? 0.0 : unit.getOthercharges();
			double agreementValue =  (unit.getAgreementvalue() == null) ? 0.0 : unit.getAgreementvalue();
			double totalTax =  (unit.getTotaltax() == null) ? 0.0 : unit.getTotaltax();
			double totalCostWithTax =  (unit.getTotalcostwithtax() == null) ? 0.0 : unit.getTotalcostwithtax();
			double totalCost =  (unit.getTotalcost() == null) ? 0.0 : unit.getTotalcost();
			
			Iterator<Unitamenity> iterator = unit.getUnitamenities().iterator();
			while(iterator.hasNext()) {
				Amenity amenity = iterator.next().getAmenity(); 
				amenities.add(new CodeTableResource(amenity.getAmenityid(), amenity.getAmenitycode(), amenity.getAmenitydescription()));
			}
			
			boolean booked = (new UnitbookingDAO()).isBookingExistsForUnit(unit.getUnitid());
			boolean registered = (unit.getRegistrationdone() != null) ? unit.getRegistrationdone().booleanValue() : false;
			result = new UnitResource(unit.getUnitid(), unit.getProjectbuilding().getProjectbuildingid(), getUnitPricePolicyFromDAO(unit.getUnitpricepolicy()),
					displayProjectInfo, unitType,unitClassification, unit.getUnitnumber(), unit.getFloornumber(), floorType, unit.getCarpetarea(), unit.getSaleablearea(),
					unit.getCarpetareaforterrace(), floorRise, unit.isOtheroptions(), amenities, bookingAmount, otherCharges, agreementValue,
					totalTax, totalCostWithTax, totalCost, !booked, registered);
		}
		
		return result;
	}
	
	public static UnitBookingResource getUnitbookingFromDAO(Unitbooking unitbooking)  throws SQLException, IOException {
		UnitmodificationstatusDAO unitmodificationstatusDAO = new UnitmodificationstatusDAO();
		Unitmaster unit = unitbooking.getUnitmaster();
		long buildingId = unit.getProjectbuilding().getProjectbuildingid();
		Customermaster customer = unitbooking.getCustomermaster();
		Usermaster user = unitbooking.getUsermasterByBookedby();
		double bookingDiscount = (unitbooking.getBookingdiscount() == null) ? 0 : unitbooking.getBookingdiscount();
		int parkingTypeReadyReckoner = (unitbooking.getParkingtype() == null) ? 0 : unitbooking.getParkingtype();
		double deductionOnOtherCharges = (unitbooking.getDeductiononothercharges() == null) ? 0 : unitbooking.getDeductiononothercharges();
		UnitPriceDetailResource priceWithoutDiscount = ResourceUtil.getUnitPriceDetailResource(unit, 0, deductionOnOtherCharges, parkingTypeReadyReckoner);
		UnitPriceDetailResource priceWithDiscount = ResourceUtil.getUnitPriceDetailResource(unit, bookingDiscount, deductionOnOtherCharges, parkingTypeReadyReckoner);
		
		double totalPaymentReceived = getTotalPaymentReceivedForBooking(unitbooking);
		double balancePayment = priceWithDiscount.getTotalCost() - totalPaymentReceived;
		double cancelDeduction = (unitbooking.getCanceldeduction() == null) ? 0 : unitbooking.getCanceldeduction();
		double cancelDeductionPercent = 0;
		if (cancelDeduction > 0) {
			cancelDeductionPercent = (unit.getAgreementvalue() != null) ? (cancelDeduction / unit.getAgreementvalue())*100 : 0;
		}
		String cancellationDate = (unitbooking.getCancellationdate() == null) ? null : convertDateToString(unitbooking.getCancellationdate());
		String cancellationComment = (unitbooking.getCancellationcomment() == null) ? null : convertClobToString(unitbooking.getCancellationcomment());
		String cancelUserDisplayName = (unitbooking.getUsermasterByCancelledby() == null) ? null : getUserDisplayName(unitbooking.getUsermasterByCancelledby());
		RefundResource refundDetails = null;
		
		Set<Refundmaster> refundMasterSet = unitbooking.getRefundmasters();
		if (refundMasterSet != null && refundMasterSet.size() > 0) {
			Refundmaster refundMaster = refundMasterSet.iterator().next();
			refundDetails = new RefundResource(refundMaster.getRefundmasterid(), refundMaster.getRefundamount(), 
					convertDateToString(refundMaster.getRefunddate()), refundMaster.getBankname(), refundMaster.getBankbranch(), 
					refundMaster.getChequenumber(), convertDateToString(refundMaster.getChequedate()));
		}
		
		Unitmodificationstatus unitmodificationstatus = unitmodificationstatusDAO.findLatestByBookingId(unitbooking.getUnitbookingid());
		String unitModificationStatusDate = (unitmodificationstatus != null) ? convertDateToString(unitmodificationstatus.getStatusdate()) : null;
		String unitModificationStatusComment = (unitmodificationstatus != null) ? convertClobToString(unitmodificationstatus.getStatuscomment()) : null;
		UnitModificationStateResource unitModificationState = null;
		if (unitmodificationstatus != null) {
			Unitmodificationstate aUnitmodificationState = unitmodificationstatus.getUnitmodificationstate();
			unitModificationState = new UnitModificationStateResource(aUnitmodificationState.getUnitmodificationstateid(), 
					aUnitmodificationState.getUnitmodificationstatename());
		}
				
		return new UnitBookingResource(unitbooking.getUnitbookingid(), buildingId, unitbooking.getBookingformnumber(),
			getCustomerDisplayName(customer), getUnitDisplayName(unit), 
			getUserDisplayName(user), convertDateToString(unitbooking.getBookingdate()), unit.getBookingamount(), 
			bookingDiscount, deductionOnOtherCharges, convertClobToString(unitbooking.getBookingcomment()), priceWithoutDiscount.getTotalCost(),
			priceWithDiscount.getTotalCost(), totalPaymentReceived, balancePayment, unitbooking.getIscancelled(), cancelUserDisplayName,
			cancelDeduction, cancelDeductionPercent, cancellationDate, cancellationComment, refundDetails, convertClobToString(unitbooking.getUnitmodificationdetails()),
			unitModificationState, unitModificationStatusDate, unitModificationStatusComment);
	}
	
	public static ParkingResource getParkingFromDAO(Parkingmaster parking)  throws SQLException, IOException {
		Parkingtype aParkingtype = parking.getParkingtype();
		CodeTableResource parkingType = new CodeTableResource(aParkingtype.getParkingtypeid(), aParkingtype.getParkingcode(), aParkingtype.getParkingname());
		return new ParkingResource(parking.getParkingmasterid(), parking.getProjectbuilding().getProjectbuildingid(), 
				parkingType, parking.getTotal(), parking.getAvailable(), parking.getBooked());
	}
	
	public static CodeTableResource getUnitClassificationResourceFromDAO(
			Unitclassificationmaster unitclassificationmaster) {
		if(unitclassificationmaster != null){
			return new CodeTableResource(unitclassificationmaster.getUnitclassid(), unitclassificationmaster.getUnitclasscode(), unitclassificationmaster.getUnitclassdesc());
		}else{
			return null;
		}
		
	}
	
	public static PaymentResource getPaymentFromDAO(Paymentmaster payment)  throws SQLException, IOException {
		PaymentResource result = null;
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		if (payment != null) {
			Unitbooking unitbooking = payment.getUnitbooking();
			Usermaster user = payment.getUsermaster();
			Paymenttype paymenttype = payment.getPaymenttype();
			Paymentstatus paymentstatusPOJO = paymentstatusDAO.findLatestByPaymentId(payment.getPaymentid());
			String displayPaymentStatus = null;
			PaymentStateResource paymentStatus = null;
			String statusUpdatedBy = null;
			String statusDate = null;
			String statusComment = null;
			List<CodeTableResource> paymentStages = new ArrayList<CodeTableResource>();
			int floorCount = (int)unitbooking.getUnitmaster().getProjectbuilding().getFloorcount();
			
			if (paymentstatusPOJO != null) {
				Paymentstate aPaymentState = paymentstatusPOJO.getPaymentstate();
				displayPaymentStatus = aPaymentState.getPaymentstatename();
				paymentStatus = new PaymentStateResource(aPaymentState.getPaymentstateid(), aPaymentState.getPaymentstatename(), aPaymentState.isAllowstatechange());
				statusUpdatedBy = getUserDisplayName(paymentstatusPOJO.getUsermaster());
				statusDate = convertDateToString(paymentstatusPOJO.getStatusdate());
				statusComment = convertClobToString(paymentstatusPOJO.getStatuscomment());
			}
			
			Iterator<Paymentstage> iterator = payment.getPaymentstages().iterator();
			while(iterator.hasNext()) {
				Paymentstage paymentstage = iterator.next(); 
				paymentStages.add(getPaymentStage(floorCount, paymentstage.getPaymentstagetype()));
			}
			
			result = new PaymentResource(payment.getPaymentid(), user.getUsermasterid(), getUserDisplayName(user), 
					unitbooking.getUnitbookingid(), unitbooking.getBookingformnumber(), getPaymenttypeResourceFromDAO(paymenttype), 
					convertClobToString(paymenttype.getPaymenttypedescription()), payment.getReceiptnumber(), payment.getAltreceiptnumber(),
					payment.getPaymentamount(), convertClobToString(payment.getPaymentdescription()), 
					convertDateToString(payment.getPaymentreceiveddate()), payment.getBankname(), payment.getBankbranch(), 
					payment.getChequenumber(), convertDateToString(payment.getChequedate()), payment.getUtrnumber(), payment.getCardnumber(), payment.getCardexpirydate(), 
					payment.getCardholdername(), payment.getCardtype(), displayPaymentStatus, paymentStatus, statusUpdatedBy, statusDate, statusComment, paymentStages);
		} 
		
		return result;
	}
	
	public static CodeTableResource getPaymentStage(int floorCount, int paymentStage) {
		
		if (paymentStage == 0) {
			return new CodeTableResource(0, "BOK", "Booking Payment");
		} else if (paymentStage == 1) {
			return new CodeTableResource(1, "REG", "Registration payment");
		} else if (paymentStage == 2) {
			return new CodeTableResource(2, "PLI", "Plinth payment");
		} else if (paymentStage > 2 && paymentStage <= floorCount + 2) { 
			return new CodeTableResource(paymentStage, "SLB"+ (paymentStage - 2), "Slab " + (paymentStage - 2) + "  payment");
		} else if (paymentStage == floorCount+3) {
			return new CodeTableResource(floorCount+3, "BRI", "Brickwork in progress");
		} else if (paymentStage == floorCount+4) {
			return new CodeTableResource(floorCount+4, "PLA", "Plastering in progress");
		} else if (paymentStage == floorCount+5) {
			return new CodeTableResource(floorCount+5, "FLO", "Flooring in progress");
		} else if (paymentStage == floorCount+6) {
			return new CodeTableResource(floorCount+6, "POS", "Ready for Possession");
		} else if (paymentStage == floorCount+7) {
			return new CodeTableResource(floorCount+7, "TAX1", "Stampduty payment");
		} else if (paymentStage == floorCount+8) {
			return new CodeTableResource(floorCount+8, "TAX2", "Registration charges payment");
		} else if (paymentStage == floorCount+9) {
			return new CodeTableResource(floorCount+9, "TAX3", "Service tax payment");
		} else if (paymentStage == floorCount+10) {
			return new CodeTableResource(floorCount+10, "TAX4", "MVAT payment");
		} else if (paymentStage == floorCount+11) {
			return new CodeTableResource(floorCount+11, "EXTRA", "Extra work payment");
		}
		
		return null;
	}
	
	public static UnitPaymentScheduleResource getUnitPaymentScheduleFromDAO(Unitpaymentschedule paymentSchedule)  throws SQLException, IOException {
		UnitPaymentScheduleResource result = null;
		if (paymentSchedule != null) {
			Projectbuilding projectbuilding = paymentSchedule.getProjectbuilding();
			result = new UnitPaymentScheduleResource(paymentSchedule.getUnitpaymentscheduleid(), 
					projectbuilding.getProjectbuildingid(), projectbuilding.getBuildingname(), paymentSchedule.getPaymentscheduleposition(),
					paymentSchedule.getPaymentscheduletype(), convertClobToString(paymentSchedule.getPaymentscheduledescription()), 
					convertDateToString(paymentSchedule.getPaymentscheduledate()), 0, paymentSchedule.getPercentamount(), 0, paymentSchedule.getApplicableto());
			
		}
		
		return result;
	}
	
	public static UnitPricePolicyResource getUnitPricePolicyFromDAO(Unitpricepolicy unitPricePolicy)  throws SQLException, IOException {
		if (unitPricePolicy == null) {
			return null;
		}
		
		AmenityDAO amenityDAO = new AmenityDAO();
		AmenitypricepolicyDAO amenitypricepolicyDAO = new AmenitypricepolicyDAO();
		Map<String, Double> amenityCharges = null;
		Set<String> assignedProjects = new HashSet<String>();
		Iterator<Unitmaster> iterator = unitPricePolicy.getUnitmasters().iterator();
		while(iterator.hasNext()) {
			assignedProjects.add(getProjectDisplayName(iterator.next()));
		}
		
		StringBuffer assignedToProjects = new StringBuffer();
		String[] array = assignedProjects.toArray(new String[0]);
		for (int i=0; i < array.length; i++) {
			assignedToProjects.append(array[i]);
			if (i < array.length - 1) {
				assignedToProjects.append(" | ");
			}
		}
		
		double totalTax = unitPricePolicy.getStampduty() + unitPricePolicy.getRegistrationcharge() + 
				unitPricePolicy.getServicetax() + unitPricePolicy.getValueaddedtax(); 
		int maintenancecharge1duration = (unitPricePolicy.getMaintenancecharge1duration() == null) ? 1 : unitPricePolicy.getMaintenancecharge1duration();
		
		List<Amenity> allAmenityTypes = amenityDAO.findAll();
		if (allAmenityTypes.size() > 0) {
			amenityCharges = new HashMap<String, Double>();
			for (Amenity aAmenityType : allAmenityTypes) {
					double charges = amenitypricepolicyDAO.findAmenityChargeByPricePolicyAndAmenityType(
							unitPricePolicy.getUnitpricepolicyid(), aAmenityType.getAmenityid());
					amenityCharges.put(aAmenityType.getAmenitydescription(), charges);
			}
		}
		
		double landRate = (unitPricePolicy.getLandrate() == null) ? 0 : unitPricePolicy.getLandrate();
		return new UnitPricePolicyResource(unitPricePolicy.getUnitpricepolicyid(), unitPricePolicy.getPolicyname(), 
				unitPricePolicy.getBaserate(), unitPricePolicy.getReadyreckonerrate(), landRate, unitPricePolicy.getStampduty(), 
				unitPricePolicy.getRegistrationcharge(), unitPricePolicy.getServicetax(), unitPricePolicy.getValueaddedtax(), totalTax, 
				unitPricePolicy.getMaintenancecharge1(), maintenancecharge1duration, unitPricePolicy.getMaintenancecharge2(), 
				unitPricePolicy.getLegalcharge(), assignedToProjects.toString(), amenityCharges);
	}
	
	public static CustomerResource getCustomerFromDAO(Customermaster customer)  throws SQLException, IOException {
		AddressResource address = getAddressFromDAO(customer.getAddress());
		PersonResource person = getPersonResourceFromDAO(customer.getPersonByPersondetail());
		PersonResource coOwner = (customer.getPersonByCoownerdetail() == null) ? null : getPersonResourceFromDAO(customer.getPersonByCoownerdetail());
		ContactInfoResource personContactInfo = person.getContactInfo();
		Usermaster user = customer.getUsermaster();
		String userDisplayName = getUserDisplayName(user);
		
		return new CustomerResource(customer.getCustomerid(), user.getUsermasterid(), userDisplayName, address, person, 
				coOwner, getDisplayNameFromPersonResource(person), getDisplayAddressFromAddressResource(address),
				personContactInfo.getPhoneNumber(), personContactInfo.getMobileNumber(), personContactInfo.getEmailID());
	}
	
	public static EnquiryResource getEnquiryFromDAO(Enquiry enquiry)  throws SQLException, IOException {
		EnquirycommentDAO enquirycommentDAO = new EnquirycommentDAO();
		List<EnquiryCommentResource> enquiryComments = new ArrayList<EnquiryCommentResource>();
		CodeTableResource budget =  null;
		CodeTableResource probability =  null;
		CodeTableResource currentHomeStatus =  null;
		
		Usermaster user = enquiry.getUsermaster();
		Customermaster customer = enquiry.getCustomermaster();
		Sourcemaster source = enquiry.getSourcemaster();
		Unittype interest = enquiry.getUnittype(); 
		List<Enquirycomment> enquiryCommentList = enquirycommentDAO.findByEnquiry(enquiry.getEnquiryid());
		String latestComment = "";
		String followupDate = "";
		for (Enquirycomment enquirycomment : enquiryCommentList) {
			enquiryComments.add(ResourceUtil.getEnquiryCommentFromDAO(enquirycomment));
		}
		
		if (enquiryComments.size() > 0) {
			latestComment = enquiryComments.get(enquiryComments.size() - 1).getCommentText();
			followupDate = enquiryComments.get(enquiryComments.size() - 1).getFollowupDate();
		}
		
		switch (enquiry.getProbability().intValue()) {
			case 0 :
				probability = new CodeTableResource(0, "HOT", "Hot");
				break;
			case 1 :
				probability = new CodeTableResource(1, "WARM", "Warm");
				break;
			case 2 :
				probability = new CodeTableResource(2, "COLD", "Cold");
				break;
		}
		
		switch (enquiry.getCurrenthomestatus().intValue()) {
			case 0 :
				currentHomeStatus = new CodeTableResource(0, "RENTED", "Rented");
				break;
			case 1 :
				currentHomeStatus = new CodeTableResource(1, "OWNED", "Owned");
				break;
		}
		
		switch (enquiry.getBudget().intValue()) {
			case 0 :
				budget = new CodeTableResource(0, "10-15LACS", "10 to 15 Lacs");
				break;
			case 1 :
				budget = new CodeTableResource(1, "15-20LACS", "15 to 20 Lacs");
				break;
			case 2 :
				budget = new CodeTableResource(2, "20-25LACS", "20 to 25 Lacs");
				break;
			case 3 :
				budget = new CodeTableResource(3, "25-30LACS", "25 to 30 Lacs");
				break;
			case 4 :
				budget = new CodeTableResource(4, "30-35LACS", "30 to 35 Lacs");
				break;
			case 5 :
				budget = new CodeTableResource(5, "35-40LACS", "35 to 40 Lacs");
				break;
			case 6 :
				budget = new CodeTableResource(6, "40-45LACS", "40 to 45 Lacs");
				break;
			case 7 :
				budget = new CodeTableResource(7, "45-50LACS", "45 to 50 Lacs");
				break;
		}
		
		return new EnquiryResource(enquiry.getEnquiryid(), customer.getCustomerid(), getCustomerDisplayName(customer), 
				user.getUsermasterid(), getUserDisplayName(user), getSourceResourceFromDAO(source), 
				convertDateToString(enquiry.getOriginalenquirydate()), budget, getUnittypeResourceFromDAO(interest), 
				probability, currentHomeStatus, latestComment, followupDate, enquiryComments);
	}
	
	public static AddressResource getAddressFromDAO(Address address)  throws SQLException, IOException {
		Citymaster city = address.getCitymaster();
		Statemaster state = address.getStatemaster();
		
		CodeTableResource cityResource = new CodeTableResource(city.getCitymasterid(), city.getCitycode(), city.getCityname());
		CodeTableResource stateResource = new CodeTableResource(state.getStatemasterid(), state.getStatecode(), state.getStatename());
		String addressLine1 = convertClobToString(address.getAddressline1());
		String addressLine2 = convertClobToString(address.getAddressline2());
		
		return new AddressResource(addressLine1, addressLine2, cityResource, stateResource, address.getZipcode());
	}
	
	public static ContactInfoResource getContactInfoFromDAO(Contactinfo contactInfo) {
		return new ContactInfoResource(contactInfo.getPhonenumber(), contactInfo.getAlternatenumber(), contactInfo.getMobilenumber(), contactInfo.getEmailid());
	}
	
	
	public static PersonResource getPersonResourceFromDAO(Person person)  throws SQLException, IOException {
		ContactInfoResource contactInfo = getContactInfoFromDAO(person.getContactinfo());
		String otherDetail = convertClobToString(person.getOtherdetail());
		
		return new PersonResource(person.getFirstname(), person.getMiddlename(), person.getLastname(), 
				contactInfo, convertDateToString(person.getDateofbirth()), person.getProfession(), otherDetail);
	}
	
	public static CodeTableResource getBankAccountTypeResourceFromDAO(Bankaccounttype bankaccounttype) {
		return new CodeTableResource(bankaccounttype.getBankaccounttypeid(), bankaccounttype.getAccounttypecode(), bankaccounttype.getAccounttypename());
	}
	
	public static CodeTableResource getUnittypeResourceFromDAO(Unittype unitType) {
		return new CodeTableResource(unitType.getUnittypeid(), unitType.getUnittypecode(), unitType.getUnittypename());
	}
	
	public static CodeTableResource getSourceResourceFromDAO(Sourcemaster source) {
		return new CodeTableResource(source.getSourcemasterid(), source.getSourcecode(), source.getSourcename());
	}
	
	public static CodeTableResource getPaymenttypeResourceFromDAO(Paymenttype paymenttype) throws SQLException, IOException {
		return new CodeTableResource(paymenttype.getPaymenttypeid(), paymenttype.getPaymenttypecode(), 
				convertClobToString(paymenttype.getPaymenttypedescription()));
	}
	
	public static EnquiryCommentResource getEnquiryCommentFromDAO(Enquirycomment enquirycomment) throws SQLException, IOException {
		return new EnquiryCommentResource(enquirycomment.getEnquirycommentid(), getUserDisplayName(enquirycomment.getUsermaster()), 
				enquirycomment.getEnquiry().getEnquiryid(), convertClobToString(enquirycomment.getVisitcomment()), 
				convertDateToString(enquirycomment.getVisitdate()), convertDateToString(enquirycomment.getFollowupdate()));
	}
	
	public static UnitPriceDetailResource getUnitPriceDetailResource(Unitmaster unit, double discount, 
			double deductionOnOtherCharges, int parkingTypeReadyReckoner)  throws SQLException, IOException {
		String displayProjectInfo = getProjectDisplayName(unit);
		Unitpricepolicy unitpricepolicy = unit.getUnitpricepolicy();
		
		double floorRise = (unit.getFloorrise() == null) ? 0 : unit.getFloorrise();
		double parkingArea = (unit.getParkingarea() == null) ? 100 : unit.getParkingarea();
		if (unitpricepolicy != null) {
			double amenityCharges = getAmenityCharges(unit, unitpricepolicy);
			double baseRate = unitpricepolicy.getBaserate();
			double readyreckonerrate = unitpricepolicy.getReadyreckonerrate();
			double landrate = (unitpricepolicy.getLandrate() == null) ? 0 : unitpricepolicy.getLandrate();
			double totalRate = baseRate + floorRise + amenityCharges - discount; 
			double basicCost = totalRate*unit.getSaleablearea();
			double otherCharges = unit.getOthercharges() - deductionOnOtherCharges;
			double parkingChargesReadyReckoner = getParkingChargesReadyReckoner(readyreckonerrate, landrate, parkingArea, parkingTypeReadyReckoner);
			double basicCostReadyReckoner = getBasicCostUsingReadyReckonerRate(readyreckonerrate, unit.getCarpetarea(), unit.getCarpetareaforterrace());
			double agreementValueBaseRate = getAgreementValueUsingBaseRate(totalRate, unit.getSaleablearea(), otherCharges);
			double agreementValueReadyReckoner = getAgreementValueUsingReadyReckonerRate(basicCostReadyReckoner, unit.getFloornumber(), parkingChargesReadyReckoner);
			double agreementValue = (agreementValueBaseRate >= agreementValueReadyReckoner) ? agreementValueBaseRate : agreementValueReadyReckoner;
			
			int maintenancecharge1duration = (unitpricepolicy.getMaintenancecharge1duration() == null) ? 1 : unitpricepolicy.getMaintenancecharge1duration();
			double maintainanceCharge1 = unitpricepolicy.getMaintenancecharge1()*maintenancecharge1duration*unit.getSaleablearea();
			double maintainanceCharge2 = unitpricepolicy.getMaintenancecharge2()*unit.getSaleablearea();
			double logalCharge = unitpricepolicy.getLegalcharge();
			double totalCharges = maintainanceCharge1 + maintainanceCharge2;
			
			double stampDuty = (unitpricepolicy.getStampduty()*agreementValue)/100;
			double registrationCharge = (unitpricepolicy.getRegistrationcharge()*agreementValue)/100;
			if (registrationCharge > MAX_REGISTRATION_CHARGE) {
				registrationCharge = MAX_REGISTRATION_CHARGE;
			}
			double serviceTax = (unitpricepolicy.getServicetax()*agreementValueBaseRate)/100;
			double valueAddedtax = (unitpricepolicy.getValueaddedtax()*agreementValue)/100;
			
			double totalTax = stampDuty + registrationCharge + serviceTax + valueAddedtax;
			double totalCostWithTax = agreementValueBaseRate + totalTax + logalCharge;
			double totalCostWithTaxReadyReckoner = agreementValueReadyReckoner + totalTax;
			
			double totalCost = totalCostWithTax + totalCharges;
			double totalCostReadyReckoner = totalCostWithTaxReadyReckoner + totalCharges;
			
			return new UnitPriceDetailResource(unit.getUnitid(), unit.getUnitnumber(), displayProjectInfo, getUnitPricePolicyFromDAO(unitpricepolicy), 
					unit.getSaleablearea(), unit.getCarpetarea(), baseRate, readyreckonerrate, amenityCharges, discount, deductionOnOtherCharges, floorRise, totalRate, stampDuty, 
					registrationCharge, serviceTax, valueAddedtax, maintainanceCharge1, maintainanceCharge2, logalCharge, otherCharges, totalCharges, 
					basicCost, basicCostReadyReckoner, agreementValueBaseRate, agreementValueReadyReckoner, totalTax, totalCostWithTax, 
					totalCostWithTaxReadyReckoner, totalCost, totalCostReadyReckoner);
		}
		
		return new UnitPriceDetailResource(unit.getUnitid(), unit.getUnitnumber(), displayProjectInfo, null, unit.getSaleablearea(), 
				unit.getCarpetarea(), 0, 0, 0, discount, deductionOnOtherCharges, floorRise, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public static CodeTableResource getBuildingCurrentStatus(Projectbuilding projectBuilding) {
		CodeTableResource currentStatus = null;
		int floorCount = (int)projectBuilding.getFloorcount();
		
		int buildingStatus = (projectBuilding.getCurrentstatus() == null) ? 0 : projectBuilding.getCurrentstatus().intValue();
		if (buildingStatus == 0) {
			currentStatus = new CodeTableResource(0, "REG", "Registration in progress");
		} else if (buildingStatus == 1) {
			currentStatus = new CodeTableResource(1, "PLI", "Plinth in progress");
		} else if (buildingStatus > 1 && buildingStatus <= floorCount + 1) { 
			currentStatus = new CodeTableResource(buildingStatus, "SLB"+ (buildingStatus - 1), "Slab " + (buildingStatus - 1) + "  in progress");
		} else if (buildingStatus == floorCount+2) {
			currentStatus = new CodeTableResource(floorCount+2, "BRI", "Brickwork in progress");
		} else if (buildingStatus == floorCount+3) {
			currentStatus = new CodeTableResource(floorCount+3, "PLA", "Plastering in progress");
		} else if (buildingStatus == floorCount+4) {
			currentStatus = new CodeTableResource(floorCount+4, "FLO", "Flooring in progress");
		} else if (buildingStatus == floorCount+5) {
			currentStatus = new CodeTableResource(floorCount+5, "POS", "Ready for Possession");
		}
		
		return currentStatus;
	}
	
	public static double getTotalDueForCurrentStatus(Set<UnitPaymentScheduleResource> scheduleList, 
			double bookingAmount, Unitbooking unitbooking, int currentStatus) {
		double totalDueForCurrentStatus = 0;
		for (UnitPaymentScheduleResource paymentSchedule : scheduleList) {
			if (paymentSchedule.getPosition() <= currentStatus) {
				totalDueForCurrentStatus += paymentSchedule.getAmount();
			}
		}
		
		return (totalDueForCurrentStatus < bookingAmount) ? bookingAmount : totalDueForCurrentStatus;
	}
	
	public static double getTotalPaymentReceivedForBooking(Unitbooking unitbooking) {
		PaymentmasterDAO paymentmasterDAO = new PaymentmasterDAO();
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		double totalPaymentReceived = 0;
		List<Paymentmaster> payments = paymentmasterDAO.findByUnitbooking(unitbooking.getUnitbookingid());
		if (payments != null) {
			for (Paymentmaster payment : payments) {
				Paymentstatus paymentstatusPOJO = paymentstatusDAO.findLatestByPaymentId(payment.getPaymentid());
				if (paymentstatusPOJO == null || !paymentstatusPOJO.getPaymentstate().getPaymentstatename().equalsIgnoreCase("Bounced")) {
					totalPaymentReceived += payment.getPaymentamount();
				}
			}
		}
		
		return totalPaymentReceived;
	}
	
	public static String getDisplayAddressFromAddressResource(AddressResource address) {
		StringBuffer displayAddress = new StringBuffer();
		String addressLine1 = address.getAddressLine1();
		String addressLine2 = address.getAddressLine2();
		
		if (addressLine1 != null && addressLine1.length() > 0) {
			displayAddress.append(addressLine1);
			displayAddress.append(", ");
		}
		
		
		if (addressLine2 != null && addressLine2.length() > 0) {
			displayAddress.append(addressLine2);
			displayAddress.append(", ");
		}
		
		displayAddress.append(address.getCity().getName());
		displayAddress.append(", " + address.getState().getName());
		displayAddress.append(", " + address.getZipCode());
		
		return displayAddress.toString(); 
	}
	
	public static String getDisplayNameFromPersonResource(PersonResource person) {
		StringBuffer displayName = new StringBuffer();
		String firstName = person.getFirstName();
		String middleName = person.getMiddleName(); 
		String lastName = person.getLastName();
		
		if (firstName != null && firstName.length() > 0) {
			displayName.append(firstName);
		}
		
		if (middleName != null && middleName.length() > 0) {
			displayName.append(", ");
			displayName.append(middleName);
		}
		
		if (lastName != null && lastName.length() > 0) {
			displayName.append(", ");
			displayName.append(lastName);
		}
		
		return displayName.toString(); 
	}
	
	public static String getUserDisplayName(Usermaster user) throws SQLException, IOException {
		PersonResource person = getPersonResourceFromDAO(user.getPerson());
		return getDisplayNameFromPersonResource(person);
	}
	
	public static String getCustomerDisplayName(Customermaster customer) throws SQLException, IOException {
		PersonResource person = getPersonResourceFromDAO(customer.getPersonByPersondetail());
		return getDisplayNameFromPersonResource(person);
	}
	
	public static String getProjectDisplayName(Unitmaster unit) {
		Projectbuilding projectbuilding = unit.getProjectbuilding();
		Projectphase projectphase = projectbuilding.getProjectphase();
		Projectmaster project = projectphase.getProjectmaster();
		return projectbuilding.getBuildingname() + ", " +
					projectphase.getProjectphasename() + ", " + project.getProjectname();
	}
	
	public static String getUnitDisplayName(Unitmaster unit) {
		Projectbuilding projectbuilding = unit.getProjectbuilding();
		Projectphase projectphase = projectbuilding.getProjectphase();
		Projectmaster project = projectphase.getProjectmaster();
		return unit.getUnitnumber() + "," + projectbuilding.getBuildingname() + ", " +
					projectphase.getProjectphasename() + "," + project.getProjectname();
	}
	
	 public static String convertClobToString(Clob clob) throws SQLException, IOException {
		 if (clob == null) {
			 return "";
		 }
		 
		 StringBuilder sb = new StringBuilder();
         Reader reader = clob.getCharacterStream();
         int c = -1;
         while((c = reader.read()) != -1) {
              sb.append(((char)c));
         }

         return sb.toString();
	 }
	 
	 public static Clob convertStringToClob(String s) throws SQLException {
		 return new SerialClob(s.toCharArray());
	 }
	 
	 public static String convertDateToString(Date d) {
		 if (d == null) {
			 return null;
		 }
		 return (new SimpleDateFormat("yyyy-MM-dd")).format(d);
	 }
	 
	 public static Date convertStringToDate(String s) throws ParseException {
		 if (s == null || s.length() == 0) {
			 return null;
		 }
		 return (new SimpleDateFormat("yyyy-MM-dd")).parse(s);
	 }
	 
	 public static String getFormDataValue(MultivaluedMap<String, String> formData, String formFieldName) {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 return values.get(0);
		 }
		 
		 return "";
	 }
	 
	 public static long getFormDataValueAsLong(MultivaluedMap<String, String> formData, String formFieldName) {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 String value = values.get(0);
			 if (value != null && value.length() > 0) {
				 return Long.parseLong(value);
		 	 }
		 }
		 
		 return 0;
	 }
	 
	 public static double getFormDataValueAsDouble(MultivaluedMap<String, String> formData, String formFieldName) {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 String value = values.get(0);
			 if (value != null && value.length() > 0) {
				 return Double.parseDouble(value);
			 }
		 }
		 
		 return 0.0;
	 }
	 
	 public static int getFormDataValueAsInt(MultivaluedMap<String, String> formData, String formFieldName) {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 String value = values.get(0);
			 if (value != null && value.length() > 0) {
				 return Integer.parseInt(value);
			 }
		 }
		 
		 return 0;
	 }
	 
	 public static boolean getFormDataValueAsBoolean(MultivaluedMap<String, String> formData, String formFieldName) {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			if (values.get(0).equalsIgnoreCase("on")) {
				return true;
			} 
		 }
		 
		 return false;
	 }
	 
	 public static Date getFormDataValueAsDate(MultivaluedMap<String, String> formData, String formFieldName) throws ParseException {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 String value = values.get(0);
			 if (value != null && value.length() > 0) {
				 return convertStringToDate(value);
			 }
		 }
		 
		 return null;
	 }
	 
	 public static Clob getFormDataValueAsClob(MultivaluedMap<String, String> formData, String formFieldName) throws SQLException {
		 List<String> values = formData.get(formFieldName);
		 if (values != null && values.size()  > 0) {
			 return convertStringToClob(values.get(0));
		 }
		 
		 return null;
	 }
	 
	 public static Citymaster getCityPOJO(MultivaluedMap<String, String> formData) {
		 String cityId = getFormDataValue(formData, "city");
		 CitymasterDAO dao = new CitymasterDAO();
		 return dao.findById(Long.parseLong(cityId));
	 }
	 
	 public static Statemaster getStatePOJO(MultivaluedMap<String, String> formData) {
		 String stateId = getFormDataValue(formData, "state");
		 StatemasterDAO dao = new StatemasterDAO();
		 return dao.findById(Long.parseLong(stateId));
	 }
	 
	 public static Unittype getUnittypePOJO(MultivaluedMap<String, String> formData) {
		 String unittypeId = getFormDataValue(formData, "unittype");
		 UnittypeDAO dao = new UnittypeDAO();
		 return dao.findById(Long.parseLong(unittypeId));
	 }
	 
	 public static Parkingtype getParkingtypePOJO(MultivaluedMap<String, String> formData) {
		 String parkingtypeId = getFormDataValue(formData, "parkingtype");
		 if (parkingtypeId == null || parkingtypeId.length() == 0) {
			 return null;
		 }
		 
		 ParkingtypeDAO dao = new ParkingtypeDAO();
		 return dao.findById(Long.parseLong(parkingtypeId));
	 }
	 
	 public static Unitclassificationmaster getUnitClassificationPOJO(MultivaluedMap<String, String> formData) {
		 String unitclassificationId = getFormDataValue(formData, "unitclassification");
		 if (unitclassificationId == null || unitclassificationId.length() == 0) {
			 return null;
		 }
		 
		 UnitClassificationmasterDAO dao = new UnitClassificationmasterDAO();
		 return dao.findById(Long.parseLong(unitclassificationId));
	 }
	 
	 public static Paymenttype getPaymenttypePOJO(MultivaluedMap<String, String> formData) {
		 String paymenttypeId = getFormDataValue(formData, "paymenttype");
		 PaymenttypeDAO dao = new PaymenttypeDAO();
		 return dao.findById(Long.parseLong(paymenttypeId));
	 }
	 
	 public static Bankaccounttype getBankAccountTypePOJO(MultivaluedMap<String, String> formData) {
		 String bankaccounttypeId = getFormDataValue(formData, "bankaccounttype");
		 BankaccounttypeDAO dao = new BankaccounttypeDAO();
		 return dao.findById(Long.parseLong(bankaccounttypeId));
	 }
	 
	 public static Projectmaster getProjectPOJO(MultivaluedMap<String, String> formData) {
		 String projectId = getFormDataValue(formData, "project");
		 ProjectmasterDAO dao = new ProjectmasterDAO();
		 return dao.findById(Long.parseLong(projectId));
	 }
	 
	 public static Projectphase getProjectPhasePOJO(MultivaluedMap<String, String> formData) {
		 String projectId = getFormDataValue(formData, "projectphase");
		 ProjectphaseDAO dao = new ProjectphaseDAO();
		 return dao.findById(Long.parseLong(projectId));
	 }
	 
	 public static Organization getOrganizationPOJO(MultivaluedMap<String, String> formData) {
		 String orgId = getFormDataValue(formData, "organization");
		 OrganizationDAO dao = new OrganizationDAO();
		 return dao.findById(Long.parseLong(orgId));
	 }
	 
	 public static Projectbuilding getProjectBuildingPOJO(MultivaluedMap<String, String> formData) {
		 String projectbuildingId = getFormDataValue(formData, "projectbuilding");
		 if (projectbuildingId == null || projectbuildingId.length() == 0) {
			 projectbuildingId = getFormDataValue(formData, "projectbuilding1");
		 }
		 if (projectbuildingId == null || projectbuildingId.length() == 0) {
			 projectbuildingId = getFormDataValue(formData, "projectbuilding2");
		 }
		 ProjectbuildingDAO dao = new ProjectbuildingDAO();
		 return dao.findById(Long.parseLong(projectbuildingId));
	 }
	 
	 public static Unitmaster getUnitPOJO(MultivaluedMap<String, String> formData) {
		 String unitId = getFormDataValue(formData, "unit");
		 UnitmasterDAO dao = new UnitmasterDAO();
		 return dao.findById(Long.parseLong(unitId));
	 }
	 
	 public static Unitpricepolicy getUnitpricepolicyPOJO(MultivaluedMap<String, String> formData) {
		 String UnitpricepolicyId = getFormDataValue(formData, "unitpricepolicy");
		 UnitpricepolicyDAO dao = new UnitpricepolicyDAO();
		 return dao.findById(Long.parseLong(UnitpricepolicyId));
	 }
	 
	 public static Unitbooking getUnitbookingPOJO(MultivaluedMap<String, String> formData) {
		 String unitbookingId = getFormDataValue(formData, "unitbooking");
		 UnitbookingDAO dao = new UnitbookingDAO();
		 return dao.findById(Long.parseLong(unitbookingId));
	 }
	 
	 public static Usermaster getUserPOJO(MultivaluedMap<String, String> formData) {
		 String userId = getFormDataValue(formData, "user");
		 UsermasterDAO dao = new UsermasterDAO();
		 return dao.findById(Long.parseLong(userId));
	 }
	 
	 public static Usermaster getBookingUserPOJO(MultivaluedMap<String, String> formData) {
		 String userId = getFormDataValue(formData, "bookinguser");
		 UsermasterDAO dao = new UsermasterDAO();
		 return dao.findById(Long.parseLong(userId));
	 }
	 
	 public static Customermaster getCustomerPOJO(MultivaluedMap<String, String> formData) {
		 String customerId = getFormDataValue(formData, "customer");
		 CustomermasterDAO dao = new CustomermasterDAO();
		 return dao.findById(Long.parseLong(customerId));
	 }
	 
	 public static Sourcemaster getSourcePOJO(MultivaluedMap<String, String> formData) {
		 String sourceId = getFormDataValue(formData, "source");
		 SourcemasterDAO dao = new SourcemasterDAO();
		 return dao.findById(Long.parseLong(sourceId));
	 }
	 
	 public static List<Amenity> getAmenityPOJOList(MultivaluedMap<String, String> formData) {
		 List<Amenity> amenities = new ArrayList<Amenity>();
		 AmenityDAO dao = new AmenityDAO();
		 
		 List<String> values = formData.get("amenities");
		 if (values != null && values.size()  > 0) {
			 for (String value : values) {
				 Amenity amenity = dao.findById(Long.parseLong(value));
				 if (amenity != null) {
					 amenities.add(amenity);
				 }
			 }
		 }
		 
		 return amenities;
	 }
	 
	 public static List<Rolemaster> getRolePOJOList(MultivaluedMap<String, String> formData) {
		 List<Rolemaster> roles = new ArrayList<Rolemaster>();
		 RolemasterDAO dao = new RolemasterDAO();
		 
		 List<String> values = formData.get("roles");
		 if (values != null && values.size()  > 0) {
			 for (String value : values) {
				 Rolemaster role = dao.findById(Long.parseLong(value));
				 if (role != null) {
					 roles.add(role);
				 }
			 }
		 }
		 
		 return roles;
	 }
	
	 public static void saveAddress(MultivaluedMap<String, String> formData, Address address) throws SQLException {
		 AddressDAO addressDAO = new AddressDAO();
		 Citymaster city = getCityPOJO(formData);
		 Statemaster state = getStatePOJO(formData);
			
		 address.setAddressline1(getFormDataValueAsClob(formData, "addressline1"));
		 address.setAddressline2(getFormDataValueAsClob(formData, "addressline2"));
		 address.setCitymaster(city);
		 address.setStatemaster(state);
		 address.setZipcode(getFormDataValue(formData, "zipcode"));
		 addressDAO.save(address);
	 }
	 
	 public static void deleteAddress(Address address) throws SQLException {
		 AddressDAO addressDAO = new AddressDAO();
		 addressDAO.delete(address);
	 }

	 public static void savePerson(MultivaluedMap<String, String> formData, Person person, Contactinfo personContactinfo) throws SQLException, ParseException {
		 PersonDAO personDAO = new PersonDAO();
		 ContactinfoDAO contactinfoDAO = new ContactinfoDAO();
		 
		 person.setFirstname(getFormDataValue(formData, "firstname"));
		 person.setMiddlename(getFormDataValue(formData, "middlename"));
		 person.setLastname(getFormDataValue(formData, "lastname"));
		 person.setDateofbirth(getFormDataValueAsDate(formData, "dateofbirth"));
		 person.setProfession(getFormDataValue(formData, "profession"));
		 person.setOtherdetail(getFormDataValueAsClob(formData, "otherdetails"));
		 personContactinfo.setPhonenumber(getFormDataValue(formData, "personphone"));
		 personContactinfo.setAlternatenumber(getFormDataValue(formData, "personaltphone"));
		 personContactinfo.setMobilenumber(getFormDataValue(formData, "personmobile"));
		 personContactinfo.setEmailid(getFormDataValue(formData, "personemailid"));
		 contactinfoDAO.save(personContactinfo);
		 person.setContactinfo(personContactinfo);
		 personDAO.save(person);
	 }
	 
	 public static void savePerson1(MultivaluedMap<String, String> formData, Person person, Contactinfo personContactinfo) throws SQLException, ParseException {
		 PersonDAO personDAO = new PersonDAO();
		 ContactinfoDAO contactinfoDAO = new ContactinfoDAO();
		 
		 person.setFirstname(getFormDataValue(formData, "firstname1"));
		 person.setMiddlename(getFormDataValue(formData, "middlename1"));
		 person.setLastname(getFormDataValue(formData, "lastname1"));
		 person.setDateofbirth(getFormDataValueAsDate(formData, "dateofbirth1"));
		 person.setProfession(getFormDataValue(formData, "profession1"));
		 person.setOtherdetail(getFormDataValueAsClob(formData, "otherdetails1"));
		 personContactinfo.setPhonenumber(getFormDataValue(formData, "person1phone"));
		 personContactinfo.setAlternatenumber(getFormDataValue(formData, "person1altphone"));
		 personContactinfo.setMobilenumber(getFormDataValue(formData, "person1mobile"));
		 personContactinfo.setEmailid(getFormDataValue(formData, "person1emailid"));
		 contactinfoDAO.save(personContactinfo);
		 person.setContactinfo(personContactinfo);
		 personDAO.save(person);
	 }
	 
	 public static void deletePerson(Person person) throws SQLException {
		 PersonDAO personDAO = new PersonDAO();
		 personDAO.delete(person);
	 }
	 
	 public static void saveContactInfo(MultivaluedMap<String, String> formData, Contactinfo contactInfo) throws SQLException {
		 ContactinfoDAO contactinfoDAO = new ContactinfoDAO();

		 contactInfo.setPhonenumber(getFormDataValue(formData, "phone"));
		 contactInfo.setAlternatenumber(getFormDataValue(formData, "altphone"));
		 contactInfo.setMobilenumber(getFormDataValue(formData, "mobile"));
		 contactInfo.setEmailid(getFormDataValue(formData, "emailid"));
		 contactinfoDAO.save(contactInfo);
	 }
	 
	 public static void saveUnit(MultivaluedMap<String, String> formData, Unitmaster unit, Projectbuilding projectBuilding, 
			 String unitNumber, int floorNumber, int floorType, boolean registered) throws SQLException {
		 UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		 UnitamenityDAO unitamenityDAO = new UnitamenityDAO();
		 
		 if (unit == null) {
			 unit = new Unitmaster();
		 }
		 
		 UnitClassificationmasterDAO classificationDAO = new UnitClassificationmasterDAO();
		 
		 Unitclassificationmaster classification = new Unitclassificationmaster();
		 classification.setUnitclasscode("REG");
		 classification = (Unitclassificationmaster)classificationDAO.findByExample(classification).get(0);

		 
		 unit.setProjectbuilding(projectBuilding);
		 unit.setUnittype(getUnittypePOJO(formData));			
		 unit.setUnitnumber(unitNumber);
		 unit.setFloornumber(floorNumber);
		 unit.setFloortype(floorType);

		 unit.setCarpetarea(getFormDataValueAsLong(formData, "carpetarea"));
		 unit.setSaleablearea(getFormDataValueAsLong(formData, "saleablearea"));
		 unit.setCarpetareaforterrace(getFormDataValueAsLong(formData, "carpetareaforterrace"));
		 unit.setOtheroptions(getFormDataValueAsBoolean(formData, "otheroptions"));
		 unit.setBookingamount(getFormDataValueAsDouble(formData, "bookingamount"));
		 unit.setOthercharges(getFormDataValueAsDouble(formData, "othercharges"));
		 unit.setRegistrationdone(registered);
		 unit.setUnitclassificationmaster(classification);
		 unitmasterDAO.save(unit);

		 List<Unitamenity> unitamenityList = unitamenityDAO.findByUnit(unit.getUnitid());
		 for (Unitamenity unitamenity : unitamenityList) {
			 unitamenityDAO.delete(unitamenity);
		 }
		 
		 List<Amenity> amenities = getAmenityPOJOList(formData);
		 for (Amenity amenity : amenities) {
			 Unitamenity unitamenity = new Unitamenity();
			 unitamenity.setUnitmaster(unit);
			 unitamenity.setAmenity(amenity);
			 unitamenityDAO.save(unitamenity);
		 }

		 unitmasterDAO.flushSession();
	 }
	 
	 public static double getAmenityCharges(Unitmaster unit, Unitpricepolicy unitpricepolicy) {
		 AmenitypricepolicyDAO amenitypricepolicyDAO = new AmenitypricepolicyDAO();
		 double amenityCharges = 0;
		 Set<Unitamenity> unitAmenities = unit.getUnitamenities();
		 if (unitAmenities != null) {
			 Iterator<Unitamenity> iterator = unitAmenities.iterator();
			 while (iterator.hasNext()) {
				 Unitamenity unitamenity = iterator.next();
				 if (unitamenity != null) {
					 Amenitypricepolicy amenitypricepolicy = amenitypricepolicyDAO.findByPricePolicyAndAmenityType(
							 unitpricepolicy.getUnitpricepolicyid(), unitamenity.getAmenity().getAmenityid());
					 if (amenitypricepolicy != null) {
						 amenityCharges += amenitypricepolicy.getAmenitycharge(); 
					 }
				 }
			 }
		 }

		 return amenityCharges;
	 }
	 
	 public static void saveUnitPriceInformation(Unitmaster unit, Unitpricepolicy unitpricepolicy, 
			 double floorRise, double discount, double deductionOnOtherCharges, int parkingTypeReadyReckoner) throws SQLException {
		double parkingArea = (unit.getParkingarea() == null) ? 100 : unit.getParkingarea();
		double amenityCharges = getAmenityCharges(unit, unitpricepolicy);
		double baseRate = unitpricepolicy.getBaserate();
		double readyReckonerRate = unitpricepolicy.getReadyreckonerrate(); 
		double otherCharges = unit.getOthercharges() - deductionOnOtherCharges;
		double totalRate = baseRate + floorRise + amenityCharges - discount; 
		double landrate = (unitpricepolicy.getLandrate() == null) ? 0 : unitpricepolicy.getLandrate();
		double parkingChargesReadyReckoner = getParkingChargesReadyReckoner(readyReckonerRate, landrate, parkingArea, parkingTypeReadyReckoner);
		double basicCostReadyReckoner = getBasicCostUsingReadyReckonerRate(readyReckonerRate, unit.getCarpetarea(), unit.getCarpetareaforterrace());
		double agreementValueBaseRate = getAgreementValueUsingBaseRate(totalRate, unit.getSaleablearea(), otherCharges);
		double agreementValueReadyReckoner = getAgreementValueUsingReadyReckonerRate(basicCostReadyReckoner, unit.getFloornumber(), parkingChargesReadyReckoner);
		double agreementValue = (agreementValueBaseRate >= agreementValueReadyReckoner) ? agreementValueBaseRate : agreementValueReadyReckoner;
		
		int maintenancecharge1duration = (unitpricepolicy.getMaintenancecharge1duration() == null) ? 1 : unitpricepolicy.getMaintenancecharge1duration();
		double maintainanceCharge1 = unitpricepolicy.getMaintenancecharge1()*maintenancecharge1duration;
		double maintainanceCharge2 = unitpricepolicy.getMaintenancecharge2();
		double logalCharge = unitpricepolicy.getLegalcharge();
		double totalCharges = maintainanceCharge1 + maintainanceCharge2;
		
		double stampDuty = (unitpricepolicy.getStampduty()*agreementValue)/100;
		double registrationCharge = (unitpricepolicy.getRegistrationcharge()*agreementValue)/100;
		if (registrationCharge > MAX_REGISTRATION_CHARGE) {
			registrationCharge = MAX_REGISTRATION_CHARGE;
		}
		double serviceTax = (unitpricepolicy.getServicetax()*agreementValueBaseRate)/100;
		double valueAddedtax = (unitpricepolicy.getValueaddedtax()*agreementValue)/100;
		double totalTax = stampDuty + registrationCharge + serviceTax + valueAddedtax;
		double totalCostWithTax = agreementValueBaseRate + totalTax + logalCharge;
		double totalCostWithTaxReadyReckoner = agreementValueReadyReckoner + totalTax;
		
		double totalCost = totalCostWithTax + totalCharges;
		double totalCostReadyReckoner = totalCostWithTaxReadyReckoner + totalCharges;
		
		unit.setAgreementvalue(agreementValueBaseRate);
		unit.setAgreementvalurereadyreckoner(agreementValueReadyReckoner);
		unit.setTotaltax(totalTax);
		unit.setTotalcostwithtax(totalCostWithTax);
		unit.setTotalcostwithtaxreadyreckoner(totalCostWithTaxReadyReckoner);
		unit.setTotalcost(totalCost);
		unit.setTotalcostreadyreckoner(totalCostReadyReckoner);
	 }
	 
	 public static double getAgreementValueUsingBaseRate(double totalRate, long saleableArea, 
			 double otherCharges) throws SQLException {
		 return saleableArea*totalRate + otherCharges;
	 }
	 
	 public static double getParkingChargesReadyReckoner(double readyreckonerrate, double landrate, double parkingArea, int parkingTypeReadyReckoner) {
		 double parkingChargesReadyReckoner = 0;
		 if (parkingTypeReadyReckoner == 1) {
			 parkingChargesReadyReckoner = (0.4*parkingArea)*(landrate/10.764);
		 } if (parkingTypeReadyReckoner == 2) {
			 parkingChargesReadyReckoner = (0.25*parkingArea)*(readyreckonerrate/10.764);
		 }
		 
		 return parkingChargesReadyReckoner;
	 }
	 
	 public static double getBasicCostUsingReadyReckonerRate(double readyReckonerRate, long carpetArea,  
			 long carpetAreaForTerrace) throws SQLException {
		 double saleableAreaReadyReckoner = ((carpetArea + 0.4*carpetAreaForTerrace)*1.2)/10.764;
		 double basicCostReadyReckoner = Math.round(readyReckonerRate*saleableAreaReadyReckoner*100)/100D;
		 
		 return basicCostReadyReckoner;
	 }
	 
	 public static double getAgreementValueUsingReadyReckonerRate(double basicCostReadyReckoner, int floorNumber,
			 double parkingChargesReadyReckoner) throws SQLException {
		 double agreementValue = basicCostReadyReckoner;
		 
		 if (floorNumber > 5 && floorNumber <= 10) {
			 agreementValue += agreementValue*0.05;
		 } else if (floorNumber > 10 && floorNumber <= 20) {
			 agreementValue += agreementValue*0.10;
		 } else if (floorNumber > 20) {
			 agreementValue += agreementValue*0.15;
		 }
		 
		 return agreementValue + parkingChargesReadyReckoner;
	 }
	 
	 public static void saveUnitPaymentSchedule(MultivaluedMap<String, String> formData, String typeFieldName, 
			 String percentFieldName, String dateFieldName, String descFieldName, int applicableTo) throws SQLException, ParseException {
		 UnitpaymentscheduleDAO unitpaymentscheduleDAO = new UnitpaymentscheduleDAO();
		 Unitpaymentschedule paymentSchedule;
		 String paymentScheduleType = null;
		 double percentAmount = 0.0;
		 Projectbuilding projectBuilding = getProjectBuildingPOJO(formData);
		 percentAmount = getFormDataValueAsDouble(formData, percentFieldName);
		 paymentScheduleType = getFormDataValue(formData, typeFieldName);
		 paymentSchedule = unitpaymentscheduleDAO.findByTypeAndBuilding(paymentScheduleType, projectBuilding.getProjectbuildingid(), applicableTo);
		 
		 if (percentAmount > 0) {
			 if (paymentSchedule == null) {
				 paymentSchedule = new Unitpaymentschedule();
			 }
	
			 paymentSchedule.setPaymentscheduleposition(getPaymentSchedulePosition(paymentScheduleType, (int)projectBuilding.getFloorcount()));
			 paymentSchedule.setPaymentscheduledate(getFormDataValueAsDate(formData, dateFieldName));
			 paymentSchedule.setPaymentscheduletype(paymentScheduleType);
			 paymentSchedule.setPaymentscheduledescription(getFormDataValueAsClob(formData, descFieldName));
			 paymentSchedule.setPercentamount(percentAmount);
			 paymentSchedule.setApplicableto(applicableTo);
			 paymentSchedule.setProjectbuilding(projectBuilding);
			 unitpaymentscheduleDAO.save(paymentSchedule);
		 } else {
			 if (paymentSchedule != null) {
				 unitpaymentscheduleDAO.delete(paymentSchedule);
			 }
		 }
		 
		 unitpaymentscheduleDAO.flushSession();
	 }
	 
	 public static boolean isPaymentScheduleApplicableToUnit(Unitpaymentschedule paymentSchedule, Unitmaster unit) throws SQLException {
		 boolean hasMultiplePaymentSchedule = unit.getProjectbuilding().isHasmultiplepaymentschedules();
		 int floorType = unit.getFloortype();
		 int applicableTo = paymentSchedule.getApplicableto();
		 
		 if (hasMultiplePaymentSchedule) {
			 if (applicableTo == 1 && floorType == 0) {
				 return true;
			 } else  if (applicableTo == 2 && floorType == 1) {
				 return true;
			 }
		 } else {
			 if (applicableTo == 0) {
				return true; 
			 } 
		 }
		 
		 return false;
	 }
	 
	 public static void deleteContactInfo(Contactinfo contactInfo) throws SQLException {
		 ContactinfoDAO contactinfoDAO = new ContactinfoDAO();
		 contactinfoDAO.delete(contactInfo);
	 }
	 
	 
	 public static int getPaymentSchedulePosition(String type, int floorCount) {
		 if (type.equalsIgnoreCase("Registration payment")) {
			 return 0;
		 } else if (type.equalsIgnoreCase("Plinth payment")) {
			 return 1;
		 } else if (type.startsWith("Slab")) {
			 StringTokenizer sTok = new StringTokenizer(type, " ");
			 sTok.nextToken();
			 return Integer.parseInt(sTok.nextToken()) + 1;
		 } else if (type.equalsIgnoreCase("Brick payment")) {
			 return floorCount + 2;
		 } else if (type.equalsIgnoreCase("Plastering payment")) {
			 return floorCount + 3;
		 } else if (type.equalsIgnoreCase("Flooring payment")) {
			 return floorCount + 4;
		 } else if (type.equalsIgnoreCase("Possession payment")) {
			 return floorCount + 5;
		 }
		 
		 return 0;
	 }
	 
	 public static double getFloorRisePaymentSchedule(String type, long buildingId) {
		 double floorRise = 0;
		 UnitmasterDAO unitmasterDAO = new UnitmasterDAO();
		 
		 if (type.startsWith("Slab")) {
			 StringTokenizer sTok = new StringTokenizer(type, " ");
			 sTok.nextToken();
			 floorRise = unitmasterDAO.findMaxFloorRiseByFloorNumberAndBuilding(Integer.parseInt(sTok.nextToken()), buildingId);
		 } 
		 
		 return floorRise;
	 }
}
