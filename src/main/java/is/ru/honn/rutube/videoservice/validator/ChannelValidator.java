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
 * Channel is valid if:
 * - channel name is not null.
 * - channel name is 4 or more letters,
 * - channel name is no more than 32 letters
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
     * @throws ValidationException if channel is not valid.
     */
    @Override
    public void validate() throws ValidationException {
        if(channel == null)
            throw new ValidationException("No channel provided");

        String channelName = channel.getChannelName();

        if(channelName == null || channelName.length() < 4)
            throw new ValidationException("channelName is to short: minimum length 4");
        if(channelName.length() > 32)
            throw new ValidationException("channelName is to long: maximum length 100");
    }

    /**
     * Sets the channel to validate.
     *
     * @param object The channel to validate.
     */
    @Override
    public void setObject(Channel object) {
        channel = object;
    }
}