package com.google.api.client.googleapis.json;

import java.io.*;
import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;

public class GoogleJsonResponseException extends HttpResponseException
{
    private static final long serialVersionUID = 409811126989994864L;
    private final transient GoogleJsonError details;
    
    public GoogleJsonResponseException(final Builder a1, final GoogleJsonError a2) {
        super(a1);
        this.details = a2;
    }
    
    public final GoogleJsonError getDetails() {
        return this.details;
    }
    
    public static GoogleJsonResponseException from(final JsonFactory v-4, final HttpResponse v-3) {
        final Builder a3 = new Builder(v-3.getStatusCode(), v-3.getStatusMessage(), v-3.getHeaders());
        Preconditions.checkNotNull(v-4);
        GoogleJsonError a4 = null;
        String v0 = null;
        try {
            if (!v-3.isSuccessStatusCode() && HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", v-3.getContentType()) && v-3.getContent() != null) {
                JsonParser v2 = null;
                try {
                    v2 = v-4.createJsonParser(v-3.getContent());
                    JsonToken a1 = v2.getCurrentToken();
                    if (a1 == null) {
                        a1 = v2.nextToken();
                    }
                    if (a1 != null) {
                        v2.skipToKey("error");
                        if (v2.getCurrentToken() != JsonToken.END_OBJECT) {
                            a4 = v2.parseAndClose(GoogleJsonError.class);
                            v0 = a4.toPrettyString();
                        }
                    }
                }
                catch (IOException a2) {
                    a2.printStackTrace();
                }
                finally {
                    if (v2 == null) {
                        v-3.ignore();
                    }
                    else if (a4 == null) {
                        v2.close();
                    }
                }
            }
            else {
                v0 = v-3.parseAsString();
            }
        }
        catch (IOException v3) {
            v3.printStackTrace();
        }
        final StringBuilder v4 = HttpResponseException.computeMessageBuffer(v-3);
        if (!Strings.isNullOrEmpty(v0)) {
            v4.append(StringUtils.LINE_SEPARATOR).append(v0);
            a3.setContent(v0);
        }
        a3.setMessage(v4.toString());
        return new GoogleJsonResponseException(a3, a4);
    }
    
    public static HttpResponse execute(final JsonFactory a1, final HttpRequest a2) throws GoogleJsonResponseException, IOException {
        Preconditions.checkNotNull(a1);
        final boolean v1 = a2.getThrowExceptionOnExecuteError();
        if (v1) {
            a2.setThrowExceptionOnExecuteError(false);
        }
        final HttpResponse v2 = a2.execute();
        a2.setThrowExceptionOnExecuteError(v1);
        if (!v1 || v2.isSuccessStatusCode()) {
            return v2;
        }
        throw from(a1, v2);
    }
}
