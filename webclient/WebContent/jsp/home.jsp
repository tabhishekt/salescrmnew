<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Sales CRM application</title>

<style type="text/css">
@import "../js/dojo_1.10.2/dojo/resources/dojo.css";
@import "../js/dojo_1.10.2/dijit/themes/claro/claro.css";
@import "../css/homePage.css";
@import "../js/dojo_1.10.2/dojox/widget/Dialog/Dialog.css";
</style>

<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/dojo_1.10.2/dojo/dojo.js"></script>

<script>
    require(["dojo/_base/lang", "dijit/registry", "dijit/layout/BorderContainer",
            "dijit/layout/TabContainer", "dijit/layout/ContentPane", 
            "dijit/layout/AccordionContainer", "dojo/on", "dojo/Deferred", 
            "lib/DialogHandler", "dojo/dom", "dojo/dom-construct", "dojo/domReady!"],
        function(lang, registry, BorderContainer, TabContainer, ContentPane, 
        		AccordionContainer, on, Deferred, DialogHandler, dom, domConstruct) {
    		var dialogHandler = new DialogHandler();
    		
            load = function () {
            	createLayout();
            };
            
            createLayout = function () {
            	// create the BorderContainer and attach it to our appLayout div
            	var appLayout = new BorderContainer({
            	    design: "headline"
            	}, "appLayout");
            	 
            	 
            	// create the TabContainer
            	var contentTabs = new TabContainer({
            	    region: "center",
            	    id: "contentTabs",
            	    tabPosition: "top",
            	    "class": "centerPanel"
            	});
            	
            	// add the TabContainer as a child of the BorderContainer
            	appLayout.addChild( contentTabs );
            	 
            	// create and add the BorderContainer edge regions
            	appLayout.addChild(
            	    new ContentPane({
            	        region: "top",
            	        "class": "edgePanel",
            	        content: createBanner(),
            	        style: "background-color: #222222"
            	    })
            	);
            	appLayout.addChild(
            	    new ContentPane({
            	        region: "left",
            	        id: "leftCol", "class": "edgePanel",
            	        content: createAccordionPanel(),
            	        style: "background-color: #F8FBEF",
            	        splitter: true
            	    })
            	);
            	
            	// start up and do layout
            	appLayout.startup();
            };
            
            createAccordionPanel = function() {
            	var aContainer = new AccordionContainer({style:"height: 300px"}, "markup");
            	var linkData = [];
            	
            	if (this.userData) {
            		linkData = [
            	                ["Create Unit Booking", "contentTabPage.jsp?page=projectbuilding*mode=createbookings"],
            	                ["Manage Unit Bookings", "contentTabPage.jsp?page=projectbuilding*mode=showbookings"],
            	                ["Update Unit Price for Building", "contentTabPage.jsp?page=projectbuilding*mode=updateprice"],
            	                ["Show Availability for Building", "contentTabPage.jsp?page=projectbuilding*mode=availability"]
            	               ];	
            	} 
        	    aContainer.addChild(createAccordionContentPanel("Manage Inventory", linkData));
        	    
        	    if (this.userData) {
	        	    linkData = [
	            	           		["Create Daily Enquiries", "contentTabPage.jsp?page=customer*mode=select"],
	            	           		["Edit Daily Enquiries", "contentTabPage.jsp?page=enquiry"]
	            	           ];
	        	    aContainer.addChild(createAccordionContentPanel("Manage Enquiry", linkData));
        	    }
        	    
        	    if (this.userData && this.userData.admin == true) {
	        	    linkData = [
	            	                ["Manage Orgnizations", "contentTabPage.jsp?page=organization"],
	            	                ["Manage Projects", "contentTabPage.jsp?page=project"],
	            	                ["Manage Project Phases", "contentTabPage.jsp?page=projectphase"],
	            	                ["Manage Buildings", "contentTabPage.jsp?page=projectbuilding"],
	            	                ["Manage Unit Price Policies", "contentTabPage.jsp?page=unitpricepolicy"],
	            	                ["Manage Customers", "contentTabPage.jsp?page=customer"],
	            	                ["Manage Users", "contentTabPage.jsp?page=user"],
	            	                ["Manage Roles", "contentTabPage.jsp?page=role"]
	            	           ];
	        	    aContainer.addChild(createAccordionContentPanel("Admin Pages", linkData));
	        	    
	        	    linkData = [
	        	                ["Manage Cities", "contentTabPage.jsp?page=city"],
	        	                ["Manage States", "contentTabPage.jsp?page=state"],
	        	                ["Manage Unit Types", "contentTabPage.jsp?page=unittype"],
	        	                ["Manage Unit Amenities", "contentTabPage.jsp?page=amenity"],
	        	                ["Manage Sources", "contentTabPage.jsp?page=source"],
	        	                ["Manage Payment Types", "contentTabPage.jsp?page=paymenttype"]
	        	           ];
	        	    aContainer.addChild(createAccordionContentPanel("Code Table", linkData));
        	    }
        	    
        	    aContainer.startup();
        	    
        	    return aContainer; 
            };
			
            createAccordionContentPanel = function(title, linkData) {
            	var groupDiv = domConstruct.create("div", { class:  "groupDiv"});
            	var ul = domConstruct.create("ul", null, groupDiv);
            	for (var i = 0; i < linkData.length; i++) {
	            	var li = domConstruct.create("li", null, ul);
	            	var link = domConstruct.create("span", {innerHTML: linkData[i][0]}, li);
	            	on(link, "click", lang.hitch(this,"openTab",linkData[i]));  
            	}
            	
            	return new ContentPane({
        	        title: title,
        	        content: groupDiv,
        	        style: "background-color: #F8FBEF"
        	    });
            };
            
            openTab = function(tabData) {
            	var contentTabs = registry.byId("contentTabs");
            	var currentTabs = registry.findWidgets(contentTabs.domNode);
            	
            	if (currentTabs.length == 6) {
            		var options = {
	    	        		title:"Too many open tabs", 
	    	        		informationText: "No more than 5 tabs can be opened at a time.<br>Please close some existing tabs and than open a new one. "
	    	        }
            		dialogHandler.setDimension({width:window.screen.width*0.30, height: window.screen.height*0.15});
            		dialogHandler.openInformationDialog(options);	
            		return;
            	}
            	
            	var existingTab = getExistingTab(currentTabs, tabData);
            	if (!existingTab) {
                	var existingTab = createMainContentPane(tabData); 
                	contentTabs.addChild (existingTab);	
            	}
            	
            	contentTabs.selectChild(existingTab);
            };
            
            getExistingTab = function(tabs, tabData) {
            	for (var i = 0; i < tabs.length; i++) {
            		  if (tabs[i].title && tabData[0] == tabs[i].title) {
            			  return tabs[i];
            		  }
            	}
            };
            
            createMainContentPane = function(tabData) {
            	return new ContentPane({
	        	        href: "contentTab.jsp?target=" + tabData[1],
	        	        title: tabData[0],
	        	        style: "background-color: #F8FBEF",
	        	        closable: true,
	        	        onClose: function(evt) {
	        	        	return true;
	        	        }
	        	    });
            };
            
            login = function() {	
            	var options = {
                    	title:"Enter your credentials",
                    	url:"../rest/json/data/userrole/login/post"
                }
            	dialogHandler.setDimension({width:window.screen.width*0.30, height: window.screen.height*0.15});
            	dialogHandler.openLoginDialog(options);
            };
            
            createBanner = function() {
            	var bannerHTML = "<div><table width='95%'><tr><td width='70%'>" +
            	"<span class='bannerText'>Welcome to Sales CRM Application</span></td>";
            	
            	if (this.userData) {
            		bannerHTML += "<td  width='20%' align='right'>";
            		bannerHTML += "<span class='usernameText'>Welcome " + this.userData.name + "</span></td><td width='10%' align='right'>";
            		bannerHTML += "<a href='javascript:this.logout()' class='loginText'>Logout</a>";
            		bannerHTML += "</td>"
            	} else {
            		bannerHTML += "<td  width='10%' align='right'>";
            		bannerHTML += "<a href='javascript:this.login()' class='loginText'>Login</a>";
            		bannerHTML += "</td>"
            	}
            	
            	bannerHTML += "</tr></table></div>";
            	return bannerHTML;
            };
            
        });
</script>
</head>
<body class="claro"  onload="this.load()">
	 <div id="appLayout" class="demoLayout"></div>
</body>
</html>