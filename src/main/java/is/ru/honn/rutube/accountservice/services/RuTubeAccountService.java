/*************************************************************************************************
 *
 * RuTubeAccountService.java - The RuTubeAccountService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.services;

import is.ru.honn.rutube.accountservice.data.AccountDataGateway;
import is.ru.honn.rutube.accountservice.data.AccountDataGatewayException;
import is.ru.honn.rutube.accountservice.domain.Account;
import is.ru.honn.rutube.accountservice.domain.AccountRegistration;
import is.ru.honn.rutube.accountservice.domain.Token;
import is.ru.honn.rutube.clients.user.UserServiceClient;

/**
 * RuTube account service.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class RuTubeAccountService implements AccountService {

    private AccountDataGateway accountDataGateway;
    private UserServiceClient userServiceClient;

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
        try
        {
            accountRegistration.initialize();
            if(!accountRegistration.validate())
                throw new AccountServiceException("Account registration is invalid.", AccountServiceErrorCode.SYNTAX_ERROR);

            int userId = accountDataGateway.addAccount(accountRegistration);
            userServiceClient.createUserProfile(userId);
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
     * @return The new token for the user.
     * @throws AccountServiceException If update fails.
     */
    @Override
    public Token updateAccountData(int userId, AccountRegistration updatedAccountRegistration) throws AccountServiceException {
        try
        {
            updatedAccountRegistration.initialize();
            if(!updatedAccountRegistration.validate())
                throw new AccountServiceException("Updated account information is invalid. Update aborted.",
                        AccountServiceErrorCode.SYNTAX_ERROR);

            Account account = accountDataGateway.updateAccount(userId, updatedAccountRegistration);
            return new Token(account);
        }
        catch (AccountDataGatewayException adge)
        {
            throw new AccountServiceException(adge.getMessage(), AccountServiceErrorCode.DUPLICATE_ADD);
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
     * @param username The username of the account being deleted.
     * @throws AccountServiceException If the user doesn't exist.
     */
    @Override
    public void deleteAccount(String username) throws AccountServiceException {
        try
        {
            accountDataGateway.deleteAccount(username);
        }
        catch (AccountDataGatewayException adgex)
        {
            throw new AccountServiceException("Account with the username " + username + " cannot be found.",
                    adgex.getCause(), AccountServiceErrorCode.NOT_FOUND);
        }
    }

    /**
     * Sets the account data gateway used by this service.
     *
     * @param accountDataGateway The account data gateway use by the service.
     */
    public void setAccountDataGateway(AccountDataGateway accountDataGateway) {
        this.accountDataGateway = accountDataGateway;
    }

    /**
     * Sets the user service client used by this service.
     *
     * @param userServiceClient The user service client.
     */
    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }
}