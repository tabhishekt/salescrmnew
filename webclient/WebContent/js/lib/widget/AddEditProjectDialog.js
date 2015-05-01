define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditProjectTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.name.set("value", data.name);
			this.logofilename.set("value", data.logoFileName);
			this.description.set("value", data.description);
			this.totalphases.set("value", data.totalPhases);
			this.organization.set("item", data.organization);
			this.address.addressline1.set("value", data.address.addressLine1);
			this.address.addressline2.set("value", data.address.addressLine2);
			this.address.city.set("item", data.address.city);
			this.address.state.set("item", data.address.state);
			this.address.zipcode.set("value", data.address.zipCode);
			this.termsandconditions.set("value", data.termsAndConditions);
		},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/inventory/organization/get/all', this.organization);
        }
    });
});