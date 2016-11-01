/*************************************************************************************************
 *
 * AccountValidator.java - The AccountValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

import is.ru.honn.rutube.accountservice.domain.Account;

/**
 * Account validator. Used to validate accounts.
 * Is valid if username is valid according to the
 * rules of {@link UsernameValidator} and the password
 * is valid according to the rules of {@link PasswordValidator}
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountValidator implements Validator<Account> {

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
        UsernameValidator usernameValidator = new UsernameValidator(account.getUsername());
        PasswordValidator passwordValidator = new PasswordValidator(account.getPassword());
        return usernameValidator.validate() && passwordValidator.validate();
    }
}