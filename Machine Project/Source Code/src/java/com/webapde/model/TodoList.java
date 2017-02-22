package com.webapde.model;

public class TodoList {
	private String id;
	private String name;
	private String backgroundColor;
	private String foregroundColor;
	
	public static class Builder implements IBuilder<TodoList> {
		private String id;
		private String name;
		private String backgroundColor;
		private String foregroundColor;
		
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
		
		public Builder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}
		
		public Builder foregroundColor(String foregroundColor) {
			this.foregroundColor = foregroundColor;
			return this;
		}
		
		@Override
		public TodoList create() {
			return new TodoList(this);
		}
	}
	
	private TodoList(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.backgroundColor = builder.backgroundColor;
		this.foregroundColor = builder.foregroundColor;
	}
	
	public void setID(int id) {
		this.id = id + "";
	}
	
	public void setID(String id) {
		this.id = id;
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
	
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public String getBackgroundColor() {
		return this.backgroundColor;
	}
	
	public void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	public String getForegroundColor() {
		return this.foregroundColor;
	}
}
