package com.ssj.search.exception;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;



@Configuration
@PropertySource("classpath:exceptioncodes.properties")
public class SearchException extends Exception {

	@Autowired
	private Environment environment;
	private String id;
	private Object[] attributes;
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public SearchException(){
		super("Unknown Error occured");
	}
	
	public SearchException(String id) {
		super(id);
		this.id = id;
	}

	public SearchException(String id, Object... attributes) {
		super(id);
		this.id = id;
		this.attributes = attributes;
	}
	
	
	/** 
	 * @return String
	 */
	public String getIdMessage() {
		String message = environment.resolvePlaceholders("${" + id + "}");
		if(attributes!=null) {
			message = MessageFormat.format(message, attributes);
		}
		
		return message;
	}
}
