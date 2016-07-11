package eu.motogymkhana.server.resource.ui;

import eu.motogymkhana.server.api.request.GetRiderRequest;
import eu.motogymkhana.server.api.response.GetRiderResponse;

public interface UIGetRiderResource {

	GetRiderResponse getRider(GetRiderRequest request);
}
