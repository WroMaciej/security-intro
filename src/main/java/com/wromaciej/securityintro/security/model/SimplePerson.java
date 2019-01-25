package com.wromaciej.securityintro.security.model;

import java.io.Serializable;

public class SimplePerson implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String name;
	public Integer age;
	

	public SimplePerson(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}


	@Override
	public String toString() {
		return "SimplePerson [name=" + name + ", age=" + age + "]";
	}


	public String getName() {
		return name;
	}


	public void setName( String name ) {
		this.name = name;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge( Integer age ) {
		this.age = age;
	}



	
	
	
}
