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
