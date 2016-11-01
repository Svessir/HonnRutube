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
     * Creates userProfile.
     *
     * @param userId The userId of the user.
     * @throws UserServiceException If the user could not be added to the database.
     */
    void createUserProfile(int userId) throws UserServiceException;

    /**
     * Get a user profile.
     *
     * @param userId The userId of the user.
     * @return The userProfile data.
     */
    UserProfile getUserProfile(int userId);

    /**
     * Deletes user profile
     *
     * @param userId The userId of the user being deleted.
     * @throws UserServiceException If the user could not be removed to the database.
     */
    void deleteUserProfile(int userId) throws UserServiceException;

    /**
     * Adds a video to a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being added.
     * @throws UserServiceException If the user entry could not be added to the database.
     */
    void addVideoToFavorites(int userId, int videoId) throws UserServiceException;

    /**
     * Deletes a video from a users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video being deleted.
     * @throws UserServiceException If the user entry could not be removed to the database.
     */
    void deleteVideoFromFavorites(int userId, int videoId) throws UserServiceException;

    /**
     * Adds a friend to a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId of the user being added.
     * @throws UserServiceException If the user entry could not be added to the database.
     */
    void addUserToCloseFriends(int userId, int friendId) throws UserServiceException;

    /**
     * Deleted a friend from a users list.
     *
     * @param userId The userId of the user.
     * @param friendId The friendId og the user being deleted.
     * @throws UserServiceException If the user entry could not be removed to the database.
     */
    void deleteUserFromCloseFriends(int userId, int friendId) throws UserServiceException;
}