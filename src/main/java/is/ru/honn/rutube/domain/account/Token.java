/*************************************************************************************************
 *
 * Token.java - The Token class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.domain.account;

import is.ru.honn.rutube.domain.validator.TokenValidator;
import is.ru.honn.rutube.domain.validator.Validatable;
import is.ru.honn.rutube.domain.validator.Validator;

/**
 * Authentication token for RuTube.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class Token  implements Validatable {

    private int userId;
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
        this.userId = account.getUserId();
        this.username = account.getUsername();

        //Expires after 60 seconds;
        this.expires = System.currentTimeMillis() + (60L * 1000L);
    }

    /**
     * @param username The username of the user.
     * @param password The password of the user.
     * @param expires The time this token expires.
     */
    public Token(int userId, String username, String password, long expires) {
        this.userId = userId;
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
        String[] parts = token.split(".");

        if(parts.length != 4)
            return null;

        int userId = Integer.parseInt(parts[0]);
        String username = parts[1];
        String password = parts[2];
        long expires = Long.parseLong(parts[3]);
        return new Token(userId, username, password, expires);
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
        addValidator(new TokenValidator(this));
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

    /**
     * Get the username of this token.
     *
     * @return The username of the account this token is issued to.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of this token.
     *
     * @return The password of the account this token is issued to.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the user id of this token.
     *
     * @return The user id of the account this token is issued to.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Encodes this token to header format.
     *
     * @return token to header format.
     */
    @Override
    public String toString() {
        return userId + "." + username + "." + password + "." + expires;
    }
}