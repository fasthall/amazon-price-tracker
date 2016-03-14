<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>




<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content=""> 
    <title>Amazon Price Tracker</title>
    <!-- Bootstrap core CSS -->
    <!-- Bootstrap core CSS -->
    <link href="stylesheet/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="stylesheet/jumbotron.css" rel="stylesheet">
  </head>

  <body>
  
    
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			pageContext.setAttribute("user", user);
	%>
    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="jumbotron">
            <font size="4"><p>
		       Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			   href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			   out</a>.)
	        </p></font>
          </div>
	   </div><!--/row-->
     </div><!--/.col-xs-12.col-sm-9-->
	
	<%
		} else {
	%>
	<div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="jumbotron">
            <font size="4"><p>
		       Hello! <a
			   href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			   in</a> to the project.
	         </p></font>
          </div>
	   </div><!--/row-->
     </div><!--/.col-xs-12.col-sm-9-->

	<%
		}
	%>
	
    <nav class="navbar navbar-fixed-top navbar-inverse">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="home.jsp">Amazon PriceTracker</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="home.jsp">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->

        
    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="row">
            <div class="col-xs-6 col-lg-4">
              <h2>Amazon</h2>
              <p> </p>
              <p><a class="btn btn-default" href="http://www.amazon.com/" target="_blank" role="button">Go To Amazon &raquo;</a></p>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-xs-6 col-lg-4">
              <h2>GoogleAppEngine</h2>
              <p></p>
              <p><a class="btn btn-default" href="https://cloud.google.com/appengine/" target="_blank">Go To Appengine &raquo;</a></p>
            </div><!--/.col-xs-6.col-lg-4-->
          </div><!--/row-->
        </div><!--/.col-xs-12.col-sm-9-->
		
		
		
		
		
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
          <div class="list-group">
            <a href="home.jsp" class="list-group-item active">Home</a>
			<font size="4"><a href="add.html"class="list-group-item">Add Product</a></font>
			<font size="4"><a href="delete.html" class="list-group-item">Delete Product</a></font>
            <font size="4"><a href="wishlist.jsp"class="list-group-item">Wishlist</a></font>
			<font size="4"><a href="share.jsp"class="list-group-item">Share</a></font>
	        <font size="4"><a href="sharedlist.jsp"class="list-group-item">Sharedlist</a></font>
          </div>
        </div><!--/.sidebar-offcanvas-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>&copy; 2016 CS263, Inc.</p>
      </footer>

    </div><!--/.container-->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
    <script src="offcanvas.js"></script>
  </body>
</html>
