package utility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class calculateZipDistance {
    private final static String USER_AGENT = "Mozilla/5.0";


    // HTTP GET request
    public static Double getDistance(int zip1, int zip2) throws Exception {

       
        String url = "https://www.zipcodeapi" +
                ".com/rest/MsuEFlzAk7Zbk25xPX2AblnTZAXpR7tEzXifjSPEZPPkRURHRTh3fFXefUE727LB/distance.json/";
        url += zip1;
        url += "/";
        url += zip2;
        url += "/mile";



        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        String output = response.toString();
        output = output.replace("{", "");
        output = output.replace("}", "");
        String substringOutput = output.substring(11);
        Double distance = Double.parseDouble(substringOutput);
        System.out.println(zip1 + " " + zip2 + " " + distance);
        return distance;

    }

}