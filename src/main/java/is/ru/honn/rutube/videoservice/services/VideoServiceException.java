/*************************************************************************************************
 *
 * VideoServiceException.java - The VideoServiceException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.services;

/**
 * Exception dedicated to video service errors.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class VideoServiceException extends RuntimeException {
    private VideoServiceErrorCode errorCode;

    /**
     * @param message The error message.
     */
    public VideoServiceException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param errorCode An error code to further identify the cause.
     */
    public VideoServiceException(String message, VideoServiceErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public VideoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     * @param errorCode An error code to further identify the cause.
     */
    public VideoServiceException(String message, Throwable cause, VideoServiceErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code of this exception.
     *
     * @return The error code.
     */
    public VideoServiceErrorCode getErrorCode() {
        return errorCode;
    }
}