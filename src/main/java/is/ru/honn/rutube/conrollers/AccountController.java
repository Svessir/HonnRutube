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
import is.ru.honn.rutube.services.AccountService;
import is.ru.honn.rutube.services.AccountServiceException;
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
     * @return A response of 200 OK if sign up succeeded else 400 Bad Request.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity signUp(@RequestBody AccountRegistration accountRegistration) {
        System.out.println(accountRegistration);
        try
        {
            accountService.register(accountRegistration);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (AccountServiceException ase)
        {
            return new ResponseEntity<String>(ase.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * The controller method that handles authentication/login to RuTube.
     *
     * @param account The login form.
     * @return Token header and 200 OK on success else 401 UNAUTHORIZED.
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> authenticate(@RequestBody Account account) {
        Token token = accountService.login(account);

        if(token != null)
            return new ResponseEntity<String>(token.toString(), HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
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
        if(!accountService.isValidAccountToken(token))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        else if(accountService.updateAccountData(accountRegistration))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    /**
     * Deletes an account from the system.
     *
     * @param username The username of the account being deleted.
     * @return 200 OK if succeeded else 400 Bad Request.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    ResponseEntity delete(String username) {
        if(accountService.deleteAccount(username))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Checks if user token is valid.
     *
     * @param token The token sent by the user
     * @return 200 OK if token is valid else 401 UNAUTHORIZED
     */
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    ResponseEntity isAuthenticated(@RequestBody String token) {
        if(accountService.isValidAccountToken(token))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}