<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript">
		readUserDataFromSession = function () {
	    	<%Boolean isAutheticated=(Boolean)session.getAttribute("isAutheticated");%>
			var isAutheticated = <%=isAutheticated%>
			if (isAutheticated != null && isAutheticated == true) {
				<% 
	    			String userName=(String)session.getAttribute("userName");
	    			Long id=(Long)session.getAttribute("userId");
	    			Boolean isAdmin=(Boolean)session.getAttribute("isAdmin");
	    		%>
				this.userData = new Object();
				this.userData.id = <%=id%>;
				this.userData.name = "<%=userName%>";
				this.userData.admin = <%=isAdmin%>;
			}
	    };
	</script>
</head>
<body>

</body>
</html>