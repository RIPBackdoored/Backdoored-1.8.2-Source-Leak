package com.google.api.client.googleapis.notifications;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.nio.charset.*;

@Beta
public abstract class TypedNotificationCallback<T> implements UnparsedNotificationCallback
{
    private static final long serialVersionUID = 1L;
    
    public TypedNotificationCallback() {
        super();
    }
    
    protected abstract void onNotification(final StoredChannel p0, final TypedNotification<T> p1) throws IOException;
    
    protected abstract ObjectParser getObjectParser() throws IOException;
    
    protected abstract Class<T> getDataClass() throws IOException;
    
    public final void onNotification(final StoredChannel v2, final UnparsedNotification v3) throws IOException {
        final TypedNotification<T> v4 = new TypedNotification<T>(v3);
        final String v5 = v3.getContentType();
        if (v5 != null) {
            final Charset a1 = new HttpMediaType(v5).getCharsetParameter();
            final Class<T> a2 = Preconditions.checkNotNull(this.getDataClass());
            v4.setContent(this.getObjectParser().parseAndClose(v3.getContentStream(), a1, a2));
        }
        this.onNotification(v2, v4);
    }
}
