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
    void createUserProfile(int userId) throws UserDataGatewayException;
    UserProfile getUserProfile(int userId);
    void deleteUserProfile(int userId) throws UserDataGatewayException;
    void addFavoriteVideo(int userId, int videoId) throws UserDataGatewayException;
    void deleteFavoriteVideo(int userId, int videoId) throws UserDataGatewayException;
    void addCloseFriend(int userId, int friendId) throws UserDataGatewayException;
    void deleteCloseFriend(int userId, int friendId) throws UserDataGatewayException;
}