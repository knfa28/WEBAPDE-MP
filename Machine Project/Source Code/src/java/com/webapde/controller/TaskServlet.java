package com.webapde.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.webapde.exceptions.IncompleteFieldException;
import com.webapde.model.MySQLTaskDAO;
import com.webapde.model.MySQLTodoListDAO;
import com.webapde.model.Priority;
import com.webapde.model.Task;
import com.webapde.model.TaskDAO;
import com.webapde.model.TodoList;
import com.webapde.model.TodoListDAO;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TaskServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action.toUpperCase()) {
			case "GET" : {
				get(request, response);
				break;
			}
			case "ADD" : {
				add(request, response);
				break;
			}
			case "EDIT" : {
				edit(request, response);
				break;
			}
			case "TOGGLE_FINISHED" : {
				toggleFinished(request, response);
				break;
			}
			case "DELETE" : {
				delete(request, response);
				break;
			}
		}
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String listID = request.getParameter("listID");
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TaskDAO taskDAO = new MySQLTaskDAO();
		taskDAO.setOrderOfGet(request.getParameter("order"));
		TodoList list = listDAO.getList(listID);
		Task[] tasks = taskDAO.getAllTasksOfList(list);
		for (Task x : tasks) { // Bad design, forced to do so because of JSON
			x.setBackgroundColor(list.getBackgroundColor());
			x.setForegroundColor(list.getForegroundColor());
		}
		String json = new Gson().toJson(tasks);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TaskDAO taskDAO = new MySQLTaskDAO();
		String listID = request.getParameter("listID");
		String name = request.getParameter("name");
		Priority priority = Priority.valueOf(request.getParameter("priority"));
		String description = request.getParameter("description");
		String dateTimeInput = request.getParameter("due-datetime");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime dueDateTime = LocalDateTime.parse(dateTimeInput, formatter);
		TodoList list = listDAO.getList(listID);
		Task task = new Task.Builder().name(name)
				.priority(priority).description(description)
				.dueDateTime(dueDateTime).create();
		try {
			taskDAO.createTask(list, task);
		} catch (SQLException | IncompleteFieldException e) {
			e.printStackTrace();
		}
		response.sendRedirect("todo.jsp");
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TaskDAO taskDAO = new MySQLTaskDAO();
		String taskID = request.getParameter("taskID");
		Task task = taskDAO.getTask(taskID);
		task.setName(request.getParameter("name"));
		task.setPriority(Priority.valueOf(request.getParameter("priority")));
		task.setDescription(request.getParameter("description"));
		String dateTimeInput = request.getParameter("due-datetime");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime dueDateTime = LocalDateTime.parse(dateTimeInput, formatter);
		task.setDueDateTime(dueDateTime);
		try {
			taskDAO.updateTask(task);
		} catch (IncompleteFieldException e) {
			e.printStackTrace();
		}
		response.sendRedirect("todo.jsp");
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TaskDAO taskDAO = new MySQLTaskDAO();
		String taskID = request.getParameter("taskID");
		Task task = taskDAO.getTask(taskID);
		try {
			taskDAO.deleteTask(task);
		} catch (IncompleteFieldException e) {
			e.printStackTrace();
		}
	}

	private void toggleFinished(HttpServletRequest request, HttpServletResponse response) {
		TaskDAO taskDAO = new MySQLTaskDAO();
		String taskID = request.getParameter("taskID");
		Task task = taskDAO.getTask(taskID);
		task.setFinished((task.isFinished()) ? false : true);
		try {
			taskDAO.updateTask(task);
		} catch (IncompleteFieldException e) {
			e.printStackTrace();
		}
	}
}
