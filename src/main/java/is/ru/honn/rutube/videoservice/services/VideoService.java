/*************************************************************************************************
 *
 * VideoService.java - The VideoService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.services;

import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;

import java.util.List;

/**
 * API for a video service
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public interface VideoService {

    /**
     * Gets all RuTube videos.
     *
     * @return list of all videos.
     */
    List<Video> getAllVideos();

    /**
     * Gets all videos for specific channel
     *
     * @param channelName The name of the channel.
     * @return List of all videos in the channel.
     * @throws VideoServiceException if request failed.
     */
    List<Video> getAllVideosForChannel(String channelName) throws VideoServiceException;

    /**
     * Adds video to to a video channel.
     *
     * @param channelName The name of the channel.
     * @param videoId The id of the video being added to the channel.
     * @throws VideoServiceException If adding the video fails.
     */
    void addExistingVideoToChannel(String channelName, int videoId) throws VideoServiceException;

    /**
     * Removes a video from the system.
     *
     * @param videoId The id of the video being removed.
     * @throws VideoServiceException if video doesn't exist.
     */
    void removeVideo(int videoId) throws VideoServiceException;

    /**
     * Gets a single video by a video id.
     *
     * @param videoId The id of the video.
     * @return The Video.
     */
    Video getVideo(int videoId);

    /**
     * Gets a channel by name.
     *
     * @param channelName The name of the channel.
     * @return the Channel
     */
    Channel getChannel(String channelName);


    /**
     * Adds a channel to RuTube.
     *
     * @param channel The channel being added.
     * @throws VideoServiceException If creating a channel fails.
     */
    void addChannel(Channel channel) throws VideoServiceException ;

    /**
     * Adds a new video to a channel.
     *
     * @param channelName The name of the channel.
     * @param video The new video.
     * @throws VideoServiceException If adding the video fails.
     */
    void addNewVideoToChannel(String channelName, Video video) throws VideoServiceException;
}