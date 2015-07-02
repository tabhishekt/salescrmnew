<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update unit registration</title>
<style type="text/css">
@import "../js/dojo_1.10.2/dojo/resources/dojo.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/claro.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/document.css";
@import "../js/dojo_1.10.2/dojox/widget/Dialog/Dialog.css";
@import "../js/lib/gridx-1.3.6/resources/claro/Gridx.css";
@import "../css/contentPage.css";
</style>

<jsp:directive.include file="common.jsp" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/dojo_1.10.2/dojo/dojo.js"></script>
<script>
    require(["dojo/ready", 
             "dojo/_base/lang",
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
        function(ready, lang, registry, currency, on, domForm, request, DialogHandler, GridHandler, AddEditCustomerDialog, Button) {
	    	ready(function() {
	    		this.readUserDataFromSession();
	    		this.load();
			});
	    	
            load = function () {
            	readUserDataFromSession();
            	this.buildingId = this.getQueryVariable("buildingId");
            	this.dialogHandler = new DialogHandler();
            	this.createUnitGrid();
            	on(registry.byId("registerunitform"), "submit", lang.hitch(this,"onSubmit"));  
            };
            
            formatCurrency = function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
			};
			
			formatAvailability = function (value) {
				if(value) {
					return "Registered";
				}
				
				return "Not Registered";
			};
			
			decorateAvailability = function (cell) {
				if(!cell.data()) { 
                	return 'text-align: center; background: green'; 
             	} 
				
				return 'text-align: center; background: red';
			};
			
            createUnitGrid = function() {
            	var gridLayout = [
  	   							{field: "unitNumber",name: "Unit Number",width: "8%"},
  	   							{field: "registered",name: "Registered",width: "15%", style: this.decorateAvailability, decorator: this.formatAvailability},
	   							{field: "displayProjectInfo",name: "Building / Project",width: "34%"},
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
            
            onSubmit = function (event) {
            	event.preventDefault(); 
            	var rowDataUnit = this.unitGridHandler.getSelectedRowData();
            	if (rowDataUnit == null) {
            		this.unitGridHandler.updateMessage("Please select a unit to mark registration done.", "error");
            		return;
				} else {
					registry.byId("unit").set("value", rowDataUnit.id); 
				}
            	
            	var promise = request.post("../rest/json/data/inventory/unit/post/markregistered", {
     				data: domForm.toObject(registry.byId("registerunitform").id),
     				timeout: 2000,
     				handleAs: "json"
     			});
     			
     			promise.response.then(
     				function(response) {
     					this.unitGridHandler.updateMessage("Successfully marked registration done for this unit.", "success");
     					this.unitGridHandler.refreshGrid();
     				},
     				function(error) {
     					this.unitGridHandler.updateMessage(error.response.data.message, "error");
     				}
     			);
   			};
        });
</script>
</head>
<body class="claro">
<div class="messageSpanSuccess"><span id="messageSpanNode"></span></div>
<div id="backLinkDiv" align="right"><a href="javascript:history.back()">Go Back</a></div>
<fieldset style="width: 95%;">
	 	<legend id="unitGridLegend"></legend>
	 	<br>
	 	<span id="dataGridUnitToolbar"></span>	 	
		<div id="dataGridUnit"></div>
</fieldset>
<p>		
		<div id="registerunitform" data-dojo-type="dijit/form/Form" enctype="multipart/form-data" action="" method="POST">
			<div>
				<input data-dojo-type='dijit/form/TextBox' id="unit" name="unit" class="hiddenInput" value=""></input>
			</div>
			<div>
				<button data-dojo-type="dijit/form/Button" type="submit" id="submitButton" name="submitButton" value="Submit">Mark as Registration done</button>
			</div>
		</div>
</body>
</html>