/*************************************************************************************************
 *
 * AccountServiceException.java - The AccountServiceException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services.account;

/**
 * Exception dedicated to account service exceptions.
 *
 * @author Sverrir
 * @version 1.0, 27 okt. 2016
 */
public class AccountServiceException extends RuntimeException {

    private AccountServiceErrorCode errorCode;

    /**
     * @param message The error message.
     */
    public AccountServiceException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param errorCode An error code to further identify the cause.
     */
    public AccountServiceException(String message, AccountServiceErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     * @param errorCode An error code to further identify the cause.
     */
    public AccountServiceException(String message, Throwable cause, AccountServiceErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code of this exception.
     *
     * @return The error code.
     */
    public AccountServiceErrorCode getErrorCode() {
        return errorCode;
    }
}