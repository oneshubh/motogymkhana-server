/*******************************************************************************
 * Copyright (c) 2015, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.app;

import org.restlet.Component;

public interface ApplicationManager {

	public void setMainComponent(Component component);

	public void setUIComponent(Component component);

	public Component getMainComponent();

	public Component getUIComponent();
}
