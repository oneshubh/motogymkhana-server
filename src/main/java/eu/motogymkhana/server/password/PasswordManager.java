/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.password;

import eu.motogymkhana.server.model.Country;

public interface PasswordManager {

	public boolean checkPassword(Country country, String password);

	public String createHash(String password);

	boolean checkRiderPassword(String email, String password);
}
