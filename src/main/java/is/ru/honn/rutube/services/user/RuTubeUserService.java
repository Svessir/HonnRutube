/*************************************************************************************************
 *
 * RuTubeUserService.java - The RuTubeUserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services.user;

import is.ru.honn.rutube.data.user.UserDataGateway;
import is.ru.honn.rutube.data.user.UserDataGatewayException;
import is.ru.honn.rutube.domain.user.UserProfile;

/**
 * RuTube user service
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class RuTubeUserService implements UserService {
    UserDataGateway userDataGateway;

    /**
     * Get a user.
     *
     * @param userId The userId of the user.
     * @return The userProfile data.
     */
    @Override
    public UserProfile getUser(int userId) {
        return userDataGateway.getUserProfile(userId);
    }

    /**
     * Deletes user
     *
     * @param userId The userId of the user being deleted.
     */
    @Override
    public void deleteUser(int userId){
        userDataGateway.deleteUserProfile(userId);

    }

    /**
     * Adds a video to a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being added.
     */
    @Override
    public void addVideoToFavorites(int userId, int videoId) {
        userDataGateway.addFavoriteVideo(userId, videoId);
    }

    /**
     * Deletes a video from a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being deleted.
     */
    @Override
    public void deleteVideoFromFavorites(int userId, int videoId) {
        userDataGateway.deleteFavoriteVideo(userId, videoId);
    }

    /**
     * Adds a friend to a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId of the user being added.
     */
    @Override
    public void addUserToCloseFriends(int userId, int friendId) {
        userDataGateway.addCloseFriend(userId, friendId);
    }

    /**
     * Deleted a friend from a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId og the user being deleted.
     */
    @Override
    public void deleteUserFromCloseFriends(int userId, int friendId) {
        userDataGateway.deleteCloseFriend(userId, friendId);
    }

    /**
     * Sets the userDataGteway
     *
     * @param userDataGateway The userDataGateway
     */
    public void setUserDataGateway(UserDataGateway userDataGateway) {
        this.userDataGateway = userDataGateway;
    }
}