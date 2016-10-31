/*************************************************************************************************
 *
 * VideoServiceClient.java - The VideoServiceClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.video;

import java.util.List;

/**
 * API for a client that communicates
 * with the video micro service.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public interface VideoServiceClient {

    /**
     * Gets a video from the video service.
     *
     * @param token The authentication token.
     * @param videoId The id of the video.
     * @return The video if exists else null.
     */
    RuTubeVideo getVideo(String token, int videoId);

    /**
     * Gets all videos in the video service.
     *
     * @param token The authentication token.
     * @return List of all existing videos.
     */
    List<RuTubeVideo> getAllVideos(String token);
}