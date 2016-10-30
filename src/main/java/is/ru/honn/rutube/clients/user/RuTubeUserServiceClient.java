/*************************************************************************************************
 *
 * RuTubeUserServiceClient.java - The RuTubeUserServiceClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.user;


import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Component description
 *
 * @author Kári
 * @version 1.0, 30 okt. 2016
 */
public class RuTubeUserServiceClient implements UserServiceClient {
    private URL userServiceUrl;

    /**
     * Creates a userProfile.
     *
     * @param userId The userId of the user.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean createUserProfile(int userId) {
        RestTemplate template = new RestTemplate();
        UserID id = new UserID();
        id.setUserId(userId);
        ResponseEntity response;
        try {
            response = template.postForEntity(userServiceUrl.toString() + "/create/", id, String.class);
            return response.getStatusCode() == HttpStatus.OK ? true : false;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * Deletes a userProfile.
     *
     * @param userId THe userId of the user being deleted.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean deleteUserProfile(int userId) {
        RestTemplate template = new RestTemplate();
        UserID user = new UserID();
        user.setUserId(userId);
        ResponseEntity response;
        return true;
    }

    /**
     * Adds a video from users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean addVideoToFavorites(int userId, int videoId) {
        return true;
    }

    /**
     * Deletes a video from users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean deleteVideoFromFavorites(int userId, int videoId) {
        return true;
    }

    /**
     * Sets the userServiceUrl
     *
     * @param userServiceUrl The userServiceUrl.
     */
    public void setUserServiceUrl(URL userServiceUrl) {
        this.userServiceUrl = userServiceUrl;
    }

    private class UserID{
        private int userId;

        /**
         * Get the userId.
         *
         * @return The userId.
         */
        public int getUserId() {
            return userId;
        }

        /**
         * Sets the userId.
         *
         * @param userId The userId of the user.
         */
        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}