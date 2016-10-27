/*************************************************************************************************
 *
 * RuTubeAccountService.java - The RuTubeAccountService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

import is.ru.honn.rutube.data.AccountDataGateway;
import is.ru.honn.rutube.data.AccountDataGatewayException;
import is.ru.honn.rutube.domain.account.Account;
import is.ru.honn.rutube.domain.account.AccountRegistration;
import is.ru.honn.rutube.domain.account.Token;

/**
 * RuTube account service.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class RuTubeAccountService implements AccountService {

    private AccountDataGateway accountDataGateway;

    public RuTubeAccountService() {}

    /**
     * registers new account.
     *
     * @param accountRegistration The account registration form.
     * @return The account if the registration succeeded else null.
     */
    @Override
    public void register(AccountRegistration accountRegistration) throws AccountServiceException {
        accountRegistration.initialize();
        if(!accountRegistration.validate())
            throw new AccountServiceException("Account registration is invalid.");

        try
        {
            accountDataGateway.addAccount(accountRegistration);
        }
        catch (AccountDataGatewayException adge)
        {
            throw new AccountServiceException("Account could not be persisted into database", adge);
        }
    }

    /**
     * Updates account data.
     *
     * @param updatedAccountRegistration The updated account registration information.
     */
    @Override
    public boolean updateAccountData(AccountRegistration updatedAccountRegistration) {
        return false;
    }

    /**
     * Login to account.
     *
     * @param account The account login form.
     * @return The authentication token for the account if
     *         the login succeeded else false.
     */
    @Override
    public Token login(Account account) {
        Account retrievedAccount = account != null ?
                accountDataGateway.getAccountByUsername(account.getUsername()) : null;

        return retrievedAccount != null && account.getPassword().equals(retrievedAccount.getPassword()) ?
                new Token(retrievedAccount) : null;
    }

    /**
     * Checks if an authentication token is valid.
     *
     * @param token The authentication token being validated.
     * @return The token as an object if valid else null.
     */
    @Override
    public Token isValidAccountToken(String token) {
        Token authenticationToken = Token.parse(token);
        if(authenticationToken == null)
            return null;
        authenticationToken.initialize();
        return authenticationToken.validate() ? authenticationToken : null;
    }

    /**
     * Deletes and account from the system.
     *
     * @param username The username of the account being deleted.
     * @return true if deletion succeeded else false.
     */
    @Override
    public boolean deleteAccount(String username) {
        return false;
    }

    /**
     * Sets the account data gateway used by this service.
     *
     * @param accountDataGateway The account data gateway use by the service.
     */
    public void setAccountDataGateway(AccountDataGateway accountDataGateway) {
        this.accountDataGateway = accountDataGateway;
    }
}