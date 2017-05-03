package eu.motogymkhana.server.api.response;

import eu.motogymkhana.server.model.Rider;

public class UpdateRiderResponse {
    
    private int status;
    private Rider rider;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOK() {
        return status == 0;
    }

	public void setRider(Rider rider) {
		this.rider = rider;
	}
}

