define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/dom-construct",
    "dojo/_base/array",
    "dojo/date/locale",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/ShowUnitAvailabilityTemplate.html"
], function (declare, lang, domConstruct, array, locale, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		show: function() {
			this.inherited(arguments);
			if (this.availability == null) {
				var dataDiv = dojo.byId("dataDiv");
				domConstruct.create("span", null, dataDiv).innerHTML = "No unit has been defined for this building";
			} else {
				this.createDataDiv();
			}
		},
		
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDiv");
			var mainTable = domConstruct.create("table", null, dataDiv);
			var tr, td;
			
			for (var property in this.availability) {
				if (this.availability.hasOwnProperty(property)) {
					tr = domConstruct.create("tr", null, mainTable);
					var floorAvailability = this.availability[property];
					array.forEach(floorAvailability, function (unitAvailability)
					{
						if (unitAvailability.available) {
							td = domConstruct.create("td", {style:"font-weight: bold; background-color: #009900; color: #ffffff"}, tr);
							domConstruct.create("span", null, td).innerHTML = 
								"Type: " + unitAvailability.unitType + "<br>" + 
								"Number: " + unitAvailability.unitNumber + "<br>"
								"Booking Type: " + unitAvailability.bookingType;
						} else {
							td = domConstruct.create("td", {style:"font-weight: bold; background-color: #FF0000; color: #ffffff"}, tr);
							domConstruct.create("span", null, td).innerHTML = 
								"Type: " + unitAvailability.unitType + "<br>" +
								"Number: " + unitAvailability.unitNumber + "<br>" +
								"Booking Type: " + unitAvailability.bookingType + "<br>" +
								"Parking: " + unitAvailability.parkingName + "<br>" + 
								"Booked by: " + unitAvailability.customerName + "<br>" +
								"Booked for: " + unitAvailability.userName;
						}
					});
				}
			}
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDiv");
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		}
    });
});