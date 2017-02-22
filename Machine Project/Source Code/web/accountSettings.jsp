<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simon Says!</title>
<script type="text/javascript" src="vendors/jquery/jquery.min.js"></script>
<script type="text/javascript" src="vendors/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<%
		AccountDAO acctDAO = new MySQLAccountDAO();
		Account acct = acctDAO.getAccount((String)session.getAttribute("username"));
	%>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <a class="navbar-brand" href="index.jsp">Simon Says!</a>
	    </div>

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="todo.html">Todo</a></li>
	        <li><a href="index.jsp">Logout</a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-5">
				<form class="form-horizontal" action="EditAccountServlet" method="post">
					<div class="form-group">
						<label class="control-label col-md-2" for="username">Username:</label>
						<div class="col-md-10">
							<label class="form-control uneditable-input" id="username"> <%= acct.getUsername() %></label>
						</div>
					</div>
					<div class="form-group password">
						<label class="control-label col-md-2" for="password">Password:</label>
						<div class="col-md-10">
							<input type="password" class="form-control" name="password" value="<%= acct.getPassword() %>">
						</div>
					</div>
					<div class="form-group password">
						<label class="control-label col-md-2" for="confirm-password">Confirm Password:</label>
						<div class="col-md-10">
							<input type="password" class="form-control" name="confirm-password" value="<%= acct.getPassword()%>">
						</div>
					</div>
					<div class="form-group name">
						<label class="control-label col-md-2" for="first-name">First Name:</label>
						<div class="col-md-10">
							<input type="text" class="form-control" name="first-name" value="<%= acct.getFirstName()%>">
						</div>
					</div>
					<div class="form-group name">
						<label class="control-label col-md-2" for="last-name">Last Name:</label>
						<div class="col-md-10">
							<input type="text" class="form-control" name="last-name" value="<%= acct.getLastName()%>">
						</div>
					</div>
					<div class="form-group email">
						<label class="control-label col-md-2" for="email">Email:</label>
						<div class="col-md-10">
							<input type="email" class="form-control" name="email" value="<%= acct.getEmail()%>">
						</div>
					</div>
					<div class="col-md-offset-5">
						<button type="submit" class="btn btn-default" >Submit</button>
						<a class="btn btn-danger" href="todo.jsp"> Cancel </a>
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
</html>