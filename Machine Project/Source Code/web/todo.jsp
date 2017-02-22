<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.AccountDAO,
    com.webapde.model.MySQLAccountDAO, com.webapde.model.Account,
    com.webapde.model.TodoListDAO, com.webapde.model.MySQLTodoListDAO,
    com.webapde.model.TodoList, com.webapde.model.TaskDAO, 
    com.webapde.model.MySQLTaskDAO, com.webapde.model.Task"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Todo - Simon Says!</title>
	<script type="text/javascript" src="vendors/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="vendors/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
	<script type="text/javascript" src="resources/js/todo.js"></script>
	<script type="text/javascript" src="resources/js/countdown.js"></script>
	
</head>
<body>
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <a class="navbar-brand" href="index.jsp">Simon Says!</a>
	    </div>

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav navbar-right">
	     	 <li><a href="calendar.html">Calendar View</a></li>
	        <li><a href="accountSettings.jsp">Account Settings</a></li>
	        <li><a href="LogoutServlet">Logout</a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	<!---------------------------------------NAVBAR-------------------------------------------------->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4" id="todo-list-col">
				<form class="form-inline">
					<div class="form-group">
						<select class="form-control sort-type" id="list-order">
							<option>ASC_ALPHA</option>
							<option>DSC_ALPHA</option>
							<option>ASC_CREATE</option>
							<option>DSC_CREATE</option>
						</select>
						<a class="btn btn-success" href="addlist.jsp">Add List</a>
						<button type="button" class="btn btn-info" id="edit-todo-list-btn">Edit List</button>
						<button type="button" class="btn btn-danger" id="delete-todo-list-btn">Delete List</button>
					</div>
				</form>
				<ul class="list-group" id="todo-lists-list-group">
				</ul>
			</div><!-- /#todo-list-col -->
			<div class="col-md-4" id="tasks-col">
				<form class="form-inline" role="form">
					<div class="form-group">
						<select class="form-control sort-type" id="task-order">
							<option>ASC_ALPHA</option>
							<option>DSC_ALPHA</option>
							<option>ASC_CREATE</option>
							<option>DSC_CREATE</option>
							<option>ASC_PRIORITY</option>
							<option>DSC_PRIORITY</option>
							<option>ASC_DUEDATE</option>
							<option>DSC_DUEDATE</option>
						</select>
						<button type="button" class="btn btn-success" id="add-task-btn">Add Task</button>
						<button type="button" class="btn btn-danger" id="remove-finished-btn">Remove Finished</button>
					</div>
				</form>
				<ul class="list-group" id="tasks-list-group">

				</ul>
			</div><!-- /#tasks-col -->
			<div class="col-md-4" id="task-desc-col">
				<form class="form" role="form">
					<button type="button" class="btn btn-info" id="edit-task-btn">Edit Task</button>
					<button type="button" class="btn btn-danger" id="delete-task-btn">Delete Task</button>
					<button type="button" class="btn btn-warning" id="toggle-finished-btn">Toggle Finished</button>
				</form>
				<form id="task-desc-form">
					<div class="form-group">
						<h4>Name:</h4>
						<p id="task-desc-name"></p>
					</div>
					<div class="form-group">
						<h4>Finished:</h4>
						<p id="task-desc-finished"></p>
					</div>
					<div class="form-group">
						<h4>Priority:</h4>
						<p id="task-desc-priority"></p>
					</div>
					<div class="form-group">
						<h4>Description:</h4>
						<p id="task-desc-description"></p>
					</div>
					<div class="form-group">
						<h4>Due Date:</h4>
						<p id="task-desc-due-date"></p>
					</div>
					<div class="form-group">
						<h4>Countdown:</h4>
						<p id="task-desc-countdown"></p>
					</div>
				</form><!-- /#task-desc-form -->
			</div><!--  /#task-desc-col -->
		</div><!-- /.row -->
	</div><!-- /.container-fluid -->
</body>
</html>