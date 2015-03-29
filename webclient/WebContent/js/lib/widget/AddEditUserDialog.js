define([
    'dojo/_base/declare',
    "dojo/_base/array",
    "lib/widget/AddEditDialog",
    'lib/widget/PersonWidget',
    'lib/widget/AddressWidget',
    'lib/widget/PersonContactInfoWidget',
    "dijit/form/MultiSelect",
    "dojo/text!./template/AddEditUserTemplate.html"
], function (declare, array, AddEditDialog, PersonWidget, AddressWidget, PersonContactInfoWidget, MultiSelect, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.personaldetails.firstname.set("value", data.person.firstName);
			this.personaldetails.middlename.set("value", data.person.middleName);
			this.personaldetails.lastname.set("value", data.person.lastName);
			this.personaldetails.dateofbirth.set("value", data.person.dateOfBirth);
			this.personaldetails.profession.set("value", data.person.profession);
			this.personaldetails.otherdetails.set("value", data.person.otherDetail);
			
			this.address.addressline1.set("value", data.address.addressLine1);
			this.address.addressline2.set("value", data.address.addressLine2);
			this.address.city.set("item", data.address.city);
			this.address.state.set("item", data.address.state);
			this.address.zipcode.set("value", data.address.zipCode);
			
			this.loginname.set("value", data.loginname);
			this.password.set("value", data.password);
			
			var widget = this;
			array.forEach(data.roles, function (role)
			{
				var options = widget.roles.domNode.children;
				for (var i = 0; i < options.length; i++) {
				    if (options[i].value == role.id) {
				    	options[i].selected = "selected";
				    } 
				}
			});
		},
		
		postCreate: function () {
			this.inherited(arguments);
        	this.setDataToMultiselect('../rest/json/data/userrole/role/get/all', this.roles.domNode);
        }
    });
});