<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.webapde.model.AccountDAO,
    com.webapde.model.MySQLAccountDAO, com.webapde.model.Account,
    com.webapde.model.TodoListDAO, com.webapde.model.TodoList,
    com.webapde.model.MySQLTodoListDAO, com.webapde.model.TaskDAO,
    com.webapde.model.MySQLTaskDAO, com.webapde.model.Task, 
    com.webapde.model.Priority, java.time.LocalDateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample Code Usage</title>
</head>
<body>
	<%--
		AccountDAO acctDAO = new MySQLAccountDAO();
		//Account acct = acctDAO.getAccount("jtbmilan");
		Account newAcct = new Account.Builder().username("jtbmilan")
							.password("tristanmilan").firstName("Jan Tristan")
							.lastName("Milan").email("jan_milan@dlsu.edu.ph")
							.create();
		acctDAO.createUserAccount(newAcct);
		Account otherAcct = new Account.Builder().username("vincentbelkin")
				.password("vincentheha").firstName("Vincent")
				.lastName("Belkin").email("vinceintbelkin@gmail.com")
				.create();
		acctDAO.createUserAccount(otherAcct);
		Account acct = acctDAO.getAccount("jtbmilan");
		TodoListDAO listDAO = new MySQLTodoListDAO();
	--%> 
	<%--
		TodoList list = new TodoList.Builder().name("jtbList1")
						.backgroundColor("BLACK").foregroundColor("RED")
						.create();
		listDAO.createList(acct, list);
		list = new TodoList.Builder().name("jtbList2")
				.backgroundColor("GREEN").foregroundColor("WHITE")
				.create();
		listDAO.createList(acct, list);
		list = new TodoList.Builder().name("jtbList3")
				.backgroundColor("YELLOW").foregroundColor("GREEN")
				.create();
		listDAO.createList(acct, list);
	--%>
	

	
	<%--
		TodoList[] lists = listDAO.getAllListsOfAccount(acct);
		TodoList listToAddTasks = null;
		for (TodoList x : lists) {
			if (x.getName().equals("jtbList2")) {
				listToAddTasks = x;
			}	
		}
		TaskDAO taskDAO = new MySQLTaskDAO();
		Task task = new Task.Builder().name("Task1")
					.priority(Priority.valueOf("HIGH"))
					.description("Hi im task 1")
					.dueDateTime(LocalDateTime.now())
					.create();
		taskDAO.createTask(listToAddTasks, task);
		task = new Task.Builder().name("Task2")
				.priority(Priority.valueOf("NORMAL"))
				.description("hohohohoh")
				.dueDateTime(LocalDateTime.now())
				.create();
		taskDAO.createTask(listToAddTasks, task);
		task = new Task.Builder().name("Task3")
				.priority(Priority.valueOf("LOW"))
				.description("adfdsfdsfdsU")
				.dueDateTime(LocalDateTime.now())
				.create();
		taskDAO.createTask(listToAddTasks, task);
	
		
	--%>
	
	<%--
		AccountDAO accountDAO = new MySQLAccountDAO();
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TaskDAO taskDAO = new MySQLTaskDAO();
		
		Account acct = accountDAO.getAccount("jtbmilan");
		accountDAO.deleteAccount(acct);
	--%>	
	
	<%
		AccountDAO accountDAO = new MySQLAccountDAO();
		TodoListDAO listDAO = new MySQLTodoListDAO();
		//TaskDAO taskDAO = new MySQLTaskDAO();
		Account acct = accountDAO.getAccount("vincentbelkin");
		TodoList list = listDAO.getList("12");
		listDAO.unshareList(acct, list);
	%>
	
</body>
</html>