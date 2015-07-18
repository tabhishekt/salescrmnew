define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/request",
    "dojo/currency",
    "dojo/date/locale",
    "dojo/dom-construct",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/ShowUnitPaymentScheduleTemplate.html"
], function (declare, lang, request, currency, locale, domConstruct, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
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
			    if (value  || value != null) {
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
			    } else {
					return "Not available";
			    }
			  } catch (e) {
			    console.error('error decorating date: ' + e.toString());
			  }
		},
		
		createSpan: function(column, id, value) {
			span = domConstruct.create("span", null, column);
			if (value == null) {
				value = "Not available";
			}
			span.innerHTML = value;
		},
		
		show: function() {
			this.inherited(arguments);
			var dialog = this;
			var promise = request.get("../rest/json/data/inventory/unit/get/paymentschedule?rowId=" + this.unitId, {
   				timeout: 5000,
   				handleAs: "json"
   			});
   			
   			promise.response.then(
   				function(response) {
   					dialog.unitnumber.innerHTML = response.data.unitNumber;
   					dialog.displayprojectinfo.innerHTML = response.data.displayProjectInfo;
   					dialog.agreementvalue.innerHTML = dialog.formatCurrency(response.data.agreementvalue);
   					dialog.bookingamount.innerHTML = dialog.formatCurrency(response.data.bookingAmount);
   					dialog.totaltax.innerHTML = dialog.formatCurrency(response.data.totalTax);
   					dialog.totalcost.innerHTML = dialog.formatCurrency(response.data.totalCost);
   					
   					var tr, th1, th2, th3, th4, td1, td2, td3, td4;
   					var dataDiv = dojo.byId("dataDiv");
   					domConstruct.empty(dataDiv);
   					
   					var table = domConstruct.create("table", {"style": "width:100%"}, dataDiv);
   					tr = domConstruct.create("tr", null, table);
   					th1 = domConstruct.create("th", null, tr);
   					dialog.createSpan(th1, "header_1", "Type");
					th2 = domConstruct.create("th", null, tr);
					dialog.createSpan(th2, "header_2", "Percent Amount");
					th3 = domConstruct.create("th", null, tr);
					dialog.createSpan(th3, "header_3", "Date");
					th4 = domConstruct.create("th", null, tr);
					dialog.createSpan(th4, "header_4", "Amount");
						
   					for (var i=0; i<response.data.scheduleList.length; i++) {
   						var schedule = response.data.scheduleList[i];
   						tr = domConstruct.create("tr", null, table);
   						
   						td1 = domConstruct.create("td", null, tr);
   						td2 = domConstruct.create("td", null, tr);
   						td3 = domConstruct.create("td", null, tr);
   						td4 = domConstruct.create("td", null, tr);
   						
   						dialog.createSpan(td1, "type_" + i, schedule.type);
   						dialog.createSpan(td2, "percentamount" + i, schedule.percentamount);
   						dialog.createSpan(td3, "scheduledate_" + i, dialog.formatDate(schedule.scheduledate));
   						dialog.createSpan(td4, "amount_" + i, dialog.formatCurrency(schedule.amount));
   					}
   				},
   				function(error) {
   					dialog.gridHandler.updateMessage("Problem occured fetching price details: " + error.response.data.message, "error");
   				}
   			);
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		}
    });
});