define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/PersonWidget',
    'lib/widget/AddressWidget',
    'lib/widget/ContactInfoWidget',
    'lib/widget/PersonContactInfoWidget',
    "dojo/text!./template/AddEditOrganizationTemplate.html"
], function (declare, registry, AddEditDialog, PersonWidget, AddressWidget, ContactInfoWidget, PersonContactInfoWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.orgname.set("value", data.name);
			
			this.orgcontactinfo.phone.set("value", data.contactInfo.phoneNumber);
			this.orgcontactinfo.altphone.set("value", data.contactInfo.alternateNumber);
			this.orgcontactinfo.mobile.set("value", data.contactInfo.mobileNumber);
			this.orgcontactinfo.emailid.set("value", data.contactInfo.emailID);
			
			this.orgaddress.addressline1.set("value", data.address.addressLine1);
			this.orgaddress.addressline2.set("value", data.address.addressLine2);
			this.orgaddress.city.set("item", data.address.city);
			this.orgaddress.state.set("item", data.address.state);
			this.orgaddress.zipcode.set("value", data.address.zipCode);
			
			this.orgcontactperson.firstname.set("value", data.contactPerson.firstName);
			this.orgcontactperson.middlename.set("value", data.contactPerson.middleName);
			this.orgcontactperson.lastname.set("value", data.contactPerson.lastName);
			this.orgcontactperson.dateofbirth.set("value", data.contactPerson.dateOfBirth);
			this.orgcontactperson.profession.set("value", data.contactPerson.profession);
			this.orgcontactperson.otherdetails.set("value", data.contactPerson.otherDetail);
			
			this.orgcontactpersoncontactinfo.personphone.set("value", data.contactPerson.contactInfo.phoneNumber);
			this.orgcontactpersoncontactinfo.personaltphone.set("value", data.contactPerson.contactInfo.alternateNumber);
			this.orgcontactpersoncontactinfo.personmobile.set("value", data.contactPerson.contactInfo.mobileNumber);
			this.orgcontactpersoncontactinfo.personemailid.set("value", data.contactPerson.contactInfo.emailID);
		}
    });
});