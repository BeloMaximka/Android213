package itstep.learning.andrioid_213.chat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatServices {
    public static String fetchUrlText(String urlPath) throws IOException {
        URL url;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }

        try (InputStream urlStream = url.openStream()) {
            return readAllText(urlStream);
        }
    }

    public static String readAllText(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        int receivedBytes;
        while ((receivedBytes = inputStream.read(buffer)) > 0) {
            byteBuilder.write(buffer, 0 ,receivedBytes);
        }
        return byteBuilder.toString();
    }
}
