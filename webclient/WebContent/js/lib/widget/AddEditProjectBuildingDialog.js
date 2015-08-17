define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/dom-style",
    "dojo/store/JsonRest",
    "dojo/store/Memory",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditProjectBuildingTemplate.html"
], function (declare, registry, domStyle, JsonRest, Memory, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.projectId) {
				this.project.set("value", this.projectId);
			}
			
			domStyle.set(dojo.byId("buildingStatus"), "display", "none");
		},
		
		setData: function (data) {
			domStyle.set(dojo.byId("buildingStatus"), "display", "inline");
			
			this.setDataToCurrentStatus('../rest/json/data/inventory/projectbuilding/get/status?rowId=' + data.id, this.currentstatus, data.currentStatus);
			this.project.set("value", data.projectId);
			this.rowId.set("value", data.id);
			this.name.set("value", data.name);
			this.wingname.set("value", data.wingName);
			this.floorcount.set("value", data.floorCount);
			this.parkingfloorcount.set("value", data.parkingFloorCount);
			this.type.set("value", data.type);
			this.expectedcompletiondate.set("value", data.expectedCompletionDate);
			this.remarks.set("value", data.remarks);
			this.hasmultiplepaymentschedule.set("value", data.hasMultiplePaymentSchedules);
			this.projectphase.set("item", data.projectPhase	);
			this.planapprovaldate.set("value", data.planApprovalDate);
		},
		
		setDataToCurrentStatus: function (url, node, selectedItem) {
			var dialog = this;
			var store = new JsonRest ({
         		target: url,
         		idProperty:'id'
     		});
   		
        	store.query("", {
				handleAs : "json"
			}).then(function(data) {
				datastore = new Memory({
					data : data
				});
				node.set("store", datastore);
				dialog.currentstatus.set("item", selectedItem);
			});
		},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/inventory/projectphase/get/byproject?projectId=' + this.projectId, this.projectphase);
        },
        
        validate: function() {
        	var isValid = this.inherited(arguments);
        	
        	if (isValid) {
        		 var d1 = this.planapprovaldate.get("value");
        		 var d2 = this.expectedcompletiondate.get("value");
        		 
        		 if (d1 != null && d1 != "" && d2 != null && d2 != "") {
        			 var dt1 = new Date(d1);
        			 var dt2 = new Date(d1);
        			 
        			 if (d2 <= d1) {
        				 this.messageNode.innerHTML = "Expected completion date should be after plan approval date.";
                 		 return false
        			 }
        		 }
        	}
        	
        	return isValid;
        }
    });
});