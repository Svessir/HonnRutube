/*************************************************************************************************
 *
 * AccountRegistration.java - The AccountRegistration class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account;

import is.ru.honn.rutube.domain.validator.AccountRegistrationValidator;

/**
 * The account registration class.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountRegistration extends Account {
    protected String repeatedPassword;

    /**
     * Default constructor
     */
    public AccountRegistration(){
    }

    /**
     * @param username The accountRegistration username.
     * @param password The accountRegistration password.
     * @param repeatedPassword The accountRegistration repeated password.
     */
    public AccountRegistration(String username, String password, String repeatedPassword) {
        super(username, password);
        this.repeatedPassword = repeatedPassword;
    }

    /**
     * Get the repeated password of the registration.
     *
     * @return The repeated password.
     */
    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    /**
     * Set the repeated password of the registration.
     *
     * @param repeatedPassword The repeated password.
     */
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    /**
     * @return Account registration fields as a string
     */
    @Override
    public String toString() {
        return "username: " + getUsername() + " password: " + getPassword()
                + " repeatedPassword: " + repeatedPassword;
    }

    /**
     * Initializes the class with default validators
     */
    @Override
    public void initialize() {
        clearValidators();
        addValidator(new AccountRegistrationValidator(this));
    }
}