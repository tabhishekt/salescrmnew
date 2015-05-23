define(
    [
        'dojo/_base/declare',
        "dojo/_base/lang",
        "dijit/registry",
        "dojo/_base/array",
        "dojo/Deferred",
        "dojo/on",
        "dojo/currency",
        "dojo/request",
        "dojo/dom-class",
        "dojo/dom-style",
        "dojo/store/JsonRest", 
        "dojo/store/Memory",
        "dijit/Toolbar", 
        "dijit/form/Button",
        "gridx/Grid",
		"gridx/core/model/cache/Async",
		"gridx/modules/VirtualVScroller",
		"gridx/modules/ColumnResizer",
		"gridx/modules/SingleSort",
		"gridx/modules/select/Row",
		"gridx/modules/Pagination",
		"gridx/modules/pagination/PaginationBar",
		"gridx/modules/Filter",
		"gridx/modules/filter/FilterBar",
		"gridx/core/model/extensions/FormatSort"
    ],
    function (
        declare, lang, registry, array, Deferred, on, currency, request, domClass, domStyle, JsonRest, Memory, Toolbar, Button, 
        Grid, Async, VirtualVScroller, ColumnResizer, SingleSort, SelectRow, Pagination, PaginationBar, 
        Filter, FilterBar, FormatSort
    ) {
        return declare([], {
        	
            constructor:function (options) {
            	lang.mixin(this, options);
                this.inherited(arguments)
            },
            
            setProjectId : function (projectId) {
            	this.projectId = projectId;
            },
            
            setUnitId : function (unitId) {
            	this.unitId = unitId;
            },
            
            setBuildingId : function (buildingId) {
            	this.buildingId = buildingId;
            },
            
            setUnitbookingId : function (unitbookingId) {
            	this.unitbookingId = unitbookingId;
            },
            
            setCustomerId : function (customerId) {
            	this.customerId = customerId;
            },
            
            setUserId : function (userId) {
            	this.userId = userId;
            },
            
            setEnquiryId : function (enquiryId) {
            	this.enquiryId = enquiryId;
            },
            
            formatCurrency : function (value) {
				  if (value) { 
				    var formattedCurrency = currency.format(value, {currency: "Rs "});
				    return formattedCurrency;   
				  } 
				  
				  return "Rs " + 0;
			},
			
            createDataGrid : function (layoutData, node) {
            	var gridHandler = this;
            	var url = this.getURL("get") + "/all";
            	if (this.page == "payment") {
            		url += "?unitbookingId=" + this.unitbookingId;
            	} else {
            		if (this.projectId) {
                		url += "?projectId=" + this.projectId;
                	} else if (this.unitId) {
                		url += "?unitId=" + this.unitId;
                	} else if (this.buildingId) {
                		url += "?buildingId=" + this.buildingId;
                	} else if (this.unitbookingId) {
                		url += "?unitbookingId=" + this.unitbookingId;
                	} else if (this.enquiryId) {
                		url += "?enquiryId=" + this.enquiryId;
                	} else if (this.customerId) {
                		url += "?customerId=" + this.customerId;
                	}
            	}
            	this.restStore = new JsonRest ({
   	         		target: url,
   	         		idProperty:'field'
   	     		});
   	   			this.restStore.query("", {
	   				handleAs : "json"
	   			}).then(function(data) {
	   				dataStore = new Memory({
	   						data : data
	   				});
	            	var grid = new Grid({
	            		store : dataStore,
	            		cacheClass: Async,
		   				structure : layoutData,
	            		selectRowTriggerOnCell: true,
	            		autoHeight: true,
	            		modules: [
	            		    VirtualVScroller,
	            			ColumnResizer,
	            			SingleSort,
	            			SelectRow,
	            			Pagination,
	            			PaginationBar,
	            			Filter,
	            			FilterBar,
	            		],
	            		modelExtensions: [
	            		    FormatSort
	            		]
	            	});
	
	            	grid.placeAt(node);
	            	grid.startup();
	            	
	            	gridHandler.gridx = grid; 
	            	gridHandler.showHideToolBarButtons();
	            	gridHandler.updateOverallPaymentDetailsDiv();
	            	if (this.page == "enquiry") {
	            		gridHandler.sortGridColumn(4);
	            	}
	   			});
            },
        
	        createDataGridToolBar : function(node, buttonDefinition, style) {
	        	var gridHandler = this;
				toolbar = new Toolbar(style, node);
	
				array.forEach(buttonDefinition, function (but)
				{
				    var button = new Button(
				    {
				        label: but[1],
				        showLabel: true,
				        iconClass: "dijitEditorIcon dijitEditorIcon" + but[2],
				        id: but[0]
				    });
				    on(button, "click",  lang.hitch(gridHandler,"toolbarButtonClicked",but));   
				    toolbar.addChild(button);
				});
				
				toolbar.startup();
			},
			
			showHideToolBarButtons : function() {
				var rowCount = this.gridx.store.data.length;
				var newButton = registry.byId("new");
				var editButton = registry.byId("edit");
				var deleteButton = registry.byId("delete")
				
				if (rowCount == 0) {
					if (this.page == "enquiry") {
						domStyle.set(newButton.domNode, "display", "inline");
					}
					if (editButton) {
						domStyle.set(editButton.domNode, "display", "none");
					}
					if (deleteButton) {
						domStyle.set(registry.byId("delete").domNode, "display", "none");
					}
				} else {
					if (this.page == "enquiry") {
						domStyle.set(newButton.domNode, "display", "none");
					}
					if (editButton) {
						domStyle.set(editButton.domNode, "display", "inline");
					}
					if (deleteButton) {
						domStyle.set(registry.byId("delete").domNode, "display", "inline");
					}
				}
			},
			
			sortGridColumn : function (index) {
				this.gridx.column(index).sort(true);
			},
			
			resizeGrid : function (width, height) {
				var gridHandler = this;
    			this.restStore.query("", {
	   				handleAs : "json"
	   			}).then(function(data) {
	   				gridHandler.gridx.resize({w: width, h: height});
	   			});
    		},
    		
			refreshGrid : function () {
				var gridHandler = this;
    			this.restStore.query("", {
	   				handleAs : "json"
	   			}).then(function(data) {
	   				gridHandler.gridx.model.clearCache();
	   				gridHandler.gridx.model.store.setData(data);
	   				gridHandler.gridx.body.refresh();
	   	            gridHandler.showHideToolBarButtons();
	   	            gridHandler.updateOverallPaymentDetailsDiv();
	   			});
    		},
    		
    		getSelectedRowData : function () {
    			var selectedRow = this.gridx.select.row.getSelected();
    			if (selectedRow.length == 0) {
    				return null;
    			}
    			var index = this.gridx.store.index[selectedRow];
    			return this.gridx.store.data[index];
    		},
    		
    		getURL : function(method) {
    			return "../rest/json/data/" + this.serviceName + "/" + this.page + "/" + method;
    		},
    		
    		toolbarButtonClicked : function(action) {
    			this.clearMessage();	
    			if (action[0] == "new") {
    				this.openAddDialog();
    			} else if (new RegExp("^refresh").test(action[0])) {
    				this.refreshGrid();
    				this.updateMessage("Grid refresh done.", "success");	
    			} else {
    				var rowData = this.getSelectedRowData();
    				if (rowData == null) {
    					this.updateMessage("Please select a record to " + action[1].toLowerCase() + ".", "error");	
    				} else {
    					if (action[0] == "edit") {
    						if (this.page == "payment" && !rowData.paymentStatus.allowStateChange) {
    							this.updateMessage("Payment in state \"" + rowData.paymentStatus.name + "\" cannot be edited. Please add a new payment record.", "error");
    							return;
    						}
    						this.editRecord(this.getURL("get") + "?rowId=" + rowData.id, rowData);
    					} else if (action[0] == "delete") {
    						var gridHandler = this;
    						var deferred = new Deferred();
	        	        	var options = {
		    	        		title:"Confirm record delete", 
		    	        	    confirmText : "Do you want to delete this record?", 
		    	        	    def: deferred
		    	        	}
	        	        	var dim = dialogHandler.dialogDimension; 
	        	        	dialogHandler.setDimension({width:window.screen.width*0.30, height: window.screen.height*0.15});
		    	        	dialogHandler.openConfirmDialog(options);
		    	        	dialogHandler.setDimension(dim);
		    	        	
	        	        	deferred.then(function(res){
	        	        		gridHandler.deleteRecord(gridHandler.getURL(action[0]) + "?rowId=" + rowData.id);
	        	        	});	
    					} else if (action[0] == "projectbankaccount") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&projectId=" + rowData.id + "&projectName=" + rowData.name;
    					} else if (action[0] == "unit") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "unitpaymentschedule") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "payment") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&unitbookingId=" + rowData.id + "&buildingId=" + rowData.buildingId + "&unitInfo=" + rowData.unitDisplayName;
    					} else if (action[0] == "enquiry") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&customerId=" + rowData.id + "&customerName=" + rowData.displayName;
    					} else if (action[0] == "createbooking") {
    						window.location = "createbooking.jsp?buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "unitregistration") {
    						window.location = "updateunitregistration.jsp?buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					}else if (action[0] == "manageclassification") {
    						window.location = "updateunitclassification.jsp?buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "unitbooking") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "cancelledbooking") {
    						window.location = "contentTabPage.jsp?page=" + action[0] + "&buildingId=" + rowData.id + "&buildingName=" + rowData.name;
    					} else if (action[0] == "updateunitpricepolicy") {
    						var options = {
    								action: action[0],
    		                    	title: "Update unit price policy for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	url: "../rest/json/data/inventory/unit/post/unitpricepolicy",
    		                    	mode: "new",
    		                    	gridHandler: this
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.50);
    					} else if (action[0] == "definepaymentschedule") {
    						var options = {
    								action: action[0], 
    		                    	title: "Define payment schedule for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	data: rowData.paymentSchedule,
    		                    	floorcount: rowData.floorCount,
    		                    	hasMultiplePaymentSchedules: rowData.hasMultiplePaymentSchedules,
    		                    	url: "../rest/json/data/inventory/projectbuilding/post/paymentschedule",
    		                    	mode: "edit",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height);
    					} else if (action[0] == "defineparking") {
    						var options = {
    								action: action[0], 
    		                    	title: "Define parking for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	data: rowData.parking,
    		                    	url: "../rest/json/data/inventory/projectbuilding/post/parking",
    		                    	mode: "edit",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.50);
    					} else if (action[0] == "updateunitfloorrise") {
    						var options = {
    								action: action[0], 
    		                    	title: "Update floor rise for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	data: rowData.floorRise,
    		                    	floorcount: rowData.floorCount,
    		                    	url: "../rest/json/data/inventory/unit/post/floorrise",
    		                    	mode: "edit",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.50);
    					} else if (action[0] == "updateunitcharges") {
    						var options = {
    								action: action[0], 
    		                    	title: "Update unit charges for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	data: rowData.unitCharges,
    		                    	url: "../rest/json/data/inventory/unit/post/unitcharges",
    		                    	mode: "edit",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.50);
    					} else if (action[0] == "cancelbooking") {
    						var options = {
    								action: action[0], 
    		                    	title: "Cancel booking " + rowData.id,
    		                    	data: rowData,
    		                    	userId: this.userId,
    		                    	url: "../rest/json/data/inventory/unitbooking/post/cancel",
    		                    	mode: "new",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.50);
    					} else if (action[0] == "unitmodificationrequest") {
    						var options = {
    								action: action[0], 
    		                    	title: "Unit modification request for booking " + rowData.id,
    		                    	data: rowData,
    		                    	userId: this.userId,
    		                    	url: "../rest/json/data/inventory/unitbooking/post/unitmodification",
    		                    	mode: "edit",
    		                    	gridHandler: this,
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.75);
    					} else if (action[0] == "showcomments") {
    						var options = {
    								action: action[0],
    		                    	title: "Comments for enquiry with id " + rowData.id,
    		                    	enquiryId: rowData.id
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height*0.35);
    					} else if (action[0] == "showunitpricedetails") {
    						if (rowData.unitPricePolicy && rowData.unitPricePolicy != null) {
    							var options = {
    									action: action[0],
    			                    	title: "Price details for unit " + rowData.unitNumber + ", " + rowData.displayProjectInfo,
    			                    	unitId: rowData.id,
    			                    	booingdiscount: 0,
    			                    	deductiononothercharges: 0
    			                }
    							this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height);
    						} else {
    							this.updateMessage("Price policy for unit " + rowData.unitNumber + ", " + 
    									rowData.displayProjectInfo + " is not defined.", "error");	
    						}
    						
    					} else if (action[0] == "showcancelbookingdetails") {
    						var options = {
									action: action[0],
			                    	title: "Details for cancelled booking with id " + rowData.id,
			                    	data: rowData,
			                }
							this.dialogHandler.openOtherActionDialog(options, window.screen.width*0.50, window.screen.height);
    						
    					} else if (action[0] == "showunitpaymentschedule") {
    						if (rowData.unitPricePolicy && rowData.unitPricePolicy != null) {
    							var options = {
    									action: action[0],
    			                    	title: "Payment schedule for unit " + rowData.unitNumber + ", " + rowData.displayProjectInfo,
    			                    	unitId: rowData.id
    			                }
    							this.dialogHandler.openOtherActionDialog(options, window.screen.width, window.screen.height);
    						} else {
    							this.updateMessage("Price policy for unit " + rowData.unitNumber + ", " + 
    									rowData.displayProjectInfo + " is not defined.", "error");	
    						}
    						
    					} else if (action[0] == "showunitavailability") {
    						var options = {
    								action: action[0], 
    		                    	title: "Show availability for building " + rowData.name,
    		                    	buildingId: rowData.id,
    		                    	floorcount: rowData.floorCount,
    		                    	availability: rowData.availability
    		                }
    						this.dialogHandler.openOtherActionDialog(options, window.screen.width, window.screen.height);
    					} else if (action[0] == "printbookingform") {
    						window.open("printbookingform.jsp?unitbookingId=" + rowData.id);
    					} else if (action[0] == "printdemandletter") {
    						window.open("printdemandletter.jsp?unitbookingId=" + rowData.id);
    					} else if (action[0] == "printreceipt") {
    						window.open("printreceipt.jsp?paymentId=" + rowData.id);
    					}
    				}
    			}
    		},
    		
    		openAddDialog : function() {
    			var title = (this.dialogTtileString) ? "Add new " + this.dialogTtileString : "Add new " + this.page;
    			var options = {
    					mode: "new",
    					projectId: this.projectId,
    					unitId: this.unitId,
    					buildingId: this.buildingId,
    					unitbookingId: this.unitbookingId,
    					userId: this.userId,
    					customerId: this.customerId,
                    	title: title,
                    	url: this.getURL("post"),
                    	gridHandler: this
                }
				if (this.page == "payment") {
					var gridHandler = this;
	    			var promise = request.get(this.getURL("get") + "/receiptnumber", {
	       				timeout: 2000,
	       				handleAs: "json"
	       			});
	       			
	       			promise.response.then(
	       				function(response) {
	       					options.receiptNumber = response.data;
	       					gridHandler.dialogHandler.openAddEditDialog(options);
	       				},
	       				function(error) {
	       					gridHandler.updateMessage("Problem occured fetching receipt number: " + error.response.data.message, "error");
	       				}
	       			);
				} else {
					this.dialogHandler.openAddEditDialog(options);
				}
    		},
    		
    		editRecord : function(url, rowData) {
    			var gridHandler = this;
    			var promise = request.get(url, {
       				timeout: 2000,
       				handleAs: "json"
       			});
       			
       			promise.response.then(
       				function(response) {
       					var title = (gridHandler.dialogTtileString) ? "Update " + gridHandler.dialogTtileString : "Update " + gridHandler.page;
       					title += " \"" + rowData[gridHandler.dialogTtileField] + "\"";
       					var options = {
       							projectId: gridHandler.projectId,
       	    					unitId: gridHandler.unitId,
       	    					buildingId: gridHandler.buildingId,
       	    					unitbookingId: gridHandler.unitbookingId,
       	    					userId: gridHandler.userId,
       	    					customerId: gridHandler.customerId,
       							data: response.data,
            					mode: "edit",
                            	title: title,
                            	url: gridHandler.getURL("post"),
                            	gridHandler: gridHandler
                        }
       					gridHandler.dialogHandler.openAddEditDialog(options);
       				},
       				function(error) {
       					gridHandler.updateMessage("Problem occured fetching record details: " + error.response.data.message, "error");
       				}
       			);
    		},
    		
    		deleteRecord : function(url) {
    			var gridHandler = this;
    			var promise = request.del(url, {
     				timeout: 2000,
     				handleAs: "json"
     			});
     			
     			promise.response.then(
     				function(response) {
     					var data = response.data;
     					gridHandler.updateMessage("Record successfully deleted", "success");
     					gridHandler.refreshGrid();
     				},
     				function(error) {
     					gridHandler.updateMessage("Record deletion error: " + error.response.data.message, "error");
     				}
     			);
    		},
    		
    		updateOverallPaymentDetailsDiv : function () {
    			var gridHandler = this;
            	var overallPaymentDetailsDiv = dojo.byId("overallPaymentDetails");
            	
            	if (!overallPaymentDetailsDiv || overallPaymentDetailsDiv == null) {
            		return;
            	}
     	   		if (this.page == "payment") {
     	   			domClass.remove(overallPaymentDetailsDiv);
	    			var promise = request.get("../rest/json/data/inventory/unitbooking/get?rowId=" + this.unitbookingId, {
	       				timeout: 2000,
	       				handleAs: "json"
	       			});
	       			
	       			promise.response.then(
	       				function(response) {
	       					dojo.byId("totalUnitCost").innerHTML = 
	       						"Total unit cost: " + gridHandler.formatCurrency(response.data.totalUnitCost);
	         	   			dojo.byId("totalUnitCostWithDiscount").innerHTML = 
	         	   				"Total unit cost with discount: " + gridHandler.formatCurrency(response.data.totalUnitCostWithDiscount);
	         	   			dojo.byId("totalPaymentReceived").innerHTML = 
	         	   				"Total payment received: " + gridHandler.formatCurrency(response.data.totalPaymentReceived);
	         	   			dojo.byId("balancePayment").innerHTML = 
	         	   				"Balance payment: " + gridHandler.formatCurrency(response.data.balancePayment);
	       				},
	       				function(error) {
	       					gridHandler.updateMessage("Problem occured fetching booking details: " + error.response.data.message, "error");
	       				}
	       			);
     	   		} else {
     	   			domClass.add(overallPaymentDetailsDiv, "hiddenInput");
     	   		}
            },
            
    		clearMessage : function () {
    			dojo.byId("messageSpanNode").innerHTML = "";
    		},
    		
    		updateMessage : function (message, type) {
    			var messageSpan = dojo.byId("messageSpanNode");
    			if (message && messageSpan) {
    				domClass.remove(messageSpan);
    				if (type == "success") {
    					message = "Success: " + message;
    					domClass.add(messageSpan, "messageSpanSuccess");	
    				} else if (type == "error") {
    					message = "Error: " + message;
    					domClass.add(messageSpan, "messageSpanFailure");	
    				}
    				messageSpan.innerHTML = message;
    			}
    		}
        });
    }
);