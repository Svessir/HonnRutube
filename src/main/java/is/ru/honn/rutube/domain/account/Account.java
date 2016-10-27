/*************************************************************************************************
 *
 * Account.java - The Account class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account;

import is.ru.honn.rutube.domain.validator.AccountValidator;
import is.ru.honn.rutube.domain.validator.Validatable;
import is.ru.honn.rutube.domain.validator.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Account domain class.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class Account implements Validatable{
    private String username;
    private String password;
    protected List<Validator> validators = new ArrayList<Validator>();

    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * @param username The account username.
     * @param password The account password.
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the account username.
     *
     * @return The account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the account new username.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the account password.
     *
     * @return The account password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the account new password.
     *
     * @param password The account new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validate the account information.
     *
     * @return true if account information are valid else false
     */
    @Override
    public boolean validate() {
        for(Validator validator : validators) {
            if(!validator.validate())
                return false;
        }
        return true;
    }

    /**
     * Adds a validator to the list of validators to
     * validate this object.
     *
     * @param validator A validator to validate accounts.
     */
    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    /**
     * @return Account fields as string.
     */
    @Override
    public String toString() {
        return "username: " + username + " password: " + password;
    }

    /**
     * Initializes account validators.
     */
    public void initialize() {
        clearValidators();
        addValidator(new AccountValidator(this));
    }

    /**
     * Clears the validator list.
     */
    protected void clearValidators() {
        validators.clear();
    }
}