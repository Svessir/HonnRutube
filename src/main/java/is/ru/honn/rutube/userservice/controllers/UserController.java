/*************************************************************************************************
 *
 * UserController.java - The UserController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.controllers;

import is.ru.honn.rutube.clients.authentication.AuthenticationClient;
import is.ru.honn.rutube.clients.authentication.User;
import is.ru.honn.rutube.userservice.domain.UserProfile;
import is.ru.honn.rutube.userservice.dto.UserIdDTO;
import is.ru.honn.rutube.userservice.services.UserService;
import is.ru.honn.rutube.userservice.services.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @param userService The userService being.
     * @param authenticationClient A client to communicate with the authentication micro service.
     */
    @Autowired
    public UserController(UserService userService, AuthenticationClient authenticationClient){
        this.userService = userService;
        this.authenticationClient = authenticationClient;
    }

    /**
     * Creates a userProfile
     *
     * @param userId The userId of the user.
     * @return 200 OK if userProfile is created, 409 CONFLICT if userProfile already exists
     *          and 404 NOT FOUND if user is null.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity createUserProfile(@RequestBody int userId){
        UserProfile userProfile = userService.getUser(userId);
        if(userProfile != null){
            try{
                userService.createUserProfile(userProfile.getId());
            } catch (UserServiceException e) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    /**
     * Get a userProfile.
     *
     * @param token The token of the user.
     * @return UserProfile data and HttpStatus.OK if successful
     *          HttpStatus.UNAUTORIZED otherwise.
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfile> getUserProfile(@RequestHeader(name = "Token", required = false) String token){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null){
            UserProfile userProfile = userService.getUser(user.getUserId());
            return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Deletes a user.
     *
     * @param userId The userIdDTO of the user being deleted.
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteUser(@RequestBody UserIdDTO userId){
        try {
            userService.deleteUser(userId.getUserId());
        }catch (UserServiceException usex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * THe controller method for a video being added to user favorites
     *
     * @param userId The id of the user.
     * @param videoId The id of the video being added from this users favorites.
     * @return
     */
    @RequestMapping(value = "/favoriteVideo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addVideoToFavorites(@RequestHeader(name = "Token", required = false) String token,
                                       @RequestBody int userId, int videoId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                // TODO: make videoClient get the video with this videoId, if that video is null. Bad_Request
                //return new ResponseEntity(HttpStatus.BAD_REQUEST);
                userService.addVideoToFavorites(userId, videoId);
                return new ResponseEntity(HttpStatus.OK);
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    /**
     * The controller method for a video being removed from user favorites.
     *
     * @param userId The id of the user.
     * @param videoId The id of the video being removed from this users favorites.
     * @return
     */
    @RequestMapping(value = "/favoriteVideo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteVideoFromFavorites(@RequestHeader(name = "Token", required = false) String token,
                                            @RequestBody int userId, int videoId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                // TODO: make videoClient get the video with this videoId, if that video is null. Bad_Request
                // return new ResponseEntity(HttpStatus.BAD_REQUEST);
                userService.deleteVideoFromFavorites(userId, videoId);
                return new ResponseEntity(HttpStatus.OK);
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    /**
     * The controller method for a user being added to close friends
     *
     * @param userId The id of a user.
     * @param friendId The id of the friend being added from this users close friends.
     * @return
     */
    @RequestMapping(value = "/closeFriends", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addCloseFriend(@RequestHeader(name = "Token", required = false) String token,
                                  @RequestBody int userId, int friendId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                UserProfile userProfile = userService.getUser(friendId);
                if (userProfile != null) {
                    userService.addUserToCloseFriends(userId, friendId);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    /**
     * The controller method for a user being removed from close friends.
     *
     * @param userId The id of a user.
     * @param friendId The id of the friend being removed from this users close friends.
     * @return
     */
    @RequestMapping(value = "/closeFriends", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteFromCloseFriends(@RequestHeader(name = "Token", required = false) String token,
                                          @RequestBody int userId, int friendId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                UserProfile userProfile = userService.getUser(friendId);
                if (userProfile != null) {
                    userService.deleteUserFromCloseFriends(userId, friendId);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}