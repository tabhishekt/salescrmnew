define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/InformationDialogTemplate.html"
], function (declare, lang, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		show: function () {
			this.inherited(arguments);
			this.informationTextNode.innerHTML = this.informationText;
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		},
		
		setInformationText: function (informationText) {
			this.informationText = informationText;
		}
    });
});