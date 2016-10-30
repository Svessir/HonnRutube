/*************************************************************************************************
 *
 * RuTubeAuthenticationClient.java - The RuTubeAuthenticationClient class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.authentication;

import org.springframework.http.*;
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
     * Signs up an account on RuTube.
     *
     * @param username The username of the new account.
     * @param password The password of the new account.
     * @param repeatedPassword The repeated password of the new account.
     * @return true if sign up succeeded else false.
     */
    public boolean signUp(String username, String password, String repeatedPassword) {
        RestTemplate restTemplate = new RestTemplate();
        AccountForm accountForm = new AccountForm();
        accountForm.setUsername(username);
        accountForm.setPassword(password);
        accountForm.setRepeatedPassword(repeatedPassword);
        ResponseEntity response;
        try
        {
            response = restTemplate.postForEntity(serviceUrl.toString() + "/signup/", accountForm, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /**
     * Logs in to RuTube.
     *
     * @param username The account username.
     * @param password The account password.
     * @return The authentication token string if succeeded else null.
     */
    public String logIn(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        AccountForm accountForm = new AccountForm();
        accountForm.setUsername(username);
        accountForm.setPassword(password);
        ResponseEntity<Token> response;
        try
        {
            response = restTemplate.postForEntity(serviceUrl.toString() + "/authenticate/", accountForm, Token.class);
            return response.getStatusCode() == HttpStatus.OK ? response.getBody().getToken() : null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

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
        try
        {
            ResponseEntity<User> response = restTemplate.exchange(serviceUrl.toString() + "/authenticated/",
                    HttpMethod.GET, entity, User.class);
            return response.getStatusCode().equals(HttpStatus.OK) ? response.getBody() : null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Sets the url of the authentication micro service
     *
     * @param serviceUrl The url of the authentication micro service.
     */
    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    private class AccountForm {

        private String username;
        private String password;
        private String repeatedPassword;

        /**
         * Default constructor
         */
        public AccountForm() {}

        /**
         * Gets username from the account form.
         *
         * @return The username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username of the account form.
         *
         * @param username The new username.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Gets the password of the account form.
         *
         * @return The password.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the password of the account form.
         *
         * @param password The password.
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Gets the repeated password of the account form.
         *
         * @return The repeated password.
         */
        public String getRepeatedPassword() {
            return repeatedPassword;
        }

        /**
         * Sets the repeated password of the account form.
         *
         * @param repeatedPassword The repeated password.
         */
        public void setRepeatedPassword(String repeatedPassword) {
            this.repeatedPassword = repeatedPassword;
        }
    }
}