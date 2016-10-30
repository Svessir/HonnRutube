/*************************************************************************************************
 *
 * AuthenticationClient.java - The AuthenticationClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.authentication;

/**
 * API for a client that communicates
 * with the authentication service.
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public interface AuthenticationClient {

    /**
     * Signs up an account.
     *
     * @param username The username of the new account.
     * @param password The password of the new account.
     * @param repeatedPassword The repeated password of the new account.
     * @return true if sign up succeeded else false.
     */
    boolean signUp(String username, String password, String repeatedPassword);

    /**
     * Logs into account.
     *
     * @param username The account username.
     * @param password The account password.
     * @return The authentication token string if succeeded else null.
     */
    String logIn(String username, String password);

    /**
     * Gets the logged in user.
     *
     * @return The logged in user.
     */
    User getLoggedInUser(String token);
}