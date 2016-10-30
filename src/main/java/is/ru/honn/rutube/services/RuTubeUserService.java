/*************************************************************************************************
 *
 * RuTubeUserService.java - The RuTubeUserService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

import is.ru.honn.rutube.data.user.UserDataGateway;

/**
 * RuTube user service
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class RuTubeUserService implements UserService {
    UserDataGateway userDataGateway;
    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean addVideoToFavorites(int id) {
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteVideoFromFavorites(int id) {
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean addUserToCloseFriends(int id) {
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteUserFromCloseFriends(int id) {
        return false;
    }
}