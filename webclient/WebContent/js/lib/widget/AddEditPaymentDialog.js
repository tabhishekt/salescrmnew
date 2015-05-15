define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/request",
    "dojo/_base/array",
    "dojo/currency",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditPaymentTemplate.html"
], function (declare, registry, request, array, currency, AddEditDialog, AddressWidget, template) {
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
			
			this.updateBookingDetails();
		},
		
		formatCurrency : function (value) {
			  if (value) { 
			    var formattedCurrency = currency.format(value, {currency: "Rs "});
			    return formattedCurrency;   
			  } 
			  
			  return "Rs " + 0;
		},
		
		updateBookingDetails : function () {
			var widget = this;
			var promise = request.get('../rest/json/data/inventory/unitbooking/get?rowId=' + this.unitbookingId, {
 				timeout: 2000,
 				handleAs: "json"
 			});
 			
 			promise.response.then (
 				function(response) {
 					var data = response.data;
 					widget.customerDisplayName.innerHTML = data.customerDisplayName;
 					widget.unitDisplayName.innerHTML = data.unitDisplayName;
 					widget.bookingAmount.innerHTML = widget.formatCurrency(data.bookingAmount);
 					widget.totalUnitCostWithDiscount.innerHTML = widget.formatCurrency(data.totalUnitCostWithDiscount);
 					widget.totalPaymentReceived.innerHTML = widget.formatCurrency(data.totalPaymentReceived);
 					widget.balancePayment.innerHTML = widget.formatCurrency(data.balancePayment);
 				},
 				function(error) {
 					console.log("Problem occured fetching booking details");
 				}
 			);
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.unitbooking.set("value", data.unitbookingId);
			this.user.set("value", data.userId);
			this.updateBookingDetails();
			
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