define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/PersonWidget',
    'lib/widget/AddressWidget',
    'lib/widget/PersonContactInfoWidget',
    'lib/widget/Person1Widget',
    'lib/widget/Person1ContactInfoWidget',
    "dojo/text!./template/AddEditCustomerTemplate.html"
], function (declare, registry, AddEditDialog, PersonWidget, AddressWidget, 
		PersonContactInfoWidget, Person1Widget, Person1ContactInfoWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			
			this.address.city.set("item", {});
			this.address.state.set("item", {});
			if (this.userId) {
				this.user.set("value", this.userId);
			}
	       	this.personaldetails.dateofbirth.set("required",false);
	       	this.address.zipcode.set("required", false); 
			
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.user.set("value", data.userId);
			
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
			
			this.contactinfo.personphone.set("value", data.person.contactInfo.phoneNumber);
			this.contactinfo.personaltphone.set("value", data.person.contactInfo.alternateNumber);
			this.contactinfo.personmobile.set("value", data.person.contactInfo.mobileNumber);
			this.contactinfo.personemailid.set("value", data.person.contactInfo.emailID);
			
			if (data.coOwner && data.coOwner != null) {
				this.coapplicantpersonaldetails.firstname1.set("value", data.coOwner.firstName);
				this.coapplicantpersonaldetails.middlename1.set("value", data.coOwner.middleName);
				this.coapplicantpersonaldetails.lastname1.set("value", data.coOwner.lastName);
				this.coapplicantpersonaldetails.dateofbirth1.set("value", data.coOwner.dateOfBirth);
				this.coapplicantpersonaldetails.profession1.set("value", data.coOwner.profession);
				this.coapplicantpersonaldetails.otherdetails1.set("value", data.coOwner.otherDetail);
				
				this.coapplicantcontactinfo.person1phone.set("value", data.coOwner.contactInfo.phoneNumber);
				this.coapplicantcontactinfo.person1altphone.set("value", data.coOwner.contactInfo.alternateNumber);
				this.coapplicantcontactinfo.person1mobile.set("value", data.coOwner.contactInfo.mobileNumber);
				this.coapplicantcontactinfo.person1emailid.set("value", data.coOwner.contactInfo.emailID);
			}
		},
		
		validate: function() {
        	var isValid = this.inherited(arguments);4
        	console.log('test');
        	console.log(this.personaldetails.dateofbirth);
        	this.personaldetails.dateofbirth.setAttribute("data-dojo-props", "required:false");
        	
        	if (isValid) {
        		var phoneNumber = this.contactinfo.personphone.get("value");
        		var mobileNumber = this.contactinfo.personmobile.get("value");
        		
            	if (!phoneNumber && !mobileNumber) {
            		this.messageNode.innerHTML = "Either phone or mobile number should be provided.";
            		isValid = false;
            	}
        	}
        	
        	return isValid;
        }
    });
});