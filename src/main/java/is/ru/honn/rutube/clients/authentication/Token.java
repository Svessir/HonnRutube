/*************************************************************************************************
 *
 * Token.java - The Token class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.authentication;

/**
 * Token implementation used by the
 * authenticaiton client.
 *
 * @author Kári
 * @version 1.0, 30 okt. 2016
 */
public class Token {

    private String token;

    /**
     * Default constructor
     */
    public Token() {}

    /**
     * Gets the string representation of the token.
     *
     * @return the token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the String representation of the token.
     *
     * @param token The token string.
     */
    public void setToken(String token) {
        this.token = token;
    }
}