define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/request",
    "dojo/currency",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/ShowUnitPriceDetailTemplate.html"
], function (declare, lang, request, currency, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
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
		
		show: function() {
			this.inherited(arguments);
			var dialog = this;
			var promise = request.get("../rest/json/data/inventory/unit/get/pricedetail?rowId=" + 
					this.unitId + "&discount=" + this.booingdiscount + "&deductiononothercharges=" + this.deductiononothercharges, {
   				timeout: 2000,
   				handleAs: "json"
   			});
   			
   			promise.response.then(
   				function(response) {
   					dialog.unitnumber.innerHTML = response.data.unitNumber;
   					dialog.displayprojectinfo.innerHTML = response.data.displayProjectInfo;
   					
   					dialog.maintenancecharge1.innerHTML = dialog.formatCurrency(response.data.maintenancecharge1);
   					dialog.maintenancecharge2.innerHTML = dialog.formatCurrency(response.data.maintenancecharge2);
   					dialog.legalcharge.innerHTML = dialog.formatCurrency(response.data.legalcharge);
   					dialog.othercharges.innerHTML = dialog.formatCurrency(response.data.othercharges);
   					dialog.deductionothercharges.innerHTML = dialog.formatCurrency(response.data.deductionOnOtherCharges);
   					dialog.totalcharges.innerHTML = dialog.formatCurrency(response.data.totalcharges);
   					
   					dialog.stampduty.innerHTML = dialog.formatCurrency(response.data.stampduty);
   					dialog.registrationcharge.innerHTML = dialog.formatCurrency(response.data.registrationcharge);
   					dialog.servicetax.innerHTML = dialog.formatCurrency(response.data.servicetax);
   					dialog.valueaddedtax.innerHTML = dialog.formatCurrency(response.data.valueaddedtax);
   					dialog.totaltax.innerHTML = dialog.formatCurrency(response.data.totalTax);
   					
   					dialog.saleablearea.innerHTML = response.data.saleableArea;
   					dialog.carpetarea.innerHTML = response.data.carpetArea;
   					dialog.baserate.innerHTML = dialog.formatCurrency(response.data.baserate);
   					dialog.ratediscount.innerHTML = dialog.formatCurrency(response.data.discount);
   					dialog.floorrise.innerHTML = dialog.formatCurrency(response.data.floorrise);
   					dialog.agreementvalue.innerHTML = dialog.formatCurrency(response.data.agreementvalue);
   					dialog.totalcostwithtax.innerHTML = dialog.formatCurrency(response.data.totalCostWithTax);
   					dialog.totalcost.innerHTML = dialog.formatCurrency(response.data.totalCost);
   				},
   				function(error) {
   					this.gridHandler.updateMessage("Problem occured fetching price details: " + error.response.data.message, "error");
   				}
   			);
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		}
    });
});