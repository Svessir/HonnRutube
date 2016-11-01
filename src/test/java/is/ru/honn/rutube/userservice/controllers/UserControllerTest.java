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
        videoServiceClient = (VideoServiceClient) applicationContext.getBean("videoServiceClient");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterClass
    public static void cleanDataBases(){
        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.update("DELETE Favorites;");
        template.update("DELETE Friends;");
        template.update("DELETE UserProfile;");
        template.update("DELETE Account;");
        template.update("DELETE Video");
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

    public static void setUpDatabase(){
        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.update("INSERT INTO Video VALUES ('honn16', 'fyrirlestur', 'http://myschool.com/honn16', 50, 15)");
        template.update("INSERT INTO UserProfile VALUES (1000)");
        template.update("INSERT INTO UserProfile VALUES (1001)");

    }

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8080 + "/user/");

        if(!isSetupDone) {
            cleanDataBases();
            signIn();
            setUpDatabase();
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

        response = template.getForEntity(base.toString() + "profile/", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not logged in.", response.getBody().toString());
    }

    /**
     *
     */
    @Test
    public void stage2_deleteUserTest(){
        int user = 1000;
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
        int video = videoServiceClient.getAllVideos(token).get(0).getVideoId();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.postForEntity(base.toString() + "favorite/" + video,
                                                            httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        response = template.postForEntity(base.toString() + "favorite/" + video,
                httpEntity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Duplicate add.", response.getBody().toString());
    }

    /**
     *
     */
    @Test
    public void stage4_deleteVideoFromFavoritesTest(){
        int video = videoServiceClient.getAllVideos(token).get(0).getVideoId();
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "favorite/" + video,
                                                        HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     *
     */
    @Test
    public void stage5_addCloseFriendTest(){
        int friend = 1001;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.postForEntity(base.toString() + "friends/" + friend,
                                                            httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        response = template.postForEntity(base.toString() + "friends/" + friend,
                httpEntity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Duplicate add.", response.getBody().toString());
        int notFriend = 0;
        response = template.postForEntity(base.toString() + "friends/" + notFriend,
                httpEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Friend not found", response.getBody().toString());
    }

    /**
     *
     */
    @Test
    public void stage6_deleteCloseFriendTest(){
        int friend = 1001;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity response = template.exchange(base.toString() + "friends/" + friend,
                                                        HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Friend was successfully removed from favorites.", response.getBody().toString());
    }
}