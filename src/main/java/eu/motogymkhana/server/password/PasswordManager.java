/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.password;

import eu.motogymkhana.server.api.request.TokenRequest;
import eu.motogymkhana.server.model.Country;

public interface PasswordManager {

	boolean checkPassword(Country country, String password);

	String createHash(String password);

	boolean checkRiderPassword(String email, String password);

	int createRiderAccount(TokenRequest request);
}
