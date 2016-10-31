/*************************************************************************************************
 *
 * RuTubeUserService.java - The RuTubeUserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.services;

import is.ru.honn.rutube.clients.video.VideoServiceClient;
import is.ru.honn.rutube.userservice.data.UserDataGateway;
import is.ru.honn.rutube.userservice.data.UserDataGatewayException;
import is.ru.honn.rutube.userservice.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RuTube user service
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class RuTubeUserService implements UserService {
    private UserDataGateway userDataGateway;


    /**
     * Creates a userProfile
     *
     * @param userId The userId of the user.
     */
    @Override
    public void createUserProfile(int userId) throws UserServiceException {
        try {
            userDataGateway.createUserProfile(userId);
        } catch (Exception e) {
            throw new UserServiceException("Adding user failed.");
        }
    }

    /**
     * Gets a user profile.
     *
     * @param userId The userId of the user.
     * @return The userProfile data.
     */
    @Override
    public UserProfile getUserProfile(int userId) {
        return userDataGateway.getUserProfile(userId);
    }

    /**
     * Deletes user profile.
     *
     * @param userId The userId of the user being deleted.
     */
    @Override
    public void deleteUserProfile(int userId) throws UserServiceException {
        try {
            userDataGateway.deleteUserProfile(userId);
        }catch (UserDataGatewayException udgx){
            throw new UserServiceException("Deletion failed.");
        }

    }

    /**
     * Adds a video to a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being added.
     */
    @Override
    public void addVideoToFavorites(int userId, int videoId) throws UserServiceException {
        try {
            userDataGateway.addFavoriteVideo(userId, videoId);
        }catch (UserDataGatewayException udex){
            throw new UserServiceException("Adding failed.");
        }
    }

    /**
     * Deletes a video from a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being deleted.
     */
    @Override
    public void deleteVideoFromFavorites(int userId, int videoId) throws UserServiceException {
        try {
            userDataGateway.deleteFavoriteVideo(userId, videoId);
        }catch (UserDataGatewayException udex){
            throw new UserServiceException("Deletion failed.");
        }
    }

    /**
     * Adds a friend to a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId of the user being added.
     */
    @Override
    public void addUserToCloseFriends(int userId, int friendId) throws UserServiceException {
        try {
            userDataGateway.addCloseFriend(userId, friendId);
        }catch (UserDataGatewayException udex){
            throw new UserServiceException("Adding failed.");
        }
    }

    /**
     * Deleted a friend from a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId og the user being deleted.
     */
    @Override
    public void deleteUserFromCloseFriends(int userId, int friendId) throws UserServiceException {
        try {
            userDataGateway.deleteCloseFriend(userId, friendId);
        }catch (UserDataGatewayException udex){

            throw new UserServiceException("Deletion failed.");
        }

    }

    /**
     * Sets the user data gateway
     *
     * @param userDataGateway The userDataGateway
     */
    public void setUserDataGateway(UserDataGateway userDataGateway) {
        this.userDataGateway = userDataGateway;
    }
}