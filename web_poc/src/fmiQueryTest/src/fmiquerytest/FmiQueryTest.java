/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmiquerytest;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Double.NaN;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.text.DecimalFormat;
import static oracle.jrockit.jfr.events.Bits.intValue;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

class Coordinates implements Serializable
{
    public double Lat; 
    public double Lon;  
    // constructor
    public Coordinates(double Lat, double Lon)
    {
        this.Lat = Lat;
        this.Lon = Lon;
    }
}
class distance implements Comparable<distance> 
{
    public int stationNum;
    public double stationDistance;
    
    @Override
    public int compareTo(distance o) {
        return Double.compare(this.stationDistance, o.stationDistance);
    }
    public distance(int stationNum, double stationDistance)
    {
        this.stationNum = stationNum;
        this.stationDistance = stationDistance;
    }
}
class routeStep implements Serializable
{
    public Coordinates StartLocation;
    public Coordinates EndLocation;
    public int Distance;
    public int Duration;
    public String Polyline;
    public String Instructions;
    // constructor
    public routeStep(Coordinates StartLocation, Coordinates EndLocation,
            int Distance, int Duration, String Polyline, String Instructions)
    {
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
        this.Distance = Distance;
        this.Duration = Duration;
        this.Polyline = Polyline;
        this.Instructions = Instructions;
    }
}
class routePoint
{
    public Coordinates Coords;
    public int nearestStation;
    // constructor
    public routePoint(Coordinates Coords, int nearestStation)
    {
        this.Coords = Coords;
        this.nearestStation = nearestStation;
    }
}
class weatherData implements Serializable
{
    public String isoTime;
    public String parameterName;
    public String parameterValue;
    // constructor
    public weatherData(String isoTime, String parameterName,
            String parameterValue)
    {
        this.isoTime = isoTime;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }
}
class stationData implements Serializable, Comparable<stationData>
{
    public Coordinates stationLocation;
    public String stationName;
    public List<weatherData> weatherData;
    
    @Override
    public int compareTo(stationData o) {
        return this.stationName.compareTo(o.stationName);
    }
    // constructor
    public stationData(Coordinates stationLocation, String stationName,
            List<weatherData> weatherData)
    {
        this.stationLocation = stationLocation;
        this.stationName = stationName;
        this.weatherData = weatherData;
    }
}
class stepWeather
{
    public Coordinates StartLocation;
    public Coordinates EndLocation;
    public int Distance;
    public int Duration;
    public int totalDuration;
    public double timeAsDouble;
    public int nearestStation;
    public String stationName;
    public List<weatherData> weatherData;
    public List<String> Warnings;
    public double sunRise;
    public double sunSet;
    // constructor
    public stepWeather(Coordinates StartLocation, Coordinates EndLocation,
            int Distance, int Duration, int totalDuration, double timeAsDouble,
            int nearestStation, String stationName, List<weatherData> weatherData,
            List<String> Warnings, double sunRise, double sunSet)
    {
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
        this.Distance = Distance;
        this.Duration = Duration;
        this.totalDuration = totalDuration;
        this.timeAsDouble = timeAsDouble;
        this.nearestStation = nearestStation;
        this.stationName = stationName;
        this.weatherData = weatherData;
        this.Warnings = Warnings;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
    }
}

class RouteTools
{
    //    interpolate_step(step,curr_step,curr_coords)
    //    start_point = curr_step
    //    end_point = curr_step + step.duration
    //    for i in (start_point:end_point)
    //        lat = curr_coords[1]+(step.end_location[1]-curr_coords[1])/(step.duration)*(i-curr_step)
    //        lon = curr_coords[2]+(step.end_location[2]-curr_coords[2])/(step.duration)*(i-curr_step)
    //    end for
    static Coordinates interpolateOnePoint(routeStep step, int currPoint, int startPoint){
        Coordinates currCoor = step.StartLocation;
        double lat = currCoor.Lat 
                + (step.EndLocation.Lat-currCoor.Lat)/step.Duration*(currPoint-startPoint);
        double lon = currCoor.Lon 
                + (step.EndLocation.Lon-currCoor.Lon)/step.Duration*(currPoint-startPoint);
        Coordinates corrNow = new Coordinates(lat, lon);
        return corrNow;
    }
    
    //    current_step = 0
    //    current_coordinates = start_coordinates
    //    for i in length(steps)
    //    append(steps, interpolate_step(steps[i]),current_step,current_coordinates)
    //    current_step = current_step + steps[i].duration
    //    current_coordinates = steps[i].end_location
    //    end for
    //    Loop the points of one step
    //    for(int j=startPoint; j < endPoint;j++){
    //        //System.out.println("j: "+j);
    //        routeTemp[j] = new routePoint(RouteTools.interpolateOnePoint(gSteps.get(i), j, 
    //                startPoint, currentCoords), 0);
    //    }
    static List<routeStep> stepsToRoute(List<routeStep> gSteps, 
                                Coordinates routeStart, int routeDur, int refreshInterval){
        List<routeStep> routeTemp = new ArrayList<>();
        //System.out.println("routeTemp.length: "+routeTemp.size());
        int currentTime = 0;
        Coordinates currentCoords = routeStart;
        // Loop the gSteps
        for(int i=0; i < gSteps.size();i++){
            int startPoint = currentTime;
            int endPoint = currentTime + gSteps.get(i).Duration;
            double fractionOfRoute = ((double)refreshInterval/(double)gSteps.get(i).Duration);
            int oneIntervalDistance = intValue(gSteps.get(i).Distance*(fractionOfRoute));
            
            //If current step is longer than 5km, we follow the polyline
            List<Coordinates> points = new ArrayList<>();
            List<Coordinates> polyCoords = new ArrayList<>();
            double polylineLength = 0, polyMultiplier = 0, oneIntervalDisplacement = 0;
            if (gSteps.get(i).Distance>oneIntervalDistance){
                points = decodePolyLine(gSteps.get(i).Polyline);
                for (int j=0;j<points.size()-1;++j){
                    polylineLength += euclideanDistance(points.get(j),points.get(j+1));
                }
                polyMultiplier = polylineLength/gSteps.get(i).Distance;
                oneIntervalDisplacement = oneIntervalDistance*polyMultiplier;
                polyCoords = followPolyLine(points, oneIntervalDisplacement);
            }
            int totalDistance = 0, index = 0;
            while((currentTime+refreshInterval) < endPoint){
                currentTime += refreshInterval;
                Coordinates startCoords = currentCoords;
                if (polyCoords.size()>0){
                    currentCoords = polyCoords.get(index++);
                } else {  
                    currentCoords = RouteTools.interpolateOnePoint(gSteps.get(i),currentTime,startPoint);
                }
                routeTemp.add(new routeStep(startCoords,currentCoords,oneIntervalDistance,refreshInterval,"",""));
                totalDistance += oneIntervalDistance;
            }
            routeTemp.add(new routeStep(currentCoords,gSteps.get(i).EndLocation,
                    gSteps.get(i).Distance-totalDistance,gSteps.get(i).Duration-(currentTime-startPoint),"",""));
            currentTime = endPoint;
            currentCoords = gSteps.get(i).EndLocation;
            //System.out.format("%-7s%-10s%-5s%-10s%-7s%-12s %-12s%n",
            //        "Start: ",startPoint,"End: ",endPoint,"Coord: ",currentCoords.Lat,currentCoords.Lon);
        }
        return routeTemp;
    }

    static double euclideanDistance(Coordinates targetCoordinates, Coordinates stationCoordinates){
        double distance = sqrt(pow((targetCoordinates.Lat-stationCoordinates.Lat),2)
                +pow((targetCoordinates.Lon-stationCoordinates.Lon),2));
        return distance;
    }
    
    static distance[] calculateStationDistances(Coordinates currentPoint, List<stationData> allStations){
        distance[] stationDistances = new distance[allStations.size()];
        for (int i=0; i < allStations.size();i++){
            stationDistances[i] = 
                    new distance(i, euclideanDistance(currentPoint,allStations.get(i).stationLocation));
//            System.out.format("%-12s%-4s%-10s%-10s%n",
//                   "stationNum: ",stationDistances[i].stationNum,
//                   "distance: ",stationDistances[i].stationDistance);
        }
        Arrays.sort(stationDistances);
        return stationDistances;
    }
    
    static int findNearestStation(Coordinates currentPoint, List<stationData> allStations){
        //max stationDistance from origin (current) coordinates is 2x stationDistance travelled + nearest station in the otigin
        //double maxDistance = 2*euclideanDistance(target_coordinates,origin_coordinates)+station_distances[1].stationDistance
        //# this takes into account for example the situation where the route is so short that the station does not change
        //# for long trip (Helsinki-Ivalo) it may be good idea to trigger the station stationDistance table calculation again
        //# (to produce new station list where the more relevant stations are on the top)
        //# take all points that are closer than max stationDistance
        //near_stations = station_distances LESS_THAN max_distance
        //if (length(near_stations MORE_THAN 30){
        //# NOTE. Change values that are stored in the main program level
        //# These will be used later
        //calculate_station_distances(station_distances,target_coordinates)
        //}
        //else {
        //# calculate current distances to those stations
        //results = calculate_station_distances(near_stations,target_coordinates)
        //nearest = sort(results,ASCENDING)[1]
        //}
        //return nearest
        return 0;
    }
    static List<routeStep> compileRoute(List<routeStep> gSteps, Integer refreshInterval){
        Coordinates routeStart = gSteps.get(0).StartLocation;
        System.out.format("%-12s%-12s%-12s%-12s%n","Start Lat: ",routeStart.Lat,"Start Lon: ",routeStart.Lon);
        Coordinates routeEnd = gSteps.get(gSteps.size()-1).EndLocation;
        System.out.format("%-12s%-12s%-12s%-12s%n","End Lat: ",routeEnd.Lat,"End Lon: ",routeEnd.Lon);
        int routeDist = 0;
        for(int i=0; i < gSteps.size();i++)
            routeDist += gSteps.get(i).Distance;
        System.out.println("Google route distance: "+routeDist+" (m)");
        //int routeDur = 0;
        for(int i=0; i < gSteps.size();i++)
            FmiQueryTest.routeDur += gSteps.get(i).Duration;
        System.out.println("Google route duration: "+FmiQueryTest.routeDur+" (s)");
        List<routeStep> routeData = new ArrayList<>();
        routeData = RouteTools.stepsToRoute(gSteps, routeStart, FmiQueryTest.routeDur, refreshInterval);
        
        //Add endpoint to Google steps
        routeData.add(new routeStep(routeEnd, routeEnd, 0, 0, "", ""));
        
        return routeData;
    }

    static List<stepWeather> combineRouteDatabase(List<routeStep> routeData, 
            List<Integer> neededStations, List<stationData> allStations) {
        List<stepWeather> temp = new ArrayList<>();
        int totalDuration = 0;
        double zenithAngleOfficial = 90+((double)50/60);
        double startTime = DayLightTime.stringTimeToDouble(
                FmiQueryTest.df_short.format(new Date(System.currentTimeMillis())))
                +FmiQueryTest.startTimeOffset;
        for(int i=0;i<routeData.size();++i){
            routeStep step = routeData.get(i);
            List<weatherData> weatherPoint = new ArrayList<>();
            weatherPoint.add(0, new weatherData("","",""));
            List<String> warnings = new ArrayList<>();
            warnings.add("");
            double timeNow = startTime+(double)totalDuration/60/60;
            //If the trip is going over the change of day, we add 1 to getSunTime offSet
            int offSet = intValue(timeNow/24);
            double sunRise = DayLightTime.getSunTime(TRUE,offSet,step.StartLocation.Lat,step.StartLocation.Lon,zenithAngleOfficial);;
            double sunSet = DayLightTime.getSunTime(FALSE,offSet,step.StartLocation.Lat,step.StartLocation.Lon,zenithAngleOfficial);;
            temp.add(new stepWeather(step.StartLocation, step.EndLocation, 
                    step.Distance, step.Duration, totalDuration, timeNow, 
                    neededStations.get(i), allStations.get(neededStations.get(i)).stationName, weatherPoint, 
                    warnings, sunRise, sunSet));
            //Finally add the duration to the total
            totalDuration += step.Duration;
        }
        return temp;
    }

    static Coordinates findLocationAtTime(double time, List<stepWeather> stepDataBase) {
        Coordinates loc = new Coordinates(0,0);
        for(int i=0;i<stepDataBase.size()-1;++i){
            stepWeather step = stepDataBase.get(i);
            stepWeather nextStep = stepDataBase.get(i+1);
            //If the time we are looking for is during the next step:
            if (time > step.timeAsDouble && time < nextStep.timeAsDouble){
                //Interpolate the location approximately
                loc.Lat = step.StartLocation.Lat
                                + (((time-step.timeAsDouble) / ((double) step.Duration / 3600))
                                 * (nextStep.StartLocation.Lat - step.StartLocation.Lat));
                loc.Lon = step.StartLocation.Lon
                                + (((time-step.timeAsDouble) / ((double) step.Duration / 3600))
                                 * (nextStep.StartLocation.Lon - step.StartLocation.Lon));
            }
        }
        return loc;
    }

    // http://stackoverflow.com/questions/15924834/decoding-polyline-with-new-google-maps-api
    private static List<Coordinates> decodePolyLine(String Polyline) {
        List<Coordinates> temp = new ArrayList<>();

        int index = 0, len = Polyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = Polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = Polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Coordinates p = new Coordinates((((double) lat / 1E5)),(((double) lng / 1E5)));
            temp.add(p);
        }     
        return temp;
    }

    private static List<Coordinates> followPolyLine(List<Coordinates> points, double oneIntervalDisplacement) {
        List<Coordinates> temp = new ArrayList<>();
        double distanceFollowed = 0;
        for (int i=0;i<points.size()-1;++i){
            double distNow = euclideanDistance(points.get(i),points.get(i+1));
            distanceFollowed += distNow;
            if (distanceFollowed > oneIntervalDisplacement){
                distanceFollowed -= oneIntervalDisplacement;
                double fractionFromStart = (distNow-distanceFollowed)/distNow;
                temp.add(new Coordinates(
                        points.get(i).Lat+(points.get(i+1).Lat-points.get(i).Lat)*fractionFromStart,
                        points.get(i).Lon+(points.get(i+1).Lon-points.get(i).Lon)*fractionFromStart));
            }
        }
        return temp;
    }
}

class ToolBox {
    // Not used?
    static String getURLContent(String p_sURL)
    {
        URL oURL;
        URLConnection oConnection;
        BufferedReader oReader;
        String sLine;
        StringBuilder sbResponse;
        String sResponse = null;
            
        try {
            oURL = new URL(p_sURL);
            oConnection = oURL.openConnection();
            oReader = new BufferedReader(new InputStreamReader(oConnection.getInputStream()));
            sbResponse = new StringBuilder();
            while((sLine = oReader.readLine()) != null){
                sbResponse.append(sLine);
            }
            sResponse = sbResponse.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ToolBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sResponse;
    }
    
    static ArrayList<stationData> getUniqueStations(List<stationData> stations){
        ArrayList<stationData> temp = new ArrayList<>();
        Set<String> uniques = new HashSet<>();
        
        for (stationData station : stations) {
            String addedAsString = new StringBuilder(String.valueOf(station.stationLocation.Lat))
                                                .append(String.valueOf(station.stationLocation.Lon))
                                                .toString();
            if (!uniques.contains(addedAsString)){
                uniques.add(addedAsString);
                temp.add(new stationData(station.stationLocation,station.stationName,station.weatherData));
            }
        }
        return temp;
    }
}

class Parser 
{
    private static List<String> getValueList(Document doc, XPath xpath, String expression)
    {
        List<String> list = new ArrayList<>();
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile(expression);
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
//            NodeList nodeList = doc.getElementsByTagName("*");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    // do something with the current element
//                    System.out.println(node.getNodeName());
//                }
//            }
            
            for (int i = 0; i < nodes.getLength(); i++){
                //System.out.println(nodes.item(i).getTextContent().trim());
                list.add(nodes.item(i).getTextContent().trim());
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    static List<routeStep> getSteps(String gQuery) 
    {
        List<routeStep> list = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(gQuery);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();        
            String expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/start_location/lat/text()";
            // System.out.println(expr);
            List<String> start_lats = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/start_location/lng/text()";
            // System.out.println(expr);
            List<String> start_lngs = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/end_location/lat/text()";
            List<String> end_lats = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/end_location/lng/text()";
            List<String> end_lngs = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/distance/value/text()";
            List<String> dists = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/duration/value/text()";
            List<String> durs = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/polyline/points/text()";
            List<String> polys = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route["+(FmiQueryTest.gRouteOption+1)+"]/leg/step/html_instructions/text()";
            List<String> insts = Parser.getValueList(doc, xpath, expr);

            for (int i = 0; i < start_lats.size(); i++) {
                double slat = Double.parseDouble(start_lats.get(i));
                double slon = Double.parseDouble(start_lngs.get(i));
                Coordinates start = new Coordinates(slat, slon);
                double elat = Double.parseDouble(end_lats.get(i));
                double elon = Double.parseDouble(end_lngs.get(i));
                Coordinates end = new Coordinates(elat, elon);
                int dist = parseInt(dists.get(i));
                int dur = parseInt(durs.get(i));
                String poly = polys.get(i);
                String inst = insts.get(i);
                
                list.add(new routeStep(start, end, dist, dur, poly, inst));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    static List<stationData> getStations(String fmiQuery) 
    {
        List<stationData> list = new ArrayList<>();  
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fmiQuery);
            
            NodeList nodeList = doc.getElementsByTagName("BsWfs:BsWfsElement");
            for (int i = 0; i < nodeList.getLength(); i++) {
                //System.out.println(nodeList.item(i).getNodeName());
                NodeList nodeData = nodeList.item(i).getChildNodes();
                        
                Coordinates stationCoords = null;
                List<weatherData> weatherPoint = new ArrayList<>();
                weatherPoint.add(0, new weatherData("","",""));
                for (int j = 0; j < nodeData.getLength(); j++) {
                    switch (nodeData.item(j).getNodeName()) {
                        case "BsWfs:Location":
                            String Coords = nodeData.item(j).getTextContent().trim();
                            //System.out.println(Coords);
                            String[] splitCoords = Coords.split("[ ]");
                            stationCoords = new Coordinates(Double.parseDouble(splitCoords[0]),
                                                            Double.parseDouble(splitCoords[1]));
                            break;
                        case "BsWfs:Time":
                            weatherPoint.get(0).isoTime = nodeData.item(j).getTextContent().trim();
                            break;
                        case "BsWfs:ParameterName":
                            weatherPoint.get(0).parameterName = nodeData.item(j).getTextContent().trim();
                            break;
                        case "BsWfs:ParameterValue":
                            weatherPoint.get(0).parameterValue = nodeData.item(j).getTextContent().trim();
                            break;
                    }
                }
                list.add(new stationData(stationCoords, "", weatherPoint));
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    static String getStationName(String geoQuery){
        String name = new String();
        String backupName = new String();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(geoQuery);
            
            NodeList statusCheck = doc.getElementsByTagName("status");
            //System.out.println(statusCheck.item(0).getTextContent().trim());
            if (statusCheck.item(0).getTextContent().trim().equals("OK")){
                NodeList nodeList = doc.getElementsByTagName("address_component");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    //System.out.println(nodeList.item(i).getNodeName());
                    NodeList nodeData = nodeList.item(i).getChildNodes();
                    Boolean nameFound = FALSE;
                    for (int j = 0; j < nodeData.getLength(); j++) {
                        //System.out.println(nodeData.item(j).getTextContent().trim());
                        switch (nodeData.item(j).getNodeName()) {
                            case "long_name":
                                name = nodeData.item(j).getTextContent().trim();
                                //System.out.println("Name changed to: "+name);
                            case "type":
                                if (nodeData.item(j).getTextContent().trim().equals("locality") || 
                                        nodeData.item(j).getTextContent().trim().equals("administrative_area_level_3")){
                                    nameFound = TRUE;
                                    System.out.println("Locality or Adm.area3 found. Saving name: "+name);
                                }
                                else if(nodeData.item(j).getTextContent().trim().equals("administrative_area_level_2")){
                                    backupName = name;
                                    System.out.println("backupName set to: "+backupName);
                                }
                        }
                    }
                    if (nameFound){
                        return name;
                    }
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {            
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("End of file, returning backupName: "+backupName);
        return backupName;
    }

    static String getQueryFromShare(String gShare){
        StringBuilder queryTemp = new StringBuilder(FmiQueryTest.gDirectionQuery);
        //System.out.println(gShare);
        if (gShare.contains(FmiQueryTest.gWebShareBase)){
            String data = gShare.substring(FmiQueryTest.gWebShareBase.length(), gShare.indexOf("/@"));
            data = data.replaceAll("%C5|%C3%85", "Å");
            data = data.replaceAll("%C4|%C3%84", "Ä");
            data = data.replaceAll("%D6|%C3%96", "Ö");
            data = data.replaceAll("%E5|%C3%A5", "å");
            data = data.replaceAll("%E4|%C3%A4", "ä");
            data = data.replaceAll("%F6|%C3%B6", "ö");
            String[] wayPoints = data.split("/");
            if(!wayPoints[0].isEmpty()){
                FmiQueryTest.gOrigin = wayPoints[0];
            }
            if(!wayPoints[wayPoints.length-1].isEmpty()){
                FmiQueryTest.gDestination = wayPoints[wayPoints.length-1];
            }
            String wayPointTemp = "";
            if(wayPoints.length>2){
                for(int i=1;i<wayPoints.length-1;++i){
                    wayPointTemp += wayPoints[i]+"|";
                }
                wayPointTemp = wayPointTemp.substring(0,wayPointTemp.length()-1);
            }
            FmiQueryTest.gWaypoints = wayPointTemp;
            if(gShare.contains(FmiQueryTest.gWebShareRouteOptPar)){
                int found = gShare.indexOf(FmiQueryTest.gWebShareRouteOptPar)
                        +FmiQueryTest.gWebShareRouteOptPar.length();
                int routeOption = parseInt(gShare.substring(found, found+1));
                FmiQueryTest.gRouteOption = routeOption;
            }
        }
        queryTemp.append("origin=").append(FmiQueryTest.gOrigin);
        queryTemp.append("&destination=").append(FmiQueryTest.gDestination);
        queryTemp.append("&key=").append(FmiQueryTest.gKey);
        queryTemp.append("&mode=").append(FmiQueryTest.gMode);
        queryTemp.append("&waypoints=").append(FmiQueryTest.gWaypoints);
        queryTemp.append("&alternatives=true");
        return queryTemp.toString();
    }
}

class FileSystemTools{
    static void saveObjectToFile(Object object, File fileName){
        System.out.println("Saving object to file: "+fileName);
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            fout.close();
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Saved file: "+fileName);
    }
    static List<stationData> loadStationsFromFile(File stationFileName){
        List<stationData> savedStations = new ArrayList<>();
        try {
            FileInputStream fin = new FileInputStream(stationFileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            savedStations = (List<stationData>) ois.readObject();
            fin.close();
            ois.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return savedStations;
    }
    static Boolean isSavedRoute(String gQuery){
        if (FmiQueryTest.routeFileName.exists()){
            gQuery = gQuery.replace(FmiQueryTest.gDirectionQuery, "");
            gQuery = gQuery.replace(FmiQueryTest.gKey, "");
            gQuery = gQuery.replace("&key=", "");
            //Find gQuery from the file
            List<String> queries = readLinesFromFile(FmiQueryTest.routeFileName);
            for (String query : queries){
                if (query.equals(gQuery)){
//                    System.out.println("Route found from saved list.");
                    return TRUE;
                }
            }
        }
        return FALSE;
    }
    static List<routeStep> loadSavedRoute(String gQuery){
        List<routeStep> loadedRoute = new ArrayList<>();
        gQuery = gQuery.replace(FmiQueryTest.gDirectionQuery, "");
        gQuery = gQuery.replace(FmiQueryTest.gKey, "");
        gQuery = gQuery.replace("&key=", "");
        File savedFileName = new File(gQuery.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        //Load route
        try {
            FileInputStream fin = new FileInputStream(savedFileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            loadedRoute = (List<routeStep>) ois.readObject();
            fin.close();
            ois.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FmiQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadedRoute;
    }
    static void saveRoute(String gQuery, List<routeStep> gSteps){
        gQuery = gQuery.replace(FmiQueryTest.gDirectionQuery, "");
        gQuery = gQuery.replace(FmiQueryTest.gKey, "");
        gQuery = gQuery.replace("&key=", "");
        //Save gQuery
        FileSystemTools.appendLineToFile(gQuery, FmiQueryTest.routeFileName);
        //Save gSteps
        File savedFileName = new File(gQuery.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        FileSystemTools.saveObjectToFile(gSteps, savedFileName);
    }
    static void appendLineToFile(String gQuery, File file){
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(gQuery+FmiQueryTest.newLine);
            bw.close();

            System.out.println("Saved route to "+file.getName());
        } catch (IOException ex) {
            Logger.getLogger(FileSystemTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static List<String> readLinesFromFile(File file){
        List<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader;
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    static List<stationData> extractHourOfWeatherData(String savedFileTime, List<stationData> fmiData){
        List<stationData> thisHourData = new ArrayList<>();
        System.out.println("savedFileTime: "+savedFileTime);
        for (stationData line : fmiData){
            for (weatherData data : line.weatherData){
                List<weatherData> weatherPoint = new ArrayList<>();
                if(data.isoTime.equals(savedFileTime)){
                    weatherPoint.add(0, data);
                    thisHourData.add(new stationData(line.stationLocation,line.stationName,weatherPoint));
                }
            }
        }
        return thisHourData;
    }
    static void cleanupOldWeatherData(int daysToStoreWeatherData){
        int cleanUpTime = parseInt(FmiQueryTest.df_daycode.format(new Date(System.currentTimeMillis() - ((daysToStoreWeatherData*24*3600) * 1000))));
        File dir = new File(".");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("weather");
            }
        };
        String[] weatherFiles = dir.list(filter);
        System.out.println("Cleaning up old weather data from directory:");
        for (String file : weatherFiles){
            System.out.print(file);
            int currentDayCode = parseInt(file.replaceAll("weather", "").substring(0,8));
            if (currentDayCode <= cleanUpTime){
                try {
                    Files.delete(FileSystems.getDefault().getPath(file));
                } catch (IOException ex) {
                    Logger.getLogger(FileSystemTools.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(" - was removed.");
            }else{
                System.out.println(" - was kept.");
            }
        }   
    }
}
class WeatherTools{
    //    Data structure
    //    hmap (keys: String isoTime-parameterName)
    //      parameterValue
    static Map extractNeededStations(Set<Integer> uniqueEntries, List<stationData> fmiData, List<stationData> allStations){
        //Prepare returned HashMap
        HashMap<String, String> hmap = new HashMap<>();
        //Prepare HashMap for Coordinates-Integer pairs
        HashMap<String, Integer> idMap = new HashMap<>();
        
        //Prepare inner HashMap for every station (keys: isotimes)
        for (Integer stationNum : uniqueEntries){
            //Save needed stations as Coordinates
            String coor = allStations.get(stationNum).stationLocation.Lat+"-"+allStations.get(stationNum).stationLocation.Lon;
            idMap.put(coor, stationNum);
        }
        //Save all needed weather data
        for (stationData station : fmiData){
            //Use idMap to check if current station is needed
            String coor = station.stationLocation.Lat+"-"+station.stationLocation.Lon;
            //System.out.println(coor);
            if(idMap.containsKey(coor)){
                //If it is, save any weather data found here
                Integer stationNum = idMap.get(coor);
                for(weatherData weather : station.weatherData){
                    //System.out.format("%-25s%-35s%-20s%n",weather.isoTime,weather.parameterName,weather.parameterValue);
                    hmap.put(stationNum+"-"+weather.isoTime+"-"+weather.parameterName, weather.parameterValue);
                } 
            }
        }
        return hmap;
    }
    static void makeResultHtml(List<stepWeather> stepDataBase, List<stationData> allStations, 
            Map routeWeather, List<String> sunEvents){
        try {
            // if file doesnt exists, then create it
            if (!FmiQueryTest.resultFileName.exists()) {
                FmiQueryTest.resultFileName.createNewFile();
            }
            FileWriter fw = new FileWriter(FmiQueryTest.resultFileName.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(
                        "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <title>Weather en Route</title>\n" +
                        "    <style>\n" +
                        "      html, body {\n" +
                        "        height: 100%;\n" +
                        "        margin: 0;\n" +
                        "        padding: 0;\n" +
                        "      }\n" +
                        "      #map {\n" +
                        "        height: 100%;\n" +
                        "      }\n" +
                        "      #map > div > div:nth-child(1) > div:nth-child(3) > div:nth-child(4) > div > div:nth-child(1) > div:nth-child(3){display:none;}\n" +
                        "      #map > div > div:nth-child(1) > div:nth-child(3) > div:nth-child(4) > div > div:nth-child(1) > div:nth-child(1){display:none;}\n" +
                        "    </style>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"map\"></div>\n" +
                        "    <script>\n" +
                        "  function initMap() {\n");
            bw.write(
                        "  var siikalatva = {lat: 64.179265, lng: 25.804715};\n" +
                        "  var map = new google.maps.Map(document.getElementById('map'), {\n" +
                        "    zoom: 6,\n" +
                        "    center: siikalatva,\n" +
                        "    zoomControl: true,\n" +
                        "    scaleControl: true,\n" +    
                        "    streetViewControl: true,\n" +
                        "    mapTypeControl: true,\n" +
                        "    mapTypeControlOptions: {\n" +
                        "      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,\n" +
                        "      mapTypeIds: [\n" +
                        "        google.maps.MapTypeId.ROADMAP,\n" +
                        "        google.maps.MapTypeId.TERRAIN\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  });\n");
            bw.write(   "  var directionsService = new google.maps.DirectionsService;\n" +
                        "  var directionsDisplay = new google.maps.DirectionsRenderer({\n" +
                        "    draggable: false,\n" +
                        "    map: map\n" +
                        "  });");
            bw.write(   "  displayRoute('"+FmiQueryTest.gOrigin+"', '"+FmiQueryTest.gDestination+"', directionsService,\n" +
                        "      directionsDisplay);");
            
            bw.write(makeWeatherStationBoxes(stepDataBase, allStations));
            if (!sunEvents.isEmpty()){
                bw.write(makeSunEventBoxes(stepDataBase, sunEvents));
            }
            bw.write(makeRouteWeatherBoxes(stepDataBase, routeWeather));
            bw.write(makeChartBoxes(stepDataBase, routeWeather));
            
            //http://en.marnoto.com/2014/09/5-formas-de-personalizar-infowindow.html
            
            bw.write(   "  }\n");
            bw.write(   "function displayRoute(origin, destination, service, display) {\n" +
                        "  service.route({\n" +
                        "    origin: origin,\n" +
                        "    destination: destination,\n");
            if (!FmiQueryTest.gWaypoints.isEmpty()){
                bw.write(   "    waypoints: [");
                String[] locs = FmiQueryTest.gWaypoints.split("\\|");
                for (int i=0;i<locs.length;++i){
                    bw.write("{location: '"+locs[i]+"'}");
                    if (i<locs.length-1){
                        bw.write(", ");
                    }
                }
                bw.write(   "],\n");
            }
            bw.write(   "    provideRouteAlternatives: true,\n" +
                        "    travelMode: google.maps.TravelMode.DRIVING\n" +
                        "  }, function(response, status) {\n" +
                        "    if (status === google.maps.DirectionsStatus.OK) {\n" +
                        "      display.setDirections(response);\n" +
                        "      display.setRouteIndex("+FmiQueryTest.gRouteOption+");\n" +
                        "    } else {\n" +
                        "      alert('Could not display directions due to: ' + status);\n" +
                        "    }\n" +
                        "  });\n" +
                        "}");
            bw.write(   "    </script>\n" +
                        "    <script async defer\n" +
                        "        src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDvB2p97Ee-USxdOsOTyCa50HnW8H4PlRQ&signed_in=true&callback=initMap\"></script>\n" +
                        "  </body>\n" +
                        "</html>");
            bw.close();
        } catch (IOException ex) {
            System.err.println("Something went wrong: "+ex.getMessage());
        }
//        try {
//            //Open the file
//            Desktop.getDesktop().browse(FmiQueryTest.resultFileName.toURI());
//        } catch (IOException ex) {
//            Logger.getLogger(WeatherTools.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private static String makeWeatherStationBoxes(List<stepWeather> stepDataBase,
            List<stationData> allStations) {
        String markers = "";
        List<Integer> neededStations = new ArrayList<>();
        for (int i=0;i<stepDataBase.size();++i){neededStations.add(stepDataBase.get(i).nearestStation);}
        Set<Integer> uniqueSet = new HashSet<>(neededStations);
        List<Integer> uniqueEntries = new ArrayList<>();
        for (Integer a : uniqueSet){uniqueEntries.add(a);}
        for (int i=0;i<uniqueEntries.size();++i){
            Integer nearestStation = uniqueEntries.get(i);
            markers += ("  var station"+i+" = '<div id=\"content"+i+"\"><pre>'+")+FmiQueryTest.newLine;
            markers += ("'Station:     "+allStations.get(nearestStation).stationName+"<br>'+")+FmiQueryTest.newLine;
            markers += ("'Coordinates: "+
                    FmiQueryTest.df_fiveDecimal.format(allStations.get(nearestStation).stationLocation.Lat)+" "+
                    FmiQueryTest.df_fiveDecimal.format(allStations.get(nearestStation).stationLocation.Lon)
                    +"<br>'+")+FmiQueryTest.newLine;
            markers += ("'Time(s):     ");
            for (int j=0;j<stepDataBase.size();++j){
                if (stepDataBase.get(j).nearestStation==uniqueEntries.get(i)){
                    markers += DayLightTime.doubleTimeToString(stepDataBase.get(j).timeAsDouble)+" ";
                }
            }
            markers += ("<br>'+")+FmiQueryTest.newLine;
            markers += ("'</pre></div>';")+FmiQueryTest.newLine;
            markers += ("  var station_infowindow"+i+" = new google.maps.InfoWindow({\n" 
                    +   "    content: station"+i+",\n"
                    +   "    maxWidth: 400,\n"
                    +   "    pixelOffset: new google.maps.Size(-210, 25)\n"
                    +   "  });");
            markers += ("  var station_position"+i+" = {lat: "+allStations.get(nearestStation).stationLocation.Lat
                    +   ", lng: "+allStations.get(nearestStation).stationLocation.Lon+"};");
            markers += ("  var station_marker"+i+" = new google.maps.Marker({\n"
                    +   "    position: station_position"+i+",\n"
                    +   "    map: map,\n");
            markers += ("    icon: { url: 'http://mt.google.com/vt/icon?color=ff004C13&name=icons/spotlight/spotlight-waypoint-blue.png' },\n");
            markers += ("    title: '"+allStations.get(nearestStation).stationName+"'\n"
                    +   "  });");
            markers += ("  station_marker"+i+".addListener('click', function() {\n"
                    +   "    station_infowindow"+i+".open(map, station_marker"+i+");\n"
                    +   "  });");
            //This makes the info box visible when the map opens
            //markers += ("station_infowindow"+i+".open(map, station_marker"+i+");\n");
        }
        return markers;
    }

    private static String makeSunEventBoxes(List<stepWeather> stepDataBase, List<String> sunEvents) {
        String markers = "";
        markers += ("  var sun_events = '<div id=\"sun_content\"><pre>'+")+FmiQueryTest.newLine;
        for(String s : sunEvents){markers += ("'"+s.replaceAll(",", ".")+"<br>'+")+FmiQueryTest.newLine;}
        markers += ("'</pre></div>';")+FmiQueryTest.newLine;
        markers += ("  var sun_infowindow = new google.maps.InfoWindow({\n" 
                +   "    content: sun_events,\n"
                +   "    pixelOffset: new google.maps.Size(250, 25)\n"
                +   "  });");
        markers += ("  var sun_position = {lat: "
                +   (stepDataBase.get(0).StartLocation.Lat-0.05)
                +   ", lng: "
                +   (stepDataBase.get(0).StartLocation.Lon+0.05)
                +"};");
        markers += ("  var sun_marker = new google.maps.Marker({\n"
                +   "    position: sun_position,\n"
                +   "    map: map,\n"
                +   "    draggable:true,\n");
        markers += ("    icon: { url: 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png' },\n");
        markers += ("    title: 'Sun events'\n"
                +   "  });");
        markers += ("  sun_marker.addListener('click', function() {\n"
                +   "    sun_infowindow.open(map, sun_marker);\n"
                +   "  });");
        //This makes the info box visible when the map opens
        markers += ("sun_infowindow.open(map, sun_marker);\n");
        //Skip the first sunEvent, which is trip start time
        for (int i=1;i<sunEvents.size();++i){
            String coords = sunEvents.get(i).substring(sunEvents.get(i).indexOf("(") + 1, sunEvents.get(i).indexOf(")"));
            String[] parts = coords.split(" ");
            //System.out.println(parts[1].trim()); // STRING_VALUES
            markers += ("  var sun_events"+i+" = '<div id=\"sun_content"+i+"\"><pre>'+")+FmiQueryTest.newLine;
            markers += ("'"+sunEvents.get(i).substring(0, sunEvents.get(i).indexOf("("))+"<br>'+");
            markers += ("'</pre></div>';")+FmiQueryTest.newLine;
            markers += ("  var sun_infowindow"+i+" = new google.maps.InfoWindow({\n" 
                    +   "    content: sun_events"+i+",\n"
                    +   "    pixelOffset: new google.maps.Size(180, 25)\n"
                    +   "  });");
            markers += ("  var sun_position"+i+" = {lat: "
                    +   parts[0].replaceAll(",", ".")
                    +   ", lng: "
                    +   parts[1].replaceAll(",", ".")
                    +"};");
            markers += ("  var sun_marker"+i+" = new google.maps.Marker({\n"
                    +   "    position: sun_position"+i+",\n"
                    +   "    map: map,\n");
            markers += ("    icon: { url: 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png' },\n");
            markers += ("    title: 'Sun event "+i+"'\n"
                    +   "  });");
            markers += ("  sun_marker"+i+".addListener('click', function() {\n"
                    +   "    sun_infowindow"+i+".open(map, sun_marker"+i+");\n"
                    +   "  });");
            //This makes the info box visible when the map opens
            markers += ("sun_infowindow"+i+".open(map, sun_marker"+i+");\n");
        }
        return markers;
    }

    private static String makeRouteWeatherBoxes(List<stepWeather> stepDataBase, Map routeWeather) {
        String markers = "";
//        List<Integer> neededStations = new ArrayList<>();
//        for (int i=0;i<stepDataBase.size();++i){neededStations.add(stepDataBase.get(i).nearestStation);}
//        Set<Integer> uniqueSet = new HashSet<>(neededStations);
//        List<Integer> uniqueEntries = new ArrayList<>();
//        for (Integer a : uniqueSet){uniqueEntries.add(a);}
        for (int i=0;i<stepDataBase.size();++i){
//            Integer nearestStation = uniqueEntries.get(i);
            markers += ("  var weatherpoint"+i+" = '<div id=\"weathercontent"+i+"\"><pre>'+")+FmiQueryTest.newLine;
//            markers += ("'Station:     "+allStations.get(nearestStation).stationName+"<br>'+")+FmiQueryTest.newLine;
//            markers += ("'Coordinates: "+allStations.get(nearestStation).stationLocation.Lat
//                    +   " "+allStations.get(nearestStation).stationLocation.Lon+"<br>'+")+FmiQueryTest.newLine;
            markers += ("'"+DayLightTime.doubleTimeToString(stepDataBase.get(i).timeAsDouble)+"<br>'+");
//            for (int j=0;j<stepDataBase.size();++j){
//                if (stepDataBase.get(j).nearestStation==uniqueEntries.get(i)){
//                    markers += DayLightTime.doubleTimeToString(stepDataBase.get(j).timeAsDouble)+" ";
//                }
//            }
            markers += (extractWeatherAtStep(stepDataBase,i,routeWeather))+FmiQueryTest.newLine;
//            markers += ("'<br>'+")+FmiQueryTest.newLine;
            markers += ("'</pre></div>';")+FmiQueryTest.newLine;
            markers += ("  var weather_infowindow"+i+" = new google.maps.InfoWindow({\n" 
                    +   "    content: weatherpoint"+i+",\n"
                    +   "    maxWidth: 400,\n"
                    +   "    pixelOffset: new google.maps.Size(-125, 25)\n"
                    +   "  });");
            markers += ("  var weather_position"+i+" = {lat: "+stepDataBase.get(i).StartLocation.Lat
                    +   ", lng: "+stepDataBase.get(i).StartLocation.Lon+"};");
            markers += ("  var weather_marker"+i+" = new google.maps.Marker({\n"
                    +   "    position: weather_position"+i+",\n"
                    +   "    map: map,\n");
            markers += ("    icon: { url: 'http://mt.google.com/vt/icon?psize=30&font=fonts/arialuni_t.ttf&color=ff304C13&name=icons/spotlight/spotlight-waypoint-a.png&ax=43&ay=48&text=%E2%80%A2' },\n");
            markers += ("    title: 'Weather point "+i+"'\n"
                    +   "  });");
            markers += ("  weather_marker"+i+".addListener('click', function() {\n"
                    +   "    weather_infowindow"+i+".open(map, weather_marker"+i+");\n"
                    +   "  });");
            //This makes the info box visible when the map opens
            //markers += ("weather_infowindow"+i+".open(map, weather_marker"+i+");\n");
        }
        return markers;
    }

    private static String extractWeatherAtStep(List<stepWeather> stepDataBase, int i, Map routeWeather) {
        String weatherBoxContent = "";
        stepWeather step = stepDataBase.get(i);
        stepWeather nextstep = step;
        if (i<stepDataBase.size()-1){
            nextstep = stepDataBase.get(i+1);
        }
        String isoTimeNowBegin = FmiQueryTest.df_iso.format(new Date(
                FmiQueryTest.startTimeMillis+step.totalDuration*1000));

        List<String> paramsToShow = new ArrayList<>();
        paramsToShow.add("Temperature");
        paramsToShow.add("Pressure");
        paramsToShow.add("Humidity");
        paramsToShow.add("DewPoint");
        paramsToShow.add("Precipitation1h");
        paramsToShow.add("WindSpeedMS");
        paramsToShow.add("WindDirection");
        paramsToShow.add("TotalCloudCover");
        //WeatherSymbol3, Pressure, DewPoint, CloudSymbol(null), TotalCloudCover
        //MaximumWind(null), WindVMS, WindUMS, PrecipitationType(NaN), WindDirection
        //WindSpeedMS, Precipitation1h, Humidity, Temperature
        for (String param : paramsToShow){
            double result = getParameterAverage(routeWeather, step, nextstep, param);
            weatherBoxContent += String.format("'%-16s%s", param, FmiQueryTest.df_oneDecimal.format(result));
            switch (param){
                case "Temperature"      : weatherBoxContent += " °C";   break;
                case "DewPoint"         : weatherBoxContent += " °C";   break;
                case "WindSpeedMS"      : weatherBoxContent += " m/s";  break;
                case "Pressure"         : weatherBoxContent += " mbar"; break;
                case "Humidity"         : weatherBoxContent += " %";    break;
                case "TotalCloudCover"  : weatherBoxContent += " %";    break;
                case "WindDirection"    : weatherBoxContent += "° "+directionToCardinal(result); break;
                case "Precipitation1h"  : weatherBoxContent += " mm";   break;
                default                 : break;
            }
            weatherBoxContent += "<br>'+";
        }
        try {
            int weatherSymbol = intValue(Double.parseDouble(
                    routeWeather.get(step.nearestStation+"-"+isoTimeNowBegin+"-WeatherSymbol3").toString()));
            weatherBoxContent += String.format("'%-16s%s", "WeatherSymbol3", 
                    getWeatherSymbolDescription(weatherSymbol))+"<br>'+";
        } catch (NullPointerException ex) {
            //weatherBoxContent += "'WeatherSymbol3  : NA<br>'+";
        }
        return weatherBoxContent;
    }

    private static double getParameterAverage(Map routeWeather, stepWeather step, stepWeather nextstep, String param) {
        double result = NaN;
        try {
            String isoTimeNowBegin = FmiQueryTest.df_iso.format(new Date(
            FmiQueryTest.startTimeMillis+step.totalDuration*1000));
            String isoTimeNowEnd = FmiQueryTest.df_iso.format(new Date(
            FmiQueryTest.startTimeMillis+nextstep.totalDuration*1000));
            String isoTimeNextBegin = FmiQueryTest.df_iso.format(new Date(
            FmiQueryTest.startTimeMillis+(step.totalDuration+3600)*1000));
            String isoTimeNextEnd = FmiQueryTest.df_iso.format(new Date(
            FmiQueryTest.startTimeMillis+(step.totalDuration+3600)*1000));

            //System.out.println(routeWeather.get(step.nearestStation+"-"+isoTimeNowBegin+"-"+param).toString());
            double beginNow = Double.parseDouble(routeWeather.get(step.nearestStation+"-"+isoTimeNowBegin+"-"+param).toString());
            //System.out.println(routeWeather.get(step.nearestStation+"-"+isoTimeNextBegin+"-"+param).toString());
            double beginNext = Double.parseDouble(routeWeather.get(step.nearestStation+"-"+isoTimeNextBegin+"-"+param).toString());
            double beginInterp = beginNow + step.timeAsDouble%1*(beginNext-beginNow);
            //System.out.println(step.timeAsDouble%1);
            //System.out.println(beginInterp);
            double endNow = Double.parseDouble(routeWeather.get(nextstep.nearestStation+"-"+isoTimeNowEnd+"-"+param).toString());
            double endNext = Double.parseDouble(routeWeather.get(nextstep.nearestStation+"-"+isoTimeNextEnd+"-"+param).toString());
            double endInterp = endNow + nextstep.timeAsDouble%1*(endNext-endNow);
            //System.out.println(endInterp);
            result = (beginInterp+endInterp)/2;
            //System.out.println(result);
        } catch (NullPointerException ex) {
            //obsolete
            //weatherBoxContent += "'"+param+": NA<br>'+";
        }
        return result;
    }
    
    private static String directionToCardinal(double result) {
        String cardinal = "";
        if      (result >= 0 && result < 11.25){cardinal = "N";} 
        else if (result >= 11.25 && result < 33.75){cardinal = "NNE";} 
        else if (result >= 33.75 && result < 56.25){cardinal = "NE";} 
        else if (result >= 56.25 && result < 78.75){cardinal = "ENE";} 
        else if (result >= 78.75 && result < 101.25){cardinal = "E";} 
        else if (result >= 101.25 && result < 123.75){cardinal = "ESE";} 
        else if (result >= 123.75 && result < 146.25){cardinal = "SE";} 
        else if (result >= 146.25 && result < 168.75){cardinal = "SSE";} 
        else if (result >= 168.75 && result < 191.25){cardinal = "S";} 
        else if (result >= 191.25 && result < 213.75){cardinal = "SSW";} 
        else if (result >= 213.75 && result < 236.25){cardinal = "SW";} 
        else if (result >= 236.25 && result < 258.75){cardinal = "WSW";} 
        else if (result >= 258.75 && result < 281.25){cardinal = "W";} 
        else if (result >= 281.25 && result < 303.75){cardinal = "WNW";} 
        else if (result >= 303.75 && result < 326.25){cardinal = "NW";} 
        else if (result >= 326.25 && result < 348.75){cardinal = "NNW";} 
        else if (result >= 348.75 && result <= 360){cardinal = "N";} 
        else    {cardinal = "-";}
        return cardinal;
    }

    //case 1  : symbol += "selkeää"; break;
    //case 2  : symbol += "puolipilvistä"; break;
    //case 21 : symbol += "heikkoja sadekuuroja"; break;
    //case 22 : symbol += "sadekuuroja"; break;
    //case 23 : symbol += "voimakkaita sadekuuroja"; break;
    //case 3  : symbol += "pilvistä"; break;
    //case 31 : symbol += "heikkoa vesisadetta"; break;
    //case 32 : symbol += "vesisadetta"; break;
    //case 33 : symbol += "voimakasta vesisadetta"; break;
    //case 41 : symbol += "heikkoja lumikuuroja"; break;
    //case 42 : symbol += "lumikuuroja"; break;
    //case 43 : symbol += "voimakkaita lumikuuroja"; break;
    //case 51 : symbol += "heikkoa lumisadetta"; break;
    //case 52 : symbol += "lumisadetta"; break;
    //case 53 : symbol += "voimakasta lumisadetta"; break;
    //case 61 : symbol += "ukkoskuuroja"; break;
    //case 62 : symbol += "voimakkaita ukkoskuuroja"; break;
    //case 63 : symbol += "ukkosta"; break;
    //case 64 : symbol += "voimakasta ukkosta"; break;
    //case 71 : symbol += "heikkoja räntäkuuroja"; break;
    //case 72 : symbol += "räntäkuuroja"; break;
    //case 73 : symbol += "voimakkaita räntäkuuroja"; break;
    //case 81 : symbol += "heikkoa räntäsadetta"; break;
    //case 82 : symbol += "räntäsadetta"; break;
    //case 83 : symbol += "voimakasta räntäsadetta"; break;
    //case 91 : symbol += "utua"; break;
    //case 92 : symbol += "sumua"; break;
    private static String getWeatherSymbolDescription(int number) {
        String symbol = "";
        switch (number){
            case 1  : symbol += "Clear"; break;
            case 2  : symbol += "Partly cloudy"; break;
            case 21 : symbol += "Light rain showers"; break;
            case 22 : symbol += "Rain showers"; break;
            case 23 : symbol += "Heavy rain showers"; break;
            case 3  : symbol += "Cloudy"; break;
            case 31 : symbol += "Light rain"; break;
            case 32 : symbol += "Rain"; break;
            case 33 : symbol += "Heavy rain"; break;
            case 41 : symbol += "Light snow showers"; break;
            case 42 : symbol += "Snow showers"; break;
            case 43 : symbol += "Heavy snow showers"; break;
            case 51 : symbol += "Light snow"; break;
            case 52 : symbol += "Snow"; break;
            case 53 : symbol += "Heavy snow"; break;
            case 61 : symbol += "Thunder showers"; break;
            case 62 : symbol += "Heavy thunder showers"; break;
            case 63 : symbol += "Thunder"; break;
            case 64 : symbol += "Heavy thunder"; break;
            case 71 : symbol += "Light sleet showers"; break;
            case 72 : symbol += "Sleet showers"; break;
            case 73 : symbol += "Heavy sleet showers"; break;
            case 81 : symbol += "Light sleet"; break;
            case 82 : symbol += "Sleet"; break;
            case 83 : symbol += "Heavy sleet"; break;
            case 91 : symbol += "Mist"; break;
            case 92 : symbol += "Fogg"; break;
            default : symbol += "-"; break;
        }
        return symbol;
    }
    
    // http://www.java2s.com/Code/Java/Chart/JFreeChartCompassFormatDemo.htm
    //        int count = 100;
    //        for (int i = 0; i < count; i++) {
    //            double temperature = (Math.random() - 0.5) * 20;
    //            s1.add(start, temperature);
    //            start = start.next();
    //        }
    //        start = new Minute();
    //        for (int i = 0; i < count; i++) {
    //            double rain = Math.random();
    //            s2.add(start, rain);
    //            start = start.next();
    //        }
    static String drawTempRainChart(List<stepWeather> stepDataBase, Map routeWeather){
        //First, fetch the real data
        List<Double> times = new ArrayList<>();
        List<Double> temps = new ArrayList<>();
        List<Double> rains = new ArrayList<>();
        List<Double> lights = new ArrayList<>();
        for (int i=0;i<stepDataBase.size();++i){
            stepWeather step = stepDataBase.get(i);
            stepWeather nextstep = step;
            if (i<stepDataBase.size()-1){
                nextstep = stepDataBase.get(i+1);
            }
            times.add(step.timeAsDouble);
            temps.add(getParameterAverage(routeWeather, step, nextstep, "Temperature"));
            rains.add(getParameterAverage(routeWeather, step, nextstep, "Precipitation1h"));
            lights.add(Math.pow(Math.abs(step.timeAsDouble%24-((step.sunRise+step.sunSet)/2))/((step.sunSet-step.sunRise)/2),5));
        }
        
        double zeroPoint = times.get(0);        
        // Make first dataset
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries s1 = new TimeSeries("Temperature °C", Minute.class);
        // Make second dataset
        final TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        final TimeSeries s2 = new TimeSeries("Rain (mm)", Minute.class);
        // Make third dataset
        //final TimeSeriesCollection dataset3 = new TimeSeriesCollection();
        final TimeSeries s3 = new TimeSeries("Darkness", Minute.class);
        // Show data per minute
        RegularTimePeriod start = new Minute(new Date(FmiQueryTest.startTimeMillis));
        for (int i=0;i<times.size()-1;++i){
            double time = times.get(i);
            double nexttime = times.get(i+1);
            int firstMinute = intValue((time-zeroPoint)*60);
            int currentMinute = firstMinute;
            int lastMinute = intValue((nexttime-zeroPoint)*60);
            while (currentMinute < lastMinute){
                s1.add(start, temps.get(i)
                        +((double)currentMinute-firstMinute)/(lastMinute-firstMinute)*(temps.get(i+1)-temps.get(i)));
                s2.add(start, (rains.get(i)+rains.get(i+1))/2);
                        //+((double)currentMinute-firstMinute)/(lastMinute-firstMinute)*(rains.get(i+1)-rains.get(i)));
                s3.add(start, lights.get(i)
                        +((double)currentMinute-firstMinute)/(lastMinute-firstMinute)*(lights.get(i+1)-lights.get(i)));
                ++currentMinute;
                start = start.next();
            }
        }
        dataset.addSeries(s1);
        dataset2.addSeries(s3);
        dataset2.addSeries(s2);
        
        // Initialize chart
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Temperature and rain", 
            "Time", 
            "Temperature °C",
            dataset,
            true, // legend? 
            true, // tooltips? 
            false // URLs? 
        );
        final XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        
        // configure the range axis to display first dataset...
        final ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();
        rangeAxis.setRange(-30, 30);
        final TickUnits units = new TickUnits();
        units.add(new NumberTickUnit(10.0));
        units.add(new NumberTickUnit(1.0));
        units.add(new NumberTickUnit(0.1));
        rangeAxis.setStandardTickUnits(units);
        //rangeAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 16));
        
        // add the secondary dataset/renderer/axis
        plot.setRangeAxis(rangeAxis);
        final XYItemRenderer renderer2 = new XYAreaRenderer();
        final ValueAxis axis2 = new NumberAxis("Rain (mm) ");
        axis2.setRange(0, 2);
        axis2.setStandardTickUnits(units);
        //axis2.setTickLabelFont(new Font("SansSerif", Font.BOLD, 16));
        renderer2.setSeriesPaint(0, new Color(0, 0, 255, 128));
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);
        plot.setRangeAxis(1, axis2);
        plot.mapDatasetToRangeAxis(1, 1);
        final XYItemRenderer renderer3 = new XYAreaRenderer();
        renderer3.setSeriesPaint(0, new Color(0, 0, 0, 64));
        //plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer3);
        plot.setRangeAxis(1, axis2);
        plot.mapDatasetToRangeAxis(1, 1);
        
        chart.setBackgroundPaint(Color.white);
        //plot.setBackgroundPaint(Color.lightGray);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.black);
        
//        final TextTitle subtitle = new TextTitle("An area chart demonstration.  We use this "
//            + "subtitle as an example of what happens when you get a really long title or "
//            + "subtitle.");
//        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
//        subtitle.setPosition(RectangleEdge.TOP);
////        subtitle.setSpacer(new Spacer(Spacer.RELATIVE, 0.05, 0.05, 0.05, 0.05));
//        subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
//        chart.addSubtitle(subtitle);
        
        // Produce chart
//        ChartFrame frame = new ChartFrame("Tamperature and rain", chart);
//        frame.pack();
//        RefineryUtilities.centerFrameOnScreen(frame);
//        frame.setVisible(true);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ChartUtilities.writeChartAsPNG(bas,chart,600,400);
        } catch (IOException ex) {
            Logger.getLogger(WeatherTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] byteArray = bas.toByteArray();
        String baseCode = Base64.encode(byteArray);
        return "<img width=\"480\" alt=\"Temperature and rain\" src=\"data:image/png;base64,"+baseCode.trim()+"\" />";
    }

    private static String makeChartBoxes(List<stepWeather> stepDataBase, Map routeWeather) {
        String markers = "";
        markers += ("  var chart = '<div id=\"chart_content\">'+")+FmiQueryTest.newLine;
        markers += ("  '"+WeatherTools.drawTempRainChart(stepDataBase, routeWeather)+"'+")+FmiQueryTest.newLine;
        markers += ("  '</div>';")+FmiQueryTest.newLine;
        markers += ("  var chart_infowindow = new google.maps.InfoWindow({\n" 
                +   "    content: chart,\n"
                +   "    pixelOffset: new google.maps.Size(-280, 25)\n"
                +   "  });");
        markers += ("  var chart_position = {lat: "
                +   (stepDataBase.get(0).StartLocation.Lat-0.05)
                +   ", lng: "
                +   (stepDataBase.get(0).StartLocation.Lon-0.05)
                +"};");
        markers += ("  var chart_marker = new google.maps.Marker({\n"
                +   "    position: chart_position,\n"
                +   "    map: map,\n"
                +   "    draggable:true,\n");
        markers += ("    icon: { url: 'http://maps.google.com/mapfiles/ms/icons/orange-dot.png' },\n");
        markers += ("    title: 'Temperature and rain'\n"
                +   "  });");
        markers += ("  chart_marker.addListener('click', function() {\n"
                +   "    chart_infowindow.open(map, chart_marker);\n"
                +   "  });");
        //This makes the info box visible when the map opens
        markers += ("chart_infowindow.open(map, chart_marker);\n");
        return markers;
    }
}

public class FmiQueryTest {
    //INPUTS AND SETTINGS
    static String gKey = "AIzaSyBceB0at3czeTt5BWnL7FQvmf_AEBxLjys";
    static String gDirectionQuery = "https://maps.googleapis.com/maps/api/directions/xml?";
    static String gOrigin = "Helsinki";
    static String gDestination = "Tampere";
    static String gMode = "driving";
    static String gWaypoints = "";
    static int gRouteOption = 0;
//    static String gShare = "https://www.google.fi/maps/dir/Helsinki/Ilomantsi/Kemi/Utsjoki/@64.7725729,18.7906882,5z/am=t/data=!4m26!4m25!1m5!1m1!1s0x46920bc796210691:0xcd4ebd843be2f763!2m2!1d24.9410248!2d60.1733244!1m5!1m1!1s0x469c17bfcf5576db:0x40146d63c75ada0!2m2!1d30.9394911!2d62.6678853!1m5!1m1!1s0x442ab2b34f9628f7:0x40146d63c75b0f0!2m2!1d24.5643979!2d65.7362771!1m5!1m1!1s0x45cc09e00b8b9557:0x744946de9aa7ade0!2m2!1d27.0285297!2d69.9090465!3e0?hl=en";
//    static String gShare = "https://www.google.fi/maps/dir/Helsinki/Ilomantsi/Kemi/Utsjoki/@64.7809149,18.7736186,5z/am=t/data=!3m1!4b1!4m26!4m25!1m5!1m1!1s0x46920bc796210691:0xcd4ebd843be2f763!2m2!1d24.9410248!2d60.1733244!1m5!1m1!1s0x469c17bfcf5576db:0x40146d63c75ada0!2m2!1d30.9394911!2d62.6678853!1m5!1m1!1s0x442ab2b34f9628f7:0x40146d63c75b0f0!2m2!1d24.5643979!2d65.7362771!1m5!1m1!1s0x45cc09e00b8b9557:0x744946de9aa7ade0!2m2!1d27.0285297!2d69.9090465!3e0?hl=en";
    static String gShare = "";
    static String gWebShareBase = "https://www.google.fi/maps/dir/";
    static String gWebShareRouteOptPar = "!5i";
    static String fmiKey = "2dcc4c13-ee27-4471-b5b1-73623eb933e4";
    static String fmiBase = "http://data.fmi.fi/fmi-apikey/";
    static String fmiMiddle = "/wfs?request=getFeature&storedquery_id=";
    static String fmiQueryCities = "fmi::forecast::hirlam::surface::cities::simple";
    static String fmiQueryObsStations = "fmi::forecast::hirlam::surface::obsstations::simple";
    static String newLine = System.getProperty("line.separator");//This will retrieve line separator dependent on OS.
    static File stationFileName = new File("stationnames.txt");
    static File routeFileName = new File("savedroutes.txt");
    static Integer routeDur = 0;
    static DateFormat df_short = new SimpleDateFormat("HH:mm");
    static DateFormat df_iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:00:00'Z'");
    static DateFormat df_daycode = new SimpleDateFormat("yyyyMMdd");
    static DecimalFormat df_oneDecimal = new DecimalFormat("0.0");
    static DecimalFormat df_fiveDecimal = new DecimalFormat("0.00000");
    static TimeZone tz = TimeZone.getTimeZone("GMT+2");
    static int daysToStoreWeatherData = 1;
    static double startTimeOffset = 0;
    static long startTimeMillis = System.currentTimeMillis()+(intValue(Math.floor(startTimeOffset*60*60*1000)));
    static int refreshInterval = 15*60; //seconds
    static File resultFileName = new File("/home/navinoob/public-www/result_"+System.currentTimeMillis()+".html");
        
    public static void main(String[] args) {
        df_short.setTimeZone(tz);
        df_iso.setTimeZone(tz);
        df_daycode.setTimeZone(tz);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        df_fiveDecimal.setDecimalFormatSymbols(otherSymbols);
        String startTime = df_short.format(new Date(startTimeMillis));
        System.out.println("startTime: "+startTime);
        
        //Clean up old weather data 
        //**********************************************************************
        FileSystemTools.cleanupOldWeatherData(daysToStoreWeatherData);

        //Google query
        //**********************************************************************
        if (gShare.equals("")){
            Scanner input = new Scanner(System.in);
            System.out.println("Paste Google Directions Share:");
            gShare = input.nextLine();
        }
        String gQuery = Parser.getQueryFromShare(gShare);
        System.out.println("Google query URL: "+gQuery);
        
        //Check if we already have this route
        //Valid only if the route option is 0 (default)
        //Because otherwise we cannot be sure we already have the optional route
        List<routeStep> gSteps = new ArrayList<>();
        if(FileSystemTools.isSavedRoute(gQuery) && gRouteOption==0){
            System.out.println("Route found from saved list. Loading.");
            gSteps = FileSystemTools.loadSavedRoute(gQuery);
        }
        else {
            gSteps = Parser.getSteps(gQuery);
            if (gRouteOption==0){
                System.out.println("Saving new route to list.");
                FileSystemTools.saveRoute(gQuery, gSteps);
            }
        }
        
        //Compile route table with current settings
        //**********************************************************************
        List<routeStep> routeData = RouteTools.compileRoute(gSteps, refreshInterval);        
        String endTime = df_short.format(new Date(startTimeMillis + routeDur * 1000));
        System.out.println("endTime: "+endTime);
        //Forecast from FMI is only for 48h - warning if we are going over
        //Or is it 54h? http://ilmatieteenlaitos.fi/avoin-data-saaennustedata-hirlam
        if(((startTimeMillis + routeDur*1000) - System.currentTimeMillis()) / (1000*60*60)>48){
            System.out.println("**************************************************"+newLine
                    +"WARNING:"+newLine
                    +"Weather forecast available only for 48 hours"+newLine
                    +"**************************************************");
        }
        
        //Prepare time and file variables
        //**********************************************************************
        String nowAsISO = df_iso.format(new Date());
        System.out.println("Start ISO time: "+nowAsISO);
        double timeMarginal = routeDur*1.2+3600;
        String endTimeForFmi = df_iso.format(new Date(startTimeMillis + (intValue(timeMarginal)) * 1000));
        String endTimeForFile = df_iso.format(new Date(startTimeMillis + (intValue(routeDur+3600)) * 1000));
        System.out.println("End ISO time:   "+endTimeForFmi);
        String fmiParam = new StringBuilder("&starttime=").append(nowAsISO)
                .append("&endtime=").append(endTimeForFmi).toString();
        File weatherDataFileNameFirst = new File("weather"+nowAsISO.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        File weatherDataFileNameLast = new File("weather"+endTimeForFmi.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        File weatherDataFileNameStart = new File("weather"+(df_iso.format(new Date(startTimeMillis))).replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        File weatherDataFileNameEnd = new File("weather"+endTimeForFile.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
        List<stationData> allStations = new ArrayList<>();
        List<stationData> fmiData = new ArrayList<>();
        List<String> savedFileTimes = new ArrayList<>();
        
        //**********************************************************************
        //Check if we already have the weather data
        //**********************************************************************
        if(!weatherDataFileNameStart.exists() || !weatherDataFileNameEnd.exists()){
            //FMI query
            //**********************************************************************
            String fmiCities        = new StringBuilder(fmiBase).append(fmiKey)
                    .append(fmiMiddle).append(fmiQueryCities)
                    .append(fmiParam).toString();
            String fmiObsStations   = new StringBuilder(fmiBase).append(fmiKey)
                    .append(fmiMiddle).append(fmiQueryObsStations)
                    .append(fmiParam).toString();
            //System.out.println("FMI cities URL: "+fmiCities);
            //System.out.println("FMI obsstations URL: "+fmiObsStations);

            //Collect weather data from FMI
            //**********************************************************************
            System.out.print("FMI data:"+newLine+fmiCities+newLine+"Loading and processing...");
            fmiData.addAll(Parser.getStations(fmiCities));
            System.out.println("SUCCESS.");
            System.out.print("FMI data:"+newLine+fmiObsStations+newLine+"Loading and processing...");
            fmiData.addAll(Parser.getStations(fmiObsStations));
            System.out.println("SUCCESS.");

            //Get unique stations
            //**********************************************************************
            List<stationData> uniqueStations = ToolBox.getUniqueStations(fmiData);
            System.out.println("Parsed stations count: " + uniqueStations.size());

            //Save or load stations
            //**********************************************************************
            List<stationData> savedStations = new ArrayList<>();
            if (!stationFileName.exists()){
                //Save current parsed stations to file
                FileSystemTools.saveObjectToFile(uniqueStations, stationFileName);
            }
            else {
                //Or if the stations were already saved, load them
                System.out.println("Station information file found: "+stationFileName);
                System.out.print("Loading...");
                savedStations = FileSystemTools.loadStationsFromFile(stationFileName);
                System.out.println("DONE.");
                System.out.println("Loaded stations count: " + savedStations.size());
            }

            //Merge station information
            //**********************************************************************
            System.out.println("Merging station information.");
            savedStations.addAll(uniqueStations);
            allStations = ToolBox.getUniqueStations(savedStations);
            System.out.println("Merged stations count: " + allStations.size());

            //Find names for stations
            //**********************************************************************
            String gMapsGeoCode = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=";
            //for (stationData station : allStations){
            for(int i=0; i < allStations.size();i++){
                if (allStations.get(i).stationName.equals("")){
                    gQuery = new StringBuilder(gMapsGeoCode).append(allStations.get(i).stationLocation.Lat)
                            .append(",").append(allStations.get(i).stationLocation.Lon)
                            .append("&key=").append(gKey).toString();
                    System.out.println("Google query URL: "+gQuery);

                    allStations.get(i).stationName = Parser.getStationName(gQuery);
                }
            }
            //System.out.println("Station names parsed.");
            Collections.sort(allStations);

            //Print stations and separate them for saving
            //**********************************************************************
            List<stationData> onlyStations = new ArrayList<>();
            //int indeksi = 0;
            List<weatherData> weatherPoint = new ArrayList<>();
            weatherPoint.add(0, new weatherData("","",""));
            for(stationData station : allStations){
                //System.out.format("%-4s%-30s%-10s%-10s%n",
                //                    indeksi,station.stationName,station.stationLocation.Lat,station.stationLocation.Lon);
                //++indeksi;
                onlyStations.add(new stationData(station.stationLocation, station.stationName, weatherPoint));
            }

            //Save station names
            //**********************************************************************
            System.out.println("Saving station names.");
            FileSystemTools.saveObjectToFile(onlyStations, stationFileName);

            //Save weather dataset
            //**********************************************************************
            //Compute file names between start and end
            System.out.println("Saving weather data...");
            long currentTimeAsDouble = System.currentTimeMillis();
            int hoursPassed = intValue(Math.floor(currentTimeAsDouble-startTimeMillis)/1000/60/60);
            File weatherDataFileNameTemp = weatherDataFileNameFirst;
            while (!weatherDataFileNameTemp.equals(weatherDataFileNameLast)){
                String savedFileTime = df_iso.format(new Date(startTimeMillis + ((hoursPassed*3600) * 1000)));
                savedFileTimes.add(savedFileTime);
                weatherDataFileNameTemp = new File("weather"+savedFileTime.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
                //System.out.println("Weather data file: "+weatherDataFileNameTemp);
                //This if we don't actually maybe want
                //if (!weatherDataFileNameTemp.exists()){
                    List<stationData> thisHourWeather = FileSystemTools.extractHourOfWeatherData(savedFileTime, fmiData);
                    //System.out.println("Saving: "+weatherDataFileNameTemp);
                    FileSystemTools.saveObjectToFile(thisHourWeather, weatherDataFileNameTemp);
                //}
                ++hoursPassed;
            }
        }
        //If we have weather data saved, definitely we have the stations also
        //**********************************************************************
        else {
            System.out.println("Loading weather data...");
            File weatherDataFileNameTemp = weatherDataFileNameStart;
            int hoursPassed = 0;
            while (!weatherDataFileNameTemp.equals(weatherDataFileNameEnd)){
                String savedFileTime = df_iso.format(new Date(startTimeMillis + ((hoursPassed*3600) * 1000)));
                savedFileTimes.add(savedFileTime);
                weatherDataFileNameTemp = new File("weather"+savedFileTime.replaceAll("[^A-Za-z0-9 ]", "")+".txt");
                System.out.println("Weather data file: "+weatherDataFileNameTemp);
                if (weatherDataFileNameTemp.exists()){
                    fmiData.addAll(FileSystemTools.loadStationsFromFile(weatherDataFileNameTemp));
                }
                ++hoursPassed;
            }
            allStations = FileSystemTools.loadStationsFromFile(stationFileName);
            System.out.println("DONE.");
        }
        
        //Find closest weather stations in route points and extract their data
        //**********************************************************************
        System.out.println("Calculating nearest stations in route points:");
        List<Integer> neededStations = new ArrayList<>();
        for (routeStep step : routeData){
            distance[] stationDistances = RouteTools.calculateStationDistances(step.StartLocation, allStations);
            System.out.format("%-6s%.5f, %.5f  ","Step: ",step.StartLocation.Lat,step.StartLocation.Lon);
            for( int i=0;i<1;i++){
                System.out.format("%-9s%-5s%-20s%.5f%n",
                                    "Station: ",stationDistances[i].stationNum,
                                    allStations.get(stationDistances[i].stationNum).stationName,
                                    stationDistances[i].stationDistance);
            }
            neededStations.add(stationDistances[0].stationNum);
        }
        System.out.println("Needed stations: "+neededStations.toString().trim());
        //Remove duplicates from needed stations list
        Set<Integer> uniqueEntries = new HashSet<Integer>(neededStations);
        //Extract weather data from needed stations
        Map routeWeather = Collections.synchronizedMap(new HashMap());
        routeWeather = WeatherTools.extractNeededStations(uniqueEntries, fmiData, allStations);
        
        //Find what fields we have
        List<String> allParameters = new ArrayList<>();
        for (int i=0;i<fmiData.size();++i){
            allParameters.add(fmiData.get(i).weatherData.get(0).parameterName);
        }
        Set<String> uniqueParameters = new HashSet<String>(allParameters);
        for(String par : uniqueParameters){
            for(Integer num : uniqueEntries){
                for(String time : savedFileTimes){
                    //System.out.format("%-5s%-25s%-35s%s%n",num,time,par,routeWeather.get(num+"-"+time+"-"+par));
                }
            }
        }
        
        // Build the final data table
        //**********************************************************************
        List<stepWeather> stepDataBase = new ArrayList<>();
        stepDataBase = RouteTools.combineRouteDatabase(routeData, neededStations, allStations);
        
        //Find sunrise and sunset times during the route
        //**********************************************************************
        List<String> sunEvents = DayLightTime.calculateSunEvents(stepDataBase);
        for(String s : sunEvents){System.out.println(s.replaceAll(",", "."));}
        
        //Make a webpage to show the weather data
        //**********************************************************************
        WeatherTools.makeResultHtml(stepDataBase, allStations, routeWeather, sunEvents);
    }
}
