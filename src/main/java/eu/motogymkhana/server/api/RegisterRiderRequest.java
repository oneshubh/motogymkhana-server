package eu.motogymkhana.server.api;

public class RegisterRiderRequest extends GymkhanaRequest{

	private String email;
	private String token;
	
	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}
}
