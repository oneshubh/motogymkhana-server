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
