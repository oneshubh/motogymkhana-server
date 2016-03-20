package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.RegisterRiderRequest;
import eu.motogymkhana.server.api.RegisterRiderResult;

public interface RegisterRiderResource {

	RegisterRiderResult register(RegisterRiderRequest request);

}
