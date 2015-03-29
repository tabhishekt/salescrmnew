define([
    'dojo/_base/declare',
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dijit/form/ValidationTextBox', 
    'dijit/form/Textarea',
    'dijit/form/Button',
    'dijit/form/DateTextBox',
    "dojo/text!./template/PersonTemplate.html"
], function (declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
        ValidationTextBox, Textarea, Button, DateTextBox, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
    	templateString: template,
    	
    	constructor:function (options) {
    		declare.safeMixin(this, options);
            this.inherited(arguments)
        },
        postCreate:function () {
            this.inherited(arguments);
        }
    });
});
