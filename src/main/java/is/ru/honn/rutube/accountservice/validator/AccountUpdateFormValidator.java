/*************************************************************************************************
 *
 * AccountUpdateFormValidator.java - The AccountUpdateFormValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

import is.ru.honn.rutube.accountservice.domain.AccountUpdateForm;

/**
 * Validates an Account update form.
 * The form is valid if:
 * - If a username is provided then the username must conform to the rules of {@link UsernameValidator}
 * - If a password is provided then the password must conform to the rules of {@link PasswordValidator}
 * - If a password is provided then the repeated password must be identical to the password.
 * - Must contain either a username or password.
 *
 * @author Sverrir
 * @version 1.0, 01 nóv. 2016
 */
public class AccountUpdateFormValidator implements Validator<AccountUpdateFormValidator> {

    private AccountUpdateForm accountUpdateForm;
    private UsernameValidator usernameValidator;
    private PasswordValidator passwordValidator;

    /**
     * @param accountUpdateForm The form being validated.
     */
    public AccountUpdateFormValidator(AccountUpdateForm accountUpdateForm) {
        this.accountUpdateForm = accountUpdateForm;
        usernameValidator = new UsernameValidator(accountUpdateForm.getUsername());
        passwordValidator = new PasswordValidator(accountUpdateForm.getPassword());
    }

    /**
     * Validates the form.
     *
     * @return true if the form is valid else false.
     */
    @Override
    public boolean validate() {
        if(accountUpdateForm.getUsername() != null && !usernameValidator.validate())
            return false;
        if(accountUpdateForm.getPassword() != null && !passwordValidator.validate() &&
                !accountUpdateForm.getPassword().equals(accountUpdateForm.getRepeatedPassword()))
            return false;
        return accountUpdateForm.getUsername() != null || accountUpdateForm.getPassword() != null;
    }
}