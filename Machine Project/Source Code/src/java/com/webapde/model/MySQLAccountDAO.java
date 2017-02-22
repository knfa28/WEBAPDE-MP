package com.webapde.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import com.webapde.exceptions.IncompleteFieldException;

public class MySQLAccountDAO implements AccountDAO {
	private Connection conn;
	
	public MySQLAccountDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/webapde_mp";
			conn = DriverManager.getConnection(url, "root", "11337974");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method does not return null if there are no accounts;
	 * it just returns an empty array.
	 */
	@Override
	public Account[] getAllAccounts() {
		String sql = "SELECT * FROM accounts;";
		Account[] ret = query(sql);
		return ret;
	}

	@Override
	public Account getAccount(String username) {
		String sql = "SELECT * FROM accounts WHERE username ='"
						+ username + "';";
		Account[] ret = query(sql);
		return (ret.length == 0) ? null : ret[0];
	}
	
	private Account[] query(String sql) {
		List<Account> ret = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				Account acct = new Account.Builder().username(username)
								.password(password).firstName(firstName)
								.lastName(lastName).email(email).create();
				ret.add(acct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret.toArray(new Account[ret.size()]);
	}
	
	@Override
	public void createUserAccount(Account user) throws SQLException, IncompleteFieldException {
		if (user.getUsername() == null || user.getPassword() == null ||
				user.getFirstName() == null || user.getLastName() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "INSERT INTO accounts(username, password, "
				+ "first_name, last_name, email) VALUES('"
				+ user.getUsername() +"', '" + user.getPassword() + "', '"
				+ user.getFirstName() + "', '" + user.getLastName() + "', '"
				+ user.getEmail() + "');";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		}
	}
	
	@Override
	public void updateUserAccount(Account user) 
			throws IncompleteFieldException {
		if (user.getUsername() == null || user.getPassword() == null ||
				user.getFirstName() == null || user.getLastName() == null) {
			throw new IncompleteFieldException();
		}
		
		String sql = "UPDATE accounts SET password ='" + user.getPassword()
					+ "', first_name = '" + user.getFirstName() + "', last_name ='"
					+ user.getLastName() + "', email = '" + user.getEmail()
					+ "' WHERE username = '" + user.getUsername() +"';";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAccount(Account acct) {
		String sql = "DELETE FROM accounts WHERE "
				+ "username = '" + acct.getUsername() + "';";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
