/*************************************************************************************************
 *
 * TokenDTO.java - The TokenDTO class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account.dto;

/**
 * Token Data transfer object.
 * Transfer token in a string format.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public class TokenDTO {
    private String token;

    /**
     * Default constructor.
     */
    public TokenDTO() {}

    /**
     * @param token The token in a string format.
     */
    public TokenDTO(String token) {
        this.token = token;
    }

    /**
     * Get the token string.
     *
     * @return the token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token string.
     *
     * @param token The new token string.
     */
    public void setToken(String token) {
        this.token = token;
    }
}