/*************************************************************************************************
 *
 * RuTubeAccountService.java - The RuTubeAccountService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services.account;

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

    /**
     * Default constructor.
     */
    public RuTubeAccountService() {}

    /**
     * Registers new account.
     *
     * @param accountRegistration The account registration form.
     * @return The account if the registration succeeded else null.
     */
    @Override
    public void register(AccountRegistration accountRegistration) throws AccountServiceException {
        accountRegistration.initialize();
        if(!accountRegistration.validate())
            throw new AccountServiceException("Account registration is invalid.", AccountServiceErrorCode.SYNTAX_ERROR);
        try
        {
            accountDataGateway.addAccount(accountRegistration);
        }
        catch (AccountDataGatewayException adge)
        {
            throw new AccountServiceException("Duplicate add.", adge, AccountServiceErrorCode.DUPLICATE_ADD);
        }
    }

    /**
     * Updates account data.
     *
     * @param userId The id of the user being updated.
     * @param updatedAccountRegistration The updated account registration information.
     * @throws AccountServiceException If update fails.
     */
    @Override
    public void updateAccountData(int userId, AccountRegistration updatedAccountRegistration) throws AccountServiceException {
        updatedAccountRegistration.initialize();
        if(updatedAccountRegistration.validate()) {
            accountDataGateway.updateAccount(userId, updatedAccountRegistration);
        }
    }

    /**
     * Login to account.
     *
     * @param account The account login form.
     * @return The authentication token for the account if
     *         the login succeeded else null.
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
        if(!authenticationToken.validate())
            return null;
        return login(new Account(authenticationToken.getUsername(), authenticationToken.getPassword()));
    }

    /**
     * Deletes an account from the system.
     *
     * @param username The username of the account being deleted.
     * @return true if deletion succeeded else false.
     */
    @Override
    public boolean deleteAccount(String username) throws AccountServiceException {
        Account deletedAccount = accountDataGateway.deleteAccount(username);
        // TODO Contact UserService to delete userProfile
        // TODO: Rewind on failure.
        return deletedAccount != null;
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