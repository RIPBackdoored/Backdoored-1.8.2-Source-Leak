package com.google.cloud.storage.spi.v1;

import com.google.api.client.googleapis.json.*;

public interface Callback<T>
{
    void onSuccess(final T p0);
    
    void onFailure(final GoogleJsonError p0);
}
