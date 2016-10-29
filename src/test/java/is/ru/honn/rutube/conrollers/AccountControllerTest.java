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

import is.ru.honn.rutube.domain.account.AccountRegistration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Integration test for the account controller.
 *
 * @author Sverrir
 * @version 1.0, 28 okt. 2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountControllerTest {

    @LocalServerPort
    private static int port;

    private static URL base;

    @Autowired
    private TestRestTemplate template = new TestRestTemplate();

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8080 + "/account/");
    }

    @Test
    public void signUp() throws Exception {
        //RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = template.postForEntity(base.toString() + "signup/",
                new AccountRegistration("Sverrir", "password3", "password3"), void.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void authenticate() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void isAuthenticated() throws Exception {

    }

}