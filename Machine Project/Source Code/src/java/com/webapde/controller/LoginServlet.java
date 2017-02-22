package com.webapde.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webapde.model.Account;
import com.webapde.model.AccountDAO;
import com.webapde.model.MySQLAccountDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameEntered = request.getParameter("username");
		String passwordEntered = request.getParameter("password");
		AccountDAO acctDAO = new MySQLAccountDAO();
		Account acct = acctDAO.getAccount(usernameEntered);
		if (acct != null && acct.getPassword().equals(passwordEntered)) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", acct.getUsername());
			response.sendRedirect("todo.jsp");
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('Invalid username or password!');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.include(request, response);
		}
	}

}
