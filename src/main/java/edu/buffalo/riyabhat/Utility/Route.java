package edu.buffalo.riyabhat.Utility;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Route")
public class Route {
    private String source;
    private String destination;
    @Id
    @GeneratedValue
    private Long routeId;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "routeId")
    @OrderBy(value="sorter")
    private List<CityLocation> locations = new ArrayList<>();

    public Route () {
    }

    public Route (String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public void clearLocations() {locations.clear();}

    public void addLocation(CityLocation location) {
        locations.add(location);
    }

    public List<CityLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<CityLocation> locations) {
        this.locations = locations;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setId(Long id) {
        this.routeId = routeId;
    }
}