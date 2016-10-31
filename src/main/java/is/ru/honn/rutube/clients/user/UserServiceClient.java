/*************************************************************************************************
 *
 * UserServiceClient.java - The UserServiceClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.user;

/**
 * API for a client that communicates
 * with the user service.
 *
 * @author Kári
 * @version 1.0, 30 okt. 2016
 */
public interface UserServiceClient {
    /**
     * Creates a userProfile.
     *
     * @param userId The userId of the user.
     * @return True if successful, false otherwise.
     */
    boolean createUserProfile(int userId);

    /**
     * Deletes a userProfile.
     *
     * @param userId THe userId of the user being deleted.
     * @return True if successful, false otherwise.
     */

    boolean deleteUserProfile(int userId);

    /**
     * Adds a video to users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    boolean addVideoToFavorites(int userId, int videoId, String token);

    /**
     * Deletes a video from users favorite list.
     *
     * @param userId The userId of the user.
     * @param videoId The videoId of the video.
     * @return True if successful, false otherwise.
     */
    boolean deleteVideoFromFavorites(int userId, int videoId, String token);
}