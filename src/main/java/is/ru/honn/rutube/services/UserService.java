/*************************************************************************************************
 *
 * UserService.java - The UserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

/**
 * The interface for the user services
 *
 * @author Kári
 * @version 1.0, 26 okt. 2016
 */
public interface UserService {

    boolean deleteUser(int id);
    boolean addVideoToFavorites(int id);
    boolean deleteVideoFromFavorites(int id);
    boolean addUserToCloseFriends(int id);
    boolean deleteUserFromCloseFriends(int id);
}