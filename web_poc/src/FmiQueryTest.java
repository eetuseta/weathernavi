/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmiquerytest;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

class Coordinates
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
class routeStep
{
    public Coordinates StartLocation;
    public Coordinates EndLocation;
    public int Distance;
    public int Duration;
    // constructor
    public routeStep(Coordinates StartLocation, Coordinates EndLocation,
            int Distance, int Duration)
    {
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
        this.Distance = Distance;
        this.Duration = Duration;
    }
}
class routePoint
{
    public Coordinates Coords;
    public Coordinates nearestStation;
    // constructor
    public routePoint(Coordinates Coords, Coordinates nearestStation)
    {
        this.Coords = Coords;
        this.nearestStation = nearestStation;
    }
}
class stationData
{
    public Coordinates stationLocation;
    public String stationName;
    public String weatherData;
    // constructor
    public stationData(Coordinates stationLocation, String stationName,
            String weatherData)
    {
        this.stationLocation = stationLocation;
        this.stationName = stationName;
        this.weatherData = weatherData;
    }
}

class Extrapolator
{
    //    extrapolate_step(step,curr_step,curr_coords)
    //    start_point = curr_step
    //    end_point = curr_step + step.duration
    //    for i in (start_point:end_point)
    //        lat = curr_coords[1]+(step.end_location[1]-curr_coords[1])/(step.duration)*(i-curr_step)
    //        lon = curr_coords[2]+(step.end_location[2]-curr_coords[2])/(step.duration)*(i-curr_step)
    //    end for
    static Coordinates onePoint(routeStep step, int currPoint, int startPoint, Coordinates currCoor){
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
    //    append(steps, extrapolate_step(steps[i]),current_step,current_coordinates)
    //    current_step = current_step + steps[i].duration
    //    current_coordinates = steps[i].end_location
    //    end for
    static routePoint[] stepsToRoute(List<routeStep> gSteps, 
                                Coordinates routeStart, int routeDur){
        routePoint[] routeTemp = new routePoint[routeDur];
        System.out.println("routeTemp.length: "+routeTemp.length);
        
        int currentTime = 0;
        Coordinates currentCoords = routeStart;
        // Loop the gSteps
        for(int i=0; i < gSteps.size();i++){
            int startPoint = currentTime;
            int endPoint = currentTime + gSteps.get(i).Duration;
            // Loop the points of one step
            for(int j=startPoint; j < endPoint;j++){
                //System.out.println("j: "+j);
                routeTemp[j] = new routePoint(Extrapolator.onePoint(gSteps.get(i), j, 
                        startPoint, currentCoords),new Coordinates(0.00,0.00));
            }
            currentTime = endPoint;
            currentCoords = gSteps.get(i).EndLocation;
            
            System.out.println("Start: "+startPoint);
            System.out.println("End:   "+endPoint);
            System.out.println("Coord: "+currentCoords.Lat+" "+currentCoords.Lon);
        }
        return routeTemp;
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
                System.out.println(nodes.item(i).getNodeValue());
                list.add(nodes.item(i).getNodeValue());
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
            String expr = "/DirectionsResponse/route/leg/step/start_location/lat/text()";
            // System.out.println(expr);
            List<String> start_lats = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route/leg/step/start_location/lng/text()";
            // System.out.println(expr);
            List<String> start_lngs = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route/leg/step/end_location/lat/text()";
            List<String> end_lats = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route/leg/step/end_location/lng/text()";
            List<String> end_lngs = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route/leg/step/distance/value/text()";
            List<String> dists = Parser.getValueList(doc, xpath, expr);
            expr = "/DirectionsResponse/route/leg/step/duration/value/text()";
            List<String> durs = Parser.getValueList(doc, xpath, expr);

            for (int i = 0; i < start_lats.size(); i++) {
//                System.out.println(start_lats.get(i));
//                System.out.println(start_lngs.get(i));
//                System.out.println(end_lats.get(i));
//                System.out.println(end_lngs.get(i));
//                System.out.println(dists.get(i));
//                System.out.println(durs.get(i));
                double slat = Double.parseDouble(start_lats.get(i));
//                System.out.println(start_lats.get(i));
//                System.out.println(slat);
                double slon = Double.parseDouble(start_lngs.get(i));
                Coordinates start = new Coordinates(slat, slon);
                double elat = Double.parseDouble(end_lats.get(i));
                double elon = Double.parseDouble(end_lngs.get(i));
                Coordinates end = new Coordinates(elat, elon);
                int dist = parseInt(dists.get(i));
                int dur = parseInt(durs.get(i));
                
                list.add(new routeStep(start, end, dist, dur));
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
            
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            //String expr = "/wfs:FeatureCollection/wfs:member/BsWfs:BsWfsElement/BsWfs:Location/gml:Point/gml:pos/*";
            String expr = "Point";           
            // System.out.println(expr);
            List<String> stationCoords = Parser.getValueList(doc, xpath, expr);
            
            System.out.println(stationCoords.toString());
            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}

public class FmiQueryTest {
    static String gKey = "AIzaSyBceB0at3czeTt5BWnL7FQvmf_AEBxLjys";
    static String fmiKey = "2dcc4c13-ee27-4471-b5b1-73623eb933e4";
    static String gDirectionQuery = "https://maps.googleapis.com/maps/api/directions/xml?";
    static String fmiBase = "http://data.fmi.fi/fmi-apikey/";
    static String fmiMiddle = "/wfs?request=getFeature&storedquery_id=";
    static String fmiQueryCities = "fmi::forecast::hirlam::surface::cities::simple";
    static String fmiQueryObsStations = "fmi::forecast::hirlam::surface::obsstations::simple";
    
    public static void main(String[] args) {
        String gOrigin="origin=Etel%C3%A4inen+Hesperiankatu+20,+00100+Helsinki,+Finland";
        String gDestination="&destination=33100+Tampere";
        String gQuery = new StringBuilder(gDirectionQuery).append(gOrigin)
                .append(gDestination).append("&key=").append(gKey).toString();
        System.out.println("Google query URL: "+gQuery);
        
        List<routeStep> gSteps = Parser.getSteps(gQuery);
        Coordinates routeStart = gSteps.get(0).StartLocation;
        System.out.println("Start Lat: "+routeStart.Lat);
        System.out.println("Satrt Lon: "+routeStart.Lon);
        Coordinates routeEnd = gSteps.get(gSteps.size()-1).EndLocation;
        System.out.println("End Lat:   "+routeEnd.Lat);
        System.out.println("End Lon:   "+routeEnd.Lon);
        
        int routeDist = 0;
        for(int i=0; i < gSteps.size();i++)
            routeDist += gSteps.get(i).Distance;
        System.out.println("Google route distance: "+routeDist);
        int routeDur = 0;
        for(int i=0; i < gSteps.size();i++)
            routeDur += gSteps.get(i).Duration;
        System.out.println("Google route duration: "+routeDur);
        
        routePoint[] routeData = new routePoint[routeDur];
        routeData = Extrapolator.stepsToRoute(gSteps, routeStart, routeDur);
        
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        System.out.println("ISO time: "+nowAsISO);
        String oneHourAsISO = df.format(new Date(System.currentTimeMillis() + 3600 * 1000));
        System.out.println("ISO time: "+oneHourAsISO);
        String fmiParam = new StringBuilder("&starttime=").append(nowAsISO)
                .append("&endtime=").append(oneHourAsISO).toString();
        
        String fmiCities        = new StringBuilder(fmiBase).append(fmiKey)
                .append(fmiMiddle).append(fmiQueryCities)
                .append(fmiParam).toString();
        String fmiObsStations   = new StringBuilder(fmiBase).append(fmiKey)
                .append(fmiMiddle).append(fmiQueryObsStations)
                .append(fmiParam).toString();
        System.out.println("FMI cities URL: "+fmiCities);
        System.out.println("FMI obsstations URL: "+fmiObsStations);
        
        List<stationData> fmiStations = Parser.getStations(fmiCities);
//        System.out.println("Break.");
    } 
}
