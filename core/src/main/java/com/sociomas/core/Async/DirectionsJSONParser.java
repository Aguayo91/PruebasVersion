package com.sociomas.core.Async;

import com.google.android.gms.maps.model.LatLng;
import com.sociomas.core.WebService.Model.Response.Directions.DirectionResponse;
import com.sociomas.core.WebService.Model.Response.Directions.Leg;
import com.sociomas.core.WebService.Model.Response.Directions.Route;
import com.sociomas.core.WebService.Model.Response.Directions.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anupamchugh on 27/11/15.
 */

public class DirectionsJSONParser {

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(DirectionResponse directionResponse){

             List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
            for(Route r: directionResponse.getRoutes()){
                List path = new ArrayList<HashMap<String, String>>();
                for(Leg l: r.getLegs()){
                    for(Step s: l.getSteps()){
                        List list = decodePoly(s.getPolyline().getPoints());
                        for(int i=0;i <list.size();i++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(i)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(i)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
        return routes;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}