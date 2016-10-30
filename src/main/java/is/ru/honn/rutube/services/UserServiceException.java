/*************************************************************************************************
 *
 * UserServiceException.java - The UserServiceException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

/**
 * Component description
 *
 * @author Kári
 * @version 1.0, 29 okt. 2016
 */
public class UserServiceException extends Exception {
    /**
     *
     * @param message The error message.
     */
    public UserServiceException(String message){super(message);}

    /**
     *
     * @param message The error message.
     * @param cause The nested exception.
     */
    public UserServiceException(String message, Throwable cause){ super(message, cause );}
}