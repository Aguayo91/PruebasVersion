package com.sociomas.core.Async;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sociomas.core.Listeners.RouteCompletedListener;
import com.sociomas.core.WebService.Model.Response.Directions.DirectionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oemy9 on 29/08/2017.
 */

public class ParserTask extends AsyncTask<DirectionResponse, Integer, List<List<HashMap<String, String>>>>
{
        private RouteCompletedListener callBackRoute;
        private GoogleMap mMap;
        private Context context;
        public ParserTask(Context context, GoogleMap mMap) {

            this.context=context;
            this.mMap=mMap;
        }

        public void setCallBackRoute(RouteCompletedListener callBackRoute) {
            this.callBackRoute = callBackRoute;
        }

    // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(DirectionResponse... directionResponse) {
            List<List<HashMap<String, String>>> routes = null;
            try {
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(directionResponse[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    builder.include(position);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);
            }
            if(lineOptions!=null) {
                callBackRoute.onRouteSuccess(true);
                mMap.addPolyline(lineOptions);
                LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        }
    }


