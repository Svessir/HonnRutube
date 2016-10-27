/*************************************************************************************************
 *
 * RuTubeUserService.java - The RuTubeUserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

/**
 * Component description
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class RuTubeUserService implements UserService {
    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public boolean addVideoToFavorites(int id) {
        return false;
    }

    @Override
    public boolean deleteVideoFromFavorites(int id) {
        return false;
    }

    @Override
    public boolean addUserToCloseFriends(int id) {
        return false;
    }

    @Override
    public boolean deleteUserFromCloseFriends(int id) {
        return false;
    }
}