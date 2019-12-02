package com.google.api.client.googleapis.notifications;

import java.util.*;

public final class NotificationUtils
{
    public static String randomUuidString() {
        return UUID.randomUUID().toString();
    }
    
    private NotificationUtils() {
        super();
    }
}
