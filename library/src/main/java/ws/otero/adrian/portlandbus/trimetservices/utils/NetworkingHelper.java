package ws.otero.adrian.portlandbus.trimetservices.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NetworkingHelper {
    private static final String LOG_TAG = "NetworkingHelper";

    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_PUT = "PUT";
    private static final String HTTP_METHOD_DELETE = "DELETE";

    private static Map<String, CacheItem> cache = new HashMap<String, CacheItem>();

    private static class Cache {
        private static final int EXPIRATION_TIME = 30000;
        private static Cache instance;

        private Cache() {
        }

        public static Cache getInstance() {
            if (instance == null) {
                instance = new Cache();
            }
            return instance;
        }

        private void save(String url, String response) {
            CacheItem cacheItem = new CacheItem(Calendar.getInstance(), response);
            cache.put(url, cacheItem);

        }

        private String get(String url) {
            CacheItem cacheItem = cache.get(url);
            if (cacheItem != null) {
                if (Calendar.getInstance().getTimeInMillis() - cacheItem.hitTime.getTimeInMillis() > EXPIRATION_TIME) {
                    return null;
                } else {
                    return cacheItem.response;
                }
            }
            return null;
        }
    }

    private static class CacheItem {
        Calendar hitTime;
        String response;

        public CacheItem(Calendar hitTime, String response) {
            this.hitTime = hitTime;
            this.response = response;
        }
    }

    /**
     * @param requestString The request string to get the byte array from
     * @return The bytes returned from a GET request to request string
     */
    public static byte[] byteGETRequest(String requestString) {
        // By default, this implementation of HttpURLConnection requests that servers use gzip compression
        HttpURLConnection urlConnection = null;
        try {
            final URL url = new URL(requestString);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Read response
            final InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int length;
            final byte[] buffer = new byte[1024]; // Arbitrary buffer size
            while ((length = inputStream.read(buffer)) != -1)
                byteBuffer.write(buffer, 0, length);

            return byteBuffer.toByteArray();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error opening http connection", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return null;
    }

    /**
     * Send a GET request
     *
     * @param requestString The URL string to get
     * @return An instance of JSONArray or JSONObject, the caller should expect one of these values, or null
     */
    public static Object jsonGETRequest(String requestString) {
        return jsonRequest(HTTP_METHOD_GET, requestString, null);
    }

    /**
     * Send a POST request
     *
     * @param requestString The URL string where the request is sent
     * @param bodyParams    A map object which is encoded as JSON and passed to the API
     * @return An instance of JSONArray or JSONObject, the caller should expect one of these values, or null
     */
    public static Object jsonPOSTRequest(
            String requestString,
            JSONObject bodyParams) {
        return jsonRequest(HTTP_METHOD_POST, requestString, bodyParams);
    }

    /**
     * Send a PUT request
     *
     * @param requestString The URL string where the request is sent
     * @param bodyParams    A map object which is encoded as JSON and passed to the API
     * @return An instance of JSONArray or JSONObject, the caller should expect one of these values, or null
     */
    public static Object jsonPUTRequest(
            String requestString,
            JSONObject bodyParams) {
        return jsonRequest(HTTP_METHOD_PUT, requestString, bodyParams);
    }

    /**
     * Send a DELETE request
     *
     * @param requestString The URL string where the request is sent
     * @return An instance of JSONArray or JSONObject, the caller should expect one of these values, or null
     */
    public static Object jsonDELETERequest(String requestString) {
        return jsonRequest(HTTP_METHOD_DELETE, requestString, null);
    }

    /**
     * Request/response handling. Sends request body with content type application/json. Expects
     *
     * @param httpMethod    The http method (GET, POST, PUT, DELETE)
     * @param requestString The url to send the request
     * @param bodyParams    Optional body data
     * @return response parsed into a JSONObject, JSONArray, or primitive type
     */
    private static Object jsonRequest(
            String httpMethod,
            String requestString,
            JSONObject bodyParams
    ) {

        Log.d("NetworkingHelper", "Getting url: " + requestString);

        // By default, this implementation of HttpURLConnection requests that servers use gzip compression
        HttpURLConnection urlConnection = null;
        String responseString;
        try {
            if (Cache.getInstance().get(requestString) != null) {
                responseString = Cache.getInstance().get(requestString);
            } else {
                final URL url = new URL(requestString);

                // Create connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(httpMethod);

                if (bodyParams != null && !HTTP_METHOD_GET.equals(httpMethod)) {
                    final byte[] bodyData = bodyParams.toString().getBytes("UTF-8");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Content-Length", Integer.toString(bodyData.length));
                    urlConnection.setFixedLengthStreamingMode(bodyData.length); // set content length

                    // Send data
                    final OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                    outputStream.write(bodyData);
                    outputStream.flush();
                }

                // Read response
                final int responseCode = urlConnection.getResponseCode();
                InputStream connectionInputStream;
                if (responseCode < 400) {
                    connectionInputStream = urlConnection.getInputStream();
                } else {
                    connectionInputStream = urlConnection.getErrorStream();
                }

                final InputStream inputStream = new BufferedInputStream(connectionInputStream);

                responseString = InputStreamHelper.getStringFromInputStream(inputStream);

                if (responseCode >= 400) {
                    Log.i(LOG_TAG, "Request error");
                    Log.i(LOG_TAG, responseString);
                    return null;
                }

                Cache.getInstance().save(requestString, responseString);
            }

            return new JSONTokener(responseString).nextValue();

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing json", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error opening http connection", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return null;
    }

}
