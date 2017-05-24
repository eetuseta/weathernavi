package fmiquerytest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * Source: Almanac for Computers, 1990 published by Nautical Almanac Office
 * United States Naval Observatory Washington, DC 20392
 *
 * Inputs: day, month, year: date of sunrise/sunset latitude, longitude:
 * location for sunrise/sunset zenith: Sun's zenith for sunrise/sunset offical =
 * 90 degrees 50' civil = 96 degrees nautical = 102 degrees astronomical = 108
 * degrees
 *
 * NOTE: longitude is positive for East and negative for West NOTE: the
 * algorithm assumes the use of a calculator with the trig functions in "degree"
 * (rather than "radian") mode. Most programming languages assume radian
 * arguments, requiring back and forth convertions. The factor is 180/pi. So,
 * for instance, the equation RA = atan(0.91764 * tan(L)) would be coded as RA =
 * (180/pi)*atan(0.91764 * tan((pi/180)*L)) to give a degree answer with a
 * degree input for L.
 *
 * http://williams.best.vwh.net/sunrise_sunset_algorithm.htm
 * http://williams.best.vwh.net/sunrise_sunset_example.htm
 */
public class DayLightTime {
    
    static double getSunTime(Boolean rise, int offSet, double latitude, double longitude, double zenithAngle) {
        double zenithCos = cos((zenithAngle / 180) * PI);
        //System.out.println("Timezone:           "+timezone);
        //System.out.println("Zenith (official):  "+zenith);
        //System.out.println("Pi:                 "+PI);
        //int day = 25;
        //int month = 6;
        //int year = 1990;
        //int timezone = -4;

        //INPUTS
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, offSet);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int timezone = cal.get(Calendar.ZONE_OFFSET) / 3600000;

        //1. first calculate the day of the year
        //	N1 = floor(275 * month / 9)
        //	N2 = floor((month + 9) / 12)
        //	N3 = (1 + floor((year - 4 * floor(year / 4) + 2) / 3))
        //	N = N1 - (N2 * N3) + day - 30
        double N1 = floor((double) 275 * month / 9);
        double N2 = floor(((double) month + 9) / 12);
        double N3 = (1 + floor(((double) year - 4 * floor((double) year / 4) + 2) / 3));
        double N = N1 - (N2 * N3) + (double) day - 30;

        //2. convert the longitude to hour value and calculate an approximate time
        //	lngHour = longitude / 15
        //	if rising time is desired:
        //	  t = N + ((6 - lngHour) / 24)
        //	if setting time is desired:
        //	  t = N + ((18 - lngHour) / 24)
        double lngHour = longitude / 15;
        double t = 0;
        if (rise) {
            t = N + ((6 - lngHour) / 24);
        } else {
            t = N + ((18 - lngHour) / 24);
        }

        //3. calculate the Sun's mean anomaly
        //	M = (0.9856 * t) - 3.289
        double M = (0.9856 * t) - 3.289;

        //4. calculate the Sun's true longitude
        //       [Note throughout the arguments of the trig functions
        //       (sin, tan) are in degrees. It will likely be necessary to
        //       convert to radians. eg sin(170.626 deg) =sin(170.626*pi/180
        //       radians)=0.16287]
        double L = M + (1.916 * sin(M / 180 * PI)) + (0.020 * sin(2 * (M / 180 * PI))) + 282.634;
        //NOTE: L potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
        if (L < 0) {
            L += 360;
        } else if (L >= 360) {
            L -= 360;
        }

        //5a. calculate the Sun's right ascension
        //        RA = atan(0.91764 * tan(L))	
        double RA = atan(0.91764 * tan(L / 180 * PI)) / PI * 180;
        //NOTE: RA potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
        //I dont think so...

        //5b. right ascension value needs to be in the same quadrant as L
        //	Lquadrant  = (floor( L/90)) * 90
        //	RAquadrant = (floor(RA/90)) * 90
        //	RA = RA + (Lquadrant - RAquadrant)
        double Lquadrant = (floor(L / 90)) * 90;
        double RAquadrant = (floor(RA / 90)) * 90;
        RA = RA + (Lquadrant - RAquadrant);
        //5c. right ascension value needs to be converted into hours
        //	RA = RA / 15
        RA = RA / 15;

        //6. calculate the Sun's declination
        //	sinDec = 0.39782 * sin(L)
        //	cosDec = cos(asin(sinDec))
        double sinDec = 0.39782 * sin(L / 180 * PI);
        double cosDec = cos(asin(sinDec));

        //7a. calculate the Sun's local hour angle
        //	cosH = (cos(zenith) - (sinDec * sin(latitude))) / (cosDec * cos(latitude))
        //	if (cosH >  1) 
        //	  the sun never rises on this location (on the specified date)
        //	if (cosH < -1)
        //	  the sun never sets on this location (on the specified date)
        double cosH = (zenithCos - (sinDec * sin(latitude / 180 * PI))) / (cosDec * cos(latitude / 180 * PI));
        if (cosH > 1) //return("The sun never rises on this location (on the specified date).");
        {
            return (0);
        } else if (cosH < -1) //return("The sun never sets on this location (on the specified date)");
        {
            return (0);
        }

        //7b. finish calculating H and convert into hours
        //	if if rising time is desired:
        //	  H = 360 - acos(cosH)
        //	if setting time is desired:
        //	  H = acos(cosH)
        //	H = H / 15
        double H = 0;
        if (rise) {
            H = 360 - acos(cosH) / PI * 180;
        } else {
            H = acos(cosH) / PI * 180;
        }
        H = H / 15;

        //8. calculate local mean time of rising/setting
        //	T = H + RA - (0.06571 * t) - 6.622
        double T = H + RA - (0.06571 * t) - 6.622;

        //9. adjust back to UTC
        //	UT = T - lngHour
        //	NOTE: UT potentially needs to be adjusted into the range [0,24) by adding/subtracting 24
        double UT = T - lngHour;
//        if (UT<0)
//            UT += 24;
//        else if (UT>=24)
//            UT -= 24;

        //10. convert UT value to local time zone of latitude/longitude
        //	localT = UT + localOffset
        double localT = UT + timezone;
        if (localT < 0) {
            localT += 24;
        } else if (localT >= 24) {
            localT -= 24;
        }
        //System.out.println(rise+" "+localT);

        return localT;
    }

    static String doubleTimeToString(double doubleTime) {
        return String.format("%02d", intValue(doubleTime%24)) + ":" + String.format("%02d", intValue(doubleTime%24 % 1 * 60));
    }

    static String doubleTimeToMinutes(double doubleTime) {
        int minutes = intValue(doubleTime%24) * 60 + intValue(doubleTime%24 % 1 * 60);
        //In the rare case the time is under 1 minute, return 1
        if (minutes == 0) {
            minutes = 1;
        }
        return String.format("%d", minutes);
    }

    static double stringTimeToDouble(String stringTime) {
        String[] parts = stringTime.split(":", 2);
        return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]) / 60;
    }

    static List<String> calculateSunEvents(List<stepWeather> stepDataBase) {
        List<String> sunEvents = new ArrayList<>();
        boolean sunIsUp = FALSE;
        sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                doubleTimeToString(stepDataBase.get(0).timeAsDouble%24), "Trip starts", "(",
                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lat),
                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lon), ")"));
        for (int i = 0; i < stepDataBase.size(); ++i) {
            stepWeather step = stepDataBase.get(i);
            stepWeather nextStep = step;
            //If we are not in the last step, we have NEXT step
            if (i < (stepDataBase.size() - 1)) {
                nextStep = stepDataBase.get(i + 1);
            }
            //sunRise==0 when the sun does not rise at all
            if (step.sunRise == 0) {
                sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                        doubleTimeToString(step.timeAsDouble%24), "Sun will not rise", "(",
                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lat),
                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lon), ")"));
                sunIsUp = FALSE;
            }
            //Likewise
            if (step.sunSet == 0) {
                sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                        doubleTimeToString(step.timeAsDouble%24), "Sun will not set", "(",
                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lat),
                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lon), ")"));
                sunIsUp = TRUE;
            }
            //Is the sun up?
            if (step.sunRise < step.timeAsDouble%24 && step.sunSet > step.timeAsDouble%24) {
                //Print sun is up only once
                if (!sunIsUp) {
//                    sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
//                            doubleTimeToString(step.timeAsDouble%24), "Sun is up", "(",
//                            FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lat),
//                            FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lon), ")"));
                    sunIsUp = TRUE;
                }
                //It is now, but will it set during the next step?
                if (!(nextStep.sunRise < nextStep.timeAsDouble%24 && nextStep.sunSet > nextStep.timeAsDouble%24)) {
                    double timeToSunSetHere = step.sunSet - step.timeAsDouble%24;
                    double fractionOfDifference = (step.sunSet - step.timeAsDouble%24) / ((double) step.Duration / 3600);
                    double differenceOfSets = nextStep.sunSet - step.sunSet;
                    double timeToSunSet = timeToSunSetHere + fractionOfDifference * differenceOfSets;
                    double sunSetTime = step.timeAsDouble + timeToSunSet;
                    Coordinates sunSetLocation = RouteTools.findLocationAtTime(sunSetTime, stepDataBase);
                    //If the trip has started over 1h ago, make warning
                    if (step.timeAsDouble + timeToSunSetHere - 1 > stepDataBase.get(0).timeAsDouble%24){
                        Coordinates sunSetWarningLocation 
                                = RouteTools.findLocationAtTime(step.timeAsDouble + timeToSunSetHere - 1, stepDataBase);
                        sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                                doubleTimeToString(sunSetTime%24 - 1),
                                "Sun will set in 1 hour", "(",
                                FmiQueryTest.df_fiveDecimal.format(sunSetWarningLocation.Lat),
                                FmiQueryTest.df_fiveDecimal.format(sunSetWarningLocation.Lon), ")"));   
                    } //Otherwise print how many minutes there is to sunrise
                    else {
                        sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                                doubleTimeToString(stepDataBase.get(0).timeAsDouble%24),
                                "Sun will set in "+doubleTimeToMinutes(timeToSunSet)+" minutes", "(",
                                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lat),
                                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lon), ")"));
                    }
                    sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                            doubleTimeToString(sunSetTime%24),
                            "Sun sets", "(",
                            FmiQueryTest.df_fiveDecimal.format(sunSetLocation.Lat),
                            FmiQueryTest.df_fiveDecimal.format(sunSetLocation.Lon), ")"));  
                    sunIsUp = FALSE;
                }
            } //If sun is not up, will it rise in this step?
            else if (nextStep.sunRise < nextStep.timeAsDouble%24 && nextStep.sunSet > nextStep.timeAsDouble%24) {
                double timeToSunRiseHere = step.sunRise - step.timeAsDouble%24;
                double fractionOfDifference = (step.sunRise - step.timeAsDouble%24) / ((double) step.Duration / 3600);
                double differenceOfRises = nextStep.sunRise - step.sunRise;
                double timeToSunRise = timeToSunRiseHere + fractionOfDifference * differenceOfRises;
                double sunRiseTime = step.timeAsDouble + timeToSunRise;
                Coordinates sunRiseLocation = RouteTools.findLocationAtTime(sunRiseTime, stepDataBase);
                //If the trip has started over 1h ago, make warning
                if (step.timeAsDouble + timeToSunRiseHere - 1 > stepDataBase.get(0).timeAsDouble){
                    Coordinates sunRiseWarningLocation 
                            = RouteTools.findLocationAtTime(step.timeAsDouble + timeToSunRiseHere - 1, stepDataBase);
                    sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                            doubleTimeToString(sunRiseTime%24 - 1),
                            "Sun will rise in 1 hour", "(",
                            FmiQueryTest.df_fiveDecimal.format(sunRiseWarningLocation.Lat),
                            FmiQueryTest.df_fiveDecimal.format(sunRiseWarningLocation.Lon), ")"));
                } //Otherwise print how many minutes there is to sunrise
                else {
                    sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                            doubleTimeToString(stepDataBase.get(0).timeAsDouble%24),
                            "Sun will rise in "+doubleTimeToMinutes(timeToSunRise)+" minutes", "(",
                            FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lat),
                            FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(0).StartLocation.Lon), ")"));
                }
                sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                        doubleTimeToString(sunRiseTime%24),
                        "Sun rises", "(",
                        FmiQueryTest.df_fiveDecimal.format(sunRiseLocation.Lat),
                        FmiQueryTest.df_fiveDecimal.format(sunRiseLocation.Lon), ")"));
            }
        }
        sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
                doubleTimeToString(stepDataBase.get(stepDataBase.size()-1).timeAsDouble%24), "Trip ends", "(",
                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(stepDataBase.size()-1).EndLocation.Lat),
                FmiQueryTest.df_fiveDecimal.format(stepDataBase.get(stepDataBase.size()-1).EndLocation.Lon), ")"));
        return sunEvents;
    }
}

// ORIGINAL PLANS
//        Coordinates startCoor = stepDataBase.get(0).StartLocation;
//        double startTime = stepDataBase.get(0).timeAsDouble;
//        Coordinates endCoor = stepDataBase.get(stepDataBase.size() - 1).EndLocation;
//        double endTime = startTime + (double) stepDataBase.get(stepDataBase.size() - 1).totalDuration / 3600;
//        double zenithAngleOfficial = 90+((double)50/60);
//        double sunRiseAtStart = getSunTime(TRUE,0,startCoor.Lat,startCoor.Lon,zenithAngleOfficial);
//        double sunSetAtStart = getSunTime(FALSE,0,startCoor.Lat,startCoor.Lon,zenithAngleOfficial);
//        double sunRiseAtEnd = getSunTime(TRUE,0,endCoor.Lat,endCoor.Lon,zenithAngleOfficial);
//        double sunSetAtEnd = getSunTime(FALSE,0,endCoor.Lat,endCoor.Lon,zenithAngleOfficial);
//            if(sunRiseAtStart==0 && sunRiseAtEnd==0)
//                return         doubleTimeToString(startTime)+" No sunrise     (Start coordinates)"+FmiQueryTest.newLine
//                              +doubleTimeToString(endTime)  +" No sunrise     (End coordinates)";
//            if(sunSetAtStart==0 && sunSetAtEnd==0)
//                return         doubleTimeToString(startTime)+" No sunset      (Start coordinates)"+FmiQueryTest.newLine
//                              +doubleTimeToString(endTime)  +" No sunset      (End coordinates)";
//            if(sunRiseAtStart<startTime && sunSetAtStart>startTime)
//                sunEvents +=   doubleTimeToString(startTime)+" Sun is up      (Start coordinates)"+FmiQueryTest.newLine;
//            if(sunRiseAtStart>startTime)
//                sunEvents +=   doubleTimeToString(startTime)+" Dark (morning) (Start coordinates)"+FmiQueryTest.newLine;
//            if(sunSetAtStart<startTime)
//                sunEvents +=   doubleTimeToString(startTime)+" Dark (evening) (Start coordinates)"+FmiQueryTest.newLine;
//
//            if(sunRiseAtEnd>endTime)
//                sunEvents +=   doubleTimeToString(endTime)  +" Dark (morning) (End coordinates)";
//            if(sunSetAtEnd<endTime)
//                sunEvents +=   doubleTimeToString(endTime)  +" Dark (evening) (End coordinates)";
//            if(sunRiseAtEnd<endTime && sunSetAtEnd>endTime){
//                sunEvents +=   doubleTimeToString(endTime)  +" Sun is up      (End coordinates)";  
//            }
//                sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
//                        doubleTimeToString(step.timeAsDouble%24),
//                        ("Sun will rise in " + doubleTimeToMinutes(timeToSunRise) + " minutes"), "(",
//                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lat),
//                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lon), ")"));
//                sunEvents.add(String.format("%-6s%-30s%s%s %s%s",
//                        doubleTimeToString(step.timeAsDouble%24),
//                        ("Sun will set in " + doubleTimeToMinutes(timeToSunSet) + " minutes"), "(",
//                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lat),
//                        FmiQueryTest.df_fiveDecimal.format(step.StartLocation.Lon), ")"));
