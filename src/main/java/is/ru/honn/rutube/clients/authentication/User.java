/*************************************************************************************************
 *
 * User.java - The User class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.authentication;

/**
 * User domain class.
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public class User {
    private int userId;
    private String username;

    /**
     * Default constructor.
     */
    public User(){

    }

    /**
     *
     * @param userId The id of the user
     * @param username The username of the user
     */
    public User(int userId, String username){
        setUserId(userId);
        setUsername(username);
    }

    /**
     * Get the user id.
     *
     * @return The user id of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId The user id of the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

}