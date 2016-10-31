/*************************************************************************************************
 *
 * Channel.java - The Channel class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.domain;


import is.ru.honn.rutube.videoservice.validator.*;

import java.util.ArrayList;
import java.util.List;

/**
 * RuTube video channel domain object.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class Channel extends AbstractValidatableObject implements Validatable {

    private int channelId;
    private String channelName;

    private List<Validator> validators = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Channel() {}

    /**
     * @param channelName The name of the channel.
     */
    public Channel(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Initializes this channel with it's default validators.
     */
    @Override
    public void initialize() {
        cleanValidators();
        addValidator(new ChannelValidator(this));
    }

    /**
     * Gets the channel id.
     *
     * @return the id.
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * Sets the channel id.
     *
     * @param channelId The id being set.
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     * Gets the name of the channel.
     *
     * @return The channel name.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Sets the name of the channel.
     *
     * @param channelName The name being set.
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}