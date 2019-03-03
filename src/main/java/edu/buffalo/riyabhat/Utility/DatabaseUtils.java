package edu.buffalo.riyabhat.Utility;

import edu.buffalo.riyabhat.Model.City;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    public static void persistToDatabase(List<City> cities,String source,String dest) {
        Route route = new Route(source, dest);
        Session session = Database.getSessionFactory().openSession();
        Transaction transaction1 = session.beginTransaction();
        Long routeId = (Long) session.save(route);
        transaction1.commit();
        session.close();

        session = Database.getSessionFactory().openSession();
        route = session.get(Route.class, routeId);
        route.clearLocations();
        session.close();

        int sorter = 0;
        session = Database.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        for (City city : cities) {
            Weather weather= new Weather(city.getName(),
                    city.getMinTemp(),
                    city.getMaxTemp(),
                    city.getWeatherDescription());
            CityLocation location = new CityLocation(city.getLatitude(),
                    city.getLongitude(),
                    city.getName(),
                    routeId,
                    sorter
                    );
            session.saveOrUpdate(weather);
            route.addLocation(location);
            sorter++;
        }
        transaction2.commit();
        session.close();

        session = Database.getSessionFactory().openSession();
        Transaction transaction3 = session.beginTransaction();
        session.saveOrUpdate(route);
        transaction3.commit();
        session.close();
    }

    public static List<City> getFromDatabases(String source, String destination) {
        Session session = Database.getSessionFactory().openSession();
        String hql = "SELECT R.routeId FROM Route R WHERE R.source=:source and R.destination=:destination";
        Query query = session.createQuery(hql);
        query.setParameter("source", source);
        query.setParameter("destination", destination);
        List<Long> result = query.list();
        if(result.isEmpty()) return null;

        Long routeId = result.get(0);
        Route route = session.get(Route.class, routeId);

        List<City> cities = new ArrayList<>();
        for (CityLocation location : route.getLocations()) {
            Weather weather = session.get(Weather.class, location.getCity());
            City city = new City(location.getLatitude(),
                    location.getLongitude(),
                    location.getCity(),
                    weather.getMinTemp(),
                    weather.getMaxTemp(),
                    weather.getWeatherDescription());
            cities.add(city);
        }
        session.close();
        return cities;
    }
}