package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.GymkhanaResult;
import eu.motogymkhana.server.api.TokenRequest;

public interface SendRiderTokenResource {

	GymkhanaResult sendToken(TokenRequest request);
}
