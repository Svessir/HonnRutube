/*************************************************************************************************
 *
 * Token.java - The Token class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.domain;

import is.ru.honn.rutube.accountservice.validator.TokenValidator;
import is.ru.honn.rutube.accountservice.validator.Validatable;
import is.ru.honn.rutube.accountservice.validator.Validator;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Logger;

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
    private Logger logger = Logger.getLogger(getClass().getName());

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
        this.password = account.getPassword();

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
     * @return The token object if parse succeeded else null.
     */
    public static Token parse(String token) {
        String[] parts = token.split("\\.");

        if(parts.length != 4)
            return null;

        int userId;
        String username;
        String password;
        long expires;

        try
        {
            userId = Integer.parseInt(new String(Base64.getDecoder().decode(parts[0])));
            username = new String(Base64.getDecoder().decode(parts[1]));
            password = new String(Base64.getDecoder().decode(parts[2]));
            expires = Long.parseLong(new String(Base64.getDecoder().decode(parts[3])));
        }
        catch (NumberFormatException nfex)
        {
            return null;
        }

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
     * Encodes this token to string header format.
     *
     * @return token to string header format.
     */
    @Override
    public String toString() {
        return userId + "." + username + "." + password + "." + expires;
    }

    /**
     * Encodes this token to header format.
     *
     * @return Encodes this token.
     */
    public String encode() {
        String encodedId = "";
        String encodedUsername = "";
        String encodedPassword = "";
        String encodedExpires = "";
        try
        {
            encodedId = Base64.getEncoder().encodeToString(Integer.toString(userId).getBytes("UTF-8"));
            encodedUsername = Base64.getEncoder().encodeToString(username.getBytes("UTF-8"));
            encodedPassword = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
            encodedExpires = Base64.getEncoder().encodeToString(Long.toString(expires).getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException uex)
        {
            logger.severe("Token encoding error: " + uex.getMessage());
        }
        return encodedId + "." + encodedUsername + "." + encodedPassword + "." + encodedExpires;
    }
}