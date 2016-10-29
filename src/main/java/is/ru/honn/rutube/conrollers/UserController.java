/*************************************************************************************************
 *
 * UserController.java - The UserController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.client.authentication.AuthenticationClient;
import is.ru.honn.rutube.client.authentication.RuTubeAuthenticationClient;
import is.ru.honn.rutube.client.authentication.User;
import is.ru.honn.rutube.domain.user.UserProfile;
import is.ru.honn.rutube.domain.user.UserProfileChange;
import is.ru.honn.rutube.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful user controller
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private AuthenticationClient authenticationClient;

    @Autowired
    public UserController(UserService userService, RuTubeAuthenticationClient authenticationClient){
        this.userService = userService;
        this.authenticationClient = authenticationClient;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getUserProfile(@RequestHeader(name = "Token", required = false) String token){
        User user = authenticationClient.getLoggedInUser(token);
        if(user == null){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity modifyUserProfile(){
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @param id The id of the user
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteUser(int id){
        if(userService.deleteUser(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * THe controller method for a video being added to user favorites
     *
     * @param id The id of a video being added to favorites
     * @return
     */
    @RequestMapping(value = "/favoriteVideo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addVideoToFavorites(int id){
        if(userService.addVideoToFavorites(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * The controller method for a video being removed from user favorites
     *
     * @param id The id of a video being deleted from favorites
     * @return
     */
    @RequestMapping(value = "/favoriteVideo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteVideoFromFavorites(int id){
        if(userService.deleteVideoFromFavorites(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * The controller method for a user being added to close friends
     *
     * @param id The id of a user being added to close friends
     * @return
     */
    @RequestMapping(value = "/closeFriends", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addCloseFriend(int id){
        if(userService.addUserToCloseFriends(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * The controller method for a user being removed from close friends
     *
     * @param id The id of a user being removed from close friends
     * @return
     */
    @RequestMapping(value = "/closeFriends", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteFromCloseFriends(int id){
        if(userService.deleteUserFromCloseFriends(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}