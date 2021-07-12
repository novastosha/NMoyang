package dev.nova.nmoyang.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.nova.nmoyang.api.player.Name;
import dev.nova.nmoyang.api.player.Profile;
import dev.nova.nmoyang.api.player.Skin;
import dev.nova.nmoyang.api.stats.SaleStatistics;
import dev.nova.nmoyang.api.stats.SaleStatisticsType;
import dev.nova.nmoyang.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.login.LoginException;


public class Mojang {

    public static boolean featureRequiresAuth(WrapperFeatures feature) {
        try {
            Method method = Mojang.class.getMethod(feature.getMethodName(),feature.getClasses());

            if(method.getDeclaredAnnotation(RequiresAuth.class) != null){
                return method.getDeclaredAnnotation(RequiresAuth.class).required();
            }else{
                return false;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    private final String accesstoken;
    private final boolean authMode;

    /**
     *
     * Mojang API for accounts.
     *
     * @param email Email of the account.
     * @param password Password of the account.
     * @throws LoginException Gets thrown when the API is unable to login.
     */
    public Mojang(String email, String password) throws LoginException, IOException {
        String payload = String.format("{\"agent\": {\"name\": \"Minecraft\",\"version\": 1},\"username\": \"" + email + "\",\"password\": \"" + password + "\",\"clientToken\": \"" + UUID.randomUUID() + "\"}");

        URL authenticationURL = new URL("https://authserver.mojang.com/authenticate");

        HttpsURLConnection connection = (HttpsURLConnection)authenticationURL.openConnection();
        byte payloadAsBytes[] = payload.getBytes(Charset.forName("UTF-8"));

        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(payloadAsBytes.length));
        connection.setDoInput(true);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(payloadAsBytes);
        wr.flush();
        wr.close();

        int status = connection.getResponseCode();

        Reader streamReader = null;

        if (status > 299) {
            throw new LoginException("HTTP response code is above 299 (Bad response)");
        } else {
            streamReader = new InputStreamReader(connection.getInputStream());
        }

        JsonParser parser = new JsonParser();

        JsonObject obj = (JsonObject) parser.parse(streamReader);

        this.accesstoken = obj.get("accessToken").toString();
        this.authMode = true;
    }

    /**
     *
     * Normal public mojang API.
     *
     */
    public Mojang() {
        this.accesstoken = null;
        this.authMode = false;
    }

    @RequiresAuth
    public boolean isNameChangeAllowed() {
        try {
            if(authMode) {
                HttpURLConnection connection = getGetConnection("https://api.minecraftservices.com/minecraft/profile/namechange");


                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + accesstoken.replaceAll("\"", ""));


                int status = connection.getResponseCode();

                Reader streamReader = null;

                if (status > 299) {
                    return false;
                } else {
                    streamReader = new InputStreamReader(connection.getInputStream());
                }

                JsonParser parser = new JsonParser();

                JsonObject obj = (JsonObject) parser.parse(streamReader);

                connection.disconnect();

                return obj.get("nameChangeAllowed").getAsBoolean();
                
            }else return false;

        } catch (IOException e) {
            return false;
        }
    }

    @Nullable
    public String[] getBlockedServers() {
        try {
            HttpURLConnection connection = getGetConnection("https://sessionserver.mojang.com/blockedservers");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);


            return reader.lines().toArray(new IntFunction<String[]>() {
                @Override
                public String[] apply(int value) {
                    return new String[value];
                }
            });

        }catch (IOException e) {
            return null;
        }
    }

    public SaleStatistics getSaleStatistics(SaleStatisticsType type) {
        try {
            String payload = "{\n" +
                    "    \"metricKeys\": [\n" +
                    "        \""+type.getTypeName()+"\"\n" +
                    "    ]\n" +
                    "}";

            URL authenticationURL = new URL("https://api.mojang.com/orders/statistics");

            HttpsURLConnection connection = (HttpsURLConnection) authenticationURL.openConnection();
            byte payloadAsBytes[] = payload.getBytes(Charset.forName("UTF-8"));

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(payloadAsBytes.length));
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(payloadAsBytes);
            wr.flush();
            wr.close();

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            JsonParser parser = new JsonParser();

            JsonObject object = (JsonObject) parser.parse(streamReader);

            return new SaleStatistics(type,object.get("last24h").getAsInt(),object.get("total").getAsInt(),object.get("saleVelocityPerSeconds").getAsInt());
        }catch(IOException E){
            E.printStackTrace();
            return null;
        }
    }

    /**
     *
     * Gets the status of any of Mojang's servers.
     *
     * @param server The server to check its status.
     * @return The state of the server.
     */
    public MojangServiceState getServiceStatus(@Nonnull MojangServiceType server) {
        try {
            HttpURLConnection connection = getGetConnection("https://status.mojang.com/check");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return MojangServiceState.UNKNOWN;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(streamReader);

            MojangServiceState state = MojangServiceState.valueOf(StringUtils.substringBetween(StringUtils.substringBetween(reader.readLine(),"[","]").split(",")[server.getIndex()],"{","}").split(":")[1].replaceAll("\"","").toUpperCase(Locale.ROOT));



            reader.close();
            connection.disconnect();

            return state;
        } catch (IOException e) {
            return MojangServiceState.UNKNOWN;
        }
    }

    /**
     *
     * Get the user's {@link UUID}
     *
     * @param username The user's name
     * @return The user's UUID.
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

    @Nullable
    public Profile getProfile(UUID uuid) {

        try {
            HttpURLConnection connection = getGetConnection("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid.toString().replace("-",""));

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            JsonParser parser = new JsonParser();

            JsonObject object = (JsonObject) parser.parse(streamReader);

            String id = object.get("id").toString();

            JsonArray array = object.getAsJsonArray("properties");

            JsonObject decoded = (JsonObject) parser.parse(new String(Base64.decodeBase64(array.get(0).getAsJsonObject().get("value").toString())));

            long created = decoded.get("timestamp").getAsLong();
            JsonObject skin = decoded.getAsJsonObject("textures").getAsJsonObject("SKIN");
            String urlSkin = skin.get("url").getAsString();
            boolean slim;

            if(skin.getAsJsonObject("metadata") == null){
                slim = false;
            }else{
                slim = true;
            }

            String capeurl = null;

            JsonObject cape = decoded.getAsJsonObject("textures").getAsJsonObject("CAPE");
            if(cape != null) {
                capeurl = cape.get("url").getAsString();
            }


            if(capeurl == null){
                return new Profile(id,new Skin(new URL(urlSkin),slim),null);
            }else{
                return new Profile(id,new Skin(new URL(urlSkin),slim),new URL(capeurl));
            }
        } catch (IOException e) {
            return null;
        }

    }

    @Nullable
    public String getName(UUID uuid) {

        try {
            HttpURLConnection connection = getGetConnection("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = (JsonArray) jsonParser.parse(streamReader);

                JsonObject object = (JsonObject) jsonArray.get(jsonArray.size() - 1);

            connection.disconnect();

                return object.get("name").toString();




        } catch (IOException e) {
            return null;
        }
    }

    public Name[] getNameHistory(UUID uuid) {
        try {
            HttpURLConnection connection = getGetConnection("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names");

            int status = connection.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                return null;
            } else {
                streamReader = new InputStreamReader(connection.getInputStream());
            }

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(streamReader);

            Name[] names = new Name[jsonArray.size()];

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                long changed;
                if(jsonObject.get("changedToAt") == null) {
                    changed = 0;
                }else{
                    changed = Long.parseLong(jsonObject.get("changedToAt").toString());
                }
                names[i] = new Name(jsonObject.get("name").toString().replaceAll("\"",""),changed);
            }


            connection.disconnect();

            return names;
        }catch (IOException e){
            return null;
        }
    }


    private UUID getProperUUID(String uuidString){
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

    @RequiresAuth
    public MojangNameState isNameAvailable(String name) {

        try {
            if(accesstoken != null || name.contains(" ") || name.equalsIgnoreCase("")) {

                HttpURLConnection connection = getGetConnection("https://api.minecraftservices.com/minecraft/profile/name/"+name+"/available");

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer "+accesstoken.replaceAll("\"",""));


                int status = connection.getResponseCode();

                Reader streamReader = null;

                if (status > 299) {
                    return MojangNameState.UNKNOWN;
                } else {
                    streamReader = new InputStreamReader(connection.getInputStream());
                }

                JsonParser parser = new JsonParser();

                JsonObject obj = (JsonObject) parser.parse(streamReader);

                connection.disconnect();

                if(obj.get("status").getAsString().equalsIgnoreCase("DUPLICATE")){
                    return MojangNameState.UNAVAILABLE;
                }else if(obj.get("status").getAsString().equalsIgnoreCase("AVAILABLE")){
                    return MojangNameState.AVAILABLE;
                }else{
                    return MojangNameState.UNKNOWN;
                }
            }else{
                return MojangNameState.UNKNOWN;
            }
        } catch (IOException e) {
            return MojangNameState.UNKNOWN;
        }
    }

    private HttpURLConnection getGetConnection(String url) throws IOException {
            URL statusURL = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
            connection.setRequestMethod("GET");

            return connection;
    }

    private HttpURLConnection getPostConnection(String url) throws IOException {
        URL statusURL = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
        connection.setRequestMethod("POST");

        return connection;
    }

    public boolean isAuthMode() {
        return authMode;
    }
}
