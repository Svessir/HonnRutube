/*************************************************************************************************
 *
 * VideoDataGatewayException.java - The VideoDataGatewayException class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.data;

/**
 * Exception dedicated to video data gateway errors.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class VideoDataGatewayException extends RuntimeException {

    /**
     * @param message The error message.
     */
    public VideoDataGatewayException(String message) {
        super(message);
    }

    /**
     * @param message The error message.
     * @param cause The nested exception.
     */
    public VideoDataGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

}