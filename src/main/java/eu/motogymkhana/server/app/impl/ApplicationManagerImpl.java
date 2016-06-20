/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.app.impl;

import org.restlet.Component;

import eu.motogymkhana.server.app.ApplicationManager;


public class ApplicationManagerImpl implements ApplicationManager {

	private static Component mainComponent;
	private static Component uiComponent;

	@Override
	public void setMainComponent(Component component) {
		mainComponent = component;
	}

	@Override
	public void setUIComponent(Component component) {
		uiComponent = component;
	}

	@Override
	public Component getMainComponent() {
		return mainComponent;
	}

	@Override
	public Component getUIComponent() {
		return uiComponent;
	}
}
