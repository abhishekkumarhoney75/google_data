package honey.google.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Utility {
    private static String filePath = null;
    private static LogManager lgmngr = null;
    private static Logger logger = null;

    public static void main(String[] args) throws IOException {
        initUtility();

        String content = readFile();
        JSONObject jsonLocations = new JSONObject(content);
        JSONArray ja_data = jsonLocations.getJSONArray("locations");
        List<Location> locations = getLocations(ja_data);

        locations.stream()
                .forEach(location -> location.setTimestampMs(convertTime(location.getTimestampMs())));

        locations.stream()
                .forEach(location -> location.setLatitudeE7(convertLatitude(location.getLatitudeE7())));

        locations.stream()
                .forEach(location -> location.setLongitudeE7(convertLongitude(location.getLongitudeE7())));

        PrintStream o = new PrintStream(new File("relevant.txt"));
        PrintStream console = System.out;

        // Assign o to output stream
        System.setOut(o);

//        locations.stream()
//                .forEach(location -> System.out.println(/*location.getTimestampMs() + "," + */location.getLatitudeE7()+ "," + location.getLongitudeE7() +",honey,blue"));

//        List<String> longList = new ArrayList<>();
//        List<String> latList = new ArrayList<>();
//
//
//        for(int i = 0 ; i  < locations.size() ; i++){
//            longList.add(locations.get(i).getLongitudeE7());
//            latList.add(locations.get(i).getLatitudeE7());
//        }
//
//        String longitudesString = longList.stream().collect(Collectors.joining(","));
//        String latitudesString = latList.stream().collect(Collectors.joining(","));
//
//        System.out.println("longitudesString"+longitudesString);
//        System.out.println("latitudesString"+latitudesString);

        int counter = 0;
        List<String> longListRelevant = new ArrayList<>();
        List<String> latListRelevant = new ArrayList<>();


        for (int i =0 ;i < locations.size()-1;i++){
            Location currentLocation = locations.get(i);
            Location nextLoction = locations.get(i + 1);

            Double lat1 = Double.valueOf(currentLocation.getLatitudeE7());
            Double lon1 = Double.valueOf(currentLocation.getLongitudeE7());

            Double lat2 = Double.valueOf(nextLoction.getLatitudeE7());
            Double lon2 = Double.valueOf(nextLoction.getLongitudeE7());

            double distance = distance(lat1, lat2, lon1, lon2, 0.0, 0.0);
            if(distance > 1000){
                counter++;
                longListRelevant.add(nextLoction.getLongitudeE7());
                latListRelevant.add(nextLoction.getLatitudeE7());
            }



        }

        System.out.println("Counter: "+counter);
        String longitudesStringRelevant = longListRelevant.stream().collect(Collectors.joining(","));
        String latitudesStringRelevant = latListRelevant.stream().collect(Collectors.joining(","));

        System.out.println("longitudesStringRelevant"+longitudesStringRelevant);
        System.out.println("latitudesStringRelevant"+latitudesStringRelevant);

    }

    private static String convertLongitude(String longitudeE7) {
        String longitude = String.valueOf(Integer.valueOf(longitudeE7)/10000000.0);
        return longitude;
    }


    private static String convertLatitude(String latitudeE7) {
        String latitude = String.valueOf(Integer.valueOf(latitudeE7)/10000000.0);
        return latitude;
    }


    private static String convertTime(String timestampMs) {
        Date expiry = new Date(Long.parseLong(timestampMs));
        return expiry.toString();
    }

    private static List<Location> getLocations(JSONArray ja_data) {
        List<Location> locations = new ArrayList<>();
        int start = 0;
        int length = ja_data.length();
//        length = 10000;
        for (int i = start; i < length ; i++) {
            Gson gson = new GsonBuilder().create();
            Location location = gson.fromJson(ja_data.get(i).toString(), Location.class);
            locations.add(location);
//            logger.log(Level.INFO, location.toString());
        }
        return locations;
    }

    private static String readFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
        logger.log(Level.INFO, "Read file " + filePath + " success.");
        return content;
    }

    private static void initUtility() {
        lgmngr = LogManager.getLogManager();
        logger = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
        filePath = "/Users/apple/Documents/study/google_data/Google Maps/Java/src/main/resources/Location_History.json";
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
