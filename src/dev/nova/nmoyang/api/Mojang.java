package dev.nova.nmoyang.api;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;


public class Mojang {


    /**
     *
     * Gets the status of any of Mojang's servers.
     *
     * @param server The server to check its status.
     * @return The state of the server.
     */
    @NotNull
    public MojangStates getStatus(@NotNull MojangServerType server) {
        try {
            HttpURLConnection connection = getGetConnection("https://status.mojang.com/check");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return MojangStates.UNKNOWN;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);

            MojangStates state = MojangStates.valueOf(StringUtils.substringBetween(StringUtils.substringBetween(reader.readLine(),"[","]").split(",")[server.getIndex()],"{","}").split(":")[1].replaceAll("\"","").toUpperCase(Locale.ROOT));



            reader.close();
            connection.disconnect();

            return state;
        } catch (IOException e) {
            return MojangStates.UNKNOWN;
        }
    }

    /**
     *
     * Get the user's {@link UUID}
     *
     * @param username The user's name
     * @return
     */

    @Nullable
    public UUID getUserUUID(String username){
        try {
            HttpURLConnection connection = getGetConnection("https://api.mojang.com/users/profiles/minecraft/"+username);

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();

            reader.close();
            connection.disconnect();

            return getProperUUID(StringUtils.substringBetween(line,"{","}").split(",")[1].split(":")[1].replaceAll("\"",""));
        } catch (IOException e) {
            return null;
        }
    }

    public UUID getProperUUID(String uuidString){
        if(uuidString.length() != 32){
            return null;
        }

        String first = uuidString.substring(0,8);
        String second = uuidString.substring(8,12);
        String third = uuidString.substring(12,16);
        String fourth = uuidString.substring(16,20);
        String fifth = uuidString.substring(20);

        return UUID.fromString(first+"-"+second+"-"+third+"-"+fourth+"-"+fifth);
    }

    private HttpURLConnection getGetConnection(String url) throws IOException {
            URL statusURL = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
            connection.setRequestMethod("GET");

            return connection;
    }

}
