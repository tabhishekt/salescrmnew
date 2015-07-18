define([
    'dojo/_base/declare',
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/form/manager/_Mixin', 
    'dojox/form/manager/_NodeMixin', 
    'dojox/form/manager/_FormMixin', 
    "dojo/_base/lang",
    "dojo/store/JsonRest",
    "dojo/store/Memory",
    "dijit/registry",
    "dojo/dom-form",
    "dojo/request",
    "dojo/_base/array",
    'dojox/widget/Dialog',
    'dijit/form/Button',
    'dijit/form/Form',
    'dijit/form/ValidationTextBox',
    'dojox/validate/web'
], function (declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, FormMgrMixin, FormMgrNodeMixin, FormMgrFormMixin, 
		lang, JsonRest, Memory, registry, domForm, request, array, Dialog, Button, Form, TextBox, validate) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, FormMgrMixin, FormMgrNodeMixin, FormMgrFormMixin], {
		message: null,
		status: null,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		getStatus: function () {
			return this.status;
		},
		
		getMessage: function () {
			return this.message;
		},
		
		setMessage: function (message) {
			this.message = message;
		},
		
		setStatus: function (status) {
			this.status = status;
		},
		
		updatePage: function () {
			this.hide();
        	this.gridHandler.refreshGrid();
        	this.gridHandler.updateMessage(this.getMessage(), this.getStatus());
		},

		show: function () {
			this.inherited(arguments);
			this.messageNode.innerHTML = "";
			if (this.data) {
				this.setData(this.data);
			} else {
				this.reset();
			}
		},
		
		setDataToDropdown: function (url, node) {
        	var dialog = this;
        	var store = new JsonRest ({
         		target: url,
         		idProperty:'id'
     		});
   		
        	store.query("", {
				handleAs : "json"
			}).then(function(data) {
				datastore = new Memory({
					data : data
				});
				node.set("store", datastore);
			});
        },
        
        setDataToMultiselect: function (url, node) {
        	var dialog = this;
        	var store = new JsonRest ({
         		target: url,
         		idProperty:'id'
     		});
   		
        	store.query("", {
				handleAs : "json"
			}).then(function(data) {
				dojo.empty(node.id);
				array.forEach(data, function (item)
				{
					var opData = document.createElement('option');
				    opData.innerHTML = item.name;
				    opData.value = item.id;
				    node.appendChild(opData);
				});
			});
        },
        
		onSubmit: function(){
			 this.inherited(arguments);
			 var widget = this;
			 this.submitButton.setDisabled(true);
             if (this.validate()) {
            	widget.setStatus(null);
				widget.setMessage(null);
            	
     			var promise = request.post(this.url, {
     				data: domForm.toObject(this.codetableForm.id),
     				timeout: 5000,
     				handleAs: "json"
     			});
     			
     			promise.response.then(
     				function(response) {
     					var data = response.data;
     					widget.setStatus("success");
     					(widget.mode == "new") ? widget.setMessage("Record successfully added.") 
     							: widget.setMessage("Record successfully updated.");  
     					widget.updatePage();
     					widget.submitButton.setDisabled(false);
     				},
     				function(error) {
     					widget.setStatus("error");
     					(widget.mode == "new") ? widget.setMessage("Record addition error: " + error.response.data.message) 
     							: widget.setMessage("Record updation error: " + error.response.data.message);
     					
     					widget.updatePage();
     					widget.submitButton.setDisabled(false);
     				}
     			);
             } else {
            	this.status = "invaliddata";
            	if (widget.messageNode.innerHTML == "") {
            		widget.messageNode.innerHTML = "Data entered is invalid.";
            	}
             }
		}
    });
});