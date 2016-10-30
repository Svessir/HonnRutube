/*************************************************************************************************
 *
 * AccountDataGatewayException.java - The AccountDataGatewayException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.accountservice.data;

/**
 * An Exception class dedicated to AccountDataGateway errors.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class AccountDataGatewayException extends Exception {

    /**
     * @param message The error message.
     */
    public AccountDataGatewayException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public AccountDataGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}