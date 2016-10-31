/*************************************************************************************************
 *
 * RuTubeVideoServiceClient.java - The RuTubeVideoServiceClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.video;

import is.ru.honn.rutube.clients.authentication.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Client that communicates with the
 * video micro service.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public class RuTubeVideoServiceClient implements VideoServiceClient {

    private URL serviceUrl;

    /**
     * Gets a video from the video service.
     *
     * @param token The authentication token.
     * @param videoId The id of the video.
     * @return The video if exists else null.
     */
    @Override
    public RuTubeVideo getVideo(String token, int videoId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        try
        {
            ResponseEntity<RuTubeVideo> response = restTemplate.exchange(serviceUrl.toString() + "/" + videoId,
                    HttpMethod.DELETE, entity, RuTubeVideo.class);
            return response.getStatusCode().equals(HttpStatus.OK) ? response.getBody() : null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Sets the url of the video micro service
     *
     * @param serviceUrl The url of the video micro service.
     */
    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}