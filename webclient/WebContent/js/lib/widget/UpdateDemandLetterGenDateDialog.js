define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dijit/registry",
    "dijit/form/ValidationTextBox",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/UpdateDemandLetterGenDateTemplate.html"
], function (declare, array, domConstruct, domStyle, registry, ValidationTextBox, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding3.set("value", this.buildingId);
			}
		},
		
		updatePage: function () {
			if (this.getStatus() == "success") {
				this.setMessage("Successfully updated demand letter generation date for active bookins of this building.")
			}
			this.inherited(arguments);
		}
    });
});