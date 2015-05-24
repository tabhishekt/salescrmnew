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
				if(cell.data() == "Regular") { 
                	return 'text-align: center; background: green'; 
             	}else if(cell.data() == "Investor") { 
                	return 'text-align: center; background: red'; 
             	}else if(cell.data() == "Farmer") { 
                	return 'text-align: center; background: yellow'; 
             	}else if(cell.data() == "Refugee") { 
                	return 'text-align: center; background: yellow'; 
             	}
				return 'text-align: center; background: red';
			};
			
            createUnitGrid = function() {
            	var gridLayout = [
  	   							{field: "unitNumber",name: "Unit Number",width: "8%"},
  	   							{field: "classification",name: "Classification",width: "15%", style: this.decorateAvailability},
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
            	
            	//alert("Button : " + event.srcElement.src ); 
            	if (rowDataUnit == null) {
            		this.unitGridHandler.updateMessage("Please select a unit to classify.", "error");
            		return;
				} else {
					registry.byId("unit").set("value", rowDataUnit.id); 
				}
            	
            	var promise = request.post("../rest/json/data/inventory/unit/post/updateclassification", {
     				data: domForm.toObject(registry.byId("registerunitform").id),
     				timeout: 2000,
     				handleAs: "json"
     			});
     			
     			promise.response.then(
     				function(response) {
     					this.unitGridHandler.updateMessage("Successfully Updated Classification.", "success");
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
<body class="claro"  onload="this.load()">
<div class="messageSpanSuccess"><span id="messageSpanNode"></span></div>
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
				<button data-dojo-type="dijit/form/Button" type="submit" id="farmerButton" name="farmerButton" value="Submit">Mark as Farmer Flat</button>
				<button data-dojo-type="dijit/form/Button" type="submit" id="refugeeButton" name="refugeeButton" value="Submit">Mark as Refugee Flat</button>
				<button data-dojo-type="dijit/form/Button" type="submit" id="invButton" name="invButton" value="Submit">Mark as Investor Flat</button>
			</div>
		</div>
</body>
</html>