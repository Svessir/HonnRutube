/*************************************************************************************************
 *
 * UserIdDTO.java - The UserIdDTO class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.dto;

/**
 * Data transfer object for user
 *
 * @author Kári
 * @version 1.0, 30 okt. 2016
 */
public class UserIdDTO {
    private int userId;

    /**
     * Default constructor.
     */
    public UserIdDTO(){}

    /**
     *
     * @param userId The userId of the user.
     */
    public UserIdDTO(int userId){

    }

    /**
     * Get the userId of the user.
     *
     * @return The userId of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the userId of the user.
     *
     * @param userId The userId og the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}