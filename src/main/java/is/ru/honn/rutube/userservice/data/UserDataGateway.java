/*************************************************************************************************
 *
 * UserDataGateway.java - The UserDataGateway class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.data;

import is.ru.honn.rutube.userservice.domain.UserProfile;

/**
 * The API for a user data gateway
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public interface UserDataGateway {

    /**
     * Adds a userProfile to database.
     *
     * @param userId The userId of the user.
     * @throws UserDataGatewayException If the user could not be added to the database.
     */
    void createUserProfile(int userId) throws UserDataGatewayException;

    /**
     * Gets the profile of user.
     *
     * @return Gets a profile for user.
     * @throws UserDataGatewayException If the user could not be found in the database.
     */
    UserProfile getUserProfile(int userId);

    /**
     * Deletes user.
     *
     * @param userId The id of a userProfile.
     * @throws UserDataGatewayException If the user could not be removed from the database.
     */
    void deleteUserProfile(int userId) throws UserDataGatewayException;

    /**
     * Removes all entries in with user as friend.
     *
     * @param friendId The id of the user.
     * @throws UserDataGatewayException If the user entry could not be removed from the database.
     */
    void deleteFromFriends(int friendId) throws UserDataGatewayException;

    /**
     * Adds a favorite video to a user.
     *
     * @param userId The id of a video.
     * @param videoId The id of a video.
     * @throws UserDataGatewayException If the user entry could not be added to the database.
     */
    void addFavoriteVideo(int userId, int videoId) throws UserDataGatewayException;

    /**
     * Deletes a favorite video from a user.
     *
     * @param userId The id of a video.
     * @param videoId The id of a video.
     * @throws UserDataGatewayException If the user entry could not be removed from the database.
     */
    void deleteFavoriteVideo(int userId, int videoId) throws UserDataGatewayException;

    /**
     * Adds a close friend to a user.
     *
     * @param userId The id of a video.
     * @param friendId The id of a userProfile.
     * @throws UserDataGatewayException If the user entry could not be added to the database.
     */
    void addCloseFriend(int userId, int friendId) throws UserDataGatewayException;

    /**
     * Deletes a close friend from a user.
     *
     * @param userId The id of a video.
     * @param friendId The id of a userProfile.
     * @throws UserDataGatewayException If the user entry could not be removed to the database.
     */
    void deleteCloseFriend(int userId, int friendId) throws UserDataGatewayException;
}