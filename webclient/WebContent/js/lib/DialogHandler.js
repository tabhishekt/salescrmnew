define(
    [
        'dojo/_base/declare',
        "dojo/_base/lang",
        'lib/widget/LoginDialog',
        'lib/widget/ConfirmDialog',
        'lib/widget/InformationDialog',
        'lib/widget/ShowEnquiryCommentDialog',
        'lib/widget/ShowUnitPriceDetailDialog',
        'lib/widget/AddEditUnitFloorRiseDialog',
        'lib/widget/AddEditUnitPriceDialog',
        'lib/widget/AddEditUnitChargesDialog',
        'lib/widget/ShowUnitPaymentScheduleDialog',
        'lib/widget/DefineBuildingPaymentScheduleDialog',
        'lib/widget/ShowUnitAvailabilityDialog',
        'lib/widget/CancelUnitBookingDialog',
        'lib/widget/ShowCancelledBookingDetailDialog',
        'lib/widget/DefineBuildingParkingDialog'
    ],
    function (
        declare, lang, LoginDialog, ConfirmDialog, InformationDialog, ShowEnquiryCommentDialog, 
        ShowUnitPriceDetailDialog, AddEditUnitFloorRiseDialog, AddEditUnitPriceDialog, AddEditUnitChargesDialog,
        ShowUnitPaymentScheduleDialog, DefineBuildingPaymentScheduleDialog, ShowUnitAvailabilityDialog,
        CancelUnitBookingDialog, ShowCancelledBookingDetailDialog, DefineBuildingParkingDialog
    ) {
        return declare([], {
            
            constructor:function (options) {
            	lang.mixin(this, options);
                this.inherited(arguments)
            },
            
            setDimension:function (dialogDimension) {
            	this.dialogDimension = dialogDimension;
            },
            
            setGridDialog:function (GridDialog) {
            	this.GridDialog = GridDialog;
            },
            
            openInformationDialog:function (options) {
            	if(!this.informationDialog) {
            		options.draggable = false;
                	options.showTitle = true;
            		this.informationDialog = new InformationDialog(options);	
            	}
            	
            	this.informationDialog.set("title", options.title);
            	this.informationDialog.setInformationText(options.informationText);
            	
    			this.informationDialog.set("dimensions", [this.dialogDimension.width, this.dialogDimension.height]);  
    			this.informationDialog.layout();
    			this.informationDialog.show();
            },
            
            openConfirmDialog:function (options) {
            	if(!this.confirmDialog) {
            		options.draggable = false;
                	options.showTitle = true;
            		this.confirmDialog = new ConfirmDialog(options);	
            	}
            	this.confirmDialog.setDeferred(options.def);
            	this.confirmDialog.set("title", options.title);
            	this.confirmDialog.setConfirmText(options.confirmText);
            	
    			this.confirmDialog.set("dimensions", [this.dialogDimension.width, this.dialogDimension.height]);
    			this.confirmDialog.layout();
    			this.confirmDialog.show();
            },
            
            openLoginDialog:function (options) {
            	if(!this.loginDialog) {
            		options.draggable = false;
                	options.showTitle = true;
            		this.loginDialog = new LoginDialog(options);	
            	}
            	
            	this.loginDialog.set("dimensions", [this.dialogDimension.width, this.dialogDimension.height]);
    			this.loginDialog.layout();
    			this.loginDialog.show();
            },
            
            openAddEditDialog:function (options) {
                if (!this.addEditDialog) {
                	options.draggable = false;
                	options.showTitle = true;
        			this.addEditDialog = new this.GridDialog(options);
    			}
                this.addEditDialog.mode = options.mode;
    			this.addEditDialog.set("dimensions", [this.dialogDimension.width, this.dialogDimension.height]); 
    			this.addEditDialog.set("title", options.title);
    			this.addEditDialog.data = options.data;
    			this.addEditDialog.projectId = options.projectId;
    			this.addEditDialog.unitId = options.unitId;
    			this.addEditDialog.buildingId = options.buildingId;
    			this.addEditDialog.unitbookingId = options.unitbookingId;
    			this.addEditDialog.userId = options.userId;
    			this.addEditDialog.customerId = options.customerId;
    			this.addEditDialog.receiptNumber = options.receiptNumber;
    			
    			this.addEditDialog.layout();
    			this.addEditDialog.show();
            },
            
            openOtherActionDialog:function (options, width, height) {
            	var Dialog;
            	if (options.action == "updateunitpricepolicy") {
            		if(!this.updatePricePolicyDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.updatePricePolicyDialog = new AddEditUnitPriceDialog(options);
                	}
            		this.updatePricePolicyDialog.buildingId = options.buildingId;
                	this.updatePricePolicyDialog.set("title", options.title);
        			this.updatePricePolicyDialog.set("dimensions", [width, height]);  
        			this.updatePricePolicyDialog.layout();
        			this.updatePricePolicyDialog.show();
            	} else if (options.action == "updateunitfloorrise") {
            		if(!this.updateUnitFloorRiseDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.updateUnitFloorRiseDialog = new AddEditUnitFloorRiseDialog(options);
                	}
            		this.updateUnitFloorRiseDialog.data = options.data;
            		this.updateUnitFloorRiseDialog.buildingId = options.buildingId;
            		this.updateUnitFloorRiseDialog.floorcount = options.floorcount;
                	this.updateUnitFloorRiseDialog.set("title", options.title);
        			this.updateUnitFloorRiseDialog.set("dimensions", [width, height]);  
        			this.updateUnitFloorRiseDialog.layout();
        			this.updateUnitFloorRiseDialog.show();
            	} else if (options.action == "updateunitcharges") {
            		if(!this.updateUnitChargesDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.updateUnitChargesDialog = new AddEditUnitChargesDialog(options);
                	}
            		this.updateUnitChargesDialog.data = options.data;
            		this.updateUnitChargesDialog.buildingId = options.buildingId;
                	this.updateUnitChargesDialog.set("title", options.title);
        			this.updateUnitChargesDialog.set("dimensions", [width, height]);  
        			this.updateUnitChargesDialog.layout();
        			this.updateUnitChargesDialog.show();
            	} else if (options.action == "cancelbooking") {
            		if(!this.cancelBookingDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.cancelBookingDialog = new CancelUnitBookingDialog(options);
                	}
            		this.cancelBookingDialog.data = options.data;
            		this.cancelBookingDialog.userId = options.userId;
                	this.cancelBookingDialog.set("title", options.title);
        			this.cancelBookingDialog.set("dimensions", [width, height]);  
        			this.cancelBookingDialog.layout();
        			this.cancelBookingDialog.show();
            	} else if (options.action == "showcomments") {
            		if(!this.showCommentsDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.showCommentsDialog = new ShowEnquiryCommentDialog(options);
                	}
            		this.showCommentsDialog.enquiryId = options.enquiryId;
                	this.showCommentsDialog.set("title", options.title);
        			this.showCommentsDialog.set("dimensions", [width, height]);  
        			this.showCommentsDialog.layout();
        			this.showCommentsDialog.show();
            	} else if (options.action == "showunitpricedetails") {
            		if(!this.showUnitPriceDetailsDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.showUnitPriceDetailsDialog = new ShowUnitPriceDetailDialog(options);
                	}
            		
            		this.showUnitPriceDetailsDialog.unitId = options.unitId;
            		this.showUnitPriceDetailsDialog.booingdiscount = options.booingdiscount;
            		this.showUnitPriceDetailsDialog.deductiononothercharges = options.deductiononothercharges;
                	this.showUnitPriceDetailsDialog.set("title", options.title);
        			this.showUnitPriceDetailsDialog.set("dimensions", [width, height]);  
        			this.showUnitPriceDetailsDialog.layout();
        			this.showUnitPriceDetailsDialog.show();
            	} else if (options.action == "showcancelbookingdetails") {
            		if(!this.showCancelBookingDetails) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.showCancelBookingDetails = new ShowCancelledBookingDetailDialog(options);
                	}
            		
            		this.showCancelBookingDetails.data = options.data;
                	this.showCancelBookingDetails.set("title", options.title);
        			this.showCancelBookingDetails.set("dimensions", [width, height]);  
        			this.showCancelBookingDetails.layout();
        			this.showCancelBookingDetails.show();
            	} else if (options.action == "showunitpaymentschedule") {
            		if(!this.showUnitPaymentScheduleDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.showUnitPaymentScheduleDialog = new ShowUnitPaymentScheduleDialog(options);
                	}
            		this.showUnitPaymentScheduleDialog.unitId = options.unitId;
                	this.showUnitPaymentScheduleDialog.set("title", options.title);
        			this.showUnitPaymentScheduleDialog.set("dimensions", [width, height]);  
        			this.showUnitPaymentScheduleDialog.layout();
        			this.showUnitPaymentScheduleDialog.show();
            	} else if (options.action == "definepaymentschedule") {
            		if(!this.definePaymentScheduleDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.definePaymentScheduleDialog = new DefineBuildingPaymentScheduleDialog(options);
                	}
            		this.definePaymentScheduleDialog.data = options.data;
            		this.definePaymentScheduleDialog.buildingId = options.buildingId;
            		this.definePaymentScheduleDialog.floorcount = options.floorcount;
            		this.definePaymentScheduleDialog.hasMultiplePaymentSchedules = options.hasMultiplePaymentSchedules;
                	this.definePaymentScheduleDialog.set("title", options.title);
        			this.definePaymentScheduleDialog.set("dimensions", [width, height]);  
        			this.definePaymentScheduleDialog.layout();
        			this.definePaymentScheduleDialog.show();
            	} else if (options.action == "defineparking") {
            		if(!this.defineParkingDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.defineParkingDialog = new DefineBuildingParkingDialog(options);
                	}
            		this.defineParkingDialog.data = options.data;
            		this.defineParkingDialog.buildingId = options.buildingId;
                	this.defineParkingDialog.set("title", options.title);
        			this.defineParkingDialog.set("dimensions", [width, height]);  
        			this.defineParkingDialog.layout();
        			this.defineParkingDialog.show();
            	} else if (options.action == "showunitavailability") {
            		if(!this.showUnitAvailabilityDialog) {
                		options.draggable = false;
                    	options.showTitle = true;	
                		this.showUnitAvailabilityDialog = new ShowUnitAvailabilityDialog(options);
                	}
            		this.showUnitAvailabilityDialog.buildingId = options.buildingId;
            		this.showUnitAvailabilityDialog.floorcount = options.floorcount;
            		this.showUnitAvailabilityDialog.availability = options.availability;
                	this.showUnitAvailabilityDialog.set("title", options.title);
        			this.showUnitAvailabilityDialog.set("dimensions", [width, height]);  
        			this.showUnitAvailabilityDialog.layout();
        			this.showUnitAvailabilityDialog.show();
            	}
            }
        });
    }
);
