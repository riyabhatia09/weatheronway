package edu.buffalo.riyabhat.Controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import edu.buffalo.riyabhat.Model.City;
import edu.buffalo.riyabhat.Utility.DatabaseUtils;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
public class DirectionsAPI {

    @RequestMapping("/getdirections")
    public String getDirections(@RequestParam(value="sname", defaultValue = "") String source,
                                @RequestParam(value="dname", defaultValue = "") String destination) throws InterruptedException, ApiException, IOException, JSONException, APIException {

        long start = System.currentTimeMillis() % 1000;
        long end;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<City> cities = DatabaseUtils.getFromDatabases(source, destination);
        if(cities!= null) {
            end = System.currentTimeMillis() % 1000;
            System.out.println("Exists in Database!! \n Time Taken = "+(end-start));
            return gson.toJson(cities);
        }
        cities = new ArrayList<>();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDU-o1JsroQ7XcevIJdEXNBLqnAjS4ulFY")
                .build();
        OWM owm = new OWM("7bae27c4ae600dd3a0628490778af00e");

        List<LatLng> latLngList = null;
        DirectionsStep[] steps;
        String cityName;
        City city;
        DirectionsResult result;

        final DirectionsApiRequest request = DirectionsApi.getDirections(context, source, destination);
        try {
            result = request.await();
            steps =  result.routes[0].legs[0].steps;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        Set<String> citySet = new LinkedHashSet<String>();

        for (int i=0;i<steps.length;i++) {
            LatLng location = new LatLng(steps[i].startLocation.lat,steps[i].startLocation.lng);
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();
            for (AddressComponent ac : results[0].addressComponents) {
                for (AddressComponentType acType : ac.types) {
                    try{
                        if (acType == AddressComponentType.LOCALITY) {
                            cityName = ac.longName;
                            if (citySet.isEmpty() || (!citySet.contains(cityName))) {
                                CurrentWeather cwd = owm.currentWeatherByCityName(cityName.toString());
                                if (cwd.hasRespCode() && cwd.getRespCode() == 200) {
                                    if (cwd.hasMainData() && cwd.hasWeatherList() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                                        city = new City(location.lat, location.lng, cityName, cwd.getMainData().getTempMin(), cwd.getMainData().getTempMax(), cwd.getWeatherList().get(0).getDescription());
                                        cities.add(city);
                                    }
                                }
                            }
                            citySet.add(cityName);
                        }
                    }
                    catch(Exception e){

                    }
                }

            }
        }

        DatabaseUtils.persistToDatabase(cities,source,destination);
        end = System.currentTimeMillis() % 1000;
        System.out.println("Does not exist in Database! \n Time Taken:"+(end-start));
        return gson.toJson(cities);
    }
}