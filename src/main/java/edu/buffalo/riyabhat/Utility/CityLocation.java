package edu.buffalo.riyabhat.Utility;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "City")
public class CityLocation implements Serializable {
    private Double latitude;
    private Double longitude;

    @Id
    private Long routeId;

    private int sorter;

    @Id
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CityLocation () {
    }

    public CityLocation (Double latitude, Double longitude, String city, Long routeId, int sorter) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeId = routeId;
        this.sorter = sorter;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public int getSorter() {
        return sorter;
    }

    public void setSorter(int sorter) {
        this.sorter = sorter;
    }

}