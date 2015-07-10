define([
    'dojo/_base/declare',
    "dojo/_base/lang",
    "dojo/date/locale",
    'dijit/_WidgetBase',
    "dijit/_TemplatedMixin",
    'dijit/_WidgetsInTemplateMixin',
    'dojox/widget/Dialog',
    "lib/GridHandler",
    'dijit/form/Button',
    "dojo/text!./template/ShowEnquiryCommentTemplate.html"
], function (declare, lang, locale, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, Dialog, GridHandler, Button, template) {
    return declare([Dialog, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
		templateString: template,
		
		constructor: function(options){
			lang.mixin(this, options);
		},
		
		show: function() {
			
			if(this.gridHandler)
			{
				while (dataGridEnquiryComment.hasChildNodes()) {
					dataGridEnquiryComment.removeChild(dataGridEnquiryComment.lastChild);
				}
				this.startup();
				
			}
			this.inherited(arguments);
			this.gridHandler.resizeGrid(this.dimensions[0]*0.95, this.dimensions[1]*0.5);
			this.gridHandler.refreshGrid();
			
		},
		
		formatDate : function (value) {
			try {
			    if (value) {
			      // NotesDateTime comes out in this format: 2014-10-21T14:47:38Z
			      var parsedDate = value.substring(0, 10);
			       
			      // Create and format the date 
			      var dt = new Date(parsedDate);
			      var dateString = locale.format(dt,
			        {
			          selector: "date",
			          datePattern: "dd-MMM-yyyy"
			        }); 
			       
			      return dateString;
			    }
			  } catch (e) {
			    console.error('error decorating date: ' + e.toString());
			  }
		},
		
		removeGrid: function(){
			while (dataGridEnquiryComment.hasChildNodes()) {
				dataGridEnquiryComment.removeChild(node.lastChild);
			}
		},
		
		startup: function() {
			this.inherited(arguments);
			var gridLayout = [
 							{field: "commentText",name: "Comment",width: "30%"},
 							{field: "commentDate",name: "Date Entered",width: "auto", decorator: this.formatDate},
 							{field: "followupDate",name: "Followup Date",width: "auto", decorator: this.formatDate},
 							{field: "userDisplayName",name: "Comment made by",width: "auto"}
 	   			  		   ];
			this.gridHandler = new GridHandler({page: "enquirycomment", serviceName: "inventory"});
			if (this.enquiryId) {
				this.gridHandler.setEnquiryId(this.enquiryId);
        	}
			this.gridHandler.createDataGrid(gridLayout, "dataGridEnquiryComment");
		},
		
		onSubmit: function() {
			this.inherited(arguments);
			this.hide();
		}
    });
});