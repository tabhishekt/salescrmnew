define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/currency",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/CancelUnitBookingTemplate.html"
], function (declare, registry, currency, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
	
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully cancelled this booking.")
			}
			this.inherited(arguments);
		},
		
		formatCurrency : function (value) {
			  if (value) { 
			    var formattedCurrency = currency.format(value, {currency: "Rs "});
			    return formattedCurrency;   
			  } 
			  
			  return "Rs " + 0;
		},
		
    	setData : function () {
    		if (this.userId) {
				this.user.set("value", this.userId);
			}
    		
			this.unitbooking.set("value", this.data.id);
			this.customerDisplayName.innerHTML = this.data.customerDisplayName;
			this.unitDisplayName.innerHTML = this.data.unitDisplayName;
			this.totalUnitCostWithDiscount.innerHTML = this.formatCurrency(this.data.totalUnitCostWithDiscount);
			this.totalPaymentReceived.innerHTML = this.formatCurrency(this.data.totalPaymentReceived);
			this.balancePayment.innerHTML = this.formatCurrency(this.data.balancePayment);
    	},
    	
    	postCreate:function () {
			this.inherited(arguments);
			this.setDataToDropdown('../rest/json/data/inventory/bankbranch/get/all', this.bankbranch);
        },
    	
    	validate: function() {
        	var isValid = this.inherited(arguments);
        	
        	if (isValid) {
        		if (this.canceldeduction.get("value") > this.data.totalPaymentReceived) {
        			this.messageNode.innerHTML = "Cancellation deduction amount cannot be more than total payment received.";
            		return false;
        		}
        	}
        	
        	return isValid;
        }
    });
});