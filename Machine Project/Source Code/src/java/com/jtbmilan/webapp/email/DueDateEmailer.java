package com.jtbmilan.webapp.email;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.webapde.model.Account;
import com.webapde.model.AccountDAO;
import com.webapde.model.MySQLAccountDAO;
import com.webapde.model.MySQLTaskDAO;
import com.webapde.model.MySQLTodoListDAO;
import com.webapde.model.Task;
import com.webapde.model.TaskDAO;
import com.webapde.model.TodoList;
import com.webapde.model.TodoListDAO;

public class DueDateEmailer implements ServletContextListener {
    private static final int MAXIMUM_CONCURRENT = 1;
    private ScheduledThreadPoolExecutor executor = null;
    private AccountDAO acctDAO = new MySQLAccountDAO();
	private TodoListDAO listDAO = new MySQLTodoListDAO();
	private TaskDAO taskDAO = new MySQLTaskDAO();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		executor = new ScheduledThreadPoolExecutor(MAXIMUM_CONCURRENT);
		executor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				Account[] accts = acctDAO.getAllAccounts();
				for (Account acct : accts) {
					TodoList[] lists = listDAO.getAllListsOfAccount(acct);
					for (TodoList list : lists) {
						Task[] tasks = taskDAO.getAllTasksOfList(list);
						for (Task task : tasks) {
							LocalDateTime dueDate = task.getDueDateTime();
							LocalDateTime minuteBeforeNow = LocalDateTime.now().minusMinutes(1);
							if (dueDate.isEqual(LocalDateTime.now()) || 
									(dueDate.isAfter(minuteBeforeNow) && dueDate.isBefore(LocalDateTime.now()))) {
								Email email = new Email.Builder().senderEmail("simonsays.webapde@gmail.com")
										.senderPassword("dlsu1234").to(acct.getEmail()).subject("Simon Says - Task Due Notification")
										.body("The task " + task.getName() + " of the list "
												+ list.getName() + " is due already").create();
								email.send();
							}
						}
					}
				}
			}
			
		}, 1, 1, TimeUnit.MINUTES);
	}

}
