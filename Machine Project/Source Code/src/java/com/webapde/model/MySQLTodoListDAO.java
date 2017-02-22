package com.webapde.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.webapde.exceptions.IncompleteFieldException;

public class MySQLTodoListDAO implements TodoListDAO {
	private Connection conn;
	private Order order;
	
	private enum Order {
		ASC_CREATE("list_id ASC"), DSC_CREATE("list_id DESC"), ASC_ALPHA("list_name ASC"), DSC_ALPHA("list_name DESC");
		
		private String sql;
		
		private Order(String sql) {
			this.sql = sql;
		}
		
		private String getSQL() {
			return sql;
		}
	}
	
	public MySQLTodoListDAO() {
		order = Order.ASC_ALPHA;
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
	 * This method does not return null if there are no lists;
	 * it just returns an empty array.
	 */
	@Override
	public TodoList[] getAllListsOfAccount(Account acct) {
		String sql = "SELECT L.list_id, L.list_name, L.background_color, L.foreground_color "
				+ "FROM accounts_has_lists AL "
				+ "JOIN lists L ON (AL.list_id = L.list_id) "
				+ "WHERE AL.username = '" + acct.getUsername() + "' ORDER BY " + this.order.getSQL() + ";";
		TodoList[] ret = query(sql);
		return ret;
	}

	@Override
	public TodoList getList(String listID) {
		String sql = "SELECT L.list_id, L.list_name, L.background_color, L.foreground_color "
				+ "FROM lists L WHERE L.list_ID ='" + listID + "';";
		TodoList[] ret = query(sql);
		return (ret.length == 0) ? null : ret[0];
		
	}

	private TodoList[] query(String sql) {
		List<TodoList> ret = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("list_id");
				String name = resultSet.getString("list_name");
				String backgroundColor = resultSet.getString("background_color");
				String foregroundColor = resultSet.getString("foreground_color");
				TodoList list = new TodoList.Builder().id(id).name(name)
								.backgroundColor(backgroundColor)
								.foregroundColor(foregroundColor)
								.create();
				ret.add(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret.toArray(new TodoList[ret.size()]);
	}
	
	@Override
	public void createList(Account acct, TodoList list) throws SQLException, IncompleteFieldException {
		if (acct.getUsername() == null || list.getName() == null ||
				list.getBackgroundColor() == null ||
				list.getForegroundColor() == null) {
			throw new IncompleteFieldException();
		}
		String sql1 = "INSERT INTO lists(list_name, background_color, "
				+ "foreground_color) VALUES('" + list.getName() + "', '" 
				+ list.getBackgroundColor() + "', '" + list.getForegroundColor() 
				+ "'); ";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql1);
		}
		createAccountHasList(acct, getLastInsertedID());
		
	}
	
	private void createAccountHasList(Account acct, String listID) throws SQLException {
		String sql2 = "INSERT INTO accounts_has_lists(username, list_id) VALUES('"
				+ acct.getUsername() + "', " + listID + ");";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql2);
		}
	}
	
	private String getLastInsertedID() {
		String ret = null;
		String sql = "SELECT MAX(list_id) AS 'maxID' FROM lists;";
		try (Statement stmt = conn.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				ret = resultSet.getString(1) + "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/*
	 * GOTCHA - be careful, when using updateList()
	 * you can only pass an instance that you got
	 * from a TodoListDAO. This is because you
	 * can create an instance with no list_id
	 */
	@Override
	public void updateList(TodoList list) 
			throws IncompleteFieldException {
		if (list.getID() == null || list.getName() == null ||
				list.getBackgroundColor() == null ||
				list.getForegroundColor() == null) {
			throw new IncompleteFieldException();
		}
		
		String sql = "UPDATE lists SET list_name = '" + list.getName() + "', "
					+ "background_color = '" + list.getBackgroundColor() + "', "
					+ "foreground_color = '" + list.getForegroundColor() + "' "
					+ "WHERE list_id = " + list.getID() + ";";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteList(TodoList list) throws IncompleteFieldException {
		if (list.getID() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "DELETE FROM lists WHERE "
				+ "list_id = '"  + list.getID() + "';";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void shareList(Account acct, TodoList list)
			throws IncompleteFieldException {
		if (acct.getUsername() == null || list.getID() == null) {
			throw new IncompleteFieldException();
		}
		try {
			createAccountHasList(acct, list.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unshareList(Account acct, TodoList list)
			throws IncompleteFieldException {
		if (acct.getUsername() == null || list.getID() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "DELETE FROM accounts_has_lists WHERE "
				+ "username ='" + acct.getUsername() + "' AND "
				+ "list_id =" + list.getID() + ";";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setOrderOfGet(String order) {
		this.order = Order.valueOf(order);
	}
	
}
