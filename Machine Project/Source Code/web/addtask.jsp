<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.TodoList,
    com.webapde.model.TodoListDAO, com.webapde.model.MySQLTodoListDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Simon Says!</title>
	<script type="text/javascript" src="vendors/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="vendors/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
	<script type="text/javascript" src="vendors/datetimepicker/jquery.datetimepicker.js"></script>
	<link rel="stylesheet" type="text/css" href="vendors/datetimepicker/jquery.datetimepicker.css">
	<script>
	     $(function(){
	         $( "#due-datetime" ).datetimepicker();
	     });
	</script>
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
</head>
<body>
		    
	<div class="container-fluid">
		<div class="row list-header">
			<div class="col-md-12">
				<h3>Create a task at "<%= list.getName() %>"</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<form action="TaskServlet" method="post">
					<div class="form-group">
						<label for="name">Name:</label>
						<input type="text" class="form-control" id="name" name="name">
					</div>
					<div class="form-group">
						<label for="priority">Priority:</label>
						<select class="form-control sort-type" id="priority" name="priority">
							<option>HIGH</option>
							<option>NORMAL</option>
							<option>LOW</option>
						</select>
					</div>
					<div class="form-group">
						<label for="description">Description:</label>
						<textarea class="form-control" id="description" name="description"></textarea>
					</div>
					<div class="form-group">
						<label for="due-datetime">Due Date:</label>
						<input type="text" class="form-control"  id="due-datetime" name="due-datetime"/>
					</div>
					<input type="hidden" name="listID" value="<%= list.getID() %>">
					<input type="hidden" name="action" value="ADD">
					<button type="submit" class="btn btn-success">Create</button>
					<a class="btn btn-danger" href="todo.jsp">Cancel</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>