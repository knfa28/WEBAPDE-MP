package com.webapde.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
	private String id;
	private String name;
	private Priority priority;
	private String description;
	private LocalDateTime dateTime;
	private String dateTimeStr;
	private boolean isFinished;
	private String backgroundColor; // Bad design, only used for JSON
	private String foregroundColor; // Bad design, only used for JSON
	
	public static class Builder implements IBuilder<Task> {
		private String id;
		private String name;
		private Priority priority;
		private String description;
		private LocalDateTime dateTime;
		private String dateTimeStr;
		private boolean isFinished;
		
		public Builder id(int id) {
			this.id = id + "";
			return this;
		}
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder priority(Priority priority) {
			this.priority = priority;
			return this;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder dueDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			this.dateTimeStr = dateTime.format(formatter);
			return this;
		}
		
		public Builder isFinished(boolean isFinished) {
			this.isFinished = isFinished;
			return this;
		}
		
		@Override
		public Task create() {
			return new Task(this);
		}
	}
	
	private Task(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.priority = builder.priority;
		this.description = builder.description;
		this.dateTime = builder.dateTime;
		this.dateTimeStr = builder.dateTimeStr;
		this.setFinished(builder.isFinished);
	}
	
	public String getID() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Priority getPriority() {
		return this.priority;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDueDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		this.dateTimeStr = dateTime.format(formatter);
	}
	
	public LocalDateTime getDueDateTime() {
		return this.dateTime;
	}
	
	public String getDueDateTimeStr() {
		return this.dateTimeStr;
	}

	public String getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}	
}
