package com.google.api.client.googleapis.batch.json;

import com.google.api.client.googleapis.batch.*;
import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.googleapis.json.*;

public abstract class JsonBatchCallback<T> implements BatchCallback<T, GoogleJsonErrorContainer>
{
    public JsonBatchCallback() {
        super();
    }
    
    public final void onFailure(final GoogleJsonErrorContainer a1, final HttpHeaders a2) throws IOException {
        this.onFailure(a1.getError(), a2);
    }
    
    public abstract void onFailure(final GoogleJsonError p0, final HttpHeaders p1) throws IOException;
    
    public /* bridge */ void onFailure(final Object o, final HttpHeaders a2) throws IOException {
        this.onFailure((GoogleJsonErrorContainer)o, a2);
    }
}
