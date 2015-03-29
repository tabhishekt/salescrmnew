define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "dojo/dom-construct",
    "dijit/registry",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditUnitFloorRiseTemplate.html"
], function (declare, array, domConstruct, registry, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding1.set("value", this.buildingId);
			}
			var dataDiv = dojo.byId("dataDiv");
			domConstruct.create("span", null, dataDiv).innerHTML = "No unit has been defined for this building";
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated unit floor rise for all non booked units of this building.")
			}
			this.inherited(arguments);
		},
		
		setData: function (data) {
			this.createDataDiv();
			if (this.buildingId) {
				this.projectbuilding1.set("value", this.buildingId);
			}
			for (var i=0; i<this.floorcount; i++) {
				registry.byId("floorrise_" + (i+1)).set("value", data[i+1]);
			}
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDiv");
			for (var i=0; i<this.floorcount; i++) {
				var tb = registry.byId("floorrise_" + (i+1));
				if (tb) {
					tb.destroy();
				}
			}
		},
		
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDiv");
			var mainTable = domConstruct.create("table", null, dataDiv);
			var tr, td;
			
			for (var i=0; i<this.floorcount; i++) {
				if (i%2 == 0) {
					tr = domConstruct.create("tr", null, mainTable);
					td = domConstruct.create("td", null, tr);
				} else {
					td = domConstruct.create("td", null, tr);
				}
					
				this.populateColumn(td, "floorrise_" + (i+1), "Floor " + (i+1) + ":", "^[0-9]([0-9]{1,3})?(\.[1-9])?$", "Enter valid decimal value upto 9999.9", "");
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