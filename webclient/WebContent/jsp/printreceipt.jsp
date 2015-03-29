<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt</title>
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
             "dojo/currency",
             "dojo/on",
             "dojo/dom-form",
             "dojo/request",
             "lib/DialogHandler",
			 "lib/GridHandler",
			 'lib/widget/AddEditCustomerDialog', 
		     "dijit/form/Button",
			 "dojo/domReady!"],
        function(lang, registry, currency, on, domForm, request, DialogHandler, GridHandler, AddEditCustomerDialog, Button) {
            load = function () {
            	
            };
            
            formatCurrency = function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
			};
			
            
            
            onSubmit = function (event) {
            	event.preventDefault(); 
            	var rowDataUnit = this.unitGridHandler.getSelectedRowData();
            	if (rowDataUnit == null) {
            		this.unitGridHandler.updateMessage("Please select a unit to create booking.", "error");
            		return;
				} else {
					registry.byId("unit").set("value", rowDataUnit.id); 
				}
            	
            	var rowDataCustomer = this.customerGridHandler.getSelectedRowData();
            	if (rowDataCustomer == null) { 
            		this.unitGridHandler.updateMessage("Please select a customer to create booking.", "error");
            		return;
				} else {
					registry.byId("customer").set("value", rowDataCustomer.id); 
				}
            	
            	var promise = request.post("../rest/json/data/inventory/unitbooking/post", {
     				data: domForm.toObject(registry.byId("createbookingForm").id),
     				timeout: 2000,
     				handleAs: "json"
     			});
     			
     			promise.response.then(
     				function(response) {
     					this.unitGridHandler.updateMessage("Booking with reference number " + response.data.bookingFormNumber + " created successfully.", "success");
     				},
     				function(error) {
     					this.unitGridHandler.updateMessage(error.response.data.message, "error");
     				}
     			);
   			};
        });
</script>
</head>
<body class="claro" onload="this.load()">
	<div id="orgDetailsDiv">
		<table style="width: 100%;">
			<tr>
				<td colspan="3"><span id="orgname"
					data-dojo-attach-point="orgname"></span></td>
			</tr>
			<tr>
				<td colspan="3"><span id="orgaddress"
					data-dojo-attach-point="orgaddress"></span></td>
			</tr>
			<tr>
				<td><span id="orgphone" data-dojo-attach-point="orgphone"></span></td>
				<td><span id="orgmobile" data-dojo-attach-point="orgmobile"></span></td>
				<td><span id="orgemailid" data-dojo-attach-point="orgemailid"></span></td>
			</tr>
		</table>
	</div>

	<h1 align="center">RECEIPT</h1>
	<table>
		<tr>
			<td><label for="receiptno">Receipt Number:</label></td>
			<td><span id="receiptno" data-dojo-attach-point="receiptno">111111</span></td>
			<td><label for="receiptdate">Receipt Date:</label></td>
			<td><span id="receiptdate" data-dojo-attach-point="receiptdate">111111</span></td>
		</tr>
	</table>
	<div>
		Received with thanks from <span id="customername"
			data-dojo-attach-point="customername">Arvind Sharma</span> the sum of <span
			id="customername" data-dojo-attach-point="customername">Rs 1,00,000</span>
			 in words <span id="customername" data-dojo-attach-point="customername">One Lac only</span>
			towards payment for <span id="customername"
			data-dojo-attach-point="customername">101</span>, <span id="customername"
			data-dojo-attach-point="customername">Majestica casa bella</span>
	</div>
	<div>
		<table>
			<tr>
				<td valign="top">
					<fieldset style="width: 100%;">
						<legend>Payment Details:</legend>
						<div id="paymentDetailsDiv"
							data-dojo-attach-point="paymentDetailsDiv"></div>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<p></p>
	<div>
		<input type="button" value="Print this page" onClick="window.print()">
	</div>
	<br>
	<br>
</body>
</html>