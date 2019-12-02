package a.a.a;

import java.nio.charset.*;
import java.net.*;
import com.google.gson.*;
import java.io.*;

public class c
{
    public c() {
        super();
    }
    
    public static String a(final String s, final String s2, final String s3) throws IOException {
        final byte[] bytes = s3.getBytes(StandardCharsets.UTF_8);
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s + "/documents").openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        httpURLConnection.setFixedLengthStreamingMode(bytes.length);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        try {
            httpURLConnection.getOutputStream().write(bytes);
            final JsonObject jsonObject = new Gson().fromJson(new InputStreamReader(httpURLConnection.getInputStream()), JsonObject.class);
            if (s2 != null && !s2.isEmpty()) {
                new StringBuilder().append(".").append(s2).toString();
            }
            httpURLConnection.disconnect();
            return s + "/" + jsonObject.get("key").getAsString() + "." + s2;
        }
        finally {
            httpURLConnection.disconnect();
        }
    }
}
