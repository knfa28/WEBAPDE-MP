package com.webapde.controller;



import com.google.gson.Gson;
import com.webapde.model.AccountDAO;
import com.webapde.model.CalendarDTO;
import com.webapde.model.MySQLAccountDAO;
import com.webapde.model.MySQLTaskDAO;
import com.webapde.model.MySQLTodoListDAO;
import com.webapde.model.Task;
import com.webapde.model.TaskDAO;
import com.webapde.model.TodoList;
import com.webapde.model.TodoListDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kurt
 */
@WebServlet("/CalendarJSONServlet")
public class CalendarJSONServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public CalendarJSONServlet(){
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ArrayList<CalendarDTO> calendarList = new ArrayList();
    HttpSession session = request.getSession();
    
    AccountDAO acctDAO = new MySQLAccountDAO();
    TodoListDAO listDAO = new MySQLTodoListDAO();
    TaskDAO taskDAO = new MySQLTaskDAO();
    TodoList[] todolist = listDAO.getAllListsOfAccount(acctDAO.getAccount((String) session.getAttribute("username")));
    for(int i = 0; i < todolist.length; i++){
    Task[] taskList = taskDAO.getAllTasksOfList(todolist[i]);
        for(int j = 0; j < taskList.length; j++){
            Task tempTask = taskList[j];
            calendarList.add(new CalendarDTO(Integer.parseInt(tempTask.getID()), 
                tempTask.getName(), tempTask.getDueDateTimeStr(), todolist[i].getBackgroundColor(),
                todolist[i].getForegroundColor()));
        }
    }
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.write(new Gson().toJson(calendarList));
    }

}
