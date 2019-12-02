package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

private static class ComputeGoogleCredential extends GoogleCredential
{
    private static final String TOKEN_SERVER_ENCODED_URL;
    
    ComputeGoogleCredential(final HttpTransport a1, final JsonFactory a2) {
        super(new Builder().setTransport(a1).setJsonFactory(a2).setTokenServerEncodedUrl(ComputeGoogleCredential.TOKEN_SERVER_ENCODED_URL));
    }
    
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        final GenericUrl a1 = new GenericUrl(this.getTokenServerEncodedUrl());
        final HttpRequest buildGetRequest = this.getTransport().createRequestFactory().buildGetRequest(a1);
        final JsonObjectParser parser = new JsonObjectParser(this.getJsonFactory());
        buildGetRequest.setParser(parser);
        buildGetRequest.getHeaders().set("Metadata-Flavor", "Google");
        buildGetRequest.setThrowExceptionOnExecuteError(false);
        final HttpResponse execute = buildGetRequest.execute();
        final int v0 = execute.getStatusCode();
        if (v0 == 200) {
            final InputStream v2 = execute.getContent();
            if (v2 == null) {
                throw new IOException("Empty content from metadata token server request.");
            }
            return parser.parseAndClose(v2, execute.getContentCharset(), TokenResponse.class);
        }
        else {
            if (v0 == 404) {
                throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", v0));
            }
            throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", v0, execute.parseAsString()));
        }
    }
    
    static {
        TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");
    }
}
