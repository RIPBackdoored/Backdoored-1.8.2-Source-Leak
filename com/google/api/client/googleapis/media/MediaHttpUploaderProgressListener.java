package com.google.api.client.googleapis.media;

import java.io.*;

public interface MediaHttpUploaderProgressListener
{
    void progressChanged(final MediaHttpUploader p0) throws IOException;
}
