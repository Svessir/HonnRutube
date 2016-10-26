/*************************************************************************************************
 *
 * Token.java - The Token class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain;

/**
 * Authentication token for RuTube.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class Token {

    private String username;

    public Token() {
    }

    public Token(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Token parse(String token) {
        return new Token();
    }
}