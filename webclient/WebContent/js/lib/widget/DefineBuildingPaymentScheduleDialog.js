define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/_base/array",
    "dojo/dom-style",
    "dojo/dom-construct",
    "dijit/registry",
    "dojo/on",
    "dijit/layout/TabContainer",
    "dijit/layout/ContentPane",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/DefineBuildingPaymentScheduleTemplate.html"
], function (declare, lang, array, domStyle, domConstruct, registry, on, TabContainer, 
		ContentPane, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
		},

		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated payment schedule for this building.")
			}
			this.inherited(arguments);
		},
		
		setData: function (data) {
			
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
			
			if (this.hasMultiplePaymentSchedules) {
				this.createDataDivWithContentTabs();
				
				this.setValueToScheduleRow(data, "Registration payment", "percentamount_even_reg", "date_even_reg", "desc_even_reg", 1);
				this.setValueToScheduleRow(data, "Plinth payment", "percentamount_even_pli", "date_even_pli", "desc_even_pli", 1);
				for (var i=0; i<this.floorcount; i++) {
					var slab = i + 1;
					if (slab%2 == 0) {
						this.setValueToScheduleRow(data, "Slab " + (i+1) + " payment", "percentamount_even_" + (i+1), "date_even_" + (i+1), "desc_even_" + (i+1), 1);
					}
				}
				this.setValueToScheduleRow(data, "Brick payment", "percentamount_even_bri", "date_even_even_bri", "desc_even_bri", 1);
				this.setValueToScheduleRow(data, "Plastering payment", "percentamount_even_pla", "date_even_pla", "desc_even_pla", 1);
				this.setValueToScheduleRow(data, "Flooring payment", "percentamount_even_flo", "date_even_flo", "desc_even_flo", 1);
				this.setValueToScheduleRow(data, "Possession payment", "percentamount_even_pos", "date_even_pos", "desc_even_pos", 1);
				
				this.setValueToScheduleRow(data, "Registration payment", "percentamount_odd_reg", "date_odd_reg", "desc_odd_reg", 2);
				this.setValueToScheduleRow(data, "Plinth payment", "percentamount_odd_pli", "date_odd_pli", "desc_odd_pli", 2);
				for (var i=0; i<this.floorcount; i++) {
					var slab = i + 1;
					if (slab%2 != 0) {
						this.setValueToScheduleRow(data, "Slab " + (i+1) + " payment", "percentamount_odd_" + (i+1), "date_odd_" + (i+1), "desc_odd_" + (i+1), 2);
					}
				}
				this.setValueToScheduleRow(data, "Brick payment", "percentamount_odd_bri", "date_odd_bri", "desc_odd_bri", 2);
				this.setValueToScheduleRow(data, "Plastering payment", "percentamount_odd_pla", "date_odd_pla", "desc_odd_pla", 2);
				this.setValueToScheduleRow(data, "Flooring payment", "percentamount_odd_flo", "date_odd_flo", "desc_odd_flo", 2);
				this.setValueToScheduleRow(data, "Possession payment", "percentamount_odd_pos", "date_odd_pos", "desc_odd_pos", 2);

				
				this.updateTotalPercentValue();
			} else {
				this.createDataDiv();
				
				this.setValueToScheduleRow(data, "Registration payment", "percentamount_reg", "date_reg", "desc_reg", 0);
				this.setValueToScheduleRow(data, "Plinth payment", "percentamount_pli", "date_pli", "desc_pli", 0);
				for (var i=0; i<this.floorcount; i++) {
					this.setValueToScheduleRow(data, "Slab " + (i+1) + " payment", "percentamount_" + (i+1), "date_" + (i+1), "desc_" + (i+1), 0);
				}
				this.setValueToScheduleRow(data, "Brick payment", "percentamount_bri", "date_bri", "desc_bri", 0);
				this.setValueToScheduleRow(data, "Plastering payment", "percentamount_pla", "date_pla", "desc_pla", 0);
				this.setValueToScheduleRow(data, "Flooring payment", "percentamount_flo", "date_flo", "desc_flo", 0);
				this.setValueToScheduleRow(data, "Possession payment", "percentamount_pos", "date_pos", "desc_pos", 0);
				
				this.updateTotalPercentValue();
			}
		},
		
		createDataDiv: function () {
			var dataDiv = dojo.byId("dataDiv");
			var table = domConstruct.create("table", null, dataDiv);
			this.createTotalPercentField(table, 0);
			
			this.createScheduleRow (table, "Registration payment", "type_reg", "Type:", "percentamount_reg", 
					"Percent Amount:", "date_reg", "Date:", "desc_reg", "Description:");
			this.createScheduleRow (table, "Plinth payment", "type_pli", "Type:", "percentamount_pli", 
					"Percent Amount:", "date_pli", "Date:", "desc_pli", "Description:");
			for (var i=0; i<this.floorcount; i++) {
				var slab = i + 1;
				this.createScheduleRow (table, "Slab " + slab + " payment", "type_" + slab, "Type:", "percentamount_" + slab, 
					"Percent Amount:", "date_" + slab, "Date:", "desc_" + slab, "Description:");
			}
			this.createScheduleRow (table, "Brick payment", "type_bri", "Type:", "percentamount_bri", 
					"Percent Amount:", "date_bri", "Date:", "desc_bri", "Description:");
			this.createScheduleRow (table, "Plastering payment", "type_pla", "Type:", "percentamount_pla", 
					"Percent Amount:", "date_pla", "Date:", "desc_pla", "Description:");
			this.createScheduleRow (table, "Flooring payment", "type_flo", "Type:", "percentamount_flo", 
					"Percent Amount:", "date_flo", "Date:", "desc_flo", "Description:");
			this.createScheduleRow (table, "Possession payment", "type_pos", "Type:", "percentamount_pos", 
					"Percent Amount:", "date_pos", "Date:", "desc_pos", "Description:");
			
        },
		
        createDataDivWithContentTabs: function () {
			/*var contentTabs = new TabContainer({
				id: "contenttabspaymentschedule",
				tabPosition: "top",
				style: "width: 700px;"
		    }, "dataDiv");

		    var cp1 = new ContentPane({
		    	 id: "evencontentpane",
		         title: "Even floors schedule",
		         content: this.createContentTabDiv(true)
		    });
		    contentTabs.addChild(cp1);

		    var cp2 = new ContentPane({
		    	 id: "oddcontentpane",
		         title: "Odd floors schedule",
		         content: this.createContentTabDiv(false)
		    });
		    contentTabs.addChild(cp2);
		    contentTabs.startup();
		    contentTabs.resize();  */
        	
        	var dataDiv = dojo.byId("dataDiv");
			var table = domConstruct.create("table", null, dataDiv);
			
			this.createTotalPercentField(table, 1);
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"colspan": 3}, tr);
			var fieldset = domConstruct.create("fieldset", {"style": "width: 100%;"}, td);
			var legend = domConstruct.create("legend", {"style": "font-size: 4px"}, fieldset).innerHTML = "Even floors schedule";
			this.createContentTabDiv(true, fieldset);
			
			this.createTotalPercentField(table, 2);
			tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"colspan": 3}, tr);
			fieldset = domConstruct.create("fieldset", {"style": "width: 100%;"}, td);
			legend = domConstruct.create("legend", {"style": "font-size: 4px"}, fieldset).innerHTML = "Odd floors schedule";
			this.createContentTabDiv(false, fieldset);
		},
		
		createContentTabDiv: function (even, fieldset) {
			var contentTabDiv = domConstruct.create("div", null, fieldset);
			var table = domConstruct.create("table", null, contentTabDiv);
			
			if (even) {
				this.createScheduleRow (table, "Registration payment", "type_even_reg", "Type:", "percentamount_even_reg", 
						"Percent Amount:", "date_even_reg", "Date:", "desc_even_reg", "Description:");
				this.createScheduleRow (table, "Plinth payment", "type_even_pli", "Type:", "percentamount_even_pli", 
						"Percent Amount:", "date_even_pli", "Date:", "desc_even_pli", "Description:");
				for (var i=0; i<this.floorcount; i++) {
					var slab=i+1;
					if (slab%2 == 0) {
						this.createScheduleRow (table, "Slab " + slab + " payment", "type_even_" + slab, "Type:", "percentamount_even_" + slab, 
								"Percent Amount:", "date_even_" + slab, "Date:", "desc_even_" + slab, "Description:");
					}
				}
				this.createScheduleRow (table, "Brick payment", "type_even_bri", "Type:", "percentamount_even_bri", 
						"Percent Amount:", "date_even_bri", "Date:", "desc_even_bri", "Description:");
				this.createScheduleRow (table, "Plastering payment", "type_even_pla", "Type:", "percentamount_even_pla", 
						"Percent Amount:", "date_even_pla", "Date:", "desc_even_pla", "Description:");
				this.createScheduleRow (table, "Flooring payment", "type_even_flo", "Type:", "percentamount_even_flo", 
						"Percent Amount:", "date_even_flo", "Date:", "desc_even_flo", "Description:");
				this.createScheduleRow (table, "Possession payment", "type_even_pos", "Type:", "percentamount_even_pos", 
						"Percent Amount:", "date_even_pos", "Date:", "desc_even_pos", "Description:");
			} else {
				this.createScheduleRow (table, "Registration payment", "type_odd_reg", "Type:", "percentamount_odd_reg", 
						"Percent Amount:", "date_odd_reg", "Date:", "desc_odd_reg", "Description:");
				this.createScheduleRow (table, "Plinth payment", "type_odd_pli", "Type:", "percentamount_odd_pli", 
						"Percent Amount:", "date_odd_pli", "Date:", "desc_odd_pli", "Description:");
				for (var i=0; i<this.floorcount; i++) {
					var slab=i+1;
					if (slab%2 != 0) {
						this.createScheduleRow (table, "Slab " + slab + " payment", "type_odd_" + slab, "Type:", "percentamount_odd_" + slab, 
								"Percent Amount:", "date_odd_" + slab, "Date:", "desc_odd_" + slab, "Description:");
					}
				}
				this.createScheduleRow (table, "Brick payment", "type_odd_bri", "Type:", "percentamount_odd_bri", 
						"Percent Amount:", "date_odd_bri", "Date:", "desc_odd_bri", "Description:");
				this.createScheduleRow (table, "Plastering payment", "type_odd_pla", "Type:", "percentamount_odd_pla", 
						"Percent Amount:", "date_odd_pla", "Date:", "desc_odd_pla", "Description:");
				this.createScheduleRow (table, "Flooring payment", "type_odd_flo", "Type:", "percentamount_odd_flo", 
						"Percent Amount:", "date_odd_flo", "Date:", "desc_odd_flo", "Description:");
				this.createScheduleRow (table, "Possession payment", "type_odd_pos", "Type:", "percentamount_odd_pos", 
						"Percent Amount:", "date_odd_pos", "Date:", "desc_odd_pos", "Description:");
			}
			
			return contentTabDiv;
        },
        
        createTotalPercentField: function (table, applicableTo) {
        	var tr, td;
        	
        	tr = domConstruct.create("tr", null, table);
			td = domConstruct.create("td", {"align": "right"}, tr);
			if (applicableTo == 1) {
				var label = domConstruct.create("label", {"for": "totalpercenteven"}, td).innerHTML = "Total Percent:";
				td = domConstruct.create("td", {"align": "right"}, tr);
				var span = domConstruct.create("span", {"id": "totalpercenteven"}, td);
			} else if (applicableTo == 2) {
				var label = domConstruct.create("label", {"for": "totalpercentodd"}, td).innerHTML = "Total Percent:";
				td = domConstruct.create("td", {"align": "right"}, tr);
				var span = domConstruct.create("span", {"id": "totalpercentodd"}, td);
			} else if (applicableTo == 0) {
				var label = domConstruct.create("label", {"for": "totalpercent"}, td).innerHTML = "Total Percent:";
				td = domConstruct.create("td", {"align": "right"}, tr);
				var span = domConstruct.create("span", {"id": "totalpercent"}, td);
			}
        },
        
		hide: function () {
			this.inherited(arguments);
			
			if (this.hasMultiplePaymentSchedules) {
				this.destroyScheduleRow("type_even_reg", "percentamount_even_reg", "date_even_reg", "desc_even_reg");
				this.destroyScheduleRow("type_even_pli", "percentamount_even_pli", "date_even_pli", "desc_even_pli");
				for (var i=0; i<this.floorcount; i++) {
					var slab = i + 1;
					if (slab%2 == 0) {
						this.destroyScheduleRow("type_even_" + (i+1), "percentamount_even_" + (i+1), "date_even_" + (i+1), "desc_even_" + (i+1));
					}
				}
				this.destroyScheduleRow("type_even_bri", "percentamount_even_bri", "date_even_bri", "desc_even_bri");
				this.destroyScheduleRow("type_even_pla", "percentamount_even_pla", "date_even_pla", "desc_even_pla");
				this.destroyScheduleRow("type_even_flo", "percentamount_even_flo", "date_even_flo", "desc_even_flo");
				this.destroyScheduleRow("type_even_pos", "percentamount_even_pos", "date_even_pos", "desc_even_pos");
				
				this.destroyScheduleRow("type_odd_reg", "percentamount_odd_reg", "date_odd_reg", "desc_odd_reg");
				this.destroyScheduleRow("type_odd_pli", "percentamount_odd_pli", "date_odd_pli", "desc_odd_pli");
				for (var i=0; i<this.floorcount; i++) {
					var slab = i + 1;
					if (slab%2 != 0) {
						this.destroyScheduleRow("type_odd_" + (i+1), "percentamount_odd_" + (i+1), "date_odd_" + (i+1), "desc_odd_" + (i+1));
					}
				}
				this.destroyScheduleRow("type_odd_bri", "percentamount_odd_bri", "date_odd_bri", "desc_odd_bri");
				this.destroyScheduleRow("type_odd_pla", "percentamount_odd_pla", "date_odd_pla", "desc_odd_pla");
				this.destroyScheduleRow("type_odd_flo", "percentamount_odd_flo", "date_odd_flo", "desc_odd_flo");
				this.destroyScheduleRow("type_odd_pos", "percentamount_odd_pos", "date_odd_pos", "desc_odd_pos");
			} else {
				this.destroyScheduleRow("type_reg", "percentamount_reg", "date_reg", "desc_reg");
				this.destroyScheduleRow("type_pli", "percentamount_pli", "date_pli", "desc_pli");
				for (var i=0; i<this.floorcount; i++) {
					this.destroyScheduleRow("type_" + (i+1), "percentamount_" + (i+1), "date_" + (i+1), "desc_" + (i+1));
				}
				this.destroyScheduleRow("type_bri", "percentamount_bri", "date_bri", "desc_bri");
				this.destroyScheduleRow("type_pla", "percentamount_pla", "date_pla", "desc_pla");
				this.destroyScheduleRow("type_flo", "percentamount_flo", "date_flo", "desc_flo");
				this.destroyScheduleRow("type_pos", "percentamount_pos", "date_pos", "desc_pos");
			}
			
			domConstruct.empty("dataDiv");
		},

		updateTotalPercentValue: function () {
			if (this.hasMultiplePaymentSchedules) {
				dojo.byId("totalpercenteven").innerHTML = this.getTotalPercentValue(1);
				dojo.byId("totalpercentodd").innerHTML = this.getTotalPercentValue(2);
			} else {
				dojo.byId("totalpercent").innerHTML = this.getTotalPercentValue(0);
			}
		},
		
		getTotalPercentValue: function (type) {
			var totalValue = 0;
			if (type == 0) {
				totalValue += this.getPercentValueFromWidget("percentamount_reg");
				totalValue += this.getPercentValueFromWidget("percentamount_pli");
				
				for (var i=0; i<this.floorcount; i++) {
					totalValue += this.getPercentValueFromWidget("percentamount_" + (i+1));
				}
				
				totalValue += this.getPercentValueFromWidget("percentamount_bri");
				totalValue += this.getPercentValueFromWidget("percentamount_pla");
				totalValue += this.getPercentValueFromWidget("percentamount_flo");
				totalValue += this.getPercentValueFromWidget("percentamount_pos");
			} else if (type == 1) {
				totalValue += this.getPercentValueFromWidget("percentamount_even_reg");
				totalValue += this.getPercentValueFromWidget("percentamount_even_pli");
				
				for (var i=0; i<this.floorcount; i++) {
					totalValue += this.getPercentValueFromWidget("percentamount_even_" + (i+1));
				}
				
				totalValue += this.getPercentValueFromWidget("percentamount_even_bri");
				totalValue += this.getPercentValueFromWidget("percentamount_even_pla");
				totalValue += this.getPercentValueFromWidget("percentamount_even_flo");
				totalValue += this.getPercentValueFromWidget("percentamount_even_pos");
			} else if (type == 2) {
				totalValue += this.getPercentValueFromWidget("percentamount_odd_reg");
				totalValue += this.getPercentValueFromWidget("percentamount_odd_pli");
				
				for (var i=0; i<this.floorcount; i++) {
					totalValue += this.getPercentValueFromWidget("percentamount_odd_" + (i+1));
				}
				
				totalValue += this.getPercentValueFromWidget("percentamount_odd_bri");
				totalValue += this.getPercentValueFromWidget("percentamount_odd_pla");
				totalValue += this.getPercentValueFromWidget("percentamount_odd_flo");
				totalValue += this.getPercentValueFromWidget("percentamount_odd_pos");
			}
			
			return totalValue;
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
		
		setValueToScheduleRow: function (data, type, percentFieldId, dateFieldId, descFieldId, applicableTo) {
			this.setValueToWidget(percentFieldId, data, type + "_" + applicableTo, "percentamount");
			this.setValueToWidget(dateFieldId, data, type + "_" + applicableTo, "scheduledate");
			this.setValueToWidget(descFieldId, data, type + "_" + applicableTo, "description");
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
				
		destroyScheduleRow: function (typeFieldId, percentFieldId, dateFieldId, descFieldId) {
			this.destroyWidget(typeFieldId);
			this.destroyWidget(percentFieldId);
			this.destroyWidget(dateFieldId);
			this.destroyWidget(descFieldId);
		},
		
		createScheduleRow: function (table, legendTitle, typeFieldId, typeFieldTitle, percentFieldId, percentFieldTitle,
        		dateFieldId, dateFieldTitle, descFieldId, descFieldTitle) {
			var PERCENT_REGEX = "100|(([1-9][0-9])|[0-9])(\.(([0-9][1-9])|[1-9]))?";
        	var fieldset = this.createTypeRow(table, legendTitle);
			var table = domConstruct.create("table", null, fieldset);
			var tr = domConstruct.create("tr", null, table);
			var td1 = domConstruct.create("td", null, tr);
			var td2 = domConstruct.create("td", null, tr);
			var td3 = domConstruct.create("td", null, tr);
			var td4 = domConstruct.create("td", null, tr);
			this.populateColumn(td1, typeFieldId, typeFieldTitle, "", "", legendTitle);
			this.populateColumn(td2, percentFieldId, percentFieldTitle, PERCENT_REGEX, "Enter valid percent value.", "");
			this.populateDateColumn(td3, dateFieldId, dateFieldTitle);
			this.populateTextareaColumn(td4, descFieldId, descFieldTitle);
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
		
        validate: function() {
        	var isValid = this.inherited(arguments);
        	
        	if (isValid) {
        		if (this.hasMultiplePaymentSchedules) {
        			if (this.getTotalPercentValue(1) != 100) {
                		this.messageNode.innerHTML = "Sum of all percent amounts for even floor schedule should be 100.";
                		return false
                	}
        			
        			if (this.getTotalPercentValue(2) != 100) {
                		this.messageNode.innerHTML = "Sum of all percent amounts for odd floor schedule should be 100.";
                		return false;
                	}
    			} else {
    				if (this.getTotalPercentValue(0) != 100) {
                		this.messageNode.innerHTML = "Sum of all percent amounts should be 100.";
                		return false;
                	}
    			}
        	}
        	
        	return isValid;
        }
    });
});