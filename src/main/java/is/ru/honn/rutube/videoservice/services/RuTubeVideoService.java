/*************************************************************************************************
 *
 * RuTubeVideoService.java - The RuTubeVideoService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.services;

import is.ru.honn.rutube.videoservice.data.VideoDataGateway;
import is.ru.honn.rutube.videoservice.data.VideoDataGatewayException;
import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;

import java.util.List;

/**
 * RuTube's video service implementation.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class RuTubeVideoService implements VideoService {

    private VideoDataGateway videoDataGateway;

    /**
     * Gets all RuTube videos.
     *
     * @return list of all videos.
     */
    @Override
    public List<Video> getAllVideos() {
        return videoDataGateway.getAllVideos();
    }

    /**
     * Gets all videos for specific channel
     *
     * @param channelName The name of the channel.
     * @return List of all videos in the channel.
     * @throws VideoServiceException if request failed.
     */
    @Override
    public List<Video> getAllVideosForChannel(String channelName) throws VideoServiceException {
        Channel channel = getChannel(channelName);

        if(channel == null)
            throw new VideoServiceException("Channel doesn't exist", VideoServiceErrorCode.NOT_FOUND);

        return videoDataGateway.getVideosByChannelId(channel.getChannelId());
    }

    /**
     * Adds video to to a video channel.
     *
     * @param channelName The name of the channel.
     * @param videoId The id of the video being added to the channel.
     * @throws VideoServiceException If adding the video fails.
     */
    @Override
    public void addVideoToChannel(String channelName, int videoId) throws VideoServiceException {
        try
        {
            Channel channel = getChannel(channelName);

            if(channel == null)
                throw new VideoServiceException("Channel doesn't exist",
                        VideoServiceErrorCode.NOT_FOUND);

            Video video = getVideo(videoId);

            if(video == null)
                throw new VideoServiceException("Video doesn't exist",
                        VideoServiceErrorCode.NOT_FOUND);


            videoDataGateway.addVideoToChannelRelation(videoId, channel.getChannelId());
        }
        catch (VideoDataGatewayException vdgex)
        {
            throw new VideoServiceException("This video has already been added to the channel",
                    VideoServiceErrorCode.CONFLICT);
        }
    }

    /**
     * Removes a video from the system.
     *
     * @param videoId The id of the video being removed.
     * @throws VideoServiceException if video doesn't exist.
     */
    @Override
    public void removeVideo(int videoId) throws VideoServiceException {
        Video video = getVideo(videoId);

        if(video == null)
            throw new VideoServiceException("Video doesn't exist", VideoServiceErrorCode.NOT_FOUND);

        videoDataGateway.deleteVideo(videoId);

    }

    /**
     * Gets a single video by id.
     *
     * @param videoId The id of the video.
     * @return The video.
     */
    @Override
    public Video getVideo(int videoId) {
        return videoDataGateway.getVideoById(videoId);
    }

    /**
     * Gets a channel by name.
     *
     * @param channelName The name of the channel.
     * @return the Channel if exists else null.
     */
    @Override
    public Channel getChannel(String channelName) {
        return videoDataGateway.getChannelByName(channelName);
    }


    /**
     * Sets the video data gateway for this service.
     *
     * @param videoDataGateway The video data gateway implementation.
     */
    public void setVideoDataGateway(VideoDataGateway videoDataGateway) {
        this.videoDataGateway = videoDataGateway;
    }
}