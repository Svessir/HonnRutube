/*************************************************************************************************
 *
 * RuTubeAuthenticationClient.java - The RuTubeAuthenticationClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.client.authentication;

/**
 * Client that communicates with the
 * authentication micro service.
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public class RuTubeAuthenticationClient implements AuthenticationClient {

    /**
     * Gets the logged in user.
     *
     * @return The logged in user.
     */
    @Override
    public User getLoggedInUser(String token) {
        return null;
    }
}