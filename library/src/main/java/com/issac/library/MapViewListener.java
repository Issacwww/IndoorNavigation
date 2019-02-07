package com.issac.library;

/**
 * MapViewListener
 *
 * @author: Randolph
 */
public interface MapViewListener {

    /**
     * when mapview load complete to callback
     */
    void onMapLoadSuccess();

    /**
     * when mapview load error to callback
     */
    void onMapLoadFail();
}
