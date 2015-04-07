define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dijit/registry",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditUnitChargesTemplate.html"
], function (declare, array, domConstruct, domStyle, registry, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding2.set("value", this.buildingId);
			}
			var dataDiv = dojo.byId("dataDiv");
			domConstruct.create("span", null, dataDiv).innerHTML = "No unit has been defined for this building";
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated unit charges for all non booked units of this building.")
			}
			this.inherited(arguments);
		},
		
		setData: function (data) {
			this.createDataDiv();
			if (this.buildingId) {
				this.projectbuilding2.set("value", this.buildingId);
			}
			
			for (var unittype in this.data) {
				if (this.data.hasOwnProperty(unittype)) {
					var id = this.getIdPart(unittype);
					var charges = data[unittype];
					this.setValueToWidget("bookingamount_" + id, charges, "bookingAmount");
					this.setValueToWidget("othercharges_" + id, charges, "otherCharges");
				}
			}
			
			registry.byId("bookingamount").set("value", data[i+1]);
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDiv");
			
			for (var unittype in this.data) {
				if (this.data.hasOwnProperty(unittype)) {
					var id = this.getIdPart(unittype);
					this.destroyWidget("unittype_" + id);
					this.destroyWidget("bookingamount_" + id);
					this.destroyWidget("othercharges_" + id);
				}
			}
		},
		
		destroyWidget: function (id) {
			var widget = registry.byId(id);
			if (widget) {
				widget.destroy();
			}
		},
		
		setValueToWidget: function (id, data, key) {
			var widget = registry.byId(id);
			if (widget) {
				if (data[key] && data[key]) {
					widget.set("value", data[key]);
				}
			}
		},
		
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDiv");
			var table = domConstruct.create("table", null, dataDiv);
			
			for (var unittype in this.data) {
				if (this.data.hasOwnProperty(unittype)) {
					var id = this.getIdPart(unittype);
					this.createRow (table, unittype, "unittype_" + id, "Unit Type:", "bookingamount_" + id, 
							"Booking amount:", "othercharges_" + id, "Other charges:");
				}
			}
        },
		
        createRow: function (table, legendTitle, unitTypeFieldId, unitTypeFieldTitle, bookingAmountFieldId, bookingAmountFieldTitle,
        		otherChargesFieldId, otherChargesFieldTitle) {
			var DECIMAL_REGEX = "^[0-9]([0-9]{1,8})?(\.[1-9])?$";
        	var fieldset = this.createTypeRow(table, legendTitle);
			var table = domConstruct.create("table", null, fieldset);
			var tr = domConstruct.create("tr", null, table);
			var td1 = domConstruct.create("td", null, tr);
			var td2 = domConstruct.create("td", null, tr);
			var td3 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, unitTypeFieldId, unitTypeFieldTitle, "", "", legendTitle);
			this.populateColumn(td2, bookingAmountFieldId, bookingAmountFieldTitle, DECIMAL_REGEX, "Enter valid decimal value upto 999999999.9", "");
			this.populateColumn(td3, otherChargesFieldId, otherChargesFieldTitle, DECIMAL_REGEX, "Enter valid decimal value upto 999999999.9", "");
        },
        
        getIdPart: function (unitType) {
        	var part = unitType.replace(" ", "_");
        	return part.toLowerCase();
        },
        
        createTypeRow: function (table, value) {
			var tr, td;
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"colspan": 3}, tr);
			var fieldset = domConstruct.create("fieldset", {"style": "width: 100%;"}, td);
			var legend = domConstruct.create("legend", {"style": "font-size: 4px"}, fieldset).innerHTML = value;
			
			return fieldset;
		},
        
		populateColumn: function(column, id, labelText, regExp, invalidMessage, value) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
			
			if (new RegExp("^unittype_").test(id)) {
				tb = new dijit.form.ValidationTextBox({
					id: id,
					name: id,
					value: value
				}, td);
				domStyle.set(tb.domNode, "display", "none");
			} else {
				domConstruct.create("label", {"for": id}, td).innerHTML = labelText;
				
				tr = domConstruct.create("tr", null, table);
				td = domConstruct.create("td", null, tr);
				
				tb = new dijit.form.ValidationTextBox({
					id: id,
					name: id,
					pattern: regExp, 
					invalidMessage: invalidMessage,
					value: value
				}, td);
			}
		}
    });
});