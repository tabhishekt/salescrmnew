define([
    'dojo/_base/declare',
    "dijit/registry",
    "dojo/dom-style",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/AddEditEnquiryTemplate.html"
], function (declare, registry, domStyle, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.userId) {
				this.user.set("value", this.userId);
			}
			
			if (this.customerId) {
				this.customer.set("value", this.customerId);
			}
			
			domStyle.set(dojo.byId("enquirydiv"), "display", "inline");
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.user.set("value", data.userId);
			this.customer.set("value", data.customerId);
			
			this.source.set("item", data.source);
			this.budget.set("item", data.budget);
			this.unittype.set("item", data.interest);
			this.probability.set("item", data.probability);
			this.currenthomestatus.set("item", data.currenthomestatus);
			this.commenttext.set("value", "");
			this.followupdate.set("value", data.followupDate);
			
			domStyle.set(dojo.byId("enquirydiv"), "display", "none");
		},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/codetable/source/get/all', this.source);
        	this.setDataToDropdown('../rest/json/data/codetable/unittype/get/all', this.unittype);
        	this.setDataToDropdown('../rest/json/data/inventory/enquiry/get/budget', this.budget);
        	this.setDataToDropdown('../rest/json/data/inventory/enquiry/get/probability', this.probability);
        	this.setDataToDropdown('../rest/json/data/inventory/enquiry/get/currenthomestatus', this.currenthomestatus);
        }
    });
});