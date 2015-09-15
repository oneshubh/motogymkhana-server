package eu.motogymkhana.server.app;

import org.restlet.Component;

public interface ApplicationManager {

	public void setMainComponent(Component component);

	public void setUIComponent(Component component);

	public Component getMainComponent();

	public Component getUIComponent();
}
