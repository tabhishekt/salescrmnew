define(
    [
        'dojo/_base/declare',
        "dojo/_base/lang",
        "dojo/currency",
        "dojo/date/locale",
        "dojo/store/JsonRest",
        "dojo/store/Memory",
        'lib/widget/AddEditCodetableDialog',
		'lib/widget/AddEditOrganizationDialog',
		'lib/widget/AddEditProjectDialog',
		'lib/widget/AddEditProjectPhaseDialog',
		'lib/widget/AddEditProjectBuildingDialog',
		'lib/widget/AddEditUnitDialog',
		'lib/widget/AddEditUnitPricePolicyDialog',
		'lib/widget/AddEditUnitPaymentScheduleDialog',
		'lib/widget/AddEditPaymentDialog',
		'lib/widget/AddEditCustomerDialog',
		'lib/widget/AddEditUserDialog',
		'lib/widget/AddEditRoleDialog',
		'lib/widget/AddEditEnquiryDialog',
    ],
    function (
        declare, lang, currency, locale, JsonRest, Memory, AddEditCodetableDialog, AddEditOrganizationDialog, 
		AddEditProjectDialog, AddEditProjectPhaseDialog, AddEditProjectBuildingDialog, AddEditUnitDialog, AddEditUnitPricePolicyDialog, 
		AddEditUnitPaymentScheduleDialog, AddEditPaymentDialog, AddEditCustomerDialog, AddEditUserDialog, AddEditRoleDialog,
		AddEditEnquiryDialog
    ) {
        return declare([], {
            
            constructor:function (options) {
            	lang.mixin(this, options);
                this.inherited(arguments)
                this.initParameters();
            },
            
            sortByUnitnumber : function (v1, v2) {
            	return parseInt(v1) - parseInt(v2);
            },
            
            sortByDate : function (v1, v2) {
            	v1 = new Date(v1);
                v2 = new Date(v2);
                return v1 - v2;
            },
            
            formatCurrency : function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
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
	        
            getPageParams : function () { 
            	return this.pageParams;
            },
            
            getToolbarStyle : function () {
            	if (this.page == "enquiry") {
            		if (this.userData) {
            			return {style : "width:50%"};
            		} else {
            			return {style : "width:20%"};
            		}
            	} else if (this.page == "projectbuilding") {
            		if (this.mode && this.mode == "createbookings") {
            			return {style : "width:30%"};
            		} else if (this.mode && this.mode == "showbookings") {
            			return {style : "width:55%"};
            		} else if (this.mode && this.mode == "updateprice") {
            			return {style : "width:55%"};
            		} else if (this.mode && this.mode == "availability") {
            			return {style : "width:30%"};
            		}
            		return {style : "width:65%"};
            	} else if (this.page == "customer") {
            		if (this.mode && this.mode == "select") {
	            		if (this.userData) {
	            			return {style : "width:40%"};
	            		} else {
	            			return {style : "width:20%"};
	            		}
            		}
            	} else if (this.page == "unitbooking") {
            		if (this.userData) {
    	   	   			return {style : "width:83%"}
    	   	   		} else {
    	   	   			return {style : "width:20%"};
    	   	   		}
            	} else if (this.page == "cancelledbooking") {
            		return {style : "width:23%"};
            	} else if (this.page == "unit") {
            		return {style : "width:75%"};
            	}
            	
            	return {style : "width:32%"};
            },
            
            getMainGridButtonDefinition : function () {
            	if (this.page == "projectbuilding") {
            		if (this.mode && this.mode == "createbookings") {
            			return [
  	  				          ["refresh", "Refresh", "NewPage"],
  	  				          ["createbooking", "Create Bookings", "NewPage"]
  	  				       ];
            		} else if (this.mode && this.mode == "showbookings") {
            			return [
  	  				          ["refresh", "Refresh", "NewPage"],
  	  				          ["unitbooking", "Manage Active Bookings", "Copy"],
  	  				          ["cancelledbooking", "Manage Cancelled Bookings", "Copy"],
  	  				       ];
            		} else if (this.mode && this.mode == "updateprice") {
            			return [
    	  				          ["refresh", "Refresh", "NewPage"],
    	  				          ["updateunitpricepolicy", "Update Unit Price Policy", "Copy"],
    	  				          ["updateunitfloorrise", "Update Unit Floor Rise", "Copy"],
    	  				       ];
              		} else if (this.mode && this.mode == "availability") {
            			return [
  	  				          ["refresh", "Refresh", "NewPage"],
  	  				          ["showunitavailability", "Show Unit Availability", "NewPage"],
  	  				       ];
            		}
            		return [
  				          ["new", "New", "NewPage"],
  				          ["edit", "Edit", "Copy"],
  				          ["delete", "Delete", "Delete"],
  				          ["refresh", "Refresh", "NewPage"],
  	       				  ["unit", "Manage Units", "NewPage"],
  	       				  ["definepaymentschedule", "Define Payment Schedule", "NewPage"]
  				       ];
            	} else if (this.page == "enquiry") {
            		if (this.userData) {
    	   	   			return [
								["new", "New", "NewPage"],
    							["edit", "Edit", "Copy"],
    	   	   			        ["delete", "Delete", "Delete"],
    	   	   			        ["showcomments", "Show Comments", "NewPage"],
    	   	   			        ["refresh", "Refresh", "NewPage"]
    	   	   			       ];
    	   	   		} else {
    	   	   			return [
    						   	["showcomments", "Show Comments", "NewPage"],
    	   			            ["refresh", "Refresh", "NewPage"]
    	   			           ];
    	   	   		}
              	} else if (this.page == "customer") {
              		if (this.mode && this.mode == "select") {
	            		if (this.userData) {
		            		return [
		  				          ["new", "New", "NewPage"],
		  				          ["refresh", "Refresh", "NewPage"],
		  				          ["enquiry", "Manage Enquiry", "NewPage"],
		  				       ];
	            		} else {
	            			return [
	  	  				          ["refresh", "Refresh", "NewPage"]
	  	  				       ];
	            		}
              		}
            	} else if (this.page == "unitbooking") {
            		if (this.userData) {
            			return [
 	   	   			           ["delete", "Delete", "Delete"],
 	   	   			           ["payment", "Manage Payment", "NewPage"],
 	   	   			           ["cancelbooking", "Cancel Booking", "NewPage"],
 	   	   			           ["printbookingform", "Print Booking Form", "NewPage"],
 	   	   			           ["printdemandletter", "Print Demand Letter", "NewPage"],
 	   	   			           ["refresh", "Refresh", "NewPage"]
 	   	   			       ];
    	   	   		} else {
    	   	   			return [
    	   			               ["refresh", "Refresh", "NewPage"]
    	   			           ];
    	   	   		}
            	} else if (this.page == "cancelledbooking") {
    	   	   			return [
    	   	   			           ["showcancelbookingdetails", "Show Details", "NewPage"],
    	   			               ["refresh", "Refresh", "NewPage"]
    	   			           ];
            	} else if (this.page == "unit") {
            		return [
  				          ["new", "New", "NewPage"],
  				          ["edit", "Edit", "Copy"],
  				          ["delete", "Delete", "Delete"],
  				          ["refresh", "Refresh", "NewPage"],
  				          ["showunitpricedetails", "Show Price Details", "NewPage"],
  				          ["showunitpaymentschedule", "Show Payment Schedule", "NewPage"]
  				       ];
            	} else if (this.page == "unitpaymentschedule") {
            		return [
    				          ["edit", "Edit", "Copy"],
    				          ["delete", "Delete", "Delete"],
    				          ["refresh", "Refresh", "NewPage"],
    				       ];
              	}
            	
            	return [
				          ["new", "New", "NewPage"],
				          ["edit", "Edit", "Copy"],
				          ["delete", "Delete", "Delete"],
				          ["refresh", "Refresh", "NewPage"]
				       ];
            },
            
            getPageLegendTitle : function () {
            	var pageLegendTitle = (this.mode && this.mode == "select") ? "Select " : "Manage ";
            	pageLegendTitle += (this.pageParams.dialogTtileString) ? this.pageParams.dialogTtileString : this.page;
            	if (this.unitInfo != "undefined") {
            		pageLegendTitle += " for unit \"" + unitInfo + "\"";
            	} else if (this.customerName != "undefined") {
            		pageLegendTitle += " for customer \"" + customerName + "\"";
            	} else if (this.buildingName != "undefined") {
            		pageLegendTitle += " for building \"" + buildingName + "\"";
            	}
            	
            	if (this.mode && this.mode == "select") {
            		pageLegendTitle += " to continue"
            	}
            	return pageLegendTitle;
            },
            
            initParameters : function () {
            	this.pageParams = {
            		serviceName: "",
            		gridLayout : [{}],
                	dialogDimension: {width:0, height: 0},
                	GridDialog: null 
            	}
            	
            	switch (this.page)
            	{
            	  case "organization": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.55;
            		  this.pageParams.dialogDimension.height = window.screen.height;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "name",name: "Name",width: "auto"},
                    	   							{field: "displayAddress",name: "Address",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.GridDialog = AddEditOrganizationDialog;
            		  break;
            	  case "project": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.60;
            		  this.pageParams.dialogDimension.height = window.screen.height;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "name",name: "Name",width: "auto"},
                    	   							{field: "orgName",name: "Organization",width: "auto"},
                    	   							{field: "displayAddress",name: "Address",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.GridDialog = AddEditProjectDialog;
            		  break;
            	  case "projectphase": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.35;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.30;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "name",name: "Name",width: "auto"},
                    	   							{field: "projectName",name: "Project",width: "auto"},
                    	   							{field: "description",name: "Description",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.dialogTtileString = "project phase";
            		  this.pageParams.GridDialog = AddEditProjectPhaseDialog;
            		  break;
            	  case "projectbuilding": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.35;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.40;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "name",name: "Name",width: "auto"},
                    	   							{field: "projectPhaseName",name: "Project Phase",width: "auto"},
                    	   							{field: "wingName",name: "Wing Name",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.dialogTtileString = "project building";
            		  this.pageParams.GridDialog = AddEditProjectBuildingDialog;
            		  break;
            	  case "unit": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.50;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.65;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "unitNumber",name: "Unit Number",width: "8%", sortFormatted: true, comparator: this.sortByUnitnumber},
                    	   							{field: "displayProjectInfo",name: "Building / Project",width: "37%"},
                    	   							{field: "carpetArea",name: "Carpet Area",width: "7%"},
                    	   							{field: "saleableArea",name: "Saleable Area",width: "8%"},
                    	   							{field: "agreementvalue",name: "Agreement Value",width: "20%", decorator: this.formatCurrency},
                    	   							{field: "totalCost",name: "Total Cost",width: "20%", decorator: this.formatCurrency}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "unitNumber";
            		  this.pageParams.GridDialog = AddEditUnitDialog;
            		  break;
            	  case "unitpricepolicy": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.45;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
													{field: "policyName",name: "Policy Name",width: "auto"},
                    	   							{field: "baserate",name: "Base Rate",width: "auto", decorator: this.formatCurrency},
													{field: "assignedToProjects",name: "Assigned Projects",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "policyName";
            		  this.pageParams.dialogTtileString = "unit price policy";
            		  this.pageParams.GridDialog = AddEditUnitPricePolicyDialog;
            		  break;
            	  case "unitpaymentschedule": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.35;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "type",name: "Type",width: "auto"},
                    	   							{field: "percentamount",name: "Percent Amount",width: "auto"},
                    	   							{field: "scheduledate",name: "Schedule Date",width: "auto", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "type";
            		  this.pageParams.dialogTtileString = "unit payment schedule";
            		  this.pageParams.GridDialog = AddEditUnitPaymentScheduleDialog;
            		  break;
            	  case "unitbooking": 
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
            										{field: "bookingFormNumber",name: "Booking Form Number",width: "5%"},
            										{field: "customerDisplayName",name: "Customer",width: "15%"},
            										{field: "unitDisplayName",name: "Unit",width: "20%"},
            										{field: "userDisplayName",name: "Booked By",width: "15%"},
            										{field: "bookingDate",name: "Booking Date",width: "10%", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate},
            										{field: "discount",name: "Discount",width: "5%", decorator: this.formatCurrency},
            										{field: "comment",name: "Comment",width: "30%"}
            		   	   			  		   ];
            		  this.pageParams.dialogTtileString = "unit bookings";
            		  break;
            	  case "cancelledbooking": 
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
            										{field: "unitDisplayName",name: "Unit",width: "30%"},
            										{field: "userDisplayName",name: "Booked By",width: "20%"},
            										{field: "bookingDate",name: "Booking Date",width: "15%", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate},
            										{field: "cancelUserDisplayName",name: "Cancelled By",width: "20%"},
            										{field: "cancellationDate",name: "Cancellation Date",width: "15%", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate}
            		   	   			  		   ];
            		  this.pageParams.dialogTtileString = "unit bookings";
            		  break;
            	  case "payment": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.75;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
                    	   							{field: "receiptNumber",name: "Receipt Number",width: "auto"},
                    	   							{field: "altReceiptNumber",name: "Alt Receipt Number",width: "auto"},
                    	   							{field: "receiptAmount",name: "Receipt Amount",width: "auto", decorator: this.formatCurrency},
                    	   							{field: "paymentTypeName",name: "Payment Type",width: "auto"},
                    	   							{field: "userDisplayName",name: "Received By",width: "auto"},
                    	   							{field: "receiptDate",name: "Received Date",width: "auto", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate},
                    	   							{field: "displayPaymentStatus",name: "Payment Status",width: "auto"},
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "receiptNumber";
            		  this.pageParams.dialogTtileString = "payment";
            		  this.pageParams.GridDialog = AddEditPaymentDialog;
            		  break;
            	  case "customer": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.55;
            		  this.pageParams.dialogDimension.height = window.screen.height;
            		  this.pageParams.serviceName = "person";
            		  this.pageParams.gridLayout = [
														{field: "displayName",name: "Name",width: "auto"},
														{field: "displayAddress",name: "Address",width: "auto"},
														{field: "phoneNumber",name: "Phone",width: "auto"},
														{field: "mobileNumber",name: "Mobile",width: "auto"},
														{field: "emailID",name: "Email Id",width: "auto"},
														{field: "userDisplayName",name: "Created By",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "displayName";
            		  this.pageParams.GridDialog = AddEditCustomerDialog;
            		  break;
            	  case "user": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.55;
            		  this.pageParams.dialogDimension.height = window.screen.height;
            		  this.pageParams.serviceName = "person";
            		  this.pageParams.gridLayout = [
														{field: "displayName",name: "Name",width: "auto"},
														{field: "loginname",name: "Login Name",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "displayName";
            		  this.pageParams.GridDialog = AddEditUserDialog;
            		  break;
            	  case "role": 
            		  this.pageParams.dialogDimension.width = window.screen.width*0.35;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.30;
            		  this.pageParams.serviceName = "userrole";
            		  this.pageParams.gridLayout = [
														{field: "name",name: "Name",width: "auto"},
														{field: "description",name: "Description",width: "auto"},
														{field: "admin",name: "Administrator",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.GridDialog = AddEditRoleDialog;
            		  break;
            	  case "city":            	
            	  case "state":
            	  case "unittype":
            	  case "amenity":
            	  case "source":
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.16;
            		  this.pageParams.serviceName = "codetable";
            		  this.pageParams.gridLayout = [
                    	   								{field: "code",name: "Code",width: "auto"},
                      	   								{field: "name",name: "Name",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.GridDialog = AddEditCodetableDialog;
            		  break;
            	  case "paymenttype":
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.16;
            		  this.pageParams.serviceName = "codetable";
            		  this.pageParams.gridLayout = [
                    	   								{field: "code",name: "Code",width: "auto"},
                      	   								{field: "name",name: "Name",width: "auto"}
                    	   	   			  		   ];
            		  this.pageParams.dialogTtileField = "name";
            		  this.pageParams.dialogTtileString = "payment type";
            		  this.pageParams.GridDialog = AddEditCodetableDialog;
            		  break;
            	  case "enquiry":
            		  this.pageParams.dialogDimension.width = window.screen.width*0.40;
            		  this.pageParams.dialogDimension.height = window.screen.height*0.50;
            		  this.pageParams.serviceName = "inventory";
            		  this.pageParams.gridLayout = [
            										{field: "customerDisplayName",name: "Customer",width: "auto"},
            										{field: "userDisplayName",name: "Created By",width: "auto"},
            										{field: "originalenquirydate",name: "Original Enquiry Date",width: "auto", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate},
            										{field: "latestComment",name: "Latest Comment",width: "30%"},
            										{field: "followupDate",name: "Followup Date",width: "auto", decorator: this.formatDate, sortFormatted: true, comparator: this.sortByDate}
            		   	   			  		   ];
            		  this.pageParams.dialogTtileField = "customerDisplayName";
            		  this.pageParams.dialogTtileString = "enquiry";
            		  this.pageParams.GridDialog = AddEditEnquiryDialog;
            		  break;
            	}
            }
        });
    }
);