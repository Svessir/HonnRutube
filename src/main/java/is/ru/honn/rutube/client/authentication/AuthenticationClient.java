/*************************************************************************************************
 *
 * AuthenticationClient.java - The AuthenticationClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.client.authentication;

/**
 * API for a client that communicates with
 * with the authentication service.
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public interface AuthenticationClient {

    /**
     * Gets the logged in user.
     *
     * @return The logged in user.
     */
    User getLoggedInUser(String token);
}