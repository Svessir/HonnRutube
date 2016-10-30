/*************************************************************************************************
 *
 * UserControllerTest.java - The UserControllerTest class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.clients.authentication.AuthenticationClient;
import is.ru.honn.rutube.userservice.domain.UserProfile;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;
import java.net.URL;

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

    private JdbcTemplate jdbcTemplate;

    private static boolean isSetupDone;

    private static HttpHeaders httpHeaders;

    private static AuthenticationClient authenticationClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        authenticationClient = (AuthenticationClient) applicationContext.getBean("authenticationClient");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterClass
    public static void cleanDataBases(){
        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.update("DELETE Favorites;");
        template.update("DELETE Friends;");
        template.update("DELETE UserProfile;");
    }

    public static void signIn() throws Exception {
        boolean success = authenticationClient.signUp("TestUser", "12345", "12345");

        if(!success)
            throw new Exception("Singing up test user failed.");

        String token = authenticationClient.logIn("TestUser", "12345");

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
        ResponseEntity response = template.exchange(base.toString() + "profile",
                HttpMethod.GET, httpEntity, String.class);
        System.out.println(response.getStatusCode().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TestUser", ((UserProfile)response.getBody()).getUsername());
    }

    /**
     *
     */
    @Test
    public void stage2_deleteUserTest(){

    }

    /**
     *
     */
    @Test
    public void stage3_addVideoToFavoritesTest(){

    }

    /**
     *
     */
    @Test
    public void stage4_deleteVideoFromFavoritesTest(){

    }

    /**
     *
     */
    @Test
    public void stage5_addCloseFriendTest(){

    }

    /**
     *
     */
    @Test
    public void stage6_deleteCloseFriendTest(){

    }
}