<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.TodoList,
    com.webapde.model.TodoListDAO, com.webapde.model.MySQLTodoListDAO,
    com.webapde.model.Color"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Simon Says!</title>
	<script type="text/javascript" src="vendors/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="vendors/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
	<%
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TodoList list = listDAO.getList(request.getParameter("listID"));
	%>
	<style>
		.list-header{
			color: <%= list.getForegroundColor() %>;
			background-color: <%= list.getBackgroundColor() %>
		}
	</style>
	<script>
		$(document).ready(function(){
			$("#name").val("<%=list.getName()%>");
			$("#background-color").val("<%=list.getBackgroundColor()%>");
			$("#foreground-color").val("<%=list.getForegroundColor()%>");
		});
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row list-header">
			<div class="col-md-12">
				<h3>Edit "<%= list.getName() %>"</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<form action="TodoListServlet" method="post">
					<div class="form-group">
						<label for="name">List Name:</label>
						<input type="text" class="form-control" id="name" name="name">
					</div>
					<div class="form-group">
						<label for="background-color">Background Color:</label>
						<select class="form-control" id="background-color" name="background-color">
							<% for (Color x : Color.class.getEnumConstants()) { %>
								<option><%= x.name() %> </option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="foreground-color">Foreground Color:</label>
						<select class="form-control" id="foreground-color" name="foreground-color">
							<% for (Color x : Color.class.getEnumConstants()) { %>
								<option><%= x.name() %> </option>
							<% } %>
						</select>
					</div>
					<input type="hidden" name="listID" value="<%= list.getID() %>">
					<input type="hidden" name="action" value="EDIT">
					<button type="submit" class="btn btn-success">Save Edit</button>
					<a class="btn btn-danger" href="todo.jsp">Cancel</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>