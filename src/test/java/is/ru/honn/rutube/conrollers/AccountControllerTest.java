package is.ru.honn.rutube.conrollers;

import is.ru.honn.rutube.RuTubeApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Sverrir on 26.10.2016.
 */
public class AccountControllerTest {
    ConfigurableApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.context = SpringApplication.run(RuTubeApplication.class, new String[] {});
    }

    @After
    public void tearDown() throws Exception {
        SpringApplication.exit(context);
    }

    @Test
    public void signUp() throws Exception {

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