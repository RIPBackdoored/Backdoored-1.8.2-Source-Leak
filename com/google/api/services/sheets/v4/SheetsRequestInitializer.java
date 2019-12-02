package com.google.api.services.sheets.v4;

import com.google.api.client.googleapis.services.json.*;
import java.io.*;

public class SheetsRequestInitializer extends CommonGoogleJsonClientRequestInitializer
{
    public SheetsRequestInitializer() {
        super();
    }
    
    public SheetsRequestInitializer(final String a1) {
        super(a1);
    }
    
    public SheetsRequestInitializer(final String a1, final String a2) {
        super(a1, a2);
    }
    
    public final void initializeJsonRequest(final AbstractGoogleJsonClientRequest<?> a1) throws IOException {
        super.initializeJsonRequest(a1);
        this.initializeSheetsRequest((SheetsRequest<?>)a1);
    }
    
    protected void initializeSheetsRequest(final SheetsRequest<?> sheetsRequest) throws IOException {
    }
}
