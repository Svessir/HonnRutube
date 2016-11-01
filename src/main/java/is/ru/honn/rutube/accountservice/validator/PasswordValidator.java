/*************************************************************************************************
 *
 * PasswordValidator.java - The PasswordValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

/**
 * Validates account passwords.
 * - Password length is lesser than 32.
 * - Password length is greater than 4
 * - Password includes a number.
 *
 * @author Sverrir
 * @version 1.0, 01 nóv. 2016
 */
public class PasswordValidator implements Validator<String> {

    private String password;

    /**
     * @param password The password being validated.
     */
    public PasswordValidator(String password) {
        this.password = password;
    }
    /**
     * Validates the password.
     *
     * @return true if password is valid else false.
     */
    @Override
    public boolean validate() {

        return password != null &&
                password.length() > 0 &&
                password.length() <= 32 &&
                containsNumber(password);
    }

    /**
     * Checks if a String field contains a number.
     *
     * @param field The String field being checked.
     * @return true if field contains a number else false.
     */
    protected boolean containsNumber(String field) {
        for(int i = 0; i < field.length(); i++) {
            if(Character.isDigit(field.charAt(i)))
                return true;
        }
        return false;
    }
}