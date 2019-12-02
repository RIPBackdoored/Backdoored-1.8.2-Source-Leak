package com.google.api.client.googleapis.media;

import java.io.*;

public interface MediaHttpDownloaderProgressListener
{
    void progressChanged(final MediaHttpDownloader p0) throws IOException;
}
