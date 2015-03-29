define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/_base/array",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditPaymentTemplate.html"
], function (declare, registry, array, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.userId) {
				this.user.set("value", this.userId);
			}
			if (this.unitbookingId) {
				this.unitbooking.set("value", this.unitbookingId);
			}
			if (this.receiptNumber) {
				this.receiptnumber.set("value", this.receiptNumber);
				this.receiptnumberdisplay.set("value", this.receiptNumber);
			}
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.unitbooking.set("value", data.unitbookingId);
			this.user.set("value", data.userId);
			
			this.receiptnumber.set("value", data.receiptNumber);
			this.receiptnumberdisplay.set("value", data.receiptNumber);
			this.altreceiptnumber.set("value", data.altReceiptNumber);
			this.receiptamount.set("value", data.receiptAmount);
			this.receiptdate.set("value", data.receiptDate);
			this.description.set("value", data.description);
			
			
			this.bankname.set("value", data.bankName);
			this.bankbranch.set("value", data.bankBranch);
			this.chequenumber.set("value", data.chequeNumber);
			this.chequedate.set("value", data.chequeDate);
			
			this.cardnumber.set("value", data.cardNumber);
			this.cardexpirydate.set("value", data.cardExpiryDate);
			this.cardholdername.set("value", data.cardHolderName);
			this.cardtype.set("value", data.cardType);
			
			this.paymenttype.set("item", data.paymentType);
			this.paymentstatus.set("item", data.paymentStatus);
			this.paymentstatuscomment.set("value", data.statusComment);
			
			var widget = this;
			array.forEach(data.paymentstage, function (stage)
			{
				var options = widget.paymentstage.domNode.children;
				for (var i = 0; i < options.length; i++) {
				    if (options[i].value == stage.id) {
				    	options[i].selected = "selected";
				    } 
				}
			});
		},
		
		postCreate:function () {
			this.inherited(arguments);
			this.receiptnumberdisplay.set("disabled", true);
        	this.setDataToDropdown('../rest/json/data/codetable/paymenttype/get/all', this.paymenttype);
        	this.setDataToDropdown('../rest/json/data/inventory/payment/get/states', this.paymentstatus);
        	this.setDataToMultiselect('../rest/json/data/inventory/payment/get/stages?rowId=' + this.buildingId, this.paymentstage.domNode);
        }
    });
});