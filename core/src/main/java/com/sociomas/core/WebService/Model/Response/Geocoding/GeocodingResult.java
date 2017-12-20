package com.sociomas.core.WebService.Model.Response.Geocoding;

import java.io.Serializable;

/**
 * Created by oemy9 on 01/09/2017.
 */

public class GeocodingResult implements Serializable {
        private double[] currentLocation;
        private String directionName;

    public GeocodingResult(double[] currentLocation, String directionName) {
        this.currentLocation = currentLocation;
        this.directionName=directionName;
    }

    public double[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(double[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }
}
