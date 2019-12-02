package a.a.a;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import java.io.*;
import com.google.api.services.sheets.v4.*;
import com.google.api.client.googleapis.javanet.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.security.*;
import com.google.api.services.sheets.v4.model.*;
import java.util.*;

public class b
{
    private static String d;
    
    public b() {
        super();
    }
    
    private static Credential a() throws IOException {
        return GoogleCredential.fromStream(b.class.getResourceAsStream("/resources/backdoored-client-340b78ae95c4.json")).createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));
    }
    
    private static Sheets b() throws GeneralSecurityException, IOException {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), a()).setApplicationName("Backdoored License Handler").build();
    }
    
    private static String a(final String s) throws IOException, GeneralSecurityException {
        final ValueRange valueRange = b().spreadsheets().values().get(b.d, s).execute();
        for (int i = 0; i < valueRange.getValues().size(); ++i) {
            System.out.println(valueRange.getValues().get(i));
        }
        return (valueRange.getValues() != null) ? valueRange.getValues().get(0).toString() : "";
    }
    
    private static List<List<Object>> b(final String s) throws IOException, GeneralSecurityException {
        return b().spreadsheets().values().get(b.d, s).execute().getValues();
    }
    
    public static boolean c(final String s) throws IOException, GeneralSecurityException {
        final ValueRange valueRange = b().spreadsheets().values().get(b.d, "A2:A").execute();
        for (int i = 0; i < valueRange.getValues().size(); ++i) {
            if (valueRange.getValues().get(i).toString().replace("[", "").replace("]", "").equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        b.d = "1_kxn8nNafDEUPpKNZ6ozlUaASlODC_Sf9hIniJvH33E";
    }
}
