package eu.motogymkhana.server.jackson;

public class HideableString implements Hidable {

	private String string;
	private boolean hidden = false;

	public HideableString(String string, boolean hidden) {
		this.string = string;
		this.hidden = hidden;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	public String toString() {
		return string;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
