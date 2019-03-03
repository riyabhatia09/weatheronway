package edu.buffalo.riyabhat.Utility;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Weather")
public class Weather {
    @Id
    private String city;
    private Double minTemp;
    private Double maxTemp;
    private String weatherDescription;

    public Weather () {
    }

    public Weather (String city, Double minTemp, Double maxTemp, String weatherDescription) {
        this.city = city;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.weatherDescription = weatherDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}