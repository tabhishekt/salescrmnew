<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand Letter</title>
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
            	this.dateSpan.innerHTML = this.formatDate(Date());
            	
            	this.bookingformnumber.innerHTML = data.bookingFormNumber;
            	this.customername.innerHTML = data.customer.displayName;
            	this.customeraddress.innerHTML = data.customer.displayAddress;
            	if (data.customer.person.contactInfo.phoneNumber) {
            		this.customerphone.innerHTML = data.customer.person.contactInfo.phoneNumber;	
            	} 
            	
            	if (data.customer.person.contactInfo.mobileNumber) {
            		this.customermobile.innerHTML = data.customer.person.contactInfo.mobileNumber;	
            	} 
            	
            	if (data.customer.person.contactInfo.emailID) {
            		this.customeremailid.innerHTML = data.customer.person.contactInfo.emailID;	
            	} 
            	
            	this.unitnumber.innerHTML = data.unit.unitNumber;
            	this.projectbuilding.innerHTML = data.unit.displayProjectInfo;
            	
            	this.projectbuildingcurrentstatus.innerHTML = data.buildingCurrentStatus.name;
            	this.balancepaymentcurrentstatus.innerHTML = this.formatCurrency(data.balancePaymentForCurrentStatus);
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
			<div id="orgDetailsDiv">
				<table style="width: 100%;">
				<tr><td colspan="3"><span id="orgname" data-dojo-attach-point="orgname"></span></td></tr>
				<tr><td colspan="3"><span id="orgaddress" data-dojo-attach-point="orgaddress"></span></td></tr>
				<tr><td><span id="orgphone" data-dojo-attach-point="orgphone"></span></td>
				<td><span id="orgmobile" data-dojo-attach-point="orgmobile"></span></td>
				<td><span id="orgemailid" data-dojo-attach-point="orgemailid"></span></td></tr>
				</table>
			</div>
			Date: <span id="dateSpan" data-dojo-attach-point="dateSpan"></span>
			<br><br>
			Booking Ref: <span id="bookingformnumber" data-dojo-attach-point="bookingformnumber"></span><br><br>
			<div id = "customerDetailsDiv">
				<span id="customername" data-dojo-attach-point="customername"></span><br>
				<span id="customeraddress" data-dojo-attach-point="customeraddress"></span><br>
				<span id="customerphone" data-dojo-attach-point="customerphone"></span><br>
				<span id="customermobile" data-dojo-attach-point="customermobile"></span><br>
				<span id="customeremailid" data-dojo-attach-point="customeremailid"></span><br>
			</div>
			<br><br>
			<div id="letterSubjectDiv">
				Sub: Demand Letter for payment towards 
				<span id="unitnumber" data-dojo-attach-point="unitnumber"></span>
				<span id="projectbuilding" data-dojo-attach-point="projectbuilding"></span>
			</div>
			<br>
			<div id="letterBodyDiv">
				Dear Sir / Madam,<br><br>
				
				We are pleased to inform you that current status of your building is 
				"<span id="projectbuildingcurrentstatus" data-dojo-attach-point="projectbuildingcurrentstatus"></span>".<br><br>
				As per the agreed advance disbursement plan with you, you are requested to kindly arrange for the payment of
				<span id="balancepaymentcurrentstatus" data-dojo-attach-point="balancepaymentcurrentstatus"></span>
				<br><br>
				
				Thanking you in anticipation of your prompt response and co-operation.<br><br>
				
				Yours truly<br><br><br><br><br><br>
				Authorized Signature
			</div>
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
<p>		
<div><input type="button" value="Print this page" onClick="window.print()"></div>
<br><br>
</body>
</html>