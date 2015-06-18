define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/dom-style",
    "dojo/currency",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/UnitModificationRequestTemplate.html"
], function (declare, registry, domStyle, currency, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
	
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated unit modification request.")
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
			
			if (this.data.unitModificationStatus && this.data.unitModificationStatus != null) {
				domStyle.set(this.unitmodificationstatusdiv, "display", "inline");
				this.unitmodificationdetails.disabled = true;
				
				this.unitmodificationdetails.set("value", this.data.unitModificationDetails);
				this.unitmodificationstatus.set("item", this.data.unitModificationStatus);
				this.unitmodificationstatuscomment.set("value", this.data.unitModificationStatusComment);
			} else {
				this.unitmodificationdetails.disabled = false;
				this.unitmodificationdetails.set("value", "");
				this.unitmodificationstatuscomment.set("value", "");
				this.unitmodificationstatus.set("item", null);
				domStyle.set(this.unitmodificationstatusdiv, "display", "none");
			}
    	},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/inventory/unitbooking/get/unitmodificationstates', this.unitmodificationstatus);
        }
    });
});