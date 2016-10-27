/*************************************************************************************************
 *
 * AccountData.java - The AccountData class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.data;

import is.ru.honn.rutube.domain.account.Account;

/**
 * RuTubes account data gateway implementation.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountData implements AccountDataGateway {

    /**
     * Adds a new account into the system database.
     *
     * @param account The account to be added to database.
     * @throws AccountDataGatewayException If the account could not be added to the database.
     */
    @Override
    public void addAccount(Account account) throws AccountDataGatewayException {
        throw new AccountDataGatewayException("Duplicate add"); // TODO: real implementaiton
    }

    /**
     * Gets an account from the system database.
     * queried via username.
     *
     * @param username The username of the account being queried.
     * @return The account if found else null.
     */
    @Override
    public Account getAccountByUsername(String username) {
        return new Account(1, "Sverrir", "hw33"); // TODO: real implementaiton
    }

    /**
     * Deletes an account from the database.
     *
     * @param username The unique username of the account being deleted.
     * @return true if deletion succeeded else false.
     */
    @Override
    public boolean deleteAccount(String username) {
        return true; // TODO: real implementaiton
    }
}