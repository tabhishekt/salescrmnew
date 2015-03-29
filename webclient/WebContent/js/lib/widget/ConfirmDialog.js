define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    'dijit/form/Button',
    "dojo/text!./template/ConfirmDialogTemplate.html"
], function (declare, lang, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, Button, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		show: function () {
			this.inherited(arguments);
			this.confirmTextNode.innerHTML = this.confirmText;
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
			this.def.resolve();
		},
		
		setDeferred: function (def) {
			this.def = def;
		},
		
		setConfirmText: function (confirmText) {
			this.confirmText = confirmText;
		}
    });
});