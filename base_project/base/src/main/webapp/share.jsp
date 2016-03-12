<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="styplesheet/favicon.ico">

<!-- Bootstrap core CSS -->
<link href="stylesheet/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="stylesheet/signin.css" rel="stylesheet">

</head>

<body>
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			pageContext.setAttribute("user", user);
	%>

	<p>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.) <b>Add a product</b>
	<div class="container">
		<form action="/rest/sharedlist" method="post">
			<br> ProductID<input type="text" name="productID"> <input
				type="submit">
		</form>

	</div>


	<%
		} else {
	%>
	<p>
		Hello! <a
			href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			in</a> to the project.
	</p>
	<%
		}
	%>

</body>
</html>
