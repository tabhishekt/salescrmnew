define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dijit/registry",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/DefineAmenityChargesTemplate.html"
], function (declare, array, domConstruct, domStyle, registry, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.unitpricepolicyId) {
				this.unitpricepolicy.set("value", this.unitpricepolicyId);
			}
			var dataDiv = dojo.byId("dataDiv");
			domConstruct.create("span", null, dataDiv).innerHTML = "No amenities are available to define charges for";
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated amenity prices for this price policy.")
			}
			this.inherited(arguments);
		},
		
		setData: function (data) {
			this.createDataDiv();
			if (this.unitpricepolicyId) {
				this.unitpricepolicy.set("value", this.unitpricepolicyId);
			}
			
			for (var amenitytype in this.data) {
				if (this.data.hasOwnProperty(amenitytype)) {
					var id = this.getIdPart(amenitytype);
					this.setValueToWidget("amenitycharge_" + id, data[amenitytype]);
				}
			}
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDiv");
			
			for (var amenitytype in this.data) {
				if (this.data.hasOwnProperty(amenitytype)) {
					var id = this.getIdPart(amenitytype);
					this.destroyWidget("amenitycharge_" + id);
				}
			}
		},
		
		destroyWidget: function (id) {
			var widget = registry.byId(id);
			if (widget) {
				widget.destroy();
			}
		},
		
		setValueToWidget: function (id, data) {
			var widget = registry.byId(id);
			if (widget) {
				widget.set("value", data);
			}
		},
		
        getIdPart: function (amenityType) {
        	var part = amenityType.replace(new RegExp(" ", 'g'), "_");
        	return part.toLowerCase();
        },
        
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDiv");
			var mainTable = domConstruct.create("table", null, dataDiv);
			var tr, td;
			var i=0;
			
			for (var amenitytype in this.data) {
				if (this.data.hasOwnProperty(amenitytype)) {
					var id = this.getIdPart(amenitytype);
					if (i%2 == 0) {
						tr = domConstruct.create("tr", null, mainTable);
						td = domConstruct.create("td", null, tr);
					} else {
						td = domConstruct.create("td", null, tr);
					}
						
					this.populateColumn(td, "amenitycharge_" + id, amenitytype + ":", "^[0-9]([0-9]{1,3})?(\.[1-9])?$", "Enter valid decimal value upto 9999.9", "");
				}
				i++;
			}
        },
		
		populateColumn: function(column, id, labelText, regExp, invalidMessage, value) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
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
    });
});