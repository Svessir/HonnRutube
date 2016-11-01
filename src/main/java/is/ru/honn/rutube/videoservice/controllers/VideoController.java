/*************************************************************************************************
 *
 * VideoController.java - The VideoController class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.controllers;

import is.ru.honn.rutube.clients.authentication.AuthenticationClient;
import is.ru.honn.rutube.clients.authentication.User;
import is.ru.honn.rutube.videoservice.domain.Channel;
import is.ru.honn.rutube.videoservice.domain.Video;
import is.ru.honn.rutube.videoservice.services.VideoService;
import is.ru.honn.rutube.videoservice.services.VideoServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful video controller.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    private VideoService videoService;
    private AuthenticationClient authenticationClient;

    /**
     * @param videoService The video service.
     * @param authenticationClient A client to talk to RuTube's authentication service.
     */
    @Autowired
    public VideoController(VideoService videoService, AuthenticationClient authenticationClient) {
        this.videoService = videoService;
        this.authenticationClient = authenticationClient;
    }

    /**
     * Gets all RuTube's videos.
     *
     * @param token The authentication token.
     * @return A list of all Videos on RuTube.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getAllVideos(@RequestHeader(name = "Token", required = false) String token) {
        User user = authenticationClient.getLoggedInUser(token);

        if(user != null)
        {
            List<Video> videos = videoService.getAllVideos();
            return new ResponseEntity<>(videos, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Gets all videos assigned to a specific video channel.
     *
     * @param token The authentication token.
     * @param channelName The name of the channel, videos are being queried for.
     * @return A list of all videos on the channel.
     */
    @RequestMapping(value = "/channel/{channelName}", method = RequestMethod.GET)
    ResponseEntity getAllVideosForChannel(@RequestHeader(name = "Token", required = false) String token,
                                          @PathVariable("channelName") String channelName) {
        if(authenticationClient.getLoggedInUser(token) == null)
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);

        try
        {
            return new ResponseEntity<>(videoService.getAllVideosForChannel(channelName), HttpStatus.OK);
        }
        catch (VideoServiceException vsex)
        {
            switch (vsex.getErrorCode()) {
                case NOT_FOUND:
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Adds a video to a channel.
     *
     * @param token The authentication token.
     * @param channelName The name of the channel the video is being assigned to.
     * @param videoId The id of the video being added to a channel.
     * @return 200 OK if the video was successfully added to the channel, 401 UNAUTHORIZED if the user is unauthorized,
     *         409 CONFLICT on database conflict else 400 BAD REQUEST.
     */
    @RequestMapping(value = "/channel/{channelName}/{videoId}", method = RequestMethod.POST)
    ResponseEntity addVideoToChannel(@RequestHeader(name = "Token", required = false) String token,
                                     @PathVariable String channelName,
                                     @PathVariable int videoId) {
        if(authenticationClient.getLoggedInUser(token) == null)
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);

        try
        {
            videoService.addExistingVideoToChannel(channelName, videoId);
            return new ResponseEntity<>("Video successfully added.", HttpStatus.CREATED);
        }
        catch (VideoServiceException vsex)
        {
            switch (vsex.getErrorCode()) {
                case CONFLICT:
                    return new ResponseEntity<>(vsex.getMessage(), HttpStatus.CONFLICT);
                default:
                    return new ResponseEntity<>(vsex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Removes a video from RuTube.
     *
     * @param token The authentication token.
     * @param videoId The video being removed from the channel.
     * @return 200 OK if the deletion was successful, 401 UNAUTHORIZED if user is not authenticated,
     *         404 NOT FOUND if the video does not exist in channel else 400 BAD REQUEST.
     */
    @RequestMapping(value = "/{videoId}", method = RequestMethod.DELETE)
    ResponseEntity removeVideo(@RequestHeader(name = "Token", required = false) String token,
                               @PathVariable int videoId) {
        if(authenticationClient.getLoggedInUser(token) == null)
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);

        try
        {
            videoService.removeVideo(videoId);
            return new ResponseEntity<>("Video successfully removed", HttpStatus.OK);
        }
        catch (VideoServiceException vsex)
        {
            switch (vsex.getErrorCode()) {
                case NOT_FOUND:
                    return new ResponseEntity<>("No video found with that id.", HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Adds a channel to RuTube.
     *
     * @param channel The channel being added.
     * @return 201 CREATED if successful, 401 UNAUTHORIZED if user is not authenticated,
     *         409 CONFLICT if the channel already exists else 400 BAD REQUEST.
     */
    @RequestMapping(value = "/channel", method = RequestMethod.POST)
    ResponseEntity addChannel(@RequestBody Channel channel) {

        try
        {
            videoService.addChannel(channel);
            return new ResponseEntity<>("Channel " + channel.getChannelName() +
                    " successfully created", HttpStatus.CREATED);
        }
        catch (VideoServiceException vsex)
        {
            switch (vsex.getErrorCode())
            {
                case CONFLICT:
                    return new ResponseEntity<>(vsex.getMessage(), HttpStatus.CONFLICT);
                default:
                    return new ResponseEntity<>(vsex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Adds a new video to channel.
     *
     * @param token The authentication token.
     * @param channelName The name of the channel.
     * @param video The video being added.
     * @return 201 CREATED if successful, 401 UNAUTHORIZED if user is not authenticated,
     *         409 CONFLICT if video is already added to channel else 400 BAD REQUEST.
     */
    @RequestMapping(value = "/channel/{channelName}", method = RequestMethod.POST)
    ResponseEntity addVideoToChannel(@RequestHeader(name = "Token", required = false) String token,
                                     @PathVariable String channelName,
                                     @RequestBody Video video) {
        if(authenticationClient.getLoggedInUser(token) == null)
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);
        try
        {
            videoService.addNewVideoToChannel(channelName, video);
            return new ResponseEntity<>("Video successfully added to channel " + channelName + ".", HttpStatus.CREATED);
        }
        catch (VideoServiceException vsex)
        {
            switch (vsex.getErrorCode())
            {
                case NOT_FOUND:
                    return new ResponseEntity<>("Channel " + channelName + " doesn't exist", HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity<>(vsex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }



    /**
     * Get a single video.
     *
     * @param token The authentication token.
     * @param videoId The id of the video being retrieved.
     * @return 200 OK with a video if success, 401 UNAUTHORIZED in user is unauthorized,
     *         404 NOT FOUND if video doesn't exist else BAD REQUEST.
     */
    @RequestMapping(value = "/{videoId}", method = RequestMethod.GET)
    ResponseEntity getVideo(@RequestHeader(name = "Token", required = false) String token,
                            @PathVariable int videoId) {
        if(authenticationClient.getLoggedInUser(token) == null)
            return new ResponseEntity<>("You are not authorized.", HttpStatus.UNAUTHORIZED);
        Video video = videoService.getVideo(videoId);
        return video != null ? new ResponseEntity<>(video, HttpStatus.OK): new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}