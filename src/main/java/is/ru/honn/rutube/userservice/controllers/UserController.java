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
import is.ru.honn.rutube.clients.video.VideoServiceClient;
import is.ru.honn.rutube.userservice.domain.UserProfile;
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
    private VideoServiceClient videoServiceClient;

    /**
     * @param userService The userService being.
     * @param authenticationClient A client to communicate with the authentication micro service.
     */
    @Autowired
    public UserController(UserService userService, AuthenticationClient authenticationClient, VideoServiceClient videoServiceClient){
        this.userService = userService;
        this.authenticationClient = authenticationClient;
        this.videoServiceClient = videoServiceClient;
    }

    /**
     * Creates a userProfile
     *
     * @param userId The userId of the user.
     * @return 200 OK if userProfile is created, 409 CONFLICT if userProfile already exists
     *          and 404 NOT FOUND if user is null.
     */
    @RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
    ResponseEntity createUserProfile(@PathVariable int userId){
        try{
            userService.createUserProfile(userId);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
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
            UserProfile userProfile = userService.getUserProfile(user.getUserId());
            userProfile.setUsername(user.getUsername());
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
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.DELETE)
    ResponseEntity deleteUser(@PathVariable int userId){
        try {
            userService.deleteUserProfile(userId);
        }catch (UserServiceException usex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * THe controller method for a video being added to user favorites
     *
     * @param videoId The id of the video being added from this users favorites.
     * @return
     */
    @RequestMapping(value = "/favoriteVideo/{videoId}", method = RequestMethod.POST)
    ResponseEntity addVideoToFavorites(@RequestHeader(name = "Token", required = false) String token,
                                       @PathVariable int videoId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                if(videoServiceClient.getVideo(token, videoId) != null) {
                    userService.addVideoToFavorites(user.getUserId(), videoId);
                }
                else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
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
     * @param videoId The id of the video being removed from this users favorites.
     * @return
     */
    @RequestMapping(value = "/favoriteVideo/{videoId}", method = RequestMethod.DELETE)
    ResponseEntity deleteVideoFromFavorites(@RequestHeader(name = "Token", required = false) String token,
                                            @PathVariable int videoId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                if(videoServiceClient.getVideo(token, videoId) != null) {
                    userService.deleteVideoFromFavorites(user.getUserId(), videoId);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * The controller method for a user being added to close friends
     *
     * @param friendId The id of the friend being added from this users close friends.
     * @return
     */
    @RequestMapping(value = "/closeFriends/{friendId}", method = RequestMethod.POST)
    ResponseEntity addCloseFriend(@RequestHeader(name = "Token", required = false) String token,
                                  @PathVariable int friendId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                UserProfile userProfile = userService.getUserProfile(friendId);
                if (userProfile != null) {
                    userService.addUserToCloseFriends(user.getUserId(), friendId);
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
     * @param friendId The id of the friend being removed from this users close friends.
     * @return
     */
    @RequestMapping(value = "/closeFriends/{friendId}", method = RequestMethod.DELETE)
    ResponseEntity deleteFromCloseFriends(@RequestHeader(name = "Token", required = false) String token,
                                          @PathVariable int friendId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            try {
                UserProfile userProfile = userService.getUserProfile(friendId);
                if (userProfile != null) {
                    userService.deleteUserFromCloseFriends(user.getUserId(), friendId);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }catch (UserServiceException usex){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}