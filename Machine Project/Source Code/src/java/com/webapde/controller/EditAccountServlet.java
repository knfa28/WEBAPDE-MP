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
import javax.servlet.http.HttpSession;

import com.webapde.exceptions.IncompleteFieldException;
import com.webapde.exceptions.InvalidPasswordConfirmationException;
import com.webapde.model.Account;
import com.webapde.model.AccountDAO;
import com.webapde.model.MySQLAccountDAO;

/**
 * Servlet implementation class EditAccountServlet
 */
@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAccountServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountDAO acctDAO = new MySQLAccountDAO();
		HttpSession session = request.getSession(false);
		Account acct = acctDAO.getAccount((String)session.getAttribute("username"));
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm-password");
			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String email = request.getParameter("email");
			
			if ( password.equals("") || confirmPassword.equals("") || firstName.equals("") ||
					lastName.equals("") || email.equals("")) {
				throw new IncompleteFieldException();
			}
			
			if (!password.equals(confirmPassword)) {
				throw new InvalidPasswordConfirmationException();
			}

			acct.setPassword(password);
			acct.setFirstName(firstName);
			acct.setLastName(lastName);
			acct.setEmail(email);
			
			acctDAO.updateUserAccount(acct);
			
			RequestDispatcher rd = request.getRequestDispatcher("todo.jsp");
			out.print("<script>alert('Account updated');</script>");
			rd.include(request, response);
			
			
		} catch (IncompleteFieldException e) {
			out.print("<script>alert('Please complete all fields');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("accountSettings.jsp");
			rd.include(request, response);
		} catch (InvalidPasswordConfirmationException e) {
			out.print("<script>alert('Passwords do not match');</script>");
			RequestDispatcher rd = request.getRequestDispatcher("accountSettings.jsp");
			rd.include(request, response);
		}
	}

}
