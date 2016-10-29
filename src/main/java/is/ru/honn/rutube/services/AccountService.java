/*************************************************************************************************
 *
 * AccountService.java - The AccountService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

import is.ru.honn.rutube.domain.account.Account;
import is.ru.honn.rutube.domain.account.AccountRegistration;
import is.ru.honn.rutube.domain.account.Token;

/**
 * The interface for the account services
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public interface AccountService {
    /**
     * Registers new account.
     *
     * @param accountRegistration The account registration form.
     * @return The account if the registration succeeded else null.
     */
    void register(AccountRegistration accountRegistration) throws AccountServiceException;

    /**
     * Updates account data.
     *
     * @param userId The id of the user being updated.
     * @param updatedAccountRegistration The updated account registration information.
     * @return true if update succeeded else false.
     */
    boolean updateAccountData(int userId, AccountRegistration updatedAccountRegistration);

    /**
     * Login to account.
     *
     * @param account The account login form.
     * @return The authentication token for the account if
     *         the login succeeded else null.
     */
    Token login(Account account);

    /**
     * Checks if an authentication token is valid.
     *
     * @param token The authentication token being validated.
     * @return The token as an object if valid else null.
     */
    Token isValidAccountToken(String token);

    /**
     * Deletes an account from the system.
     *
     * @param username The username of the account being deleted.
     * @return true if deletion succeeded else false.
     */
    boolean deleteAccount(String username);
}