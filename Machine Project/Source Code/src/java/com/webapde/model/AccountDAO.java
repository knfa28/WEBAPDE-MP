package com.webapde.model;

import java.sql.SQLException;

import com.webapde.exceptions.IncompleteFieldException;

public interface AccountDAO {
	public Account[] getAllAccounts();
	public Account getAccount(String username);
	public void createUserAccount(Account acct) throws SQLException, IncompleteFieldException;
	public void updateUserAccount(Account acct) throws IncompleteFieldException;
	public void deleteAccount(Account acct);
}
