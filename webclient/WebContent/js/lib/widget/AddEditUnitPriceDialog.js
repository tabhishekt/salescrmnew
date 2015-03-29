define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "lib/widget/AddEditDialog",
    "lib/GridHandler",
    "dojo/text!./template/AddEditUnitPriceTemplate.html"
], function (declare, lang, AddEditDialog, GridHandler, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated unit price policy for all non booked units of this building.")
			}
			this.inherited(arguments);
		},
		
		show: function() {
			this.inherited(arguments);
			this.gridHandler.resizeGrid(this.dimensions[0]*0.95, this.dimensions[1]*0.5);
			this.gridHandler.refreshGrid();
		},
		
		 onSubmit : function (event) {
        	var rowData = this.gridHandler.getSelectedRowData();
        	if (rowData == null) { 
        		this.gridHandler.updateMessage("Please select a price policy to update.", "error");
        		return;
			} else {
				this.unitpricepolicy.set("value", rowData.id); 
			}
        	
        	this.inherited(arguments);
		},
			
		postCreate: function() {
			this.inherited(arguments);
			var gridLayout = [
								{field: "policyName",name: "Policy Name",width: "50%"},
	   							{field: "baserate",name: "Base Rate",width: "50%"}
	   	   			  		 ];
			this.gridHandler = new GridHandler({page: "unitpricepolicy", serviceName: "inventory", dialogTtileField: "policyName"});
			this.gridHandler.createDataGrid(gridLayout, "dataGridUnitPricePolicy");
			var toolbarLayout = [
 	   		                 	 ["refreshUnitPricePolicyGrid", "Refresh", "NewPage"]
 	   		                    ];
 	   		this.gridHandler.createDataGridToolBar("dataGridUnitPricePolicyToolbar", toolbarLayout, {style : "width:15%"});
 	   		
 	   		dojo.byId("unitPricePolicyGridLegend").innerHTML = "Select unit price policy";
		}
    });
});