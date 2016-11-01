/*************************************************************************************************
 *
 * UsernameValidator.java - The UsernameValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

/**
 * Validates username.
 * Username is valid if:
 * - Username length is lesser than 32.
 * - Username length is greater than 4.
 * - Username starts with a letter.
 *
 * @author Sverrir
 * @version 1.0, 01 nóv. 2016
 */
public class UsernameValidator implements Validator<String> {

    private String username;

    /**
     * @param username The username being validated.
     */
    public UsernameValidator(String username) {
        this.username = username;
    }

    /**
     * Validates the username.
     *
     * @return true if username is valid else false.
     */
    @Override
    public boolean validate() {
        return username != null &&
                username.length() > 0 &&
                username.length() <= 32 &&
                !Character.isDigit(username.charAt(0));
    }
}