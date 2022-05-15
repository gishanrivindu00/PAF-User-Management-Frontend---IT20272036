<%@ page import="com.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
	<html>
		<head>
		<meta charset="ISO-8859-1">
		<title>User Management</title>
		
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery.min.js"></script>
		<script src="Components/User.js"></script>
		
		</head>
	<body>
	
	<center>
	<h1>User Management</h1>
	</center>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="formItem" name="formUser" method="post" action="users.jsp">
					Name:
					<input id="name" name="name" type="text"
					class="form-control form-control-sm">
					<br> Email:
					<input id="email" name="email" type="text"
					class="form-control form-control-sm">
					<br> Address:
					<input id="address" name="address" type="text"
					class="form-control form-control-sm">
					<br>
					<input id="btnSave" name="btnSave" type="button" value="Save"
					class="btn btn-primary btn-lg">
					<input type="hidden" id="hidUserIDSave" name="hidUserIDSave" value="">
				</form>
				
			</div>
		</div>
	</div>
	<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
	<br>
		<div id="divUsersGrid">
			 <%
				 User userObj = new User(); 
				 out.print(userObj.readUsers()); 
			 %>
		</div>
	</body>
	</html>