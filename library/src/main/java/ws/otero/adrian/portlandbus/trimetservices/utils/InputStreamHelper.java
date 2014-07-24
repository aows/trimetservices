package ws.otero.adrian.portlandbus.trimetservices.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamHelper {
    /**
     * Read an InputStream into a String
     *
     * @param inputStream The InputStream to read a String from
     * @param encoding    The text encoding when parsing the string
     * @return A string from the input stream
     * @throws java.io.IOException
     */
    public static String getStringFromInputStream(
            InputStream inputStream,
            String encoding)
            throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding), 8);
        final StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }

    /**
     * Convenience method which reads the string using UTF-8 encoding
     *
     * @param inputStream The InputStream to read a String from
     * @return A string from the input stream
     * @throws java.io.IOException
     */
    public static String getStringFromInputStream(InputStream inputStream)
            throws IOException {
        return InputStreamHelper.getStringFromInputStream(inputStream, "UTF-8");
    }

}