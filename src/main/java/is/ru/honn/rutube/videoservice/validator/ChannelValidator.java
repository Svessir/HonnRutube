/*************************************************************************************************
 *
 * ChannelValidator.java - The ChannelValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

import is.ru.honn.rutube.videoservice.domain.Channel;

/**
 * Validator that validates video channels.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class ChannelValidator implements Validator<Channel> {

    private Channel channel;

    /**
     * @param channel The channel being validated by this validator.
     */
    public ChannelValidator(Channel channel) {
        this.channel = channel;
    }

    /**
     * Validates the channel set to this validator.
     *
     * @return true if the channel is valid else false.
     */
    @Override
    public boolean validate() {
        return true;
    }
}