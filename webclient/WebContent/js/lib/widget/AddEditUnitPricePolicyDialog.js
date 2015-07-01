define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditUnitPricePolicyTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.policyname.set("value", data.policyName);
			this.baserate.set("value", data.baserate);
			this.readyreckonerrate.set("value", data.readyreckonerrate);
			this.landrate.set("value", data.landrate);
			this.stampduty.set("value", data.stampduty);
			this.registrationcharge.set("value", data.registrationcharge);
			this.servicetax.set("value", data.servicetax);
			this.valueaddedtax.set("value", data.valueaddedtax);
			this.maintenancecharge1.set("value", data.maintenancecharge1);
			this.maintenancecharge1duration.set("value", data.maintenancecharge1duration);
			this.maintenancecharge2.set("value", data.maintenancecharge2);
			this.legalcharge.set("value", data.legalcharge);
		},
		
		validate: function() {
        	var isValid = this.inherited(arguments);
        	
        	if (isValid) {
        		if (parseFloat(this.maintenancecharge1.value) > parseFloat(this.baserate.value)) {
        		  		this.messageNode.innerHTML = "Maintainance charge 1 cannot be greater than Base Rate";
                		return false          	
        			
    			}
        		if (parseFloat(this.maintenancecharge2.value) > parseFloat(this.baserate.value)) {
    		  		this.messageNode.innerHTML = "Maintainance charge 2 cannot be greater than Base Rate";
            		return false          	
    			
			} 
        	}
        	
        	return isValid;
        }
    });
});