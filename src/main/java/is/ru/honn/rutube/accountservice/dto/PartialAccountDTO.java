/*************************************************************************************************
 *
 * PartialAccountDTO.java - The PartialAccountDTO class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.dto;

/**
 * Account data transfer object that contains
 * account insensitive information.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public class PartialAccountDTO {

    private int userId;
    private String username;

    /**
     * Default constructor.
     */
    public PartialAccountDTO() {}

    /**
     * @param userId The id of the account.
     * @param username The username of the account.
     */
    public PartialAccountDTO(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Gets the id of the account.
     *
     * @return the id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the username of the account.
     *
     * @return The username of the account.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this object.
     *
     * @param username The username being set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the userId for this object.
     *
     * @param userId The userId being set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}