/*************************************************************************************************
 *
 * TokenValidator.java - The TokenValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.validator;

import is.ru.honn.rutube.accountservice.domain.Account;
import is.ru.honn.rutube.accountservice.domain.Token;

/**
 * Validator that validates authentication tokens.
 * Token is valid if:
 * - Current time has not exceeded the token expiration time.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public class TokenValidator implements Validator<Token> {

    private Token token;

    /**
     * @param token The token that is to be validated.
     */
    public TokenValidator(Token token) {
        this.token = token;
    }

    /**
     * Validates the token.
     *
     * @return true if valid else false.
     */
    @Override
    public boolean validate() {
        Account account = new Account(token.getUserId(), token.getUsername(), token.getPassword());
        account.initialize();
        return account.validate() && System.currentTimeMillis() <= token.getExpires() ;
    }
}