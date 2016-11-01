/*************************************************************************************************
 *
 * UserDataGatewayException.java - The UserDataGatewayException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.userservice.data;

/**
 * An Exception class dedicated for UserDataGateway errors.
 *
 * @author Kári
 * @version 1.0, 27 okt. 2016
 */
public class UserDataGatewayException extends Exception{

    /**
     *
     * @param message The error message.
     */
    UserDataGatewayException(String message){ super(message);}

    /**
     *
     * @param message The error message.
     * @param cause The nested exception.
     */
    UserDataGatewayException(String message, Throwable cause){ super(message, cause);}
}