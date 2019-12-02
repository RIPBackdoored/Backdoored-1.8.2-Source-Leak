package com.google.api.client.googleapis.testing.notifications;

import com.google.api.client.util.*;
import com.google.api.client.googleapis.notifications.*;
import java.io.*;

@Beta
public class MockUnparsedNotificationCallback implements UnparsedNotificationCallback
{
    private static final long serialVersionUID = 0L;
    private boolean wasCalled;
    
    public boolean wasCalled() {
        return this.wasCalled;
    }
    
    public MockUnparsedNotificationCallback() {
        super();
    }
    
    public void onNotification(final StoredChannel a1, final UnparsedNotification a2) throws IOException {
        this.wasCalled = true;
    }
}
