package com.google.api.client.googleapis.batch;

import com.google.api.client.http.*;
import java.io.*;

public interface BatchCallback<T, E>
{
    void onSuccess(final T p0, final HttpHeaders p1) throws IOException;
    
    void onFailure(final E p0, final HttpHeaders p1) throws IOException;
}
