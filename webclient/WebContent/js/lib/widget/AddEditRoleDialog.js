define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditRoleTemplate.html"
], function (declare, registry, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			
			this.admin.set("checked", false);
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.name.set("value", data.name);
			this.description.set("value", data.description);
			this.admin.set("checked", data.admin);
		}
    });
});