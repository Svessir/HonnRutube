/*************************************************************************************************
 *
 * VideoValidator.java - The VideoValidator class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.videoservice.validator;

import is.ru.honn.rutube.videoservice.domain.Video;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Validator that validates videos.
 * Video is valid if:
 * - title length atleast 4 and no longer than 100.
 * - url is a valid url.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class VideoValidator implements Validator<Video> {

    private Video video;

    /**
     * @param video The video being validated.
     */
    public VideoValidator(Video video) {
        this.video = video;
    }

    /**
     * Validates the object set to this validator.
     *
     * @throws ValidationException if object is not valid.
     */
    @Override
    public void validate() throws ValidationException {
        try
        {
            if(video == null)
                throw new ValidationException("No video provided.");

            String title = video.getTitle();
            if(title == null || title.length() < 4)
                throw new ValidationException("Video title is to short: minimum length is 4.");
            if(title.length() > 100)
                throw new ValidationException("Video title is to long: maximum length is 100.");

            URL url = new URL(video.getUrl());
        }
        catch (MalformedURLException muex)
        {
            throw new ValidationException("Video url is not a valid url.");
        }
    }

    /**
     * Sets the video to validate.
     *
     * @param object The video to validate.
     */
    @Override
    public void setObject(Video object) {
        video = object;
    }
}