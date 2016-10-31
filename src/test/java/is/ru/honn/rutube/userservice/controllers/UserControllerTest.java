/*************************************************************************************************
 *
 * UserControllerTest.java - The UserControllerTest class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.controllers;

import is.ru.honn.rutube.clients.authentication.AuthenticationClient;
import is.ru.honn.rutube.clients.user.UserServiceClient;
import is.ru.honn.rutube.clients.video.VideoServiceClient;
import is.ru.honn.rutube.userservice.domain.UserProfile;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeansException;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration test for the user controller.
 *
 * @author Kári
 * @version 1.0, 30 okt. 2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest implements ApplicationContextAware {
    private static URL base;

    private static DataSource dataSource;

    private TestRestTemplate template = new TestRestTemplate();

    private static String token;

    private JdbcTemplate jdbcTemplate;

    private static boolean isSetupDone;

    private static HttpHeaders httpHeaders;

    private static AuthenticationClient authenticationClient;

    private static UserServiceClient userServiceClient;

    private static VideoServiceClient videoServiceClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        authenticationClient = (AuthenticationClient) applicationContext.getBean("authenticationClient");
        userServiceClient = (UserServiceClient) applicationContext.getBean("userServiceClient");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterClass
    public static void cleanDataBases(){
        JdbcTemplate template = new JdbcTemplate(dataSource);

        //template.update("DELETE Favorites;");
        //template.update("DELETE Friends;");
        //template.update("DELETE UserProfile;");
        template.update("DELETE Account;");
    }

    public static void signIn() throws Exception {
        boolean success = authenticationClient.signUp("TestUser", "12345", "12345");

        if(!success)
            throw new Exception("Signing up test user failed.");

        token = authenticationClient.logIn("TestUser", "12345");

        if(token == null)
            throw new Exception("Logging into test account failed.");

        httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
    }

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8080 + "/user/");

        if(!isSetupDone) {
            cleanDataBases();
            signIn();
        }

        isSetupDone = true;
    }


    /**
     *
     */
    @Test
    public void stage1_getUserTest(){
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "profile/",
                HttpMethod.GET, httpEntity, UserProfile.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TestUser", ((UserProfile)response.getBody()).getUsername());

    }

    /**
     *
     */
    @Test
    public void stage2_deleteUserTest(){
        int user = 1;
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "profile/" + user,
                                                        HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
     *
     */
    @Test
    public void stage3_addVideoToFavoritesTest(){
        int video = 5;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.postForEntity(base.toString() + "favoriteVideo/" + video,
                                                            httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        int video2 = 6;
        response = template.postForEntity(base.toString() + "favoriteVideo/" + video2,
                httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     *
     */
    @Test
    public void stage4_deleteVideoFromFavoritesTest(){
        int video = 5;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "favoriteVideo/" + video,
                                                        HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     *
     */
    @Test
    public void stage5_addCloseFriendTest(){
        int friend = 2;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.postForEntity(base.toString() + "closeFriends/" + friend,
                                                            httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        int friend2 = 3;
        response = template.postForEntity(base.toString() + "closeFriends/" + friend2,
                httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     *
     */
    @Test
    public void stage6_deleteCloseFriendTest(){
        int friend = 2;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "closeFriends/" + friend,
                                                        HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}