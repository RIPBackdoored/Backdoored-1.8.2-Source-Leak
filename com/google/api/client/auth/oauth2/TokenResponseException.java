package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.util.*;

public class TokenResponseException extends HttpResponseException
{
    private static final long serialVersionUID = 4020689092957439244L;
    private final transient TokenErrorResponse details;
    
    TokenResponseException(final Builder a1, final TokenErrorResponse a2) {
        super(a1);
        this.details = a2;
    }
    
    public final TokenErrorResponse getDetails() {
        return this.details;
    }
    
    public static TokenResponseException from(final JsonFactory a2, final HttpResponse v1) {
        final Builder v2 = new Builder(v1.getStatusCode(), v1.getStatusMessage(), v1.getHeaders());
        Preconditions.checkNotNull(a2);
        TokenErrorResponse v3 = null;
        String v4 = null;
        final String v5 = v1.getContentType();
        try {
            if (!v1.isSuccessStatusCode() && v5 != null && v1.getContent() != null && HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", v5)) {
                v3 = new JsonObjectParser(a2).parseAndClose(v1.getContent(), v1.getContentCharset(), TokenErrorResponse.class);
                v4 = v3.toPrettyString();
            }
            else {
                v4 = v1.parseAsString();
            }
        }
        catch (IOException a3) {
            a3.printStackTrace();
        }
        final StringBuilder v6 = HttpResponseException.computeMessageBuffer(v1);
        if (!Strings.isNullOrEmpty(v4)) {
            v6.append(StringUtils.LINE_SEPARATOR).append(v4);
            v2.setContent(v4);
        }
        v2.setMessage(v6.toString());
        return new TokenResponseException(v2, v3);
    }
}
