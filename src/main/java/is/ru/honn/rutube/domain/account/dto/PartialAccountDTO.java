/*************************************************************************************************
 *
 * PartialAccountDTO.java - The PartialAccountDTO class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account.dto;

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
}