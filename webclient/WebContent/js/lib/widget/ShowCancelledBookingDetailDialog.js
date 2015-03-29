define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/request",
    "dojo/currency",
    "dojo/date/locale",
    "dojo/dom-style",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/ShowCancelledBookingDetailTemplate.html"
], function (declare, lang, request, currency, locale, domStyle, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		formatCurrency : function (value) {
			  if (value) { 
			    var formattedCurrency = currency.format(value, {currency: "Rs "});
			    return formattedCurrency;   
			  } 
			  
			  return "Rs " + 0;
		},
		
		formatDate : function (value) {
			try {
			    if (value) {
			      // NotesDateTime comes out in this format: 2014-10-21T14:47:38Z
			      var parsedDate = value.substring(0, 10);
			       
			      // Create and format the date 
			      var dt = new Date(parsedDate);
			      var dateString = locale.format(dt,
			        {
			          selector: "date",
			          datePattern: "dd-MMM-yyyy"
			        }); 
			       
			      return dateString;
			    }
			  } catch (e) {
			    console.error('error decorating date: ' + e.toString());
			  }
		},
		
		show: function() {
			this.inherited(arguments);
			
			this.bookingFormNumber.innerHTML = this.data.bookingFormNumber;
			this.customerDisplayName.innerHTML = this.data.customerDisplayName;
			this.unitDisplayName.innerHTML = this.data.unitDisplayName;
			this.userDisplayName.innerHTML = this.data.userDisplayName;
			this.bookingDate.innerHTML = this.formatDate(this.data.bookingDate);
			this.discount.innerHTML = this.formatCurrency(this.data.discount);
			this.comment.innerHTML = this.data.comment;
			this.totalUnitCost.innerHTML = this.formatCurrency(this.data.totalUnitCost);
			this.totalUnitCostWithDiscount.innerHTML = this.formatCurrency(this.data.totalUnitCostWithDiscount);
			this.totalPaymentReceived.innerHTML = this.formatCurrency(this.data.totalPaymentReceived);
			this.cancelUserDisplayName.innerHTML = this.data.cancelUserDisplayName;
			this.cancelDeduction.innerHTML = this.data.cancelDeduction + "%";
			this.cancellationDate.innerHTML = this.formatDate(this.data.cancellationDate);
			this.cancellationComment.innerHTML = this.data.cancellationComment;
			
			if (this.data.refundDetails && this.data.refundDetails != null) {
				domStyle.set(dojo.byId("refundDetailsDiv"), "display", "inline");
				domStyle.set(dojo.byId("noRefundDetailsDiv"), "display", "none");
				
				this.refundAmount.innerHTML = this.formatCurrency(this.data.refundDetails.refundAmount);
				this.refundDate.innerHTML = this.formatDate(this.data.refundDetails.refundDate);
				this.bankName.innerHTML = this.data.refundDetails.bankName;
				this.bankBranch.innerHTML = this.data.refundDetails.bankBranch;
				this.chequeNumber.innerHTML = this.data.refundDetails.chequeNumber;
				this.chequeDate.innerHTML = this.formatDate(this.data.refundDetails.chequeDate);
			} else {
				domStyle.set(dojo.byId("refundDetailsDiv"), "display", "none");
				domStyle.set(dojo.byId("noRefundDetailsDiv"), "display", "inline");
			}
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		}
    });
});