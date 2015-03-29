define([
    'dojo/_base/declare',
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/form/manager/_Mixin', 
    'dojox/form/manager/_NodeMixin', 
    'dojox/form/manager/_FormMixin', 
    "dojo/_base/lang",
    "dijit/registry",
    "dojo/dom-form",
    "dojo/request",
    'dojox/widget/Dialog',
    'dijit/form/Button',
    'dijit/form/Form',
    'dijit/form/ValidationTextBox',
    'dojox/layout/TableContainer',
    "dojo/text!./template/LoginDialogTemplate.html"
], function (declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, FormMgrMixin, FormMgrNodeMixin, FormMgrFormMixin, 
		lang, registry, domForm, request, Dialog, Button, Form, ValidationTextBox, TableContainer, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, FormMgrMixin, FormMgrNodeMixin, FormMgrFormMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		onCancel: function() {
			this.hide();
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			var widget = this;
            this.validate();
            if (this.isValid()) {
            	// Post the data to the server
    			var promise = request.post(this.url, {
    				data: domForm.toObject(this.loginForm.id),
    				timeout: 2000,
    				handleAs: "json"
    			});
    			
    			promise.response.then(
    				function(response) {
    					var data = response.data;
    					var d = new Date();
					    d.setTime(d.getTime() + (24*60*60*1000));
					    var expires = "expires="+d.toUTCString();
					    document.cookie = "salescrm" + "=" + "{\"id\":\"" + data.id + 
					    	"\", \"admin\":" + data.admin + ", \"name\":\"" + 
					    	data.userName + "\"}; " + expires;
					    widget.hide();
					    location.reload();
    				},
    				function(error) {
    					widget.messageNode.innerHTML = error.response.data.message;
    				}
    			);
            } else {
            	widget.messageNode.innerHTML = "Invalid data.";
            }
		}
    });
});