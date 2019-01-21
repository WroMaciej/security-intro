package com.wromaciej.securityintro.security.model;

public abstract class CipherDto {
	
	protected final String name;

	public CipherDto(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	

}
