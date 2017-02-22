package com.webapde.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.webapde.exceptions.IncompleteFieldException;

public class MySQLTaskDAO implements TaskDAO {
	private Connection conn;
	private Order order;
	
	private enum Order {
		ASC_CREATE("task_id ASC"), DSC_CREATE("task_id DESC"), ASC_ALPHA("task_name ASC"),
		DSC_ALPHA("task_name DESC"), ASC_PRIORITY("priority ASC"), DSC_PRIORITY("priority DESC"),
		ASC_DUEDATE("due_datetime ASC"), DSC_DUEDATE("due_datetime DESC");
		
		private String sql;
		
		private Order(String sql) {
			this.sql = sql;
		}
		
		private String getSQL() {
			return sql;
		}
	}
	
	public MySQLTaskDAO() {
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
	 * This method does not return null if there are no tasks;
	 * it just returns an empty array.
	 */
	@Override
	public Task[] getAllTasksOfList(TodoList list) {
		String sql = "SELECT * FROM tasks WHERE"
				+ " list_id =" + list.getID() + " ORDER BY " 
				+ order.getSQL() + ";";
		return query(sql);
	}

	@Override
	public Task getTask(String taskID) {
		String sql = "SELECT * FROM tasks WHERE task_id = " + taskID + ";";
		Task[] ret = query(sql);
		return (ret.length == 0) ? null : ret[0];
	}
	
	private Task[] query(String sql) {
		List<Task> ret = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("task_id");
				String name = resultSet.getString("task_name");
				Priority priority = Priority.valueOf(resultSet.getString("priority"));
				String description = resultSet.getString("description");
				LocalDateTime dateTime = resultSet.getTimestamp("due_datetime")
											.toLocalDateTime();
				boolean isFinished = resultSet.getBoolean("is_finished");
				Task task = new Task.Builder().id(id).name(name)
							.priority(priority).description(description)
							.dueDateTime(dateTime).isFinished(isFinished).create();
				ret.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return ret.toArray(new Task[ret.size()]);
	}

	@Override
	public void createTask(TodoList list, Task task)
			throws SQLException, IncompleteFieldException {
		if (list.getID() == null || task.getName() == null ||
				task.getPriority() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "INSERT INTO tasks(list_id, task_name, priority, "
					+ "description, due_datetime) VALUES('" + list.getID()
					+ "', '" + task.getName() + "', '" + task.getPriority()
					+ "', '" + task.getDescription() + "', '" 
					+ task.getDueDateTime() + "');";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		}
	}

	@Override
	public void updateTask(Task task) 
			throws IncompleteFieldException {
		if (task.getID() == null || task.getName() == null ||
				task.getPriority() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "UPDATE tasks SET task_name = '" + task.getName() + "', "
				+ "priority = '" + task.getPriority() + "', "
				+ "description = '" + task.getDescription() + "', "
				+ "due_datetime = '" + task.getDueDateTime() + "', "
				+ "is_finished = " + ((task.isFinished()) ? 1 : 0 ) + " "
				+ "WHERE task_id =" + task.getID() + ";";
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTask(Task task) throws IncompleteFieldException {
		if (task.getID() == null) {
			throw new IncompleteFieldException();
		}
		String sql = "DELETE FROM tasks WHERE "
				+ "task_id = '" + task.getID() + "';";
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
