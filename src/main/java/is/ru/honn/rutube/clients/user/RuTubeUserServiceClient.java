/*************************************************************************************************
 *
 * RuTubeUserServiceClient.java - The RuTubeUserServiceClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.user;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URL;

/**
 * Client that communicates with the user micro service.
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
        try {
            ResponseEntity response = template.postForEntity(userServiceUrl.toString() + "/create/" + userId, null, String.class);
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
        try{
            ResponseEntity response = template.exchange(userServiceUrl.toString() + "/profile/" + userId,
                                                            HttpMethod.DELETE, null, String.class);
            return response.getStatusCode() == HttpStatus.OK ? true : false;
        }catch (Exception ex){
            return false;
        }
    }


    /**
     * Adds a video from users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean addVideoToFavorites(int userId, int videoId, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
        HttpEntity entity = new HttpEntity(httpHeaders);
        RestTemplate template = new RestTemplate();
        try{
            ResponseEntity response = template.exchange(userServiceUrl.toString() + "/favoriteVideo/" + userId + "/" + videoId,
                                                            HttpMethod.POST, entity, String.class);
            return response.getStatusCode() == HttpStatus.OK ? true : false;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * Deletes a video from users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean deleteVideoFromFavorites(int userId, int videoId, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Token", token);
        HttpEntity entity = new HttpEntity(httpHeaders);
        RestTemplate template = new RestTemplate();
        try{
            ResponseEntity response = template.exchange(userServiceUrl.toString() + "/favoriteVideo/" + userId + "/" + videoId,
                                                            HttpMethod.DELETE, entity, String.class);
            return response.getStatusCode() == HttpStatus.OK ? true : false;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * Sets the userServiceUrl
     *
     * @param userServiceUrl The userServiceUrl.
     */
    public void setUserServiceUrl(URL userServiceUrl) {
        this.userServiceUrl = userServiceUrl;
    }

    private class ID{
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}