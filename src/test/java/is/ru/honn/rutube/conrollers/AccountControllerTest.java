/*************************************************************************************************
 *
 * AccountControllerTest.java - The AccountControllerTest class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.conrollers;

import java.net.URL;
import is.ru.honn.rutube.accountservice.domain.Account;
import is.ru.honn.rutube.accountservice.domain.AccountRegistration;
import is.ru.honn.rutube.accountservice.dto.PartialAccountDTO;
import is.ru.honn.rutube.accountservice.dto.TokenDTO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Integration test for the account controller.
 *
 * @author Sverrir
 * @version 1.0, 28 okt. 2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountControllerTest implements ApplicationContextAware{

    private static URL base;

    private static DataSource dataSource;

    private TestRestTemplate template = new TestRestTemplate();

    private JdbcTemplate jdbcTemplate;

    private static boolean isSetupDone;

    private static String token;

    private static HttpHeaders httpHeaders;

    private static String currentUsername;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @AfterClass
    public static void cleanDatabase() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // Clean test database before integration test.
        template.update("DELETE Account;");
    }

    /**
     * Set up url and clean database before first test.
     *
     * @throws Exception if setup fails.
     */
    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8080 + "/account/");

        if(!isSetupDone)
            cleanDatabase();
        isSetupDone = true;
    }

    /**
     * Tests if adding a valid new user to database works correctly.
     *
     * @throws Exception On failed test.
     */
    @Test
    public void stage1_signUp() throws Exception {
        String sql = "SELECT * FROM Account A WHERE A.username = 'TestUser';";
        Account account;

        try
        {
            // Verify that the account we are about to sign up doesn't exist in database.
            account = (Account) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(Account.class));
            fail("Should have thrown EmptyResultDataAccessException");
        }
        catch (Exception e)
        {
            // Verify that we got the right exception.
            assertEquals(EmptyResultDataAccessException.class, e.getClass());
        }

        // Sign the user up.
        ResponseEntity response = template.postForEntity(base.toString() + "signup/",
                new AccountRegistration("TestUser", "password3", "password3"), String.class);

        // Verify that we got a success status code
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        // Verify that the account has been added to the database.
        account = (Account) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(Account.class));

        // Verify the username of the account is correct.
        assertEquals("TestUser", account.getUsername());

        // Set the current username as the signed up username.
        currentUsername = "TestUser";

        // Sign up a user with the same username.
        response = template.postForEntity(base.toString() + "signup/",
                new AccountRegistration("TestUser", "password4", "password4"), String.class);

        // Verify that we got a conflict status.
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Test if authentication works correctly.
     *
     * @throws Exception On failed test.
     */
    @Test
    public void stage2_authenticate() throws Exception {
        // Authenticate the user
        ResponseEntity response = template.postForEntity(base.toString() + "authenticate/",
                new Account("TestUser", "password3"), TokenDTO.class);

        // Verify that the authentication status code is correct.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the token has been received.
        assertNotNull(response.getBody());

        // Extract the token
        token = ((TokenDTO)response.getBody()).getToken();
    }

    /**
     * Tests if user is authenticated after assigning the token.
     *
     * @throws Exception If test fails.
     */
    @Test
    public void stage3_isAuthenticated() throws Exception {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Token", token);
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

        // Check if user is authenticated
        ResponseEntity response = template.exchange(base.toString() + "authenticated/", HttpMethod.GET,
                httpEntity, PartialAccountDTO.class);

        // Verify that we get a success status code.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the authentication service returned the right user information.
        assertEquals(currentUsername, ((PartialAccountDTO)response.getBody()).getUsername());
    }

    /**
     * Tests if account information update works correctly.
     *
     * @throws Exception if test fails.
     */
    @Test
    public void stage4_update() throws Exception {
        // fill up an update form and pass in the authentication token to http headers of the request.
        HttpEntity<AccountRegistration> httpEntity =
                new HttpEntity<AccountRegistration>(new AccountRegistration("TestUser1", "password3", "password3"), httpHeaders);

        // Send an update request to the server to update the user information.
        ResponseEntity response = template.exchange(base.toString() + "update/", HttpMethod.PUT,
                httpEntity, TokenDTO.class);

        // Assert that the operation was successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Use the new token for the updated account information.
        token = ((TokenDTO)response.getBody()).getToken();

        // Set the current username as the updated username.
        currentUsername = "TestUser1";

        // Run is_authenticated test again and verify that the updated account information does work.
        stage3_isAuthenticated();
    }

    /**
     * Tests if user deletion works correctly.
     *
     * @throws Exception If test fails.
     */
    @Test
    public void stage5_delete() throws Exception {
        // Fill up an object that includes a username field, userId does not matter.
        HttpEntity<PartialAccountDTO> httpEntity =
                new HttpEntity<PartialAccountDTO>(new PartialAccountDTO(1, currentUsername));

        // Send delete request to the server
        ResponseEntity<String> response = template.exchange(base.toString() + "delete/", HttpMethod.DELETE,
                httpEntity, String.class);

        // Assert that we got a success response.
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Send the same deletion request again to the server.
        response = template.exchange(base.toString() + "delete/", HttpMethod.DELETE,
                httpEntity, String.class);

        // Assert that we got a 404 Not Found status code.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}