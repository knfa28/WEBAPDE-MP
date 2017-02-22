package com.webapde.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.webapde.model.MySQLTaskDAO;
import com.webapde.model.MySQLTodoListDAO;
import com.webapde.model.Task;
import com.webapde.model.TaskDAO;
import com.webapde.model.TodoList;
import com.webapde.model.TodoListDAO;

@WebServlet("/TaskDescriptionServlet")
public class TaskDescriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TaskDescriptionServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action.toUpperCase()) {
			case "GET" : {
				get(request, response);
				break;
			}
		}
	}

	private void get(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			TodoListDAO listDAO = new MySQLTodoListDAO();
			TodoList owningList = listDAO.getList(request.getParameter("listID"));
			TaskDAO taskDAO = new MySQLTaskDAO();
			Task task = taskDAO.getTask(request.getParameter("taskID"));
			task.setBackgroundColor(owningList.getBackgroundColor());
			task.setForegroundColor(owningList.getForegroundColor());
			String json = new Gson().toJson(task);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
	}
}
