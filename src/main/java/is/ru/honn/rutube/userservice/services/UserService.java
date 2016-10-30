/*************************************************************************************************
 *
 * UserService.java - The UserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.services;


import is.ru.honn.rutube.userservice.domain.UserProfile;

/**
 * The interface for the user services
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public interface UserService {

    /**
     * Get a user.
     *
     * @param userId The userId of the user.
     * @return The userProfile data.
     */
    UserProfile getUser(int userId);

    /**
     * Deletes user
     *
     * @param userId The userId of the user being deleted.
     */
    void deleteUser(int userId);

    /**
     * Adds a video to a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being added.
     */
    void addVideoToFavorites(int userId, int videoId);

    /**
     * Deletes a video from a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being deleted.
     */
    void deleteVideoFromFavorites(int userId, int videoId);

    /**
     * Adds a friend to a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId of the user being added.
     */
    void addUserToCloseFriends(int userId, int friendId);

    /**
     * Deleted a friend from a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId og the user being deleted.
     */
    void deleteUserFromCloseFriends(int userId, int friendId);
}