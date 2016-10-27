/*************************************************************************************************
 *
 * AccountDataGateway.java - The AccountDataGateway class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.data;

import is.ru.honn.rutube.domain.account.Account;

/**
 * The API for the account data gateway.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public interface AccountDataGateway {

    /**
     * Adds a new account into the system database.
     *
     * @param account The account to be added to database.
     * @throws AccountDataGatewayException If the account could not be added to the database.
     */
    void addAccount(Account account) throws AccountDataGatewayException;

    /**
     * Gets an account from the system database.
     * queried via username.
     *
     * @param username The username of the account being queried.
     * @return The account if found else null.
     */
    Account getAccountByUsername(String username);

    /**
     * Deletes an account from the database.
     *
     * @param username The unique username of the account being deleted.
     * @return true if deletion succeeded else false.
     */
    boolean deleteAccount(String username);
}