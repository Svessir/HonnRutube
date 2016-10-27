/*************************************************************************************************
 *
 * UserController.java - The UserController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.domain.User;
import is.ru.honn.rutube.domain.UserProfileChange;
import is.ru.honn.rutube.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Component description
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getUserProfile(User user){
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity modifyUserProfile(UserProfileChange userProfileChange){
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteUser(int id){
        if(userService.deleteUser(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/favoritevideo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addVideoToFavorites(int id){
        if(userService.addVideoToFavorites(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/favoritevideo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteVideoFromFavorites(int id){
        if(userService.deleteVideoFromFavorites(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/user/closeFriends", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addCloseFriend(int id){
        if(userService.addUserToCloseFriends(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/closeFriends", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteFromCloseFriends(int id){
        if(userService.deleteUserFromCloseFriends(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}