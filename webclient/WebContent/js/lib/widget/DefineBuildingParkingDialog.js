define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dijit/registry",
    "dojo/on",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/DefineBuildingParkingTemplate.html"
], function (declare, lang, array, domConstruct, domStyle, registry, on, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding1.set("value", this.buildingId);
			}
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated parking changes for this building.")
			}
			this.inherited(arguments);
		},
		
		setData: function (data) {
			this.createDataDiv();
			if (this.buildingId) {
				this.projectbuilding1.set("value", this.buildingId);
			}
			
			for (var parkingtype in this.data) {
				if (this.data.hasOwnProperty(parkingtype)) {
					var id = this.getIdPart(parkingtype);
					var parkingdetails = data[parkingtype];
					this.setValueToWidget("available_" + id, parkingdetails["available"]);
					this.setValueToSpan("booked_" + id, parkingdetails["booked"]);
					this.setValueToSpan("total_" + id, parkingdetails["total"]);
				}
			}
		},
		
		updateTotalValue: function (data) {
			for (var parkingtype in this.data) {
				if (this.data.hasOwnProperty(parkingtype)) {
					var id = this.getIdPart(parkingtype);
					var available = this.getValueFromWidget("available_" + id);
					var booked = this.getValueFromSpan("booked_" + id);
					this.setValueToSpan("total_" + id, available + booked);
				}
			}
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDivParking");
			
			for (var parkingtype in this.data) {
				if (this.data.hasOwnProperty(parkingtype)) {
					var id = this.getIdPart(parkingtype);
					this.destroyWidget("parkingtype_" + id);
					this.destroyWidget("available_" + id);
				}
			}
		},
		
		destroyWidget: function (id) {
			var widget = registry.byId(id);
			if (widget) {
				widget.destroy();
			}
		},
		
		getValueFromWidget: function (id) {
			var result = 0;
			var widget = registry.byId(id);
			if (widget) {
				var value = widget.get("value");
				if (value) {
					result = parseInt(value);
				}
			}
			
			return result;
		},
		
		setValueToWidget: function (id, value) {
			var widget = registry.byId(id);
			if (widget) {
				if (value) {
					widget.set("value", value);
				} else {
					widget.set("value", "0");
				}
			}
		},
		
		getValueFromSpan: function (id) {
			var result = 0;
			var span = dojo.byId(id);
			if (span) {
				var value = span.innerHTML;
				if (value) {
					result = parseInt(value);
				}
			}
			
			return result;
		},
		
		setValueToSpan: function (id, value) {
			var span = dojo.byId(id);
			if (span) {
				if (!value) {
    				span.innerHTML = "0";
    			} else {
    				span.innerHTML = value;
    			}
			}
		},
		
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDivParking");
			var table = domConstruct.create("table", null, dataDiv);
			
			for (var parkingtype in this.data) {
				if (this.data.hasOwnProperty(parkingtype)) {
					var id = this.getIdPart(parkingtype);
					this.createRow (table, parkingtype, "parkingtype_" + id, "Parking Type:", "available_" + id, 
							"Available parkings:", "booked_" + id, "Booked parkings:", "total_" + id, "Total parkings:");
				}
			}
        },
		
        createRow: function (table, legendTitle, parkingTypeFieldId, parkingTypeFieldTitle, availableFieldId, availableFieldTitle,
        		bookedFieldId, bookedFieldTitle, totalFieldId, totalFieldTitle) {
			var DECIMAL_REGEX = "\\d{1,3}";
        	var fieldset = this.createTypeRow(table, legendTitle);
			var table = domConstruct.create("table", null, fieldset);
			var tr = domConstruct.create("tr", null, table);
			var td1 = domConstruct.create("td", null, tr);
			var td2 = domConstruct.create("td", null, tr);
			var td3 = domConstruct.create("td", null, tr);
			var td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, parkingTypeFieldId, parkingTypeFieldTitle, "", "", legendTitle);
			this.populateColumn(td2, availableFieldId, availableFieldTitle, DECIMAL_REGEX, "Enter upto three digit number only", "");
			this.populateColumn(td3, bookedFieldId, bookedFieldTitle, "", "", "");
			this.populateColumn(td4, totalFieldId, totalFieldTitle, "", "", "");
        },
        
        getIdPart: function (parkingType) {
        	var part = parkingType.replace(" ", "_");
        	return part.toLowerCase();
        },
        
        createTypeRow: function (table, value) {
			var tr, td;
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"colspan": 3}, tr);
			var fieldset = domConstruct.create("fieldset", {"style": "width: 100%;"}, td);
			var legend = domConstruct.create("legend", {"style": "font-size: 14px"}, fieldset).innerHTML = value;
			
			return fieldset;
		},
        
		populateColumn: function(column, id, labelText, regExp, invalidMessage, value) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
			
			if (new RegExp("^parkingtype_").test(id)) {
				tb = new dijit.form.ValidationTextBox({
					id: id,
					name: id,
					value: value
				}, td);
				domStyle.set(tb.domNode, "display", "none");
			} else if (new RegExp("^available_").test(id)) {
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
				on(tb, "change", lang.hitch(this,"updateTotalValue"));
			} else {
				domConstruct.create("label", {"for": id}, td).innerHTML = labelText;
				
				tr = domConstruct.create("tr", null, table);
				td = domConstruct.create("td", null, tr);
				span = domConstruct.create("span", {"id" : id}, td);
    			if (value == null || value == "") {
    				span.innerHTML = "0";
    			} else {
    				span.innerHTML = value;
    			}
			}
		}
    });
});