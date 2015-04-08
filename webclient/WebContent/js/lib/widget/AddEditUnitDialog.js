define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "dojo/dom-class",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditUnitTemplate.html"
], function (declare, array, domClass, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			domClass.remove(this.createmultiplelabeleven, "hiddenInput");
			domClass.remove(this.createmultipleeven.domNode, "hiddenInput");
			domClass.remove(this.createmultiplelabelodd, "hiddenInput");
			domClass.remove(this.createmultipleodd.domNode, "hiddenInput");
			domClass.add(this.registered.domNode, "hiddenInput");
			domClass.add(dojo.byId("registeredlabel"), "hiddenInput");
			
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.number.set("value", data.unitNumber);
			this.projectbuilding.set("value", data.buildingId);
			this.unittype.set("item", data.unitType);
			this.floornumber.set("value", data.floorNumber);
			this.bookingamount.set("value", data.bookingAmount);
			this.carpetarea.set("value", data.carpetArea);
			this.saleablearea.set("value", data.saleableArea);
			this.otheroptions.set("checked", data.otherOptions);
			this.othercharges.set("value", data.otherCharges);
			this.registered.set("checked", data.registered);
			
			var widget = this;
			array.forEach(data.amenities, function (amenity)
			{
				var options = widget.amenities.domNode.children;
				for (var i = 0; i < options.length; i++) {
				    if (options[i].value == amenity.id) {
				    	options[i].selected = "selected";
				    } 
				}
			});
			
			domClass.remove(this.registered.domNode, "hiddenInput");
			domClass.remove(dojo.byId("registeredlabel"), "hiddenInput");
			domClass.add(this.createmultiplelabeleven, "hiddenInput");
			domClass.add(this.createmultipleeven.domNode, "hiddenInput");
			domClass.add(this.createmultiplelabelodd, "hiddenInput");
			domClass.add(this.createmultipleodd.domNode, "hiddenInput");
		},
		
		postCreate:function () {
			this.inherited(arguments);
			this.setDataToDropdown('../rest/json/data/codetable/unittype/get/all', this.unittype);
			this.setDataToMultiselect('../rest/json/data/codetable/amenity/get/all', this.amenities.domNode);
        }
    });
});