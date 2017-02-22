package com.webapde.model;

import java.sql.SQLException;

import com.webapde.exceptions.IncompleteFieldException;

public interface TodoListDAO {
	public TodoList[] getAllListsOfAccount(Account acct);
	public TodoList getList(String listID);
	public void createList(Account acct, TodoList list) throws SQLException, IncompleteFieldException;
	public void updateList(TodoList list) throws IncompleteFieldException;
	public void deleteList(TodoList list) throws IncompleteFieldException;
	public void shareList(Account acct, TodoList list) throws IncompleteFieldException;
	public void unshareList(Account acct, TodoList list) throws IncompleteFieldException;
	public void setOrderOfGet(String order);
}
