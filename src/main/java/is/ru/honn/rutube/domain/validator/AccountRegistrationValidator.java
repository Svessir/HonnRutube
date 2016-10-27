/*************************************************************************************************
 *
 * AccountRegistrationValidator.java - The AccountRegistrationValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.validator;

import is.ru.honn.rutube.domain.account.AccountRegistration;

/**
 * Account registration validator.
 * Used to validate account registration forms.
 * Validates the same as {@link AccountValidator}
 * with additional validation:
 * - Registration is invalid if password and repeated password
 *   are not equal.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountRegistrationValidator implements Validator<AccountRegistration> {

    private AccountRegistration accountRegistration;
    private AccountValidator accountValidator;

    /**
     * @param accountRegistration The account registration to be validated
     *                            by the validator.
     */
    public AccountRegistrationValidator(AccountRegistration accountRegistration) {
        this.accountRegistration = accountRegistration;
        accountValidator = new AccountValidator(accountRegistration);
    }
    /**
     * Validates the object.
     *
     * @return true if valid else false.
     */
    @Override
    public boolean validate() {
        String password = accountRegistration.getPassword();
        String repeatedPassword = accountRegistration.getRepeatedPassword();
        return accountValidator.validate() && password.equals(repeatedPassword);
    }
}