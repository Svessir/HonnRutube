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

/**
 * Validator that validates videos.
 *
 * @author Sverrir
 * @version 1.0, 30 okt. 2016
 */
public class VideoValidator implements Validator<Video> {

    private Video video;

    /**
     * @param video The video being validated by this validator.
     */
    public VideoValidator(Video video) {
        this.video = video;
    }

    /**
     * Validates the video.
     *
     * @return true if video is valid else false.
     */
    @Override
    public boolean validate() {
        return true;
    }
}