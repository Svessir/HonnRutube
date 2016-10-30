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
import is.ru.honn.rutube.data.user.UserDataGatewayException;
import is.ru.honn.rutube.domain.Video;
import is.ru.honn.rutube.domain.user.UserProfile;
import is.ru.honn.rutube.services.user.UserService;
import is.ru.honn.rutube.services.user.UserServiceException;
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
        if(user != null){
            userService.getUser(user.getUserId());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a user.
     *
     * @param userId The id of the user being deleted.
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity deleteUser(@RequestHeader(name = "Token", required = false) String token,
                              @RequestBody int userId){
        User user = authenticationClient.getLoggedInUser(token);
        if(user != null) {
            userService.deleteUser(userId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
            // TODO: make videoClient get the video with this videoId, if that video is null. Bad_Request
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
            userService.addVideoToFavorites(userId, videoId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
            // TODO: make videoClient get the video with this videoId, if that video is null. Bad_Request
            // return new ResponseEntity(HttpStatus.BAD_REQUEST);
            userService.deleteVideoFromFavorites(userId, videoId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
            UserProfile userProfile = userService.getUser(friendId);
            if(userProfile != null) {
                // TODO: make userClient get the user with this friendId, if that user is null. Bad_Request
                //return new ResponseEntity(HttpStatus.BAD_REQUEST);
                userService.addUserToCloseFriends(userId, friendId);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
            UserProfile userProfile = userService.getUser(friendId);
            if(userProfile != null) {
                // TODO: make userClient get the user with this friendId, if that user is null. Bad_Request
                //return new ResponseEntity(HttpStatus.BAD_REQUEST);
                userService.deleteUserFromCloseFriends(userId, friendId);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}