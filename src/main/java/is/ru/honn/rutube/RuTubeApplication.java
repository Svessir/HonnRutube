/*************************************************************************************************
 *
 * RuTubeApplication.java - The RuTubeApplication class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The class that starts up the
 * Rutube application.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
@SpringBootApplication
public class RuTubeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuTubeApplication.class, args);
    }
}