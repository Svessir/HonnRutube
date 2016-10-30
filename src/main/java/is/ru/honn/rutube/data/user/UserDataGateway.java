/*************************************************************************************************
 *
 * UserDataGateway.java - The UserDataGateway class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.data.user;

import is.ru.honn.rutube.domain.user.UserProfile;

/**
 * The API for a user data gateway
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public interface UserDataGateway {
    UserProfile getUserProfile(int userId);
    void deleteUserProfile(int userId);
    void addFavoriteVideo(int userId, int videoId);
    void deleteFavoriteVideo(int userId, int videoId);
    void addCloseFriend(int userId, int friendId);
    void deleteCloseFriend(int userId, int friendId);
}