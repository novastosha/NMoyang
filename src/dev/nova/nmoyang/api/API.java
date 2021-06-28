package dev.nova.nmoyang.api;

import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class API {

    public API() {

    }

    public boolean isUp() {
        try {
            HttpURLConnection connection = getGetConnection("https://status.mojang.com/check");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return false;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);
            String state = StringUtils.substringBetween(StringUtils.substringBetween(reader.readLine(),"[","]").split(",")[5],"{","}").split(":")[1];

            if(state.equalsIgnoreCase('"'+"GREEN"+'"') || state.equalsIgnoreCase('"'+"YELLOW"+'"')){
                reader.close();
                connection.disconnect();
                return true;
            }else{
                reader.close();
                connection.disconnect();
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    private HttpURLConnection getGetConnection(String url) throws IOException {
            URL statusURL = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
            connection.setRequestMethod("GET");

            return connection;
    }

}
