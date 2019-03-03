package edu.buffalo.riyabhat.Utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Database {
    private static SessionFactory databaseSessionFactory = null;
    static {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //log settings
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        //driver settings
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", getConnectionString());
        properties.put("hibernate.connection.username", "postgres");
        properties.put("hibernate.connection.password", "password");

        databaseSessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Route.class)
                .addAnnotatedClass(CityLocation.class)
                .addAnnotatedClass(Weather.class)
                .buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .applySettings(properties)
                                .build()
                );

    }
    private static String getConnectionString() {
        return "jdbc:postgresql://localhost:5432/riya";
    }
    public static SessionFactory getSessionFactory() {
        return databaseSessionFactory;
    }
}
