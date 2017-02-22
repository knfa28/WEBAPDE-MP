<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Simon Says!</title>
	<script type="text/javascript" src="vendors/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="vendors/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
	<style>
	.loginBackground{
		background: url(resources/images/index_bg.jpg);
		background-size: 100% 100%;
		width: 100%;
		height: 100%;
		position: fixed;
	}
	
	h1{
		color: black;
		font-size: 5em;
		font-style: bold;
		font-family: 'Rockwell Condensed', cursive;
	}
	
	h4{
		color: black;
		font-size: 1.75em;
		font-family: 'Rockwell Condensed', cursive;
	}
	</style>
	<%
		boolean isLoggedIn = (session.getAttribute("username") !=  null);
	%>
</head>
<body>
	<div class="loginBackground">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
			  <a class="navbar-brand" href="index.jsp">Simon Says!</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			  <ul class="nav navbar-nav navbar-right">
				<li><a href="about.html">About</a></li>
				<li><a href="help.html">Help</a></li>
				<li><a href="contactus.html">Contact Us</a></li>
			  </ul>
			</div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
	
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<div class="blackFont">
						<center>
							<h1>HAVE YOUR LISTS ON THE GO</h1>
							<h4>Access your lists anytime, anywhere. Its absolutely free</h4>
							<form class="form-inline" role="form">			
									<a class="btn btn-success" href="register.html">Sign Up</a>
									<a class="btn btn-warning" href=<%= (isLoggedIn) ? "todo.jsp" : "login.html" %>>Sign In</a>
							</form>
						</center>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>