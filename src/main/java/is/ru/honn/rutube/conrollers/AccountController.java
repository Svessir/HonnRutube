/*************************************************************************************************
 *
 * AccountController.java - The AccountController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.domain.account.Account;
import is.ru.honn.rutube.domain.account.AccountRegistration;
import is.ru.honn.rutube.domain.account.Token;
import is.ru.honn.rutube.domain.account.dto.PartialAccountDTO;
import is.ru.honn.rutube.domain.account.dto.TokenDTO;
import is.ru.honn.rutube.services.account.AccountService;
import is.ru.honn.rutube.services.account.AccountServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RESTful account controller.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    AccountService accountService;

    @Autowired
    public  AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * The controller method that handles sign up requests to RuTube.
     *
     * @param accountRegistration The account registration form.
     * @return A response of 200 OK if sign up succeeded, 400 BAD REQUEST if
     *         account information syntax is violated. Else 409 CONFLICT
     *         if there is conflicting data in the database.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity signUp(@RequestBody AccountRegistration accountRegistration) {
        try
        {
            accountService.register(accountRegistration);
            return new ResponseEntity<String>("Account successfully created", HttpStatus.OK);
        }
        catch (AccountServiceException asex)
        {
            switch (asex.getErrorCode()) {
                case DUPLICATE_ADD:
                    return new ResponseEntity<String>(asex.getMessage(), HttpStatus.CONFLICT);
                default:
                    return new ResponseEntity<String>(asex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * The controller method that handles authentication/login to RuTube.
     *
     * @param account The login form.
     * @return Token header and 200 OK on success else 401 UNAUTHORIZED.
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenDTO> authenticate(@RequestBody Account account) {
        Token token = accountService.login(account);
        if(token != null)
            return new ResponseEntity<TokenDTO>(new TokenDTO(token.encode()), HttpStatus.OK);
        else
            return new ResponseEntity<TokenDTO>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * The controller method that handles updating account information.
     *
     * @param accountRegistration The updated account information.
     * @return 200 OK if update succeeded, 401 UNAUTHORIZED if authorization is wanting
     *         else if update fails then 400 BAD REQUEST .
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity update(@RequestBody AccountRegistration accountRegistration,
                          @RequestHeader(name = "Token", required = false) String token) {
        Token authenticationToken;
        if((authenticationToken = accountService.isValidAccountToken(token)) == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        try
        {
            authenticationToken = accountService.updateAccountData(authenticationToken.getUserId(),accountRegistration);
            return new ResponseEntity<TokenDTO>(new TokenDTO(authenticationToken.encode()), HttpStatus.OK);
        }
        catch (AccountServiceException asex)
        {
            switch (asex.getErrorCode()) {
                case DUPLICATE_ADD:
                    return new ResponseEntity<String>(asex.getMessage(), HttpStatus.CONFLICT);
                default:
                    return new ResponseEntity<String>(asex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

    }

    /**
     * Deletes an account from the system.
     *
     * @param account An object containing a username field.
     * @return 200 OK if succeeded else 404 NOT FOUND.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> delete(@RequestBody PartialAccountDTO account) {
        try
        {
            accountService.deleteAccount(account.getUsername());
            return new ResponseEntity<String>("Account with username "
                    + account.getUsername() + " has been successfully deleted.",
                    HttpStatus.OK);
        }
        catch (AccountServiceException asex)
        {
            return new ResponseEntity<String>(asex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks if user token is valid.
     *
     * @param token The token sent by the user
     * @return 200 OK if token is valid else 401 UNAUTHORIZED
     */
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    ResponseEntity<PartialAccountDTO> isAuthenticated(@RequestHeader(name = "Token") String token) {
        Token authenticationToken = accountService.isValidAccountToken(token);
        if(authenticationToken != null)
            return new ResponseEntity<PartialAccountDTO>(new PartialAccountDTO(
                    authenticationToken.getUserId(),
                    authenticationToken.getUsername()
            ),HttpStatus.OK);
        return new ResponseEntity<PartialAccountDTO>(HttpStatus.UNAUTHORIZED);
    }
}