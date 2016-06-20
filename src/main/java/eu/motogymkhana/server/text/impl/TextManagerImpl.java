/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.text.impl;

import com.google.inject.Singleton;

import eu.motogymkhana.server.text.TextManager;

@Singleton
public class TextManagerImpl implements TextManager {

	private String text;
	
	@Override
	public void setText(String text) {
		this.text=text;
	}

	@Override
	public String getText() {
		return text;
	}

}
