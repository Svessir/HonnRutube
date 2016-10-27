/*************************************************************************************************
 *
 * AccountValidator.java - The AccountValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.validator;

import is.ru.honn.rutube.domain.account.Account;

/**
 * Account validator. Used to validate accounts.
 * Account information is valid if:
 *  - Username length is lesser than 32.
 *  - Username length is greater than 4.
 *  - Username starts with a letter.
 *  - Password length is lesser than 32.
 *  - Password length is greater than 4
 *  - Password includes a number.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountValidator implements Validator{

    private Account account;

    /**
     * @param account The account to be validated.
     */
    public AccountValidator(Account account) {
        this.account = account;
    }

    /**
     * Validates the object.
     *
     * @return true if the object is valid else false.
     */
    @Override
    public boolean validate() {
        String username = account.getUsername();
        String password = account.getPassword();

        if(username == null || password == null ||
                username.length() <= 0 || password.length() <= 0 ||
                username.length() > 32 || password.length() > 32 ||
                Character.isDigit(username.charAt(0)) ||
                !containsNumber(password))
            return false;
        return true;
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