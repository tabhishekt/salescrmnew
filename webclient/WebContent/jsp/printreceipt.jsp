<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment Receipt</title>
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
             "dojo/dom-construct",
             "dojo/currency",
             "dojo/date/locale",
             "dojo/dom-construct",
			 "dojo/domReady!"],
        function(lang, registry, on, request, domConstruct, currency, locale, domConstruct) {
            load = function () {
            	this.paymentId = this.getQueryVariable("paymentId");
            	var promise = request.get("../rest/json/data/inventory/payment/get/print?rowId=" + this.paymentId, {
       				timeout: 2000,
       				handleAs: "json"
       			});
       			
       			promise.response.then(
       				function(response) {
       					this.populateData(response.data);
       				},
       				function(error) {
       					this.gridHandler.updateMessage("Problem occured fetching payment details: " + error.response.data.message, "error");
       				}
       			);
            };
            
            populateData = function(data) {
            	this.loadLogoFile(this.projectLogo, data.project.name, "../images/" + data.project.logoFileName);
            	this.loadLogoFile(this.orgLogo, data.organization.name, "../images/" + data.organization.logoFileName);
            	
            	this.datespan.innerHTML = this.formatDate(data.paymentInformation.receiptDate);
            	this.customername.innerHTML = data.customer.displayName;
            	this.receiptnumber.innerHTML = "Receipt No. " + data.paymentInformation.receiptNumber + " (" + data.project.name + ")";
            	this.amountinwords.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rs&nbsp;&nbsp;" + data.amountInWords;
            	this.chequenumber.innerHTML = data.paymentInformation.chequeNumber;
            	this.bankname.innerHTML = data.paymentInformation.bankName;
            	this.bankbranch.innerHTML = data.paymentInformation.bankBranch;
            	this.chequedate.innerHTML = this.formatDate(data.paymentInformation.chequeDate);
            	this.projectname.innerHTML = data.project.name;
            	this.unitnumber.innerHTML = data.unit.unitNumber;
            	this.buildingname.innerHTML = data.buildingName;
            	this.floornumber.innerHTML = data.unit.floorNumber + " floor";
            	this.projectaddress.innerHTML = data.project.displayAddress;
            	this.receiptamount.innerHTML = this.formatCurrency(data.paymentInformation.receiptAmount);
            	this.orgname.innerHTML = data.organization.name;
            	this.orgaddress.innerHTML = data.organization.displayAddress;
            	this.orgcontact.innerHTML = data.organization.contactInfo.phoneNumber
            								+ ", " + data.organization.contactInfo.mobileNumber
            								+ ", " + data.organization.contactInfo.emailID;
            };
            
            loadLogoFile = function (node, name, url) {
            	var promise = request.get(url, {
       				timeout: 2000,
       				handleAs: "image"
       			});
       			
       			promise.response.then(
       				function(response) {
       					domConstruct.create("img", {"class":"logo", "src": url, "alt": name}, node);
       				},
       				function(error) {
       					//domConstruct.create("span", {"class":"logoAltText"}, node).innerHTML = name;
       				}
       			);
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
			<tr><td align="center" width="40%"><div id="projectLogo" data-dojo-attach-point="projectLogo"></div></td>
			<td align="center" width="40%"><div id="orgLogo" data-dojo-attach-point="orgLogo"></div></td></tr>
		</table>
	</div>
	<br><br>
	<div class="receiptSpanText" >
		<table width="100%" align="center">
			<tr><td><span id="receiptnumber" data-dojo-attach-point="receiptnumber"></span></td>
			<td align="right"><span id="datespan" data-dojo-attach-point="datespan"></span></td></tr>
		</table>
	</div>
	<div class="receiptSpanText" id="receiptDiv1">
		<table width="100%" align="center">
			<tr><td><span>Received with thank from Mr./Mrs.:</span></td></tr>
			<tr><td><span style="text-decoration: underline;" id="customername" data-dojo-attach-point="customername"></span></td></tr>
		</table>
		<br>
		<table width="100%" align="center"><tr><td>
			<span>The sum of </span><span style="text-decoration: underline;" id="amountinwords" data-dojo-attach-point="amountinwords"></span>
		</td></tr>
		<tr><td>
			<span>By Cash / Cheque / Draft No. </span><span style="text-decoration: underline;" id="chequenumber" data-dojo-attach-point="chequenumber"></span>
			<span>Drawn on Bank </span><span style="text-decoration: underline;" id="bankname" data-dojo-attach-point="bankname"></span>
		</td></tr>
		<tr><td>
			<span>Branch. </span><span style="text-decoration: underline;" id="bankbranch" data-dojo-attach-point="bankbranch"></span>
			<span>Dated </span><span style="text-decoration: underline;" id="chequedate" data-dojo-attach-point="chequedate"></span>
			<span>against the project  </span><span style="text-decoration: underline;" id="projectname" data-dojo-attach-point="projectname"></span>
		</td></tr>
		<tr><td>
			<span>of Flat no.  </span><span style="text-decoration: underline;" id="unitnumber" data-dojo-attach-point="unitnumber"></span>
			<span>In Building no. </span><span style="text-decoration: underline;" id="buildingname" data-dojo-attach-point="buildingname"></span>
			<span>On </span><span style="text-decoration: underline;" id="floornumber" data-dojo-attach-point="floornumber"></span>
		</td></tr>
		<tr><td>
			<span>Situated at  </span><span style="text-decoration: underline;" id="projectaddress" data-dojo-attach-point="projectaddress"></span>
		</td></tr></table>
	</div>
	<br><br>
	<div class="receiptSpanText" id="receiptDiv2">
		<table width="100%" align="center"><tr>
			<td>
				<table>
					<tr><td><div class="receiptAmountDiv"><span id="receiptamount" data-dojo-attach-point="receiptamount"></span></div></td></tr>
					<tr><td class="receiptListText">
						<ul>
    						<li>Pune Jurisdiction</li>
    						<li>Subject to realisation of cheque/s</li>
  						</ul>
					</td></tr>
				</table>
			</td>
			<td>
				<table align="right">
					<tr><td><span>Signature</span></td></tr>
					<tr><td><div class="receiptSignDiv"></div></td></tr>
					<tr><td><span id="orgname" data-dojo-attach-point="orgname"></span></td></tr>
				</table>
			</td>
		</tr></table>
	</div>
	<div class="receiptFooterText" id="footerDiv">
		<table style="width: 100%;">
			<tr><td colspan="3"><span id="orgaddress" data-dojo-attach-point="orgaddress"></span></td></tr>
			<tr>
				<td><span id="orgcontact" data-dojo-attach-point="orgcontact"></span></td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>