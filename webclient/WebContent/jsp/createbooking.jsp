<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration Page</title>
<style type="text/css">
@import "../js/dojo_1.10.2/dojo/resources/dojo.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/claro.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/document.css";
@import "../js/dojo_1.10.2/dojox/widget/Dialog/Dialog.css";
@import "../js/lib/gridx-1.3.6/resources/claro/Gridx.css";
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
            	this.buildingId = this.getQueryVariable("buildingId");
            	this.dialogHandler = new DialogHandler();
            	this.createUnitGrid();
            	this.createCustomerGrid();
            	on(registry.byId("createbookingForm"), "submit", lang.hitch(this,"onSubmit"));
            	on(registry.byId("showdiscountprice"), "click", lang.hitch(this,"onShowDiscountPrice"));  
            	registry.byId("bookinguser").set("value", this.userData.id);
            };
            
            formatCurrency = function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
			};
			
			formatAvailability = function (value) {
				if(!value) {
					return "Booked";
				}
				
				return "Available";
			};
			
			decorateAvailability = function (cell) {
				if(!cell.data()) { 
                	return 'text-align: center; background: red'; 
             	} 
				
				return 'text-align: center; background: green';
			};
			
            createUnitGrid = function() {
            	var gridLayout = [
  	   							{field: "unitNumber",name: "Unit Number",width: "8%"},
  	   							{field: "available",name: "Available",width: "7%", style: this.decorateAvailability, decorator: this.formatAvailability},
	   							{field: "displayProjectInfo",name: "Building / Project",width: "37%"},
	   							{field: "saleableArea",name: "Saleable Area",width: "8%"},
	   							{field: "agreementvalue",name: "Agreement Value",width: "20%", decorator: this.formatCurrency},
	   							{field: "totalCost",name: "Total Cost",width: "20%", decorator: this.formatCurrency}
	   	   			  		   ];
        		this.unitGridHandler = new GridHandler({page: "unit", serviceName: "inventory", 
        			dialogTtileField: "name", dialogHandler: this.dialogHandler});
        		if (this.buildingId) {
            		this.unitGridHandler.setBuildingId(this.buildingId);
            	}
     	   		this.unitGridHandler.createDataGrid(gridLayout, "dataGridUnit");
     	   		var toolbarLayout = [
     	   		                     ["refreshUnitGrid", "Refresh", "NewPage"],
     	   		                 	 ["showunitpricedetails", "Show Price Details", "NewPage"]
     	   		                    ];
 	   			this.unitGridHandler.createDataGridToolBar("dataGridUnitToolbar", toolbarLayout, {style : "width:30%"});
     	   		dojo.byId("unitGridLegend").innerHTML = "Select unit";
            };
            
            createCustomerGrid = function() {
            	this.dialogHandler.setDimension({width:window.screen.width*0.55, height: window.screen.height});
            	this.dialogHandler.setGridDialog(AddEditCustomerDialog);
            	var gridLayout = [
									{field: "displayName",name: "Name",width: "auto"},
									{field: "displayAddress",name: "Address",width: "auto"},
									{field: "phoneNumber",name: "Phone",width: "auto"},
									{field: "mobileNumber",name: "Mobile",width: "auto"},
									{field: "emailID",name: "Email Id",width: "auto"},
									{field: "userDisplayName",name: "Created By",width: "auto"}
	   	   			  		   ];
        		this.customerGridHandler = new GridHandler({page: "customer", serviceName: "person", 
        			dialogTtileField: "name", dialogHandler: dialogHandler, userId: this.userData.id});
        	
     	   		this.customerGridHandler.createDataGrid(gridLayout, "dataGridCustomer");
     	   		
     	   		var toolbarLayout = [
     	   		                     ["new", "New", "NewPage"],
     	   		                 	 ["refreshCustomerGrid", "Refresh", "NewPage"]
     	   		                    ];
     	   		this.customerGridHandler.createDataGridToolBar("dataGridCustomerToolbar", toolbarLayout, {style : "width:20%"});
     	   		
     	   		dojo.byId("customerGridLegend").innerHTML = "Select customer";
            };
            
            onShowDiscountPrice = function (event) {
            	var rowDataUnit = this.unitGridHandler.getSelectedRowData();
            	if (rowDataUnit == null) {
            		this.unitGridHandler.updateMessage("Please select a unit to show price details.", "error");
            		return;
				} else {
					if (rowDataUnit.unitPricePolicy && rowDataUnit.unitPricePolicy != null) {
						var booingdiscount = 0;
		            	if (dojo.byId("discount").value != "") {
		            		booingdiscount = parseInt(dojo.byId("discount").value);
		    			}
		            	var deductiononothercharges = 0;
		            	if (dojo.byId("deductiononothercharges").value != "") {
		            		deductiononothercharges = parseInt(dojo.byId("deductiononothercharges").value);
		    			}
						var options = {
								action: "showunitpricedetails",
		                    	title: "Price details for unit " + rowDataUnit.unitNumber + ", " + rowDataUnit.displayProjectInfo,
		                    	unitId: rowDataUnit.id,
		                    	booingdiscount: booingdiscount,
		                    	deductiononothercharges: deductiononothercharges
		                }
						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height);
					} else {
						this.unitGridHandler.updateMessage("Price policy for unit " + rowDataUnit.unitNumber + ", " + 
								rowDataUnit.displayProjectInfo + " is not defined.", "error");	
					} 
				}
            };
            
            onSubmit = function (event) {
            	event.preventDefault(); 
            	var rowDataUnit = this.unitGridHandler.getSelectedRowData();
            	if (rowDataUnit == null) {
            		this.unitGridHandler.updateMessage("Please select a unit to create booking.", "error");
            		return;
				} else {
					if (!rowDataUnit.unitPricePolicy || rowDataUnit.unitPricePolicy == null) {
						this.unitGridHandler.updateMessage("Price policy for unit " + rowDataUnit.unitNumber + ", " + 
								rowDataUnit.displayProjectInfo + " is not defined.", "error");	
						return;
					}
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
<body class="claro"  onload="this.load()">
<div class="messageSpanSuccess"><span id="messageSpanNode"></span></div>
<fieldset style="width: 95%;">
	 	<legend id="unitGridLegend"></legend>
	 	<br>
	 	<span id="dataGridUnitToolbar"></span>	 	
		<div id="dataGridUnit"></div>
</fieldset>
<p>
<fieldset style="width: 95%;">
	 	<legend id="customerGridLegend"></legend>
	 	<br>
	 	<span id="dataGridCustomerToolbar"></span>
		<div id="dataGridCustomer"></div>
</fieldset>
<p>		
		<div id="createbookingForm" data-dojo-type="dijit/form/Form" enctype="multipart/form-data" action="" method="POST">
			<div>
				<input data-dojo-type='dijit/form/TextBox' id="unit" name="unit" class="hiddenInput" value=""></input>
				<input data-dojo-type='dijit/form/TextBox' id="customer" name="customer" class="hiddenInput" value=""></input>
				<input data-dojo-type='dijit/form/TextBox' id="bookinguser" name="bookinguser" class="hiddenInput" value=""></input>
				<fieldset style="width: 80%;">
	 					<div>
	 						<table align="left" style="width: 100%;">
								<tr><td>
									<table>
										<tr><td><label for="comment">Comment:</label></td></tr>
										<tr><td><textarea id="comment" name="comment"
										data-dojo-type="dijit/form/SimpleTextarea" rows="3"></textarea></td>
										</tr>
									</table>
								</td><td>
									<table>
										<tr><td><label for="discount">Discount:</label></td></tr>
										<tr><td><input id="discount" name="discount"
												data-dojo-type='dijit/form/TextBox'
												type="text" /></td></tr>
									</table>
								</td><td>
									<table>
										<tr><td><label for="deductiononothercharges">Deduction on other charges:</label></td></tr>
										<tr><td><input id="deductiononothercharges" name="deductiononothercharges"
												data-dojo-type='dijit/form/TextBox'
												type="text" /></td></tr>
									</table>
								</td></tr>
							</table>
	 					</div>
					</fieldset>
			</div>
			<div>
				<button data-dojo-type="dijit/form/Button" type="submit" id="submitButton" name="submitButton" value="Submit">Create Booking</button>
				<button data-dojo-type="dijit/form/Button" type="button" id="showdiscountprice" name="showdiscountprice" value="">Show Discounted Price</button>
			</div>
		</div>
</body>
</html>