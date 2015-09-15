package eu.motogymkhana.server.api;

/**
 * Created by christine on 15-5-15.
 */
public class UpdateTextRequest extends GymkhanaRequest {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UpdateTextRequest() {

	}
}
