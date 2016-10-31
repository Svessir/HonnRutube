/*************************************************************************************************
 *
 * AccountDataGateway.java - The AccountDataGateway class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.data;

import is.ru.honn.rutube.accountservice.domain.Account;
import is.ruframework.data.RuDataAccess;

/**
 * The API for the account data gateway.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public interface AccountDataGateway extends RuDataAccess {

    /**
     * Adds a new account into the system database.
     *
     * @param account The account to be added to database.
     * @return the id of the new account.
     * @throws AccountDataGatewayException If the account could not be added to the database.
     */
    int addAccount(Account account) throws AccountDataGatewayException;

    /**
     * Gets an account from the system database.
     * queried via username.
     *
     * @param username The username of the account being queried.
     * @return The account if found else null.
     */
    Account getAccountByUsername(String username);

    /**
     * Deletes account with given username.
     *
     * @param username The unique username of the account being deleted.
     * @return The account that was deleted, null if deletion failed.
     * @throws AccountDataGatewayException On a failed deletion.
     */
    Account deleteAccount(String username) throws AccountDataGatewayException;

    /**
     * Updates account information.
     *
     * @param userId The id of the user being updated.
     * @param account The new account information.
     * @return The Account with the new account information.
     * @throws AccountDataGatewayException If update fails.
     */
    Account updateAccount(int userId, Account account) throws AccountDataGatewayException;
}