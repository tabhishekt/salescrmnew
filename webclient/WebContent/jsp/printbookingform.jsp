<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking Form</title>
<style type="text/css">
@import "../js/dojo_1.10.2/dojo/resources/dojo.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/claro.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/document.css";
@import "../css/contentPage.css";
</style>

<jsp:directive.include file="common.jsp" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/dojo_1.10.2/dojo/dojo.js"></script>
<script>
    require(["dojo/ready", 
             "dojo/_base/lang",
             "dijit/registry",
             "dojo/on",
             "dojo/request", 
             "dojo/currency",
             "dojo/date/locale",
             "dojo/dom-construct",
			 "dojo/domReady!"],
        function(ready, lang, registry, on, request, currency, locale, domConstruct) {
	    	ready(function() {
	    		this.readUserDataFromSession();
	    		this.load();
			});
	    	
            load = function () {
            	readUserDataFromSession();
            	this.unitbookingId = this.getQueryVariable("unitbookingId");
            	var promise = request.get("../rest/json/data/inventory/unitbooking/get/print?rowId=" + this.unitbookingId, {
       				timeout: 2000,
       				handleAs: "json"
       			});
       			
       			promise.response.then(
       				function(response) {
       					this.populateFormData(response.data);
       					this.populatePaymentSchedule(response.data);
       					this.populatePaymentDetails(response.data);
       				},
       				function(error) {
       					this.gridHandler.updateMessage("Problem occured fetching price details: " + error.response.data.message, "error");
       				}
       			);
            };
            
            populateFormData = function(data) {
            	this.orgname.innerHTML = data.organization.name;
            	this.orgaddress.innerHTML = data.organization.displayAddress;
            	this.orgcontact.innerHTML = data.organization.contactInfo.phoneNumber
						+ ", " + data.organization.contactInfo.mobileNumber
						+ ", " + data.organization.contactInfo.emailID;
            	
            	this.bookingformnumber.innerHTML = data.bookingFormNumber;
            	this.user.innerHTML = data.userDisplayName;
            	this.bookingdate.innerHTML = this.formatDate(data.bookingDate);
            	
            	var customerDisplayName = data.customer.displayName.split(" ");
            	this.firstname.innerHTML = customerDisplayName[0];
            	this.middlename.innerHTML = customerDisplayName[1];
            	this.lastname.innerHTML = customerDisplayName[2];
            	this.customerdob.innerHTML = this.formatDate(data.customer.person.dateOfBirth);
            	this.customerage.innerHTML = this.getAgerFromDOB(data.customer.person.dateOfBirth);
            	
            	if (data.customer.coOwner && data.customer.coOwner != null) {
            		this.firstname1.innerHTML = data.customer.coOwner.firstName;
                	this.middlename1.innerHTML = data.customer.coOwner.middleName;
                	this.lastname1.innerHTML = data.customer.coOwner.lastName;
                	this.customerdob1.innerHTML = this.formatDate(data.customer.coOwner.dateOfBirth);
                	this.customerage1.innerHTML = this.getAgerFromDOB(data.customer.coOwner.dateOfBirth);	
            	}
            	
            	this.customeraddress.innerHTML = data.customer.displayAddress;
            	this.customerprofession.innerHTML = data.customer.person.profession;
            	this.customerotherdetails.innerHTML = data.customer.person.otherDetail;
            	if (data.customer.person.contactInfo.phoneNumber) {
            		this.customerphone.innerHTML = data.customer.person.contactInfo.phoneNumber;	
            	} else {
            		this.customerphone.innerHTML = "Not available";
            	}
            	
            	if (data.customer.person.contactInfo.mobileNumber) {
            		this.customermobile.innerHTML = data.customer.person.contactInfo.mobileNumber;	
            	} else {
            		this.customermobile.innerHTML = "Not available";
            	}
            	
            	if (data.customer.person.contactInfo.emailID) {
            		this.customeremailid.innerHTML = data.customer.person.contactInfo.emailID;	
            	} else {
            		this.customeremailid.innerHTML = "Not available";
            	}
            	
            	this.project.innerHTML = data.projectName;
            	this.projectphase.innerHTML = data.projectPhaseName;
            	this.projectbuilding.innerHTML = data.projectBuildingName;
            	this.saleablearea.innerHTML = data.unit.saleableArea;
            	this.baserate.innerHTML = this.formatCurrency(data.priceDetails.baserate) + " /sq ft";
            	this.reckonerrate.innerHTML = this.formatCurrency(data.priceDetails.readyreckonerrate) + " /sq mt";
            	this.discount.innerHTML = this.formatCurrency(data.priceDetails.discount) + " /sq ft";
            	if (data.unit.amenities.length > 0) {
            		this.amenitiesfacing.innerHTML = "Yes"
            	} else {
            		this.amenitiesfacing.innerHTML = "No"
            	}
            	this.amenitycharges.innerHTML = this.formatCurrency(data.priceDetails.amenityCharges) + " /sq ft";
            	this.unitnumber.innerHTML = data.unit.unitNumber;
            	if (data.unit.floorRise > 0) {
            		this.floorrisebool.innerHTML = "Yes" 
            	} else {
            		this.floorrisebool.innerHTML = "No"
            	}
            	this.floorrise.innerHTML = this.formatCurrency(data.unit.floorRise) + " /sq ft";
            	this.unittype.innerHTML = data.unit.unitType.name;
            	this.floortype.innerHTML = data.unit.floorType.name;
            	if (data.unit.otherOptions) {
            		this.otheroptions.innerHTML = "Yes";
            	} else {
            		this.otheroptions.innerHTML = "No";	
            	}
            	this.floornumber.innerHTML = data.unit.floorNumber;
            	this.totalrate.innerHTML = this.formatCurrency(data.priceDetails.totalrate);
            	this.othercharges.innerHTML = this.formatCurrency(data.unit.otherCharges);
            	this.deductiononothercharges.innerHTML = this.formatCurrency(data.deductionOnOtherCharges);
            	
            	this.basiccost.innerHTML = this.formatCurrency(data.priceDetails.basicCost);
            	this.totaltax.innerHTML = this.formatCurrency(data.priceDetails.totalTax);
            	this.maintenance1.innerHTML = this.formatCurrency(data.unit.unitPricePolicy.maintenancecharge1) + " /sq ft";
            	this.maintenance1amt.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge1);
            	this.otherchargeswithdeduction.innerHTML = this.formatCurrency(data.unit.otherCharges - data.deductionOnOtherCharges);
            	this.legalcharges.innerHTML = this.formatCurrency(data.priceDetails.legalcharge);
            	this.maintenance2.innerHTML = this.formatCurrency(data.unit.unitPricePolicy.maintenancecharge2) + " /sq ft";
            	this.maintenance2amt.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge2);
            	this.agreementvalue.innerHTML = this.formatCurrency(data.priceDetails.agreementvalue);
            	this.totalwithtax.innerHTML = this.formatCurrency(data.priceDetails.totalCostWithTax);
            	this.totalcost.innerHTML = this.formatCurrency(data.priceDetails.totalCost);
            	this.builderaccountinformation.innerHTML = data.projectBankAccounts["BLDRAC"];
            	
            	this.stampdutypercent.innerHTML = data.unit.unitPricePolicy.stampduty + "%";
            	this.stampduty.innerHTML = this.formatCurrency(data.priceDetails.stampduty);
            	this.valueaddedtaxpercent.innerHTML = data.unit.unitPricePolicy.valueaddedtax + "%";
            	this.valueaddedtax.innerHTML = this.formatCurrency(data.priceDetails.valueaddedtax);
            	this.servicetaxpercent.innerHTML = data.unit.unitPricePolicy.servicetax + "%";
            	this.servicetax.innerHTML = this.formatCurrency(data.priceDetails.servicetax);
            	this.registrationpercent.innerHTML = data.unit.unitPricePolicy.registrationcharge + "%";
            	this.registration.innerHTML = this.formatCurrency(data.priceDetails.registrationcharge);
            	this.sumtaxpercent.innerHTML = data.unit.unitPricePolicy.totaltax + "%";
            	this.sumtax.innerHTML = this.formatCurrency(data.priceDetails.totalTax);
            	
            	this.basiccostrr.innerHTML = this.formatCurrency(data.priceDetails.basicCostReadyReckoner);
            	this.totaltaxrr.innerHTML = this.formatCurrency(data.priceDetails.totalTax);
            	this.maintenance1rr.innerHTML = this.formatCurrency(data.unit.unitPricePolicy.maintenancecharge1) + " /sq ft";
            	this.maintenance1amtrr.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge1);
            	this.floreriseandparking.innerHTML = this.formatCurrency(data.priceDetails.agreementvalueReadyReckoner - data.priceDetails.basicCostReadyReckoner);
            	this.legalchargesrr.innerHTML = this.formatCurrency(data.priceDetails.legalcharge);
            	this.maintenance2rr.innerHTML = this.formatCurrency(data.unit.unitPricePolicy.maintenancecharge2) + " /sq ft";
            	this.maintenance2amtrr.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge2);
            	this.marketvalue.innerHTML = this.formatCurrency(data.priceDetails.agreementvalueReadyReckoner);
            	this.totalwithtaxrr.innerHTML = this.formatCurrency(data.priceDetails.totalCostWithTaxReadyReckoner);
            	this.totalcostrr.innerHTML = this.formatCurrency(data.priceDetails.totalCostReadyReckoner);
            	this.builderaccountinformationrr.innerHTML = data.projectBankAccounts["BLDRAC"];
            	
            	this.stampdutypercentrr.innerHTML = data.unit.unitPricePolicy.stampduty + "%";
            	this.stampdutyrr.innerHTML = this.formatCurrency(data.priceDetails.stampduty);
            	this.valueaddedtaxpercentrr.innerHTML = data.unit.unitPricePolicy.valueaddedtax + "%";
            	this.valueaddedtaxrr.innerHTML = this.formatCurrency(data.priceDetails.valueaddedtax);
            	this.servicetaxpercentrr.innerHTML = data.unit.unitPricePolicy.servicetax + "%";
            	this.servicetaxrr.innerHTML = this.formatCurrency(data.priceDetails.servicetax);
            	this.registrationpercentrr.innerHTML = data.unit.unitPricePolicy.registrationcharge + "%";
            	this.registrationrr.innerHTML = this.formatCurrency(data.priceDetails.registrationcharge);
            	this.sumtaxpercentrr.innerHTML = data.unit.unitPricePolicy.totaltax + "%";
            	this.sumtaxrr.innerHTML = this.formatCurrency(data.priceDetails.totalTax);
            	
            	this.termsandconditions.innerHTML = data.termsAndConditions;
            };
            
            populatePaymentSchedule = function(data) {
            	var currentPosition = data.buildingCurrentStatus.id;
            	if (data.scheduleList.length == 0) {
            		this.createSpan(this.paymentScheduleDiv, "No Payment Schedule Details available");
            		return;
            	}
            	var tr, th1, th2, th3, th4, td1, td2, td3, td4;
				var table = domConstruct.create("table", {"style": "width:100%", "class": "printPageTableBorder"}, this.paymentScheduleDiv);
				tr = domConstruct.create("tr", null, table);
				th1 = domConstruct.create("th", {"width":"30%"}, tr);
				this.createSpan(th1, "Description");
				th2 = domConstruct.create("th", {"width":"10%"}, tr);
				this.createSpan(th2, " ");
				th3 = domConstruct.create("th", {"width":"20%"}, tr);
				this.createSpan(th3, "Due Date");
				th4 = domConstruct.create("th", {"width":"30%"}, tr);
				this.createSpan(th4, "Amount");
				th5 = domConstruct.create("th", {"width":"10%"}, tr);
				this.createSpan(th5, "Floor Rise (/sq ft)");
					
				tr = domConstruct.create("tr", {"bgcolor": "#999900"}, table);
				td1 = domConstruct.create("td", {"align": "left"}, tr);
				td2 = domConstruct.create("td", {"align": "center"}, tr);
				td3 = domConstruct.create("td", {"align": "right"}, tr);
				td4 = domConstruct.create("td", {"align": "right"}, tr);
				td5 = domConstruct.create("td", {"align": "center"}, tr);
				
				this.createSpan(td1, "Agreement Value");
				this.createSpan(td2, " ");
				this.createSpan(td3, " ");
				this.createSpan(td4, this.formatCurrency(data.priceDetails.agreementvalue));
				this.createSpan(td5, " ");
				
				tr = domConstruct.create("tr", {"bgcolor": "#999900"}, table);
				td1 = domConstruct.create("td", {"align": "left"}, tr);
				td2 = domConstruct.create("td", {"align": "center"}, tr);
				td3 = domConstruct.create("td", {"align": "right"}, tr);
				td4 = domConstruct.create("td", {"align": "right"}, tr);
				td5 = domConstruct.create("td", {"align": "center"}, tr);
				
				this.createSpan(td1, "Booking Amount");
				this.createSpan(td2, " ");
				this.createSpan(td3, " ");
				this.createSpan(td4, this.formatCurrency(data.unit.bookingAmount));
				this.createSpan(td5, " ");
				
				tr = domConstruct.create("tr", {"bgcolor": "#999900"}, table);
				td1 = domConstruct.create("td", {"align": "left"}, tr);
				td2 = domConstruct.create("td", {"align": "center"}, tr);
				td3 = domConstruct.create("td", {"align": "right"}, tr);
				td4 = domConstruct.create("td", {"align": "right"}, tr);
				td5 = domConstruct.create("td", {"align": "center"}, tr);
				
				this.createSpan(td1, "Total Taxes and Legal");
				this.createSpan(td2, " ");
				this.createSpan(td3, " ");
				this.createSpan(td4, this.formatCurrency(data.priceDetails.totalTax));
				this.createSpan(td5, " ");
				
				for (var i=0; i<data.scheduleList.length; i++) {
					var schedule = data.scheduleList[i];
					if (schedule.position <= currentPosition) {
						tr = domConstruct.create("tr", {"bgcolor": "#009900"}, table);	
					} else {
						tr = domConstruct.create("tr", null, table);
					}
					
					
					td1 = domConstruct.create("td", {"align": "left"}, tr);
					td2 = domConstruct.create("td", {"align": "center"}, tr);
					td3 = domConstruct.create("td", {"align": "right"}, tr);
					td4 = domConstruct.create("td", {"align": "right"}, tr);
					td5 = domConstruct.create("td", {"align": "center"}, tr);
					
					this.createSpan(td1, schedule.type);
					this.createSpan(td2, schedule.percentamount + "%");
					this.createSpan(td3, this.formatDate(schedule.scheduledate));
					this.createSpan(td4, this.formatCurrency(schedule.amount));
					this.createSpan(td5, this.formatCurrency(schedule.floorRise));
				}
				
				tr = domConstruct.create("tr", {"bgcolor": "#999900"}, table);
				td1 = domConstruct.create("td", {"align": "left"}, tr);
				td2 = domConstruct.create("td", {"align": "center"}, tr);
				td3 = domConstruct.create("td", {"align": "right"}, tr);
				td4 = domConstruct.create("td", {"align": "right"}, tr);
				td5 = domConstruct.create("td", {"align": "center"}, tr);
				
				this.createSpan(td1, "Total");
				this.createSpan(td2, "100%");
				this.createSpan(td3, " ");
				this.createSpan(td4, this.formatCurrency(data.priceDetails.agreementvalue));
				this.createSpan(td5, " ");
            };
            
            populatePaymentDetails = function(data) {
            	if (data.paymentList.length == 0) {
            		this.createSpan(this.paymentDetailsDiv, "No Payment Details available");
            		return;
            	}
            	var tr, th1, th2, th3, th4, th5, th6, th7, th8, th9, td1, td2, td3, td4, td5, td6, td7, td8, td9;
				var table = domConstruct.create("table", {"style": "width:100%", "class": "printPageTableBorder"}, this.paymentDiv);
				tr = domConstruct.create("tr", null, table);
				th1 = domConstruct.create("th", null, tr);
				this.createSpan(th1, "Bank Name");
				th2 = domConstruct.create("th", null, tr);
				this.createSpan(th2, "Branch");
				th3 = domConstruct.create("th", null, tr);
				this.createSpan(th3, "Cheque Number / UTR Number");
				th4 = domConstruct.create("th", null, tr);
				this.createSpan(th4, "Cheque Date");
				th5 = domConstruct.create("th", null, tr);
				this.createSpan(th5, "Receipt Number");
				th6 = domConstruct.create("th", null, tr);
				this.createSpan(th6, "Alt Receipt Number");
				th7 = domConstruct.create("th", null, tr);
				this.createSpan(th7, "Category");
				th8 = domConstruct.create("th", null, tr);
				this.createSpan(th8, "Amount");
				th9 = domConstruct.create("th", null, tr);
				this.createSpan(th9, "Status");
				
				for (var i=0; i<data.paymentList.length; i++) {
					var payment = data.paymentList[i];
					tr = domConstruct.create("tr", null, table);
					
					td1 = domConstruct.create("td", {"align": "center"}, tr);
					td2 = domConstruct.create("td", {"align": "center"}, tr);
					td3 = domConstruct.create("td", {"align": "center"}, tr);
					td4 = domConstruct.create("td", {"align": "center"}, tr);
					td5 = domConstruct.create("td", {"align": "center"}, tr);
					td6 = domConstruct.create("td", {"align": "center"}, tr);
					td7 = domConstruct.create("td", {"align": "center"}, tr);
					td8 = domConstruct.create("td", {"align": "center"}, tr);
					td9 = domConstruct.create("td", {"align": "center"}, tr);
					
					this.createSpan(td1, payment.bankName);
					this.createSpan(td2, payment.bankBranch);
					if (payment.chequeNumber) {
						this.createSpan(td3, payment.chequeNumber);
	            	} else if (payment.utrNumber) {
	            		this.createSpan(td3, payment.utrNumber);
	            	}
					this.createSpan(td4, this.formatDate(payment.chequeDate));
					this.createSpan(td5, payment.receiptNumber);
					this.createSpan(td6, payment.altReceiptNumber);
					this.createSpan(td7, payment.paymentTypeName);
					this.createSpan(td8, this.formatCurrency(payment.receiptAmount));
					if (payment.paymentStatus != null) {
						this.createSpan(td9, payment.paymentStatus.name);	
					} else {
						this.createSpan(td9, "Not available");
					}
				}
            };
            
            createSpan = function(column, value) {
    			span = domConstruct.create("span", null, column);
    			if (value == null) {
    				value = "Not available";
    			}
    			span.innerHTML = value;
    		};
    		
    		getAgerFromDOB = function (dateString) {
    			var today = new Date();
    		    var birthDate = new Date(dateString);
    		    var age = today.getFullYear() - birthDate.getFullYear();
    		    var m = today.getMonth() - birthDate.getMonth();
    		    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    		        age--;
    		    }
    		    
    		    return age;
    		};
    		
    		formatDate = function (value) {
				try {
				    if (value) {
				      // NotesDateTime comes out in this format: 2014-10-21T14:47:38Z
				      var parsedDate = value.substring(0, 10);
				       
				      // Create and format the date 
				      var dt = new Date(parsedDate);
				      var dateString = locale.format(dt,
				        {
				          selector: "date",
				          datePattern: "dd-MMM-yyyy"
				        }); 
				       
				      return dateString;
				    }
				} catch (e) {
				    console.error('error decorating date: ' + e.toString());
				}
			};
    		
            formatCurrency = function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
			};
        });
</script>
</head>
<body class="claro">
<div class="containerDiv">
	<div id="headerDiv">
		<table width="100%" align="center">
			<tr><td align="center"><span style="font-weight: bold; font-size: 18px;" id="orgname" data-dojo-attach-point="orgname"></span></td></tr>
			<tr><td align="center"><span style="font-size: 12px;" id="orgaddress" data-dojo-attach-point="orgaddress"></span></td></tr>
			<tr><td align="center"><span style="font-size: 12px;" id="orgcontact" data-dojo-attach-point="orgcontact"></span></td></tr>
		</table>
	</div>
	<br>
	<div class="printBookingFormText" id="bookingFormDiv">
		<table class="printPageTableBorder" align="center">
			<tr><th width="14%"></th>
			<th width="17%"></th>
			<th width="14%"></th>
			<th width="16%"></th>
			<th width="14%"></th>
			<th width="16%"></th>
			<th width="14%"></th></tr>
			
			<tr><td><span>Finance Id</span></td>
			<td><span id="financeid" data-dojo-attach-point="financeid"></span></td>
			<td colspan="2" align="center" style="font-weight: bold; font-size: 18px;"><span>Booking Form</span></td>
			<td><span id="bookingformnumber" data-dojo-attach-point="bookingformnumber"></span></td>
			<td><span id="bookingformversion" data-dojo-attach-point="bookingformversion"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Source</span></td>
			<td><span id="source" data-dojo-attach-point="source"></span></td>
			<td><span>Sales Exec</span></td>
			<td><span id="user" data-dojo-attach-point="user"></span></td>
			<td><span>Booking Date</span></td>
			<td><span id="bookingdate" data-dojo-attach-point="bookingdate"></span></td>
			<td><span></span></td><tr>
			
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Personal Details</span></td><tr>
			
			<tr><td><span>First Name</span></td>
			<td><span id="firstname" data-dojo-attach-point="firstname"></span></td>
			<td><span>Middle Name</span></td>
			<td><span id="middlename" data-dojo-attach-point="middlename"></span></td>
			<td><span>Last Name</span></td>
			<td><span id="lastname" data-dojo-attach-point="lastname"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Birth Date</span></td>
			<td><span id="customerdob" data-dojo-attach-point="customerdob"></span></td>
			<td><span>Age</span></td>
			<td><span id="customerage" data-dojo-attach-point="customerage"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>First Name</span></td>
			<td><span id="firstname1" data-dojo-attach-point="firstname1"></span></td>
			<td><span>Middle Name</span></td>
			<td><span id="middlename1" data-dojo-attach-point="middlename1"></span></td>
			<td><span>Last Name</span></td>
			<td><span id="lastname1" data-dojo-attach-point="lastname1"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Birth Date</span></td>
			<td><span id="customerdob1" data-dojo-attach-point="customerdob1"></span></td>
			<td><span>Age</span></td>
			<td><span id="customerage1" data-dojo-attach-point="customerage1"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Address</span></td>
			<td colspan="5"><span id="customeraddress" data-dojo-attach-point="customeraddress"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Profession</span></td>
			<td><span id="customerprofession" data-dojo-attach-point="customerprofession"></span></td>
			<td><span>Details</span></td>
			<td colspan="3"><span id="customerotherdetails" data-dojo-attach-point="customerotherdetails"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Mobile</span></td>
			<td><span id="customermobile" data-dojo-attach-point="customermobile"></span></td>
			<td><span>Landline</span></td>
			<td><span id="customerphone" data-dojo-attach-point="customerphone"></span></td>
			<td><span>PAN Card</span></td>
			<td><span id="customerpan" data-dojo-attach-point="customerpan"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Email</span></td>
			<td colspan="2"><span id="customeremailid" data-dojo-attach-point="customeremailid"></span></td>
			<td><span>Category</span></td>
			<td><span id="customercategory" data-dojo-attach-point="customercategory"></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Flat Details</span></td><tr>
			
			<tr><td><span>Project Name</span></td>
			<td><span id="project" data-dojo-attach-point="project"></span></td>
			<td><span>Saleable</span></td>
			<td><span id="saleablearea" data-dojo-attach-point="saleablearea"></span></td>
			<td><span>Base Rate</span></td>
			<td><span id="baserate" data-dojo-attach-point="baserate"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Phase</span></td>
			<td><span id="projectphase" data-dojo-attach-point="projectphase"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span>Discount</span></td>
			<td><span id="discount" data-dojo-attach-point="discount"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Wing/Bldg</span></td>
			<td><span id="projectbuilding" data-dojo-attach-point="projectbuilding"></span></td>
			<td><span>Amenities Facing</span></td>
			<td><span id="amenitiesfacing" data-dojo-attach-point="amenitiesfacing"></span></td>
			<td><span>Amenities Charges</span></td>
			<td><span id="amenitycharges" data-dojo-attach-point="amenitycharges"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Flat No.</span></td>
			<td><span id="unitnumber" data-dojo-attach-point="unitnumber"></span></td>
			<td><span>Floor Rise</span></td>
			<td><span id="floorrisebool" data-dojo-attach-point="floorrisebool"></span></td>
			<td><span></span></td>
			<td><span id="floorrise" data-dojo-attach-point="floorrise"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Flat Type</span></td>
			<td><span id="unittype" data-dojo-attach-point="unittype"></span></td>
			<td colspan="4"><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Floor Type</span></td>
			<td><span id="floortype" data-dojo-attach-point="floortype"></span></td>
			<td><span>Other Options</span></td>
			<td><span id="otheroptions" data-dojo-attach-point="otheroptions"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Floor No.</span></td>
			<td><span id="floornumber" data-dojo-attach-point="floornumber"></span></td>
			<td><span>Total Rate</span></td>
			<td><span id="totalrate" data-dojo-attach-point="totalrate"></span></td>
			<td><span>Reckoner Rate</span></td>
			<td><span id="reckonerrate" data-dojo-attach-point="reckonerrate"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Extra Bkng Amt</span></td>
			<td><span id="extrabookingamount" data-dojo-attach-point="extrabookingamount"></span></td>
			<td><span>Other Charges</span></td>
			<td><span id="othercharges" data-dojo-attach-point="othercharges"></span></td>
			<td><span>Deduction on Other Charges</span></td>
			<td><span id="deductiononothercharges" data-dojo-attach-point="deductiononothercharges"></span></td>
			<td><span></span></td><tr>
			
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Financial Details</span></td><tr>
			
			<tr><td><span>Basic Cost</span></td>
			<td><span id="basiccost" data-dojo-attach-point="basiccost"></span></td>
			<td><span>Total Tax</span></td>
			<td><span id="totaltax" data-dojo-attach-point="totaltax"></span></td>
			<td><span>Maintenance 1</span></td>
			<td><span id="maintenance1amt" data-dojo-attach-point="maintenance1amt"></span></td>
			<td><span id="maintenance1" data-dojo-attach-point="maintenance1"></span></td><tr>
			
			<tr><td><span>Other Charges (with deduction)</span></td>
			<td><span id="otherchargeswithdeduction" data-dojo-attach-point="otherchargeswithdeduction"></span></td>
			<td><span>Legal Charges</span></td>
			<td><span id="legalcharges" data-dojo-attach-point="legalcharges"></span></td>
			<td><span>Maintenance 2</span></td>
			<td><span id="maintenance2amt" data-dojo-attach-point="maintenance2amt"></span></td>
			<td><span id="maintenance2" data-dojo-attach-point="maintenance2"></span></td><tr>
			
			<tr><td><span>Agrmnt Value</span></td>
			<td><span id="agreementvalue" data-dojo-attach-point="agreementvalue"></span></td>
			<td><span>Total with Tax</span></td>
			<td><span id="totalwithtax" data-dojo-attach-point="totalwithtax"></span></td>
			<td><span>Total Cost</span></td>
			<td><span id="totalcost" data-dojo-attach-point="totalcost"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Bank Details</span></td>
			<td colspan="6"><span id="builderaccountinformation" data-dojo-attach-point="builderaccountinformation"></span></td><tr>
			
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Tax Calculations</span></td><tr>
			
			<tr><td><span>Stamp Duty</span></td>
			<td><span id="stampdutypercent" data-dojo-attach-point="stampdutypercent"></span></td>
			<td><span id="stampduty" data-dojo-attach-point="stampduty"></span></td>
			<td><span>VAT</span></td>
			<td><span id="valueaddedtaxpercent" data-dojo-attach-point="valueaddedtaxpercent"></span></td>
			<td><span id="valueaddedtax" data-dojo-attach-point="valueaddedtax"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Service Tax</span></td>
			<td><span id="servicetaxpercent" data-dojo-attach-point="servicetaxpercent"></span></td>
			<td><span id="servicetax" data-dojo-attach-point="servicetax"></span></td>
			<td><span>Registration</span></td>
			<td><span id="registrationpercent" data-dojo-attach-point="registrationpercent"></span></td>
			<td><span id="registration" data-dojo-attach-point="registration"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Total</span></td>
			<td><span id="sumtaxpercent" data-dojo-attach-point="sumtaxpercent"></span></td>
			<td><span id="sumtax" data-dojo-attach-point="sumtax"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
			
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Financial Details (Ready Reckoner)</span></td><tr>
			
			<tr><td><span>Basic Cost</span></td>
			<td><span id="basiccostrr" data-dojo-attach-point="basiccostrr"></span></td>
			<td><span>Total Tax</span></td>
			<td><span id="totaltaxrr" data-dojo-attach-point="totaltaxrr"></span></td>
			<td><span>Maintenance 1</span></td>
			<td><span id="maintenance1amtrr" data-dojo-attach-point="maintenance1amtrr"></span></td>
			<td><span id="maintenance1rr" data-dojo-attach-point="maintenance1rr"></span></td><tr>

			<tr><td><span>Floor Rise and Parking</span></td>
			<td><span id="floreriseandparking" data-dojo-attach-point="floreriseandparking"></span></td>
			<td><span>Legal Charges</span></td>
			<td><span id="legalchargesrr" data-dojo-attach-point="legalchargesrr"></span></td>
			<td><span>Maintenance 2</span></td>
			<td><span id="maintenance2amtrr" data-dojo-attach-point="maintenance2amtrr"></span></td>
			<td><span id="maintenance2rr" data-dojo-attach-point="maintenance2rr"></span></td><tr>
			
			<tr><td><span>Market Value</span></td>
			<td><span id="marketvalue" data-dojo-attach-point="marketvalue"></span></td>
			<td><span>Total with Tax</span></td>
			<td><span id="totalwithtaxrr" data-dojo-attach-point="totalwithtaxrr"></span></td>
			<td><span>Total Cost</span></td>
			<td><span id="totalcostrr" data-dojo-attach-point="totalcostrr"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Bank Details</span></td>
			<td colspan="6"><span id="builderaccountinformationrr" data-dojo-attach-point="builderaccountinformationrr"></span></td><tr>
				
			<tr><td colspan="7" align="center" style="font-weight: bold; font-size: 18px;"><span>Tax Calculations (Ready Reckoner)</span></td><tr>
			
			<tr><td><span>Stamp Duty</span></td>
			<td><span id="stampdutypercentrr" data-dojo-attach-point="stampdutypercentrr"></span></td>
			<td><span id="stampdutyrr" data-dojo-attach-point="stampdutyrr"></span></td>
			<td><span>VAT</span></td>
			<td><span id="valueaddedtaxpercentrr" data-dojo-attach-point="valueaddedtaxpercentrr"></span></td>
			<td><span id="valueaddedtaxrr" data-dojo-attach-point="valueaddedtaxrr"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Service Tax</span></td>
			<td><span id="servicetaxpercentrr" data-dojo-attach-point="servicetaxpercentrr"></span></td>
			<td><span id="servicetaxrr" data-dojo-attach-point="servicetaxrr"></span></td>
			<td><span>Registration</span></td>
			<td><span id="registrationpercentrr" data-dojo-attach-point="registrationpercentrr"></span></td>
			<td><span id="registrationrr" data-dojo-attach-point="registrationrr"></span></td>
			<td><span></span></td><tr>
			
			<tr><td><span>Total</span></td>
			<td><span id="sumtaxpercentrr" data-dojo-attach-point="sumtaxpercentrr"></span></td>
			<td><span id="sumtaxrr" data-dojo-attach-point="sumtaxrr"></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td>
			<td><span></span></td><tr>
		</table>
	</div>
	<br>
	<div id="footerDiv">
		<table width="100%" align="center"><tr>
			<td>
				<table align="left">
					<tr><td><span>Customer Signature</span></td></tr>
					<tr><td><div class="receiptSignDiv"></div></td></tr>
				</table>
			</td>
			<td>
				<table align="right">
					<tr><td><span>Sales Executive Signature</span></td></tr>
					<tr><td><div class="receiptSignDiv"></div></td></tr>
				</table>
			</td>
		</tr></table>
	</div>
</div>
<p style="page-break-before:always;"></p>
<div class="containerDiv">
	<div id="paymentScheduleDetailsDiv">
		<table width="100%">
			<tr><td align="center"><span style="font-weight: bold; font-size: 18px;">Payment Schedule</span></td></tr>
			<tr><td><div style="font-family: arial-body; font-size: 13px;" id="paymentScheduleDiv" data-dojo-attach-point="paymentScheduleDiv"></div></td></tr>
		</table>
	</div>
	<br>
	<div id="termsAndConditionsDiv">
		<table>
			<tr><td><span>Terms & Conditions</span></td></tr>
			<tr><td><span>&nbsp;</span></td></tr>
			<tr><td><span>We accept the following terms and conditions to book above mentioned flat</span></td></tr>
		</table>
		<span id="termsandconditions" data-dojo-attach-point="termsandconditions"></span>
		<span id="tandc">I/We have read all above terms and conditions and abide to the same</span>
	</div>
	
	<div id="footerDiv">
		<table width="100%" align="center"><tr>
			<td>
				<table align="left">
					<tr><td><span>Customer Signature</span></td></tr>
					<tr><td><div class="receiptSignDiv"></div></td></tr>
				</table>
			</td>
			<td>
				<table align="right">
					<tr><td><span>Sales Executive Signature</span></td></tr>
					<tr><td><div class="receiptSignDiv"></div></td></tr>
				</table>
			</td>
		</tr></table>
	</div>
</div>
<p style="page-break-before:always;"></p>
<div class="containerDiv">
	<div id="paymentDetailsDiv">
		<table width="100%">
			<tr><td align="center"><span style="font-weight: bold; font-size: 18px;">Sales Team - Payment Received - Instrument Details</span></td></tr>
			<tr><td><div style="font-family: arial-body; font-size: 13px;" id="paymentDiv" data-dojo-attach-point="paymentDiv"></div></td></tr>
		</table>
	</div>
	<br>
	<div id="footerDiv">
		<table width="100%" align="center"><tr>
			<td>
				<table align="left">
					<tr><td><span>Remarks</span></td></tr>
					<tr><td><div class="printBookingFormRemarksDiv"></div></td></tr>
				</table>
			</td>
		</tr></table>
	</div>
</div>
</body>
</html>