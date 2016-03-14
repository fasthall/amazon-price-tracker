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
 <link href="stylesheet/starter-template.css" rel="stylesheet">
 <link href="stylesheet/signin.css" rel="stylesheet">
</head>

<body>
    
	<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="home.jsp">Amazon Price Tracker</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="home.jsp">Home</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			pageContext.setAttribute("user", user);
	%>
    

	<font size="4"><p>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.) <b>Add a product</b></font>
	


	<%
		} else {
	%>
	<font size="4"><p>
		Hello! <a
			href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			in</a> to the project.
	</p></font>
	<%
		}
	%>
    
	<div class="container">
		<form class="form-signin"action="/rest/sharedlist" method="post">
			 <h2 class="form-signin-heading">SharedProductID</h2>
				 <input input type="text" name="productID" class="form-control" required autofocus>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
		</form>

	</div>
</body>
</html>

