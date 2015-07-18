define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/request",
    "dojo/dom-form",
    "dojox/layout/TableContainer",
    "lib/widget/AddEditDialog",
    "dojo/text!./template/LoginDialogTemplate.html"
], function (declare, array, request, domForm, TableContainer, AddEditDialog, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			this.messageNode.innerHTML = "";
		},
		
		hide : function () {
			this.inherited(arguments);
			this.reset();
		},
		
		onSubmit: function(){
			var widget = this;
            if (this.validate()) {
    			var promise = request.post(this.url, {
    				data: domForm.toObject(this.codetableForm.id),
    				timeout: 5000,
    				handleAs: "json"
    			});
    			
    			promise.response.then(
    				function(response) {
    					widget.hide();
    				    location.reload();
    				},
    				function(error) {
    					widget.messageNode.innerHTML = error.response.data.message;
    				}
    			);
            } else {
            	widget.messageNode.innerHTML = "Data entered is invalid.";
            }
		}
    });
});