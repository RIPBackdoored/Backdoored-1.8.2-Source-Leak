package com.fasterxml.jackson.core.async;

import java.io.*;

public interface ByteArrayFeeder extends NonBlockingInputFeeder
{
    void feedInput(final byte[] p0, final int p1, final int p2) throws IOException;
}
