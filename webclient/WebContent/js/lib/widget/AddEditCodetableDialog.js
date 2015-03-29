define([
    'dojo/_base/declare',
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditCodetableTemplate.html"
], function (declare, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.code.set("value", data.code);
			this.name.set("value", data.name);
		}
    });
});