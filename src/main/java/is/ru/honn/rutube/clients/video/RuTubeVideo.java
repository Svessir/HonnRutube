/*************************************************************************************************
 *
 * RuTubeVideo.java - The RuTubeVideo class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.clients.video;

/**
 * RuTube Video provided used by
 * the video service client.
 *
 * @author Sverrir
 * @version 1.0, 31 okt. 2016
 */
public class RuTubeVideo {

    private int videoId;
    private String title;
    private String description;
    private String url;
    private int viewCount;
    private int numberOfLikes;

    /**
     * Gets the id of the video.
     *
     * @return the id.
     */
    public int getVideoId() {
        return videoId;
    }

    /**
     * Sets the video id.
     *
     * @param videoId The id being set.
     */
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    /**
     * Gets the title of the video.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the video title.
     *
     * @param title The title being set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the video description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the video.
     *
     * @param description The description being set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the url of the video.
     *
     * @return the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of the video.
     *
     * @param url The url being set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the viewCount of the video.
     *
     * @return the view count.
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * Sets the view count of the video.
     *
     * @param viewCount the view count being set.
     */
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * Gets the number of likes.
     *
     * @return the number of likes.
     */
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * Sets the number of likes.
     *
     * @param numberOfLikes The number of likes being set.
     */
    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
}