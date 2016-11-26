package de.hska.exablog.web;

import org.omg.CORBA.UserException;

public class SignUpData{
	private String username, password,con_password, email;
	private UserException exception;
	
	public void setUserException(UserException exception){
		this.exception = exception;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public UserException getUserException(){
		return this.exception;
	}	
	
	public String getUsername(){
		return this.username;
	}	
	
	public String getPassword(){
		return this.password;
	}

	public String getCon_password() {
		return con_password;
	}

	public void setCon_password(String con_password) {
		this.con_password = con_password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
