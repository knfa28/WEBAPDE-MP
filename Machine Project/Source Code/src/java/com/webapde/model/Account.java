package com.webapde.model;

public class Account {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	public static class Builder implements IBuilder<Account>{
		private String username;
		private String password;
		private String firstName;
		private String lastName;
		private String email;
		
		public Builder() {
			
		}
 					
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		@Override
		public Account create() {
			return new Account(this);
		}
	}
	
	private Account(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

}
