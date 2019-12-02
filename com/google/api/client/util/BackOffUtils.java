package com.google.api.client.util;

import java.io.*;

@Beta
public final class BackOffUtils
{
    public static boolean next(final Sleeper a1, final BackOff a2) throws InterruptedException, IOException {
        final long v1 = a2.nextBackOffMillis();
        if (v1 == -1L) {
            return false;
        }
        a1.sleep(v1);
        return true;
    }
    
    private BackOffUtils() {
        super();
    }
}
