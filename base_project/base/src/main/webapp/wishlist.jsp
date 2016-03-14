<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.servlet.http.HttpServlet"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection"%>
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
<meta http-equiv="Pragma" content="no-cache">

<title>Wishlist</title>

<!-- Bootstrap core CSS -->
<link href="stylesheet/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="stylesheet/starter-template.css" rel="stylesheet">

<script src="jquery-1.12.1.min.js"></script>
<script type="text/javascript">
	function deletePID(pid) {
		$.ajax({
			url : '/rest/wishlist/' + pid,
			type : 'DELETE',
			success : function() {
				alert('Product has been deleted.');
				window.location.href = "/home.jsp";
			} || $.noop,
			error : function(request, status, error) {
				alert(request.responseText);
			} || $.noop
		});
	}
	function sharePID(pid, name) {
		$.ajax({
			url : '/rest/sharedlist',
			type : 'POST',
			data : 'productID=' + pid + '&productName=' + name,
			success : function() {
				alert('Thank you for sharing this product.');
				window.location.href = "/home.jsp";
			} || $.noop,
			error : function(request, status, error) {
				alert(request.responseText);
			} || $.noop
		});
	}
	function test(pid, name) {
		alert(pid);
		alert(name);
	}
</script>

</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="home.jsp">Amazon Price Tracker</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="home.jsp">Home</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<div class="starter-template">
			<%
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();
				if (user == null) {
			%>
			<h2>Please login first</h2>
			<%
				return;
				} else {
			%>
			<table>
				<tr>
					<td>Product Name</td>
					<td>Current Price</td>
					<td>Lowest Price</td>
				</tr>
				<%
					DatastoreService datastore = DatastoreServiceFactory
								.getDatastoreService();
						Query query = new Query(user.getEmail());
						query.addSort(Entity.KEY_RESERVED_PROPERTY,
								SortDirection.ASCENDING);
						List<Entity> results = datastore.prepare(query).asList(
								FetchOptions.Builder.withDefaults());
						for (Entity entity : results) {
							String productID = (String) entity.getKey().getName();
							String productName = (String) entity
									.getProperty("productName");
							String escapedName = productName.replaceAll("\\'", "\\\\'")
									.replaceAll("\"", "&quot;");
							double currentPrice = (Double) entity
									.getProperty("currentPrice");
							double lowestPrice = (Double) entity
									.getProperty("lowestPrice");
							Date lowestDate = (Date) entity.getProperty("lowestDate");
				%>
				<tr>
					<td><a href="http://www.amazon.com/dp/<%=productID%>"><%=productName%></a>
					</td>
					<td>$<%=currentPrice%>
					</td>
					<td>$<%=lowestPrice%>
					</td>
					<td><input type="button" id="delete_button"
						onclick="deletePID('<%=productID%>')" value="Delete"></td>
					<td><input type="button" id="share_button"
						onclick="sharePID('<%=productID%>', '<%=escapedName%>')"
						value="Share"></td>
				</tr>
				<%
					}
					}
				%>
			</table>
		</div>
	</div>

</body>
</html>
