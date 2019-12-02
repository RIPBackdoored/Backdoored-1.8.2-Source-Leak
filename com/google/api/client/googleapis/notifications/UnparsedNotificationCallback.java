package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public interface UnparsedNotificationCallback extends Serializable
{
    void onNotification(final StoredChannel p0, final UnparsedNotification p1) throws IOException;
}
