package com.webapde.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webapde.exceptions.IncompleteFieldException;
import com.webapde.exceptions.InvalidPasswordConfirmationException;
import com.webapde.model.Account;
import com.webapde.model.AccountDAO;
import com.webapde.model.MySQLAccountDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm-password");
			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String email = request.getParameter("email");
			
			if (username.equals("") || password.equals("") ||
					confirmPassword.equals("") || firstName.equals("") ||
					lastName.equals("") || email.equals("")) {
				throw new IncompleteFieldException();
			}
			
			if (!password.equals(confirmPassword)) {
				throw new InvalidPasswordConfirmationException();
			}
			Account acct = new Account.Builder().username(username)
					.password(password).firstName(firstName)
					.lastName(lastName).email(email)
					.create();
			AccountDAO acctDAO = new MySQLAccountDAO();
			acctDAO.createUserAccount(acct);
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			out.print("<script>alert('Account created');</script>");
			rd.include(request, response);
			
			
		} catch (SQLException e) {
			out.print("<script>alert('Username already exists');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			rd.include(request, response);
		} catch (IncompleteFieldException e) {
			out.print("<script>alert('Please complete all fields');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			rd.include(request, response);
		} catch (InvalidPasswordConfirmationException e) {
			out.print("<script>alert('Passwords do not match');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			rd.include(request, response);
		}
		
	}

}
