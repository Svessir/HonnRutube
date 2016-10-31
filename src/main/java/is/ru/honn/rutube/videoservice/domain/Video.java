/*************************************************************************************************
 *
 * Video.java - The Video class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.domain;

import is.ru.honn.rutube.videoservice.validator.AbstractValidatableObject;
import is.ru.honn.rutube.videoservice.validator.Validatable;
import is.ru.honn.rutube.videoservice.validator.Validator;
import is.ru.honn.rutube.videoservice.validator.VideoValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * RuTube video domain object.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class Video extends AbstractValidatableObject implements Validatable {

    private int videoId;
    private String title;
    private String description;
    private String url;
    private int viewCount;
    private int numberOfLikes;
    private List<Validator> validators = new ArrayList<Validator>();

    /**
     * Default constructor
     */
    public Video() {}

    /**
     * @param title video title.
     * @param description video description.
     * @param url video url.
     * @param viewCount video view count.
     * @param numberOfLikes video number of likes.
     */
    public Video(String title, String description, String url, int viewCount, int numberOfLikes) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.viewCount = viewCount;
        this.numberOfLikes = numberOfLikes;
    }

    /**
     * Initializes the video with it's default validators.
     */
    @Override
    public void initialize() {
        cleanValidators();
        addValidator(new VideoValidator(this));
    }

    /**
     * Gets the id of the video.
     * @return the video id.
     */
    public int getVideoId() {
        return videoId;
    }

    /**
     * Gets the id of the video.
     *
     * @param videoId The video id.
     */
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    /**
     * Gets the title of the video.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title of the video.
     *
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the video description.
     *
     * @return The video description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the video description.
     *
     * @param description The new video description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the url of the video.
     *
     * @return The url of the video.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of the video.
     *
     * @param url The new url of the video.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the view count of the video.
     *
     * @return The view count.
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * Sets the view count of the video.
     *
     * @param viewCount the new view count
     */
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * Gets the number of likes of the video.
     *
     * @return the number of likes.
     */
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * Set the number of likes of the video.
     *
     * @param numberOfLikes The new number of likes.
     */
    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
}