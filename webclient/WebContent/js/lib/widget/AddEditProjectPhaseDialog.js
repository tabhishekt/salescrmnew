define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditProjectPhaseTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.name.set("value", data.name);
			this.description.set("value", data.description);
			this.project.set("item", data.project);
		},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/inventory/project/get/all', this.project);
        }
    });
});