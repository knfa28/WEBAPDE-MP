package com.webapde.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.webapde.exceptions.IncompleteFieldException;
import com.webapde.model.Account;
import com.webapde.model.AccountDAO;
import com.webapde.model.MySQLAccountDAO;
import com.webapde.model.MySQLTaskDAO;
import com.webapde.model.MySQLTodoListDAO;
import com.webapde.model.Task;
import com.webapde.model.TaskDAO;
import com.webapde.model.TodoList;
import com.webapde.model.TodoListDAO;

/**
 * Servlet implementation class TodoListServlet
 */
@WebServlet("/TodoListServlet")
public class TodoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TodoListServlet() {
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
			case "DELETE" : {
				delete(request, response);
				break;
			}
			case "REMOVE_FINISHED" : {
				removeFinished(request, response);
				break;
			}
		}
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AccountDAO acctDAO = new MySQLAccountDAO();
		TodoListDAO listDAO = new MySQLTodoListDAO();
		listDAO.setOrderOfGet(request.getParameter("order"));
		HttpSession session = request.getSession(false);
		if (session != null) {
			Account acct = acctDAO.getAccount((String)session.getAttribute("username"));
			TodoList[] lists = listDAO.getAllListsOfAccount(acct);
			String json = new Gson().toJson(lists);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AccountDAO acctDAO = new MySQLAccountDAO();
		Account acct = acctDAO.getAccount((String) request.getSession().getAttribute("username"));
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TodoList list = new TodoList.Builder().name(request.getParameter("name"))
							.backgroundColor(request.getParameter("background-color"))
							.foregroundColor(request.getParameter("foreground-color"))
							.create();
		try {
			listDAO.createList(acct, list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncompleteFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("todo.jsp");
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TodoList list = listDAO.getList(request.getParameter("listID"));
		list.setName(request.getParameter("name"));
		list.setBackgroundColor(request.getParameter("background-color"));
		list.setForegroundColor(request.getParameter("foreground-color"));
		try {
			listDAO.updateList(list);
		} catch (IncompleteFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("todo.jsp");
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TodoListDAO listDAO = new MySQLTodoListDAO();
		String listID = request.getParameter("listID");
		TodoList list = listDAO.getList(listID);
		try {
			listDAO.deleteList(list);
		} catch (IncompleteFieldException e) {
			e.printStackTrace();
		}
		response.sendRedirect("index.html");
	}

	private void removeFinished(HttpServletRequest request,
			HttpServletResponse response) {
		TodoListDAO listDAO = new MySQLTodoListDAO();
		TaskDAO taskDAO = new MySQLTaskDAO();
		TodoList list = listDAO.getList(request.getParameter("listID"));
		Task[] tasks = taskDAO.getAllTasksOfList(list);
		for (Task x : tasks) {
			if (x.isFinished()) {
				try {
					taskDAO.deleteTask(x);
				} catch (IncompleteFieldException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
