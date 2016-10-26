/*************************************************************************************************
 *
 * Account.java - The Account class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain;

/**
 * Account domain class.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class Account {
    protected String username;
    protected String password;

    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * @param username The account username.
     * @param password The account password.
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the account username.
     *
     * @return The account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the account new username.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the account password.
     *
     * @return The account password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the account new password.
     *
     * @param password The account new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Account fields as string.
     */
    @Override
    public String toString() {
        return "username: " + username + " password: " + password;
    }
}