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
       					this.populateData(response.data);
       				},
       				function(error) {
       					this.gridHandler.updateMessage("Problem occured fetching price details: " + error.response.data.message, "error");
       				}
       			);
            };
            
            populateData = function(data) {
            	this.datespan.innerHTML = this.formatDate(data.currentDate);
            	this.customername.innerHTML = data.customer.displayName;
            	this.displayprojectinfo.innerHTML = data.displayProjectInfo;
            	this.unitnumber.innerHTML = data.unit.unitNumber;
            	this.currentstage.innerHTML = data.buildingCurrentStatus.name;
            	this.agreementvalue.innerHTML = this.formatCurrency(data.priceDetails.agreementvalue);
            	this.totaldue.innerHTML = this.formatCurrency(data.totalDueForCurrentStatus);
            	this.totalpaymentreceived.innerHTML = this.formatCurrency(data.totalPaymentReceived);
            	this.totaloutstanding.innerHTML = this.formatCurrency(data.balancePaymentForCurrentStatus);
            	this.builderaccountinformation.innerHTML = data.projectBankAccounts["BLDRAC"];
            	this.servicetax.innerHTML = this.formatCurrency(data.priceDetails.servicetax);
            	this.servicetaxpercent.innerHTML = data.unit.unitPricePolicy.servicetax + "%";
            	this.valueaddedtax.innerHTML = this.formatCurrency(data.priceDetails.valueaddedtax);
            	this.valueaddedtaxpercent.innerHTML = data.unit.unitPricePolicy.valueaddedtax + "%";
            	this.taxaccountinformation.innerHTML = data.projectBankAccounts["TAXAC"];
            	this.baserate.innerHTML = this.formatCurrency(data.unit.unitPricePolicy.baserate);
            	this.stampduty.innerHTML = this.formatCurrency(data.priceDetails.stampduty);
            	this.stampdutypercent.innerHTML = "(" + data.unit.unitPricePolicy.stampduty + "%)";
            	this.registration.innerHTML = this.formatCurrency(data.priceDetails.registrationcharge);
            	this.registrationpercent.innerHTML = "(" + data.unit.unitPricePolicy.registrationcharge + "%)";
            	this.legalcharges.innerHTML = this.formatCurrency(data.priceDetails.legalcharge);
            	var total = data.priceDetails.stampduty + data.priceDetails.registrationcharge + data.priceDetails.legalcharge;  
            	this.totaltaxcharges.innerHTML = this.formatCurrency(total);
            	this.legalaccountinformation.innerHTML = data.projectBankAccounts["LEGAC"];
            	this.orgname.innerHTML = data.organization.name;
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
<div class="containerDiv">
	<div class="printDemandLetterText" id="headerDiv">
		<table>
			<tr><td><span id="datespan" data-dojo-attach-point="datespan"></span></td></tr>
			<tr><td><span>Dear Mr/Mrs</span></td><td><span id="customername" data-dojo-attach-point="customername"></span></td></tr>
		</table>
		<br>
		<span class="printDemandLetterText">Sub: Outstanding Payment request</span><br><br>
		<span class="printDemandLetterText">Dear Sir/Madam</span><br>
		<span class="printDemandLetterText">Request your kind attention towards payments for your purchase of Flat at </span>
		<span id="displayprojectinfo" data-dojo-attach-point="displayprojectinfo"></span>
	</div>
	<br><br>
	<div id="demandDiv1">
		<table class="demandLetterTable printPageSpan">
			<tr>
				<td class="demandLetterTableColLabel"><span>Flat No</span></td>
				<td class="demandLetterTableCol"><span id="unitnumber" data-dojo-attach-point="unitnumber"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Current Construction Stage</span></td>
				<td class="demandLetterTableCol"><span id="currentstage" data-dojo-attach-point="currentstage"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Agreement Value</span></td>
				<td class="demandLetterTableCol"><span id="agreementvalue" data-dojo-attach-point="agreementvalue"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Total Due till Date</span></td>
				<td class="demandLetterTableCol"><span id="totaldue" data-dojo-attach-point="totaldue"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Payment Received Till Date</span></td>
				<td class="demandLetterTableCol"><span id="totalpaymentreceived" data-dojo-attach-point="totalpaymentreceived"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Total Outstanding Till Date</span></td>
				<td class="demandLetterTableCol"><span id="totaloutstanding" data-dojo-attach-point="totaloutstanding"></span></td>
			</tr>
		</table><br>
		<span class="printDemandLetterText">We hereby request you to send Cheque/DD/NEFT/RTGS/Cash to following bank account</span><br>
		<span class="rintDemandLetterTextSmall" id="builderaccountinformation" data-dojo-attach-point="builderaccountinformation"></span>
	</div><br>
	<div id="demandDiv2">
		<table class="demandLetterTable printDemandLetterText">
			<tr>
				<td class="demandLetterTableColLabel"><span>Service Tax</span></td>
				<td class="demandLetterTableCol"><span id=servicetax data-dojo-attach-point="servicetax"></span></td>
				<td class="demandLetterTableCol"><span id="servicetaxpercent" data-dojo-attach-point="servicetaxpercent"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>VAT</span></td>
				<td class="demandLetterTableCol"><span id="valueaddedtax" data-dojo-attach-point="valueaddedtax"></span></td>
				<td class="demandLetterTableCol"><span id="valueaddedtaxpercent" data-dojo-attach-point="valueaddedtaxpercent"></span></td>
			</tr>
		</table><br>
		<span class="printDemandLetterText">We hereby request you to send Cheque/DD/NEFT/RTGS/Cash to following bank account</span><br>
		<span class="rintDemandLetterTextSmall" id="taxaccountinformation" data-dojo-attach-point="taxaccountinformation"></span>
	</div><br>
	<div id="demandDiv3">
		<table class="demandLetterTable printDemandLetterText">
			<tr>
				<td class="demandLetterTableColLabel"><span>Ready Reckoner Rate per sq.ft</span></td>
				<td class="demandLetterTableCol"><span id="baserate" data-dojo-attach-point="baserate"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Stamp Duty </span><span id="stampdutypercent" data-dojo-attach-point="stampdutypercent"></span></td>
				<td class="demandLetterTableCol"><span id="stampduty" data-dojo-attach-point="stampduty"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Registration </span><span id="registrationpercent" data-dojo-attach-point="registrationpercent"></span></td>
				<td class="demandLetterTableCol"><span id="registration" data-dojo-attach-point="registration"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Legal Charges</span></td>
				<td class="demandLetterTableCol"><span id="legalcharges" data-dojo-attach-point="legalcharges"></span></td>
			</tr>
			<tr>
				<td class="demandLetterTableColLabel"><span>Total</span></td>
				<td class="demandLetterTableCol"><span id="totaltaxcharges" data-dojo-attach-point="totaltaxcharges"></span></td>
			</tr>
		</table><br>
		<span class="printDemandLetterText">We hereby request you to send Cheque/DD/NEFT/RTGS/Cash to following bank account</span><br>
		<span class="rintDemandLetterTextSmall" id="legalaccountinformation" data-dojo-attach-point="legalaccountinformation"></span>
	</div><br>
	<div  class="printDemandLetterText" id="footerDiv">
		<span>You are requested to arrange the payments within seven days of this letter otherwise interest at the rate of 24% will be applicable thereafter.</span><br>
		<span>Thanking you</span>
		<br><br><br><br>
		<span>Your Faithfully</span><br>
		<span>Authorized Signatory</span><br>
		<span id="orgname" data-dojo-attach-point="orgname"></span>
	</div>
</div>
</body>
</html>