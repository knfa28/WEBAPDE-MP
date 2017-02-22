package com.webapde.model;

import java.sql.SQLException;

import com.webapde.exceptions.IncompleteFieldException;

public interface TaskDAO {
	public Task[] getAllTasksOfList(TodoList list);
	public Task getTask(String taskID);
	public void createTask(TodoList list, Task task) throws SQLException, IncompleteFieldException;
	public void updateTask(Task task) throws IncompleteFieldException;
	public void deleteTask(Task task) throws IncompleteFieldException;
	public void setOrderOfGet(String order);
}
