<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.contentPanel {
	background-color: #F8FBEF;
	overflow: auto;
	width: 90%;
	height: 100%;
	display: inline-block;
}

.contentPanelFrame {
	overflow: auto;
	width: 100%;
	height: 99%;
	display: inline-block;
}
</style>
</head>
<body>
	<div id="contentPanel" class="contentPanel">
		<% 
			String target = request.getParameter("target"); 
			target = target.replace("*", "&");
		%>
		<IFRAME name="contentPanelFrame" id="contentPanelFrame" class="contentPanelFrame" src=<%= target%>
			frameborder=0 scrolling=yes></IFRAME>
	</div>
</body>
</html>