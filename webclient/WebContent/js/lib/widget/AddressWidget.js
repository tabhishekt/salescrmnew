define([
    'dojo/_base/declare',
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dijit/form/ValidationTextBox', 
    'dijit/form/FilteringSelect', 
    'dijit/form/Button',
    "dojo/store/JsonRest",  
    "dojo/store/Memory",
    "dojo/text!./template/AddressTemplate.html"
], function (declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
        ValidationTextBox, FilteringSelect, Button, JsonRest, Memory, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
    	templateString: template,
    	
    	constructor:function (options) {
    		declare.safeMixin(this, options);
            this.inherited(arguments)
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
        
        postCreate:function () {
            this.inherited(arguments);
            this.setDataToDropdown('../rest/json/data/codetable/city/get/all', this.city);
        	this.setDataToDropdown('../rest/json/data/codetable/state/get/all', this.state);
        }
    });
});
