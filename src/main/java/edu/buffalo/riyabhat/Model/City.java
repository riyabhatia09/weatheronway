package edu.buffalo.riyabhat.Model;

public class City {
    private Double latitude;
    private Double longitude;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String weatherDescription;

    public City(Double latitude, Double longitude,String name,Double minTemp,Double maxTemp,String weatherDescription) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name =  name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.weatherDescription = weatherDescription;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setMaxTemp(Double minTemp) {
        this.maxTemp = maxTemp;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
