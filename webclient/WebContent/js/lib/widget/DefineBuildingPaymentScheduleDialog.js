define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/dom-style",
    "dojo/dom-construct",
    "dijit/registry",
    "dojo/on",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/DefineBuildingPaymentScheduleTemplate.html"
], function (declare, lang, array, domStyle, domConstruct, registry, on, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
		},
		
		setData: function (data) {
			this.createDataDiv();
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
			
			this.setValueToWidget("percentamount_reg", data, "Registration payment", "percentamount");
			this.setValueToWidget("date_reg", data, "Registration payment", "scheduledate");
			this.setValueToWidget("desc_reg", data, "Registration payment", "description");
			
			this.setValueToWidget("percentamount_pli", data, "Plinth payment", "percentamount");
			this.setValueToWidget("date_pli", data, "Plinth payment", "scheduledate");
			this.setValueToWidget("desc_pli", data, "Plinth payment", "description");

			for (var i=0; i<this.floorcount; i++) {
				this.setValueToWidget("percentamount_" + (i+1), data, "Slab " + (i+1) + " payment", "percentamount");
				this.setValueToWidget("date_" + (i+1), data, "Slab " + (i+1) + " payment", "scheduledate");
				this.setValueToWidget("desc_" + (i+1), data, "Slab " + (i+1) + " payment", "description");
			}
			
			this.setValueToWidget("percentamount_bri", data, "Brick payment", "percentamount");
			this.setValueToWidget("date_bri", data, "Brick payment", "scheduledate");
			this.setValueToWidget("desc_bri", data, "Brick payment", "description");
			
			this.setValueToWidget("percentamount_pla", data, "Plastering payment", "percentamount");
			this.setValueToWidget("date_pla", data, "Plastering payment", "scheduledate");
			this.setValueToWidget("desc_pla", data, "Plastering payment", "description");
			
			this.setValueToWidget("percentamount_flo", data, "Flooring payment", "percentamount");
			this.setValueToWidget("date_flo", data, "Flooring payment", "scheduledate");
			this.setValueToWidget("desc_flo", data, "Flooring payment", "description");
			
			this.setValueToWidget("percentamount_pos", data, "Possession payment", "percentamount");
			this.setValueToWidget("date_pos", data, "Possession payment", "scheduledate");
			this.setValueToWidget("desc_pos", data, "Possession payment", "description");
			
			this.updateTotalPercentValue();
		},
		
		updateTotalPercentValue: function () {
			var totalValue = 0;
			totalValue += this.getPercentValueFromWidget("percentamount_reg");
			totalValue += this.getPercentValueFromWidget("percentamount_pli");
			
			for (var i=0; i<this.floorcount; i++) {
				totalValue += this.getPercentValueFromWidget("percentamount_" + (i+1));
			}
			
			totalValue += this.getPercentValueFromWidget("percentamount_bri");
			totalValue += this.getPercentValueFromWidget("percentamount_pla");
			totalValue += this.getPercentValueFromWidget("percentamount_flo");
			totalValue += this.getPercentValueFromWidget("percentamount_pos");
			this.totalpercent.innerHTML = totalValue;
		},
		
		hide: function () {
			this.inherited(arguments);
			domConstruct.empty("dataDiv");
			var tb;
			
			this.destroyWidget("type_reg");
			this.destroyWidget("percentamount_reg");
			this.destroyWidget("date_reg");
			this.destroyWidget("desc_reg");
			
			this.destroyWidget("type_pli");
			this.destroyWidget("percentamount_pli");
			this.destroyWidget("date_pli");
			this.destroyWidget("desc_pli");
			
			for (var i=0; i<this.floorcount; i++) {
				this.destroyWidget("type_" + (i+1));
				this.destroyWidget("percentamount_" + (i+1));
				this.destroyWidget("date_" + (i+1));
				this.destroyWidget("desc_" + (i+1));
			}
			
			this.destroyWidget("type_bri");
			this.destroyWidget("percentamount_bri");
			this.destroyWidget("date_bri");
			this.destroyWidget("desc_bri");
			
			this.destroyWidget("type_pla");
			this.destroyWidget("percentamount_pla");
			this.destroyWidget("date_pla");
			this.destroyWidget("desc_pla");
			
			this.destroyWidget("type_flo");
			this.destroyWidget("percentamount_flo");
			this.destroyWidget("date_flo");
			this.destroyWidget("desc_flo");
			
			this.destroyWidget("type_pos");
			this.destroyWidget("percentamount_pos");
			this.destroyWidget("date_pos");
			this.destroyWidget("desc_pos");
		},
		
		getPercentValueFromWidget: function (id) {
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
		
		setValueToWidget: function (id, data, key, field) {
			var widget = registry.byId(id);
			if (widget) {
				if (data[key] && data[key][field]) {
					widget.set("value", data[key][field]);
				}
			}
		},
		
		destroyWidget: function (id) {
			var widget = registry.byId(id);
			if (widget) {
				widget.destroy();
			}
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated payment schedule for this building.")
			}
			this.inherited(arguments);
		},
		
		populateColumn: function(column, id, labelText, regExp, invalidMessage, value) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
			
			if (new RegExp("^percentamount").test(id)) {
				domConstruct.create("label", {"for": id}, td).innerHTML = labelText;
				
				tr = domConstruct.create("tr", null, table);
				td = domConstruct.create("td", null, tr);
				tb = new dijit.form.ValidationTextBox({
					id: id,
					name: id,
					pattern: regExp, 
					invalidMessage: invalidMessage
				}, td);
				on(tb, "change", lang.hitch(this,"updateTotalPercentValue"));
			} else if (new RegExp("^type_").test(id)) {
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
					value: value
				}, td);
			}
		},
		
		populateDateColumn: function(column, id, labelText) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
			domConstruct.create("label", {"for": id}, td).innerHTML = labelText;
			
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", null, tr);
			
			tb = new dijit.form.DateTextBox({
				id: id,
				name: id,
			}, td);
		},
		
		populateTextareaColumn: function(column, id, labelText) {
			var table = domConstruct.create("table", null, column);
			var tr = domConstruct.create("tr", null, table);
			var td = domConstruct.create("td", null, tr);
			domConstruct.create("label", {"for": id}, td).innerHTML = labelText;
			
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", null, tr);
			
			ta = new dijit.form.SimpleTextarea({
				id: id,
				name: id,
			}, td);
		},
		
		createTypeRow: function (table, value) {
			var tr, td;
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"colspan": 3}, tr);
			var fieldset = domConstruct.create("fieldset", {"style": "width: 100%;"}, td);
			var legend = domConstruct.create("legend", {"style": "font-size: 4px"}, fieldset).innerHTML = value;
			
			return fieldset;
		},
		
		createDataDiv: function () {
			var tr, tr1, td1, td2, td3, td4, fieldset, table, table1;
			var dataDiv = dojo.byId("dataDiv");
			var PERCENT_REGEX = "100|(([1-9][0-9])|[0-9])(\.(([0-9][1-9])|[1-9]))?";
			
			table = domConstruct.create("table", null, dataDiv);
			
			fieldset = this.createTypeRow(table, "Registration payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_reg", "Type:", "", "", "Registration payment");
			this.populateColumn(td2, "percentamount_reg", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_reg", "Date:");
			this.populateTextareaColumn(td4, "desc_reg", "Description:");
			
			fieldset = this.createTypeRow(table, "Plinth payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_pli", "Type:", "", "", "Plinth payment");
			this.populateColumn(td2, "percentamount_pli", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_pli", "Date:");
			this.populateTextareaColumn(td4, "desc_pli", "Description:");
			
			for (var i=0; i<this.floorcount; i++) {
				var slab = i+1;
				fieldset = this.createTypeRow(table, "Slab " + slab + " payment");
				table1 = domConstruct.create("table", null, fieldset);
				tr = domConstruct.create("tr", null, table1);
				td1 = domConstruct.create("td", null, tr);
				td2 = domConstruct.create("td", null, tr);
				td3 = domConstruct.create("td", null, tr);
				td4 = domConstruct.create("td", null, tr);
				this.populateColumn(td1, "type_" + slab, "Type:", "", "", "Slab " + slab + " payment");
				this.populateColumn(td2, "percentamount_" + slab, "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
				this.populateDateColumn(td3, "date_" + slab, "Date:");
				this.populateTextareaColumn(td4, "desc_" + slab, "Description:");
			}
			
			fieldset = this.createTypeRow(table, "Brick payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_bri", "Type:", "", "", "Brick payment");
			this.populateColumn(td2, "percentamount_bri", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_bri", "Date:");
			this.populateTextareaColumn(td4, "desc_bri", "Description:");
			
			fieldset = this.createTypeRow(table, "Plastering payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_pla", "Type:", "", "", "Plastering payment");
			this.populateColumn(td2, "percentamount_pla", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_pla", "Date:");
			this.populateTextareaColumn(td4, "desc_pla", "Description:");
			
			fieldset = this.createTypeRow(table, "Flooring payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_flo", "Type:", "", "", "Flooring payment");
			this.populateColumn(td2, "percentamount_flo", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_flo", "Date:");
			this.populateTextareaColumn(td4, "desc_flo", "Description:");
			
			fieldset = this.createTypeRow(table, "Possession payment");
			table1 = domConstruct.create("table", null, fieldset);
			tr = domConstruct.create("tr", null, table1);
			td1 = domConstruct.create("td", null, tr);
			td2 = domConstruct.create("td", null, tr);
			td3 = domConstruct.create("td", null, tr);
			td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, "type_pos", "Type:", "", "", "Possession payment");
			this.populateColumn(td2, "percentamount_pos", "Percent Amount:", PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, "date_pos", "Date:");
			this.populateTextareaColumn(td4, "desc_pos", "Description:");
        },
		
        validate: function() {
        	var isValid = this.inherited(arguments);
        	
        	if (isValid) {
        		var percentAmount;
            	var totalPercentAmount = 0;
            	percentAmount = dojo.byId("percentamount_reg").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	percentAmount = dojo.byId("percentamount_pli").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	for (var i=0; i<this.floorcount; i++) {
            		var slab = i+1;
    				percentAmount = dojo.byId("percentamount_" + slab).value;
    				if (percentAmount != "") {
    					totalPercentAmount += parseInt(percentAmount);
    				}
    			}
            	
            	percentAmount = dojo.byId("percentamount_bri").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	percentAmount = dojo.byId("percentamount_pla").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	percentAmount = dojo.byId("percentamount_flo").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	percentAmount = dojo.byId("percentamount_pos").value;
            	if (percentAmount != "") {
            		totalPercentAmount += parseInt(percentAmount);
    			}
            	
            	if (totalPercentAmount != 100) {
            		this.messageNode.innerHTML = "Sum of all percent amounts should be 100.";
            		isValid = false;
            	}
        	}
        	
        	return isValid;
        }
    });
});