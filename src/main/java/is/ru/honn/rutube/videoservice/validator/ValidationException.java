/*************************************************************************************************
 *
 * ValidationException.java - The ValidationException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

/**
 * Exception dedicated to validator errors.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public class ValidationException extends RuntimeException {

    /**
     * @param message The error message.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}