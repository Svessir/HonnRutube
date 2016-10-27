/*************************************************************************************************
 *
 * Token.java - The Token class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account;

import is.ru.honn.rutube.domain.validator.Validatable;
import is.ru.honn.rutube.domain.validator.Validator;

/**
 * Authentication token for RuTube.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class Token  implements Validatable {

    private String username;
    private String password;
    private long expires;
    private Validator tokenValidator;

    /**
     * Default constructor
     */
    public Token() {
    }

    /**
     * Construct a token from account information.
     *
     * @param account The account being issued a token.
     */
    public Token(Account account) {
        this.username = account.getUsername();
        //Expires after 60 seconds;
        this.expires = System.currentTimeMillis() + (60L * 1000L);
    }

    /**
     * @param username The username of the user.
     * @param password The password of the user.
     * @param expires The time this token expires.
     */
    public Token(String username, String password, long expires) {
        this.username = username;
        this.password = password;
        this.expires = expires;
    }

    /**
     * Parses a token string into a token object.
     *
     * @param token The token string being parsed.
     * @return The token object.
     */
    public static Token parse(String token) {
        return new Token();
    }

    /**
     * Adds a validator to the token.
     *
     * @param validator The validator to be added.
     */
    @Override
    public void addValidator(Validator validator) {
        tokenValidator = validator;
    }

    /**
     * Validates the token.
     *
     * @return true if the token is valid else false.
     */
    @Override
    public boolean validate() {
        return tokenValidator != null ? tokenValidator.validate() : false;
    }

    /**
     * Initializes the token with a default token validator.
     */
    @Override
    public void initialize() {
    }

    /**
     * Gets the expiration time of the token.
     *
     * @return The expiration time in milliseconds.
     */
    public long getExpires() {
        return expires;
    }

    /**
     * Sets a new expiration time to token.
     *
     * @param expires The expiration time in milliseconds.
     */
    public void setExpires(long expires) {
        this.expires = expires;
    }
}