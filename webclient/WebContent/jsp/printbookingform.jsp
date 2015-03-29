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
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/dojo_1.10.2/dojo/dojo.js"></script>
<script>
    require(["dojo/_base/lang",
             "dijit/registry",
             "dojo/on",
             "dojo/request", 
             "dojo/currency",
             "dojo/date/locale",
             "dojo/dom-construct",
			 "dojo/domReady!"],
        function(lang, registry, on, request, currency, locale, domConstruct) {
            load = function () {
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
            	this.orgphone.innerHTML = data.organization.contactInfo.phoneNumber + ", ";
            	this.orgmobile.innerHTML = data.organization.contactInfo.mobileNumber + ", ";
            	this.orgemailid.innerHTML = data.organization.contactInfo.emailID;
            	
            	this.bookingformnumber.innerHTML = data.bookingFormNumber;
            	this.user.innerHTML = data.userDisplayName;
            	this.bookingdate.innerHTML = this.formatDate(data.bookingDate);
            	
            	this.customername.innerHTML = data.customer.displayName;
            	this.customerdob.innerHTML = data.customer.person.dateOfBirth;
            	this.customerage.innerHTML = this.getAgerFromDOB(data.customer.person.dateOfBirth);
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
            	
            	
            	this.projectbuilding.innerHTML = data.unit.displayProjectInfo;
            	this.saleablearea.innerHTML = data.unit.saleableArea;
            	this.baserate.innerHTML = this.formatCurrency(data.priceDetails.baserate);
            	this.discount.innerHTML = this.formatCurrency(data.priceDetails.discount);
            	this.amenitiesfacing.innerHTML = data.unit.amenitiesFacing;
            	this.unitnumber.innerHTML = data.unit.unitNumber;
            	this.floorrise.innerHTML = this.formatCurrency(data.unit.floorRise);
            	this.unittype.innerHTML = data.unit.unitType.name;
            	this.floortype.innerHTML = data.unit.floorType.name;
            	this.otheroptions.innerHTML = data.unit.otherOptions;
            	this.floornumber.innerHTML = data.unit.floorNumber;
            	this.totalrate.innerHTML = this.formatCurrency(data.priceDetails.totalrate);
            	
            	this.basiccost.innerHTML = this.formatCurrency(data.priceDetails.basicCost);
            	this.totaltax.innerHTML = this.formatCurrency(data.priceDetails.totalTax);
            	this.maintenance1.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge1);
            	this.othercharges.innerHTML = this.formatCurrency(data.priceDetails.othercharges);
            	this.legalcharges.innerHTML = this.formatCurrency(data.priceDetails.legalcharge);
            	this.maintenance2.innerHTML = this.formatCurrency(data.priceDetails.maintenancecharge2);
            	this.agreementvalue.innerHTML = this.formatCurrency(data.priceDetails.agreementvalue);
            	this.totalwithtax.innerHTML = this.formatCurrency(data.priceDetails.totalCostWithTax);
            	this.totalcost.innerHTML = this.formatCurrency(data.priceDetails.totalCost);
            	this.bankdetails.innerHTML = data.displayBankInformation;
            	
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
            	
            	this.termsandconditions.innerHTML = data.termsAndConditions;
            	this.unitbookingamount.innerHTML = this.formatCurrency(data.unit.bookingAmount);
            	this.totalpaymentreceived.innerHTML = this.formatCurrency(data.totalPaymentReceived);
            };
            
            populatePaymentSchedule = function(data) {
            	var currentPosition = data.buildingCurrentStatus.id;
            	if (data.scheduleList.length == 0) {
            		this.createSpan(this.paymentScheduleDiv, "paymentschedulenotavailable", "No Payment Schedule Details available");
            		return;
            	}
            	var tr, th1, th2, th3, th4, td1, td2, td3, td4;
				var table = domConstruct.create("table", {"style": "width:100%"}, this.paymentScheduleDiv);
				tr = domConstruct.create("tr", null, table);
				th1 = domConstruct.create("th", null, tr);
				this.createSpan(th1, "header_1", "Type");
				th2 = domConstruct.create("th", null, tr);
				this.createSpan(th2, "header_2", "Percent Amount");
				th3 = domConstruct.create("th", null, tr);
				this.createSpan(th3, "header_3", "Date");
				th4 = domConstruct.create("th", null, tr);
				this.createSpan(th4, "header_4", "Amount");
					
				for (var i=0; i<data.scheduleList.length; i++) {
					var schedule = data.scheduleList[i];
					if (schedule.position <= currentPosition) {
						tr = domConstruct.create("tr", {"bgcolor": "#009900"}, table);	
					} else {
						tr = domConstruct.create("tr", null, table);
					}
					
					
					td1 = domConstruct.create("td", null, tr);
					td2 = domConstruct.create("td", null, tr);
					td3 = domConstruct.create("td", null, tr);
					td4 = domConstruct.create("td", null, tr);
					
					this.createSpan(td1, "type_" + i, schedule.type);
					this.createSpan(td2, "percentamount" + i, schedule.percentamount);
					this.createSpan(td3, "scheduledate_" + i, this.formatDate(schedule.scheduledate));
					this.createSpan(td4, "amount_" + i, this.formatCurrency(schedule.amount));
				}
            };
            
            populatePaymentDetails = function(data) {
            	if (data.paymentList.length == 0) {
            		this.createSpan(this.paymentDetailsDiv, "paymentnotavailable", "No Payment Details available");
            		return;
            	}
            	var tr, th1, th2, th3, th4, th5, th6, th7, th8, th9, td1, td2, td3, td4, td5, td6, td7, td8, td9;
				var table = domConstruct.create("table", {"style": "width:100%"}, this.paymentDetailsDiv);
				tr = domConstruct.create("tr", null, table);
				th1 = domConstruct.create("th", null, tr);
				this.createSpan(th1, "header_1", "Bank Name");
				th2 = domConstruct.create("th", null, tr);
				this.createSpan(th2, "header_2", "Branch");
				th3 = domConstruct.create("th", null, tr);
				this.createSpan(th3, "header_3", "Cheque Number");
				th4 = domConstruct.create("th", null, tr);
				this.createSpan(th4, "header_4", "Cheque Date");
				th5 = domConstruct.create("th", null, tr);
				this.createSpan(th5, "header_5", "Receipt Number");
				th6 = domConstruct.create("th", null, tr);
				this.createSpan(th6, "header_6", "Alt Receipt Number");
				th7 = domConstruct.create("th", null, tr);
				this.createSpan(th7, "header_7", "Category");
				th8 = domConstruct.create("th", null, tr);
				this.createSpan(th8, "header_8", "Amount");
				th9 = domConstruct.create("th", null, tr);
				this.createSpan(th9, "header_9", "Status");
				
				for (var i=0; i<data.paymentList.length; i++) {
					var payment = data.paymentList[i];
					tr = domConstruct.create("tr", null, table);
					
					td1 = domConstruct.create("td", null, tr);
					td2 = domConstruct.create("td", null, tr);
					td3 = domConstruct.create("td", null, tr);
					td4 = domConstruct.create("td", null, tr);
					td5 = domConstruct.create("td", null, tr);
					td6 = domConstruct.create("td", null, tr);
					td7 = domConstruct.create("td", null, tr);
					td8 = domConstruct.create("td", null, tr);
					td9 = domConstruct.create("td", null, tr);
					
					this.createSpan(td1, "bank_" + i, payment.bankName);
					this.createSpan(td2, "branch" + i, payment.bankBranch);
					this.createSpan(td3, "chequenum_" + i, payment.chequeNumber);
					this.createSpan(td4, "chequedt_" + i, this.formatDate(payment.chequeDate));
					this.createSpan(td5, "receiptnum_" + i, payment.receiptNumber);
					this.createSpan(td6, "altreceiptnum_" + i, payment.altReceiptNumber);
					this.createSpan(td7, "category_" + i, payment.paymentTypeName);
					this.createSpan(td8, "amount_" + i, this.formatCurrency(payment.receiptAmount));
					this.createSpan(td9, "amount_" + i, payment.paymentStatus.name);
				}
            };
            
            createSpan = function(column, id, value) {
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
<body class="claro"  onload="this.load()">
<div>	
	<table><tr>
			<td width="50%"><table>
				<tr><td><div id="orgDetailsDiv">
					<table style="width: 100%;">
					<tr><td colspan="3"><span id="orgname" data-dojo-attach-point="orgname"></span></td></tr>
					<tr><td colspan="3"><span id="orgaddress" data-dojo-attach-point="orgaddress"></span></td></tr>
					<tr><td><span id="orgphone" data-dojo-attach-point="orgphone"></span></td>
					<td><span id="orgmobile" data-dojo-attach-point="orgmobile"></span></td>
					<td><span id="orgemailid" data-dojo-attach-point="orgemailid"></span></td></tr>
					</table>
				</div></td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Booking Form:</legend>
							<div id="bookingDetailsDiv">
								<table style="width: 100%;">
								<tr><td><label for="bookingformnumber">Sr. No.:</label></td><td><span id="bookingformnumber" data-dojo-attach-point="bookingformnumber"></span></td>
								<td><label for="bookingdate">Booking Date:</label></td><td><span id="bookingdate" data-dojo-attach-point="bookingdate"></span></td></tr>
								<tr><td><label for="user">Sales Executive:</label></td><td colspan="3"><span id="user" data-dojo-attach-point="user"></span></td></tr>
								</table>
							</div>
					</fieldset>
				</td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Personal Details:</legend>
						<div id="customerDetailsDiv">
							<table style="width: 100%;">
							<tr><td><label for="customername">Customer Name:</label></td><td><span id="customername" data-dojo-attach-point="customername"></span></td></tr>
							<tr><td><label for="customerdob">Date Of Birth:</label></td><td><span id="customerdob" data-dojo-attach-point="customerdob"></span></td>
							<td><label for="customerage">Age:</label></td><td><span id="customerage" data-dojo-attach-point="customerage"></span></td></tr>
							<tr><td><label for="customerprofession">Profession:</label></td><td><span id="customerprofession" data-dojo-attach-point="customerprofession"></span></td>
							<td><label for="customerotherdetails">Details:</label></td><td><span id="customerotherdetails" data-dojo-attach-point="customerotherdetails"></span></td></tr>
							<tr><td><label for="customerphone">Phone:</label></td><td><span id="customerphone" data-dojo-attach-point="customerphone"></span></td>
							<td><label for="customermobile">Mobile:</label></td><td><span id="customermobile" data-dojo-attach-point="customermobile"></span></td></tr>
							<tr><td><label for="customeremailid">Email Id:</label></td><td colspan="3"><span id="customeremailid" data-dojo-attach-point="customeremailid"></span></td></tr>
							<tr><td><label for="customeraddress">Address:</label></td><td colspan="5"><span id="customeraddress" data-dojo-attach-point="customeraddress"></span></td></tr>
							</table>
						</div>
					</fieldset>
				</td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Flat Details:</legend>
						<div id="unitDetailsDiv">
							<table style="width: 100%;">
							<tr><td><label for="projectbuilding">Project:</label></td><td colspan="5"><span id="projectbuilding" data-dojo-attach-point="projectbuilding"></span></td></tr>
							<tr><td><label for="unitnumber">Flat Number.:</label></td><td><span id="unitnumber" data-dojo-attach-point="unitnumber"></span></td>
							<td><label for="floornumber">Floor Number:</label></td><td><span id="floornumber" data-dojo-attach-point="floornumber"></span></td></tr>
							<tr><td><label for="saleablearea">Saleable Area:</label></td><td><span id="saleablearea" data-dojo-attach-point="saleablearea"></span></td>
							<td><label for="baserate">Base Rate:</label></td><td><span id="baserate" data-dojo-attach-point="baserate"></span></td></tr>
							<tr><td><label for="floorrise">Floor Rise:</label></td><td><span id="floorrise" data-dojo-attach-point="floorrise"></span></td>
							<td><label for="discount">Discount:</label></td><td><span id="discount" data-dojo-attach-point="discount"></span></td></tr><tr>
							<tr><td><label for="unittype">Flat Type:</label></td><td><span id="unittype" data-dojo-attach-point="unittype"></span></td>
							<td><label for="floortype">Floor Type:</label></td><td><span id="floortype" data-dojo-attach-point="floortype"></span></td></tr>
							<tr><td><label for="amenitiesfacing">Amenities Facing:</label></td><td><span id="amenitiesfacing" data-dojo-attach-point="amenitiesfacing"></span></td>
							<td><label for="otheroptions">Other Options:</label></td><td><span id="otheroptions" data-dojo-attach-point="otheroptions"></span></td></tr>
							<tr><td><label for="totalrate">Total Rate:</label></td><td><span id="totalrate" data-dojo-attach-point="totalrate"></span></td></tr>
							</table>
						</div>
					</fieldset>
				</td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Financial Details:</legend>
						<div id="unitPriceDetailsDiv">
							<table style="width: 100%;">
							<tr><td><label for="basiccost">Basic Cost:</label></td><td><span id="basiccost" data-dojo-attach-point="basiccost"></span></td>
							<td><label for="totaltax">Total Tax:</label></td><td><span id="totaltax" data-dojo-attach-point="totaltax"></span></td></tr>
							<tr><td><label for="maintenance1">Maintenance 1:</label></td><td><span id="maintenance1" data-dojo-attach-point="maintenance1"></span></td>
							<td><label for="othercharges">Other Charges:</label></td><td><span id="othercharges" data-dojo-attach-point="othercharges"></span></td></tr>
							<tr><td><label for="maintenance2">Maintenance 2:</label></td><td><span id="maintenance2" data-dojo-attach-point="maintenance2"></span></td>
							<td><label for="legalcharges">Legal Charges:</label></td><td><span id="legalcharges" data-dojo-attach-point="legalcharges"></span></td></tr>
							<tr><td><label for="agreementvalue">Agreement Value:</label></td><td><span id="agreementvalue" data-dojo-attach-point="agreementvalue"></span></td>
							<td><label for="totalwithtax">Total Cost with Tax:</label></td><td><span id="totalwithtax" data-dojo-attach-point="totalwithtax"></span></td></tr>
							<tr><td><label for="totalcost">Total Cost:</label></td><td><span id="totalcost" data-dojo-attach-point="totalcost"></span></td></tr>
							</table>
						</div>
					</fieldset>
				</td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Bank Details:</legend>
						<div id="bankDetailsDiv">
						<table style="width: 100%;">
							<tr><td><span id="bankdetails" data-dojo-attach-point="bankdetails"></span></td></tr>
						</table>
						</div>
					</fieldset>
				</td></tr>
				<tr><td>
					<fieldset style="width: 98%;">
	 					<legend>Tax Calculation:</legend>
						<div id="unitTaxDetailsDiv">
							<table style="width: 100%;">
							<tr><td><label for="stampduty">Stamp Duty:</label></td><td><span id="stampdutypercent" data-dojo-attach-point="stampdutypercent"></span>
								</td><td><span id="stampduty" data-dojo-attach-point="stampduty"></span></td>
							<td><label for="valueaddedtax">VAT:</label></td><td><span id="valueaddedtaxpercent" data-dojo-attach-point="valueaddedtaxpercent"></span></td>
								<td><span id="valueaddedtax" data-dojo-attach-point="valueaddedtax"></span></td></tr>
							<tr><td><label for="servicetax">Service Tax:</label></td><td><span id="servicetaxpercent" data-dojo-attach-point="servicetaxpercent"></span></td>
								<td><span id="servicetax" data-dojo-attach-point="servicetax"></span></td>
							<td><label for="registration">Registration:</label></td><td><span id="registrationpercent" data-dojo-attach-point="registrationpercent"></span></td>
								<td><span id="registration" data-dojo-attach-point="registration"></span></td></tr>
							<tr><td><label for="sumtax">Total Tax:</label></td><td><span id="sumtaxpercent" data-dojo-attach-point="sumtaxpercent"></span></td>
								<td><span id="sumtax" data-dojo-attach-point="sumtax"></span></td></tr>
							</table>
						</div>
					</fieldset>
				</td></tr>
			</table></td>
	</tr></table>
</div>
<p style="page-break-before:always;"></p>
<div>
<table>
<tr><td><div id="termsAndConditionsDiv">
			<table>
			<tr><td><span>Terms & Conditions</span></td></tr>
			<tr><td><span>&nbsp;</span></td></tr>
			<tr><td><span>We accept the following terms and conditions to book above mentioned flat</span></td></tr>
			</table>
			<span id="termsandconditions" data-dojo-attach-point="termsandconditions"></span>
			<span id="tandc">I/We have read all above terms and conditions and abide to the same</span>
			<table>
			<tr><td><span>&nbsp;</span></td></tr>
			<tr><td><span>&nbsp;</span></td></tr>
			<tr><td><span>&nbsp;</span></td></tr>
			<tr><td><label>Customer Signature:</label></td>
			<td><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
			<td><label>Sales Executive Signature:</label></td>
			</tr></table>
		</div></td></tr>
		<tr><td><div id="signature"></div></td></tr>
</table>
</div>
<p style="page-break-before:always;"></p>
<div>
	<div id="paymentScheduleDetailsDiv">
		<table>
			<tr><td>
				Booking Amount: <span id="unitbookingamount" data-dojo-attach-point="unitbookingamount"></span>
			</td></tr>
			<tr><td valign="top">
				<table>
					<tr><td>
						<fieldset style="width: 100%;">
			 				<legend>Payment Schedule:</legend>
							<div id="paymentScheduleDiv" data-dojo-attach-point="paymentScheduleDiv"></div>
						</fieldset>
					</td></tr>
					<tr><td>
						<fieldset style="width: 100%;">
			 				<legend>Payment Details:</legend>
							<div id="paymentDetailsDiv" data-dojo-attach-point="paymentDetailsDiv"></div>
						</fieldset>
					</td></tr>
				</table>
			</td></tr>
			<tr><td>
				Total Payment Received: <span id="totalpaymentreceived" data-dojo-attach-point="totalpaymentreceived"></span>
			</td></tr>
	</table>
	</div>
</div>
<BR>		
<div><input type="button" value="Print this page" onClick="window.print()"></div>
<br><br>
</body>
</html>