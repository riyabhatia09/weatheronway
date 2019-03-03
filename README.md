# WeatherOnWay(v2)


Steps to execute:

Import the project in your IDE and run WeatherOnWayApplication.java which is the entry point of the project. 
To run using maven use the command `mvn package -DskipTests` to build the standalone jar.
Then run with the `java -jar <app-jar>` command to run the app on default port 8080

Open the web browser and open this link: ​ http://localhost:8080/Polyline.html​ , you should see the application running.
Now, enter origin and destination values, you should see a route with markers of intermediate cities showing up. If you click on any marker, you should see the weather information displayed in an info-window.

Software Requirement:

Make sure you have JDK 8 in your machine.
All other maven dependencies are included in pom.xml

Directory Structure:

There are three folders(MVC):

Controller (DirectionsAPI.java,WeatherOnWayApplication.java)
Model(City.java)
Utility(CityLocation,Database,DatabaseUtils,Route,Weather)

