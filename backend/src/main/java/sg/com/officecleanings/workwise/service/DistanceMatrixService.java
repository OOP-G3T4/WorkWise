package sg.com.officecleanings.workwise.service;

// Packages to help with the HTTP request
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

// Packages to help with JSON parsing
import org.apache.http.util.EntityUtils;
import org.hibernate.internal.ExceptionConverterImpl;
import org.json.JSONArray;
import org.json.JSONObject;

// Packages to help with URL encoding
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


@Service
public class DistanceMatrixService {

    @Autowired
    private DistanceMatrixRepository DistanceMatrixRepository;

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";
    
    public HashMap<String, Double> getTravelTimes(ArrayList<String> origins, ArrayList<String> destinations) throws Exception {
        // Takes in an ArrayList<String> of origins and destinations and returns a HashMap<String, Double> of travel times, where the key is an origin-destination pair and the value is the travel time in minutes.

        // Retrieve all the origins from the ArrayList
        String originsString = "";
        for (int i = 0; i < origins.size(); i++) {
            originsString += origins.get(i);
            if (i != origins.size() - 1) {
                originsString += "|";
            }
        }

        // Retrieve all the destinations from the ArrayList
        String destinationsString = "";
        for (int i = 0; i < destinations.size(); i++) {
            destinationsString += destinations.get(i);
            if (i != destinations.size() - 1) {
                destinationsString += "|";
            }
        }

        // Properly encode the parameters
        String encodedOrigins = URLEncoder.encode(originsString, StandardCharsets.UTF_8.toString());
        String encodedDestinations = URLEncoder.encode(destinationsString, StandardCharsets.UTF_8.toString());

        // Create the URL
        String url = String.format("%s?origins=%s&destinations=%s&key=%s", BASE_URL, encodedOrigins, encodedDestinations, API_KEY);

        // Make the request
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        // Get and parse the response JSON
        String json = EntityUtils.toString(response.getEntity());
        client.close();

        // Parse the response and extract travel times
        JSONObject jsonObject = new JSONObject(json);
        JSONArray rows = jsonObject.getJSONArray("rows");
        JSONArray destinationAddresses = jsonObject.getJSONArray("destination_addresses");
        JSONArray originAddresses = jsonObject.getJSONArray("origin_addresses");

        // Store the Origin-Destination pair and the travel time in a HashMap
        HashMap<String, Double> travelTimes = new HashMap<>();

        // Iterate through the JSON response and extract the travel times
        for (int i = 0; i < rows.length(); i++) {
            JSONArray elements = rows.getJSONObject(i).getJSONArray("elements");
            String origin = originAddresses.getString(i);

            for (int j = 0; j < elements.length(); j++) {
                JSONObject element = elements.getJSONObject(j);
                String destination = destinationAddresses.getString(j);

                // Extract duration and distance
                if (element.getString("status").equals("OK")) {
                    String duration = element.getJSONObject("duration").getString("text");
                    String distance = element.getJSONObject("distance").getString("text");

                    // Print travel time between each origin-destination pair
                    System.out.println("Origin: " + origin + " to Destination: " + destination + " -> Duration: " + duration + ", Distance: " + distance);

                    // Store the Origin-Destination pair and the travel time in a HashMap
                    travelTimes.put(origin + " to " + destination, Double.parseDouble(duration.split(" ")[0]));

                    // Store the origin-destination pair, and travel time in the SQL database
                    DistanceMatrix DistanceMatrix = new DistanceMatrix(origin, destination, duration);
                    DistanceMatrixRepository.save(DistanceMatrix);

                } else {
                    System.out.println("Origin: " + origin + " to Destination: " + destination + " -> No result");

                    // Store the Origin-Destination pair and the travel time in a HashMap
                    travelTimes.put(origin + " to " + destination, "No result");
                }

            }
        }
        return travelTimes;
    }
}

