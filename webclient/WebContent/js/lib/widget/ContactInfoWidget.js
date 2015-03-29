define([
    'dojo/_base/declare',
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dijit/form/ValidationTextBox', 
    'dijit/form/Button',
    "dojo/text!./template/ContactInfoTemplate.html"
], function (declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
        ValidationTextBox, Button, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
    	templateString: template,
    	
    	constructor:function (options) {
    		declare.safeMixin(this, options);
            this.inherited(arguments)
        },
        
        isEmailAddress: function (emailAdress) {
        	if (emailAdress == "") {
        		return true;
        	}
            var EMAIL_REGEXP = new RegExp('^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$', 'i');
            return EMAIL_REGEXP.test(emailAdress)
        },
        
        isLandlineNumber: function (landlineNumber) {
        	if (landlineNumber == "") {
        		return true;
        	}
            var LANDLINE_REGEXP = new RegExp('^(([\+])?[0-9]{2}([-]|[ ])?)?[0-9]{2,5}([-]|[ ])?[0-9]{5,8}$', 'i');
            return LANDLINE_REGEXP.test(landlineNumber)
        },
        
        isMobileNumber: function (mobileNumber) {
        	if (mobileNumber == "") {
        		return true;
        	}
            var MOBILE_REGEXP = new RegExp('^(([\+])?[0-9]{2}([-]|[ ])?)?[0-9]{10}$', 'i');
            return MOBILE_REGEXP.test(mobileNumber)
        },
        
        postCreate:function () {
            this.inherited(arguments);
        }
    });
});
