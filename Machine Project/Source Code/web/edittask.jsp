<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.TodoList,
    com.webapde.model.TodoListDAO, com.webapde.model.MySQLTodoListDAO,
    com.webapde.model.TaskDAO, com.webapde.model.MySQLTaskDAO,
    com.webapde.model.Task"%>
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
	<%
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TodoList list = listDAO.getList(request.getParameter("listID"));
		TaskDAO taskDAO = new MySQLTaskDAO();
		Task task = taskDAO.getTask(request.getParameter("taskID"));
	%>
	<script>
	 $(document).ready(function(){
		 $("#name").val("<%=task.getName()%>");
		 $("#priority").val("<%=task.getPriority()%>");
		 $("#description").val("<%=task.getDescription()%>");
		 $("#due-datetime").val("<%=task.getDueDateTimeStr()%>");
	 });
	    $(function(){
	        $( "#due-datetime" ).datetimepicker();
	    });
	</script>
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
				<h3>EDIT the task "<%= task.getName() %>" of the list "<%= list.getName() %>"</h3>
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
						<input type="text" class="form-control" id="due-datetime" name="due-datetime"/>
					</div>
					<input type="hidden" name="taskID" value="<%= task.getID() %>">
					<input type="hidden" name="action" value="EDIT">
					<button type="submit" class="btn btn-success">Save Edit</button>
					<a class="btn btn-danger" href="todo.jsp">Cancel</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>