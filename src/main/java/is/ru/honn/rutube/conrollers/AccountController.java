/*************************************************************************************************
 *
 * AccountController.java - The AccountController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.domain.AccountRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful account controller.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
@RestController
public class AccountController {

    /**
     * The controller that handles sign up requests to RuTube.
     *
     * @param accountRegistration The account registration form.
     * @return A response of 200 OK if sign up succeeded else 400 Bad Request.
     */
    @RequestMapping(value = "/account/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity signUp(@RequestBody AccountRegistration accountRegistration) {
        System.out.println(accountRegistration);
        if(accountRegistration != null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}