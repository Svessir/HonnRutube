/*************************************************************************************************
 *
 * RuTubeAuthenticationClient.java - The RuTubeAuthenticationClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.client.authentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Client that communicates with the
 * authentication micro service.
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public class RuTubeAuthenticationClient implements AuthenticationClient {

    private URL serviceUrl;

    /**
     * Gets the logged in user.
     *
     * @return The logged in user.
     */
    @Override
    public User getLoggedInUser(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
        HttpEntity entity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(serviceUrl.toString() + "/authenticated/",
                HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

    /**
     * Sets the url of the authentication micro service
     *
     * @param serviceUrl The url of the authentication micro service.
     */
    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}