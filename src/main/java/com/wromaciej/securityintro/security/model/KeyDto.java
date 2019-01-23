package com.wromaciej.securityintro.security.model;

public abstract class KeyDto {
	
	protected final String name;

	public KeyDto(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	

}
