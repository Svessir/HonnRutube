/*************************************************************************************************
 *
 * AccountServiceException.java - The AccountServiceException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

/**
 * Exception dedicated to account service exceptions.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public class AccountServiceException extends RuntimeException {

    /**
     * @param message The error message.
     */
    public AccountServiceException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}