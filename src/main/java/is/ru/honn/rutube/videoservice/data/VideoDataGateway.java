/*************************************************************************************************
 *
 * VideoDataGateway.java - The VideoDataGateway class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.data;

import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;

import java.util.List;

/**
 * API for a video data gateway.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public interface VideoDataGateway {

    /**
     * Gets all videos on RuTube.
     *
     * @return list of all videos.
     */
    List<Video> getAllVideos();

    /**
     * Gets all videos for a given channel.
     *
     * @param channelId The id of the channel.
     * @return List of all videos in channel.
     */
    List<Video> getVideosByChannelId(int channelId);

    /**
     * Creates a relation in database between a video and channel.
     *
     * @param videoId The id of the video.
     * @param channelId The id of the channel.
     * @throws VideoDataGatewayException If relation already exist or if the video
     *                                   or the channel doesn't exist
     */
    void addVideoToChannelRelation(int videoId, int channelId) throws VideoDataGatewayException;

    /**
     * Deletes a video from the database.
     *
     * @param videoId The id of the video.
     */
    void deleteVideo(int videoId);

    /**
     * Gets a single video from database.
     *
     * @param videoId The id of the video.
     * @return The video if exists else null.
     */
    Video getVideoById(int videoId);

    /**
     * Gets a single channel from database.
     *
     * @param channelName The name of the channel.
     * @return The channel if exists else null.
     */
    Channel getChannelByName(String channelName);


    /**
     * Adds a channel to the database.
     *
     * @param channel The channel being added.
     * @throws VideoDataGatewayException if the video already exists.
     */
    void addChannel(Channel channel) throws VideoDataGatewayException;

    /**
     * Adds a video to database.
     *
     * @param video The video being added.
     * @return The video that was added.
     */
    Video addVideo(Video video);
}