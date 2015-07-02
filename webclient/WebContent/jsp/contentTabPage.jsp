<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration Page</title>
<style type="text/css">
@import "../js/dojo_1.10.2/dojo/resources/dojo.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/claro.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/document.css";
@import "../js/dojo_1.10.2/dojox/widget/Dialog/Dialog.css";
@import "../js/lib/gridx-1.3.6/resources/claro/Gridx.css";
@import "../css/contentPage.css";
</style>

<jsp:directive.include file="common.jsp" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/dojo_1.10.2/dojo/dojo.js"></script>
<script>
    require(["dojo/ready", 
             "dojo/_base/lang",
             "dojo/dom-class",
             "dijit/registry",
             "dojo/on",
             "dojo/dom-construct",
             "lib/ContentPageHandler",
             "lib/DialogHandler",
			 "lib/GridHandler",
			 "dojo/domReady!"],
        function(ready, lang, domClass, registry, on, domConstruct, ContentPageHandler, DialogHandler, GridHandler) {
	    	ready(function() {
	    		this.readUserDataFromSession();
	    		this.load();
			});
		
            load = function () {
            	this.init(); 
            };
            
            init = function() {
            	this.page = this.getQueryVariable("page");
            	this.mode = this.getQueryVariable("mode");
            	this.projectId = this.getQueryVariable("projectId");
            	this.unitId = this.getQueryVariable("unitId");
            	this.buildingId = this.getQueryVariable("buildingId");
            	this.customerId = this.getQueryVariable("customerId");
            	this.unitbookingId = this.getQueryVariable("unitbookingId");
            	this.unitInfo = decodeURIComponent(this.getQueryVariable("unitInfo"));
            	this.customerName = decodeURIComponent(this.getQueryVariable("customerName"));
            	this.buildingName = decodeURIComponent(this.getQueryVariable("buildingName"));
            	this.projectName = decodeURIComponent(this.getQueryVariable("projectName"));
            	
            	this.initializeHandlers();
            	
            	var dropdownDiv = dojo.byId("dropdownDiv");
            	var backLinkDiv = dojo.byId("backLinkDiv");
            	
            	if (this.page == "projectbankaccount" || this.page == "unit" || this.page == "unitbooking" || 
            			this.page == "cancelledbooking" || this.page == "payment") {
            		domClass.remove(backLinkDiv, "hiddenInput");
            	} else {
            		domClass.add(backLinkDiv, "hiddenInput");
            	}
            	
            	if (this.page == "projectbuilding") {
            		domClass.remove(dropdownDiv);
            		on(registry.byId("projectselect"), "change", lang.hitch(this,"onDropdownSelection"));  
            		this.contentPageHandler.setDataToDropdown('../rest/json/data/inventory/project/get/all', registry.byId("projectselect"));
            	} else {
            		domClass.add(dropdownDiv, "hiddenInput");
            		var pageParams = this.contentPageHandler.getPageParams();
         	   		this.gridHandler.createDataGrid(pageParams.gridLayout, "dataGrid");
         	   		this.gridHandler.createDataGridToolBar("dataGridToolbar", this.contentPageHandler.getMainGridButtonDefinition(), 
     	   				this.contentPageHandler.getToolbarStyle());
            	}
            };
            
            initializeHandlers = function() {
            	this.contentPageHandler = new ContentPageHandler({page: this.page, mode: this.mode, unitInfo: this.unitInfo,
            		customerName: this.customerName, buildingName: this.buildingName, userData: this.userData});
            	
				var pageParams = this.contentPageHandler.getPageParams();
            	
            	this.dialogHandler = new DialogHandler({dialogDimension: pageParams.dialogDimension, GridDialog: pageParams.GridDialog});
            	
            	this.gridHandler = new GridHandler({page: this.page, serviceName: pageParams.serviceName, userId: this.userData.id,
        			dialogTtileField: pageParams.dialogTtileField, dialogTtileString: pageParams.dialogTtileString,
        			dialogHandler: this.dialogHandler});
            	
            	if (this.projectId) {
            		this.gridHandler.setProjectId(this.projectId);
            	} 
            	
            	if (this.unitId) {
            		this.gridHandler.setUnitId(this.unitId);
            	} 
            	
            	if (this.buildingId) {
            		this.gridHandler.setBuildingId(this.buildingId);
            	}
            	
            	if (this.unitbookingId) {
            		this.gridHandler.setUnitbookingId(this.unitbookingId);
            	}

            	if (this.customerId) {
            		this.gridHandler.setCustomerId(this.customerId);
            	}
            	
            	dojo.byId("pageLegend").innerHTML = this.contentPageHandler.getPageLegendTitle();	
            };
            
            onDropdownSelection = function() {
            	this.projectId = registry.byId("projectselect").item.id;
            	if (this.projectId) {
            		this.gridHandler.setProjectId(this.projectId);
            		if (!this.gridHandler.gridx) {
            			this.gridHandler.createDataGridToolBar("dataGridToolbar", this.contentPageHandler.getMainGridButtonDefinition(), 
             	   				this.contentPageHandler.getToolbarStyle());
            		} else {
            			domConstruct.empty("dataGrid");	
            		}
            		var pageParams = this.contentPageHandler.getPageParams();
         	   		this.gridHandler.createDataGrid(pageParams.gridLayout, "dataGrid");
            	}
            };
        });
</script>
</head>
<body class="claro">
<div id="dropdownDiv" class="hiddenInput">
	<label for="projectselect">Select Project:</label>
	<select id="projectselect" name="projectselect" data-dojo-type="dijit/form/FilteringSelect"></select>
	<br><br>
</div>
<div id="dataGridDiv">
	<div id="backLinkDiv" align="right"><a href="javascript:history.back()">Go Back</a></div>
	<fieldset style="width: 95%;">
	 	<legend id="pageLegend"></legend>
	 	<br>
	 	<div class="messageSpanSuccess"><span id="messageSpanNode"></span></div>
	 	<span id="dataGridToolbar"></span>
		<div id="dataGrid"></div>
	</fieldset>
</div>

<div id="overallPaymentDetails" class="hiddenInput">
	<br><br>
	<fieldset style="width: 40%;">
	 	<legend>Overall Details</legend>
	 	<br>
	 	<span id="totalUnitCost"></span><BR><BR>
	 	<span id="totalUnitCostWithDiscount"></span><BR><BR>
	 	<span id="totalPaymentReceived"></span><BR><BR>
		<span id="balancePayment"></span><br><br>
	</fieldset>
</div>
</body>
</html>